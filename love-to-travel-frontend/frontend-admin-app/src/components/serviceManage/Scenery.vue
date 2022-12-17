<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="请输入景点名称"/> &nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo">查询</el-button>
        </div>
        <div style="margin-top: 10px">
          <el-button class="button" round type="success" @click="openAddDialog">添加</el-button>
          <el-button class="button" round type="warning" @click="multiDelete">删除</el-button>
        </div>

      </div>
    </template>

    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>

      <el-table-column label="景区名称" prop="name" width="100"/>
      <el-table-column label="评分" prop="score" width="80"/>
      <el-table-column label="门票" prop="ticket" width="80"/>
      <el-table-column label="开放时间" prop="opening" width="150"/>
      <el-table-column label="经度" prop="lng" width="100"/>
      <el-table-column label="纬度" prop="lat" width="100"/>
      <el-table-column label="等级" prop="level" width="80"/>
      <el-table-column label="地址" prop="address"/>
      <el-table-column label="电话" prop="tele" width="150"/>

      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button link size="small" type="primary" @click="openShowDialog(scope.row)">详情</el-button>
          <el-button link size="small" type="primary" @click="singleDelete(scope.row)">删除</el-button>
          <el-button link size="small" type="primary" @click="openUpdateDialog(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :background="true"
        :page-sizes="[10, 20, 50]"
        :total="pageInfo.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </el-card>

  <!-- 对话框：添加修改功能 -->
  <el-dialog v-model="dialogFormVisible" :title="title">
    <el-form :model="form">
      <el-form-item :label-width="formLabelWidth" label="景区名称">
        <el-input v-model="form.name" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="景区简介">
        <el-input v-model="form.introduction" type="textarea" :autosize="{ minRows: 8, maxRows: 16 }"
                  autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="景区门票">
        <el-input v-model="form.ticket" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="开放时间">
        <el-input v-model="form.opening" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="经度">
        <el-input v-model="form.lng" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="纬度">
        <el-input v-model="form.lat" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="景区等级">
        <el-input v-model="form.level" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="地址">
        <el-input v-model="form.address" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="适宜季节">
        <el-input v-model="form.season" type="textarea" :autosize="{ minRows: 2, maxRows: 8 }" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="温馨提醒">
        <el-input v-model="form.tips" type="textarea" :autosize="{ minRows: 2, maxRows: 8 }" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="所属城市">
        <el-input v-model="form.cityName" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="电话">
        <el-input v-model="form.tele" autocomplete="off"/>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="btnAddUpdate">
          {{ btnName }}
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="showVisible" :title="title">
    <el-form :model="showVisibleForm">

    </el-form>

    <el-descriptions
        :title="showVisibleForm.name"
        direction="vertical"
        :column="4"
        :size="size"
        border
    >
      <el-descriptions-item label="地区" width="100">{{ showVisibleForm.cityName }}</el-descriptions-item>
      <el-descriptions-item label="景区等级">{{ showVisibleForm.level }}</el-descriptions-item>

      <el-descriptions-item label="地址" :span="2">{{ showVisibleForm.address }}</el-descriptions-item>
      <el-descriptions-item label="评分">
        <el-tag size="small">{{ showVisibleForm.score }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="经度">{{ showVisibleForm.lng }}</el-descriptions-item>
      <el-descriptions-item label="维度">{{ showVisibleForm.lat }}</el-descriptions-item>
      <el-descriptions-item label="票价">{{ showVisibleForm.ticket }}</el-descriptions-item>
      <el-descriptions-item label="开放时间" :span="4">{{ showVisibleForm.opening }}</el-descriptions-item>

      <el-descriptions-item label="简介" :span="4">
        {{ showVisibleForm.introduction }}
      </el-descriptions-item>
      <el-descriptions-item label="适宜时节" :span="4">
        {{ showVisibleForm.season }}
      </el-descriptions-item>
      <el-descriptions-item label="小贴士" :span="4">
        {{ showVisibleForm.tips }}
      </el-descriptions-item>
    </el-descriptions>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="showVisible=false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script>
import {
  deleteSceneryInfo,
  insertProvinceInfo,
  postSceneryInfo,
  updateSceneryInfo,
  getSceneryByStr2
} from '@/apis/serviceManage/scenery.js'
// import {ElMessage, ElMessageBox} from "element-plus";


export default {
  data() {
    return {
      dialogFormVisible: false, //对话框是否显示
      queryStr: "",  //查询条件
      multipleSelection: [], //多选删除
      tableData: [], //省份信息数据
      queryData: [],//保存查询数据
      form: {},   //对话框表单数据
      pageInfo: {},
      formLabelWidth: "140px",  //对话框label宽度
      title: "",  //对话框标题
      btnName: "",  //对话框按钮文字
      pageSize: 10,
      currentPage: 1,
      showVisible: false
    }
  },
  mounted() {
    this.postSceneryInfo(1, 10);

  },
  methods: {
    postSceneryInfo(num, size) {
      num = parseInt(num)
      size = parseInt(size)
      console.log(123)
      postSceneryInfo({pageNum: num, pageSize: size}).then((response) => {
        console.log(response);
        this.pageInfo = response.data;
        this.tableData = this.pageInfo.records;
        console.log(response.data)
        console.log(this.pageInfo.records)
      });
    },


    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.getPageData(this.currentPage, this.pageSize);
      console.log("size:", pageSize);
    },
    //切换页号得到当前页码   current
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.getPageData(this.currentPage, this.pageSize);
      console.log("num:", pageNum);
    },
    getPageData(num, size) {
      num = parseInt(num)
      size = parseInt(size)
      postSceneryInfo({pageNum: num, pageSize: size}).then((response) => {
        this.pageInfo = response.data;
        this.tableData = this.pageInfo.records;
        console.log(response.data)
        console.log(this.pageInfo.records)
      })
    },
    openShowDialog(row) {
      this.showVisible = true;
      this.title = row.title
      this.showVisibleForm = row
    },
    openAddDialog() {
      this.btnName = "添加"
      this.title = "添加景区信息"
      this.dialogFormVisible = true
      this.form = {}
      console.log("openAddDialog")
    },

    openUpdateDialog(row) {
      this.btnName = "修改"
      this.title = "修改景区信息"
      this.dialogFormVisible = true
      console.log(row);
      this.form = row  //得到要修改的数据，并回显到对话框
    },

    updateScenery() {
      console.log(this.form)
      var _this = this;
      //this.form.stu_interest = this.form.stu_interest.join(',')
      updateSceneryInfo(_this.form).then((response) => {
        var _this = this;
        console.log(response.data === 1);
        if (response.data !== 1) {
          ElMessage({
            message: '景区信息修改成功',
            type: 'success',
          })
        } else {
          ElMessage({
            message: '景区信息修改失败',
            type: 'warning',
          })
        }
      })
    },

    addScenery() {
      var _this = this;
      //this.form.stu_interest = this.form.stu_interest.join(',')
      insertProvinceInfo(_this.form).then((response) => {
        var _this = this;
        console.log(response === 1);
        if (response.data !== 1) {
          ElMessage({
            message: '景区信息添加成功',
            type: 'success',
          })
          location.reload()
          _this.$router.go(0)
        } else {
          ElMessage({
            message: '景区信息添加失败',
            type: 'warning',
          })
        }
      })
    },

    //.......
    btnAddUpdate() {
      if (this.btnName === "修改") {
        console.log("修改。。。")
        this.updateScenery()
      }
      if (this.btnName === "添加") {
        this.addScenery()

        console.log("添加。。。")
        console.log(this.form)
      }
      this.dialogFormVisible = false
    },
    singleDelete(row) {
      console.log(row.id)
      ElMessageBox.confirm(
          '您确定要删除这条记录吗?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
            deleteSceneryInfo(row.id).then((response) => {
              var _this = this;
              console.log(response.data);
              console.log(response);
              if (response.code === 0) {
                ElMessage({
                  type: 'success',
                  message: '删除成功',
                })

                location.reload()
                _this.$router.go(0)

              } else {
                ElMessage({
                  type: 'warning',
                  message: '删除失败',
                })
              }
            })

          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: 'Delete canceled',
            })
          })
      // this.postSceneryInfo(1, 10);
    },
    multiDelete() {
      console.log("multiDelete()")
    },
    queryInfo() {
      if (this.queryStr.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.name).match(this.queryStr.trim()))
      } else {
        this.tableData = this.queryData
      }


      console.log("queryInfo...");
    },

    handleSelectionChange(val) {
      this.multipleSelection = val
      console.log(this.multipleSelection)
    }

  },

}
</script>


<style scoped>
.query {
  display: flex;
  justify-content: space-between;
}

</style>