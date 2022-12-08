<template>
  <div class="layout-container">
    <div class="layout-container-form flex space-between">
    </div>
    <div class="layout-container-table">
      <Table
          ref="table"
          v-model:page="page"
          v-loading="loading"
          :showSelection="false"
          :data="tableData"
          @getTableData="getTableData"
          @selection-change="handleSelectionChange"
      >
        <el-table-column prop="email" label="邮箱" align="center" />
        <el-table-column prop="name" label="用户名" align="center" />
        <el-table-column prop="osName" label="操作系统" align="center" />
        <el-table-column prop="browserName" label="浏览器" align="center" />
        <el-table-column prop="ipAddress" label="IP地址" align="center" />
        <el-table-column prop="createTime" label="注册时间" align="center" />
      </Table>
      <Layer :layer="layer" @getTableData="getTableData" v-if="layer.show" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, reactive } from "vue";
import { Page } from "@/components/table/type";
import { getData, del, updateStatus } from "@/api/system/user";
import { LayerInterface } from "@/components/layer/index.vue";
import { ElMessage } from "element-plus";
import Table from "@/components/table/index.vue";
import Layer from "./layer.vue";
import { Plus, Delete, Search } from '@element-plus/icons'
export default defineComponent({
  components: {
    Table,
    Layer,
  },
  setup() {
    // 存储搜索用的数据
    const query = reactive({
      input: "",
    });
    // 弹窗控制器
    const layer: LayerInterface = reactive({
      show: false,
      title: "新增",
      showButton: true,
    });
    // 分页参数, 供table使用
    const page: Page = reactive({
      index: 1,
      size: 20,
      total: 0,
    });
    const loading = ref(true);
    const tableData = ref([]);
    const chooseData = ref([]);
    const handleSelectionChange = (val: []) => {
      chooseData.value = val;
    };
    // 获取表格数据
    // params <init> Boolean ，默认为false，用于判断是否需要初始化分页
    const getTableData = (init: Boolean) => {
      loading.value = true
      if (init) {
        page.index = 1
      }
      let params = {
        page: page.index,
        pageSize: page.size,
        ...query
      }
      getData(params)
          .then((res) => {
            let data = res.data.list
            data.forEach((d: any) => {
              d.loading = false
            })
            tableData.value = data
            page.total = Number(res.data.pager.total);
          })
          .catch((error) => {
            tableData.value = [];
            page.index = 1;
            page.total = 0;
          })
          .finally(() => {
            loading.value = false;
          });
    }
    // 删除功能
    const handleDel = (data: object[]) => {
      let params = {
        ids: data
            .map((e: any) => {
              return e.id;
            })
            .join(","),
      };
      del(params).then((res) => {
        ElMessage({
          type: "success",
          message: "删除成功",
        });
        getTableData(tableData.value.length === 1 ? true : false);
      });
    }
    // 新增弹窗功能
    const handleAdd = () => {
      layer.title = "新增数据";
      layer.show = true;
      delete layer.row;
    }
    // 编辑弹窗功能
    const handleEdit = (row: any) => {
      layer.title = "编辑数据";
      layer.row = row;
      layer.show = true;
    }
    // 状态编辑功能
    const handleUpdateStatus = (row: any) => {
      if (!row.id) {
        return
      }
      row.loading = true
      let params = {
        id: row.id,
        status: row.status
      }
      updateStatus(params)
          .then(res => {
            ElMessage({
              type: 'success',
              message: '状态变更成功'
            })
          })
          .catch(err => {
            ElMessage({
              type: 'error',
              message: '状态变更失败'
            })
          })
          .finally(() => {
            row.loading = false
          })
    }
    getTableData(true)
    return {
      Plus,
      Delete,
      Search,
      query,
      tableData,
      chooseData,
      loading,
      page,
      layer,
      handleSelectionChange,
      getTableData,
      handleDel,
      handleAdd,
      handleEdit,
      handleUpdateStatus
    };
  }
});
</script>

<style lang="scss" scoped>
.statusName {
  margin-right: 10px;
}
</style>
