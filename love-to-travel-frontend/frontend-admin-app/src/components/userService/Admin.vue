<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="请输入邮箱"/>&nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo">查询</el-button>
        </div>
        <div>
          <el-button class="button" round type="success" @click="openAddDialog">添加</el-button>
        </div>

      </div>
    </template>

    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column label="邮箱" prop="email" width="180"/>
      <el-table-column label="权限" prop="email" width="180">
        管理员
      </el-table-column>>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button link size="small" type="primary" @click="singleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>

    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :background="true"
        :page-sizes="[10, 20, 30, 40]"
        :total="pageInfo.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </el-card>





</template>

<script>
import {getAdmin, deleteAdminInfo, getPageAdminInfo} from "../../apis/userManage/admin";

export default {
  data() {
    return {
      dialogDetailVisible: false,
      dialogFormVisible: false,
      queryStr: "",
      multipleSelection: [],
      tableData: [],
      form: {},
      input: "",
      formLabelWidth: "140px",
      title: "",
      btnName: "",
      pageInfo: {},
      pageSize: 10,
      currentPage: 1,
    };
  },
  methods: {
    // 选择每一页记录数     size
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
      getPageAdminInfo({current: num, size: size}).then((response) => {
        this.pageInfo = response.data;
        this.tableData = this.pageInfo.records;
        console.log(response.data)
        console.log(this.pageInfo.records)
      })
    },
    //-----------------------------------------

    singleDelete(id) {
      console.log(id);
      ElMessageBox.confirm(
          '您确定要删除本条数据吗?',
          'Warning',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            var _this = this;
            deleteAdminInfo(id).then(function (response) {
              console.log(response.data);
              if (response.data == '删除成功') {
                ElMessage({
                  message: '删除成功！',
                  type: 'success'
                })
              } else {
                ElMessage({
                  message: '删除失败！',
                  type: 'warnings'
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
      console.log("singleDelete...");
    },
    multiDelete() {
      console.log("multiDelete().....");
      ElMessageBox.confirm(
          '您确定要删除目前选中的多条数目吗?',
          'Warning',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            var _this = this;
            var num = 0
            this.multipleSelection.forEach(item => {
              var id = item.id
              console.log(id)
              deleteUserInfo(id).then((response) => {
                console.log(response.data);
                if (response.data == "删除成功") {
                  num = num + 1;
                }
              })
            })
            ElMessage({
              type: 'success',
              message: '您已成功删除！',
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: 'Delete canceled',
            })
          })
    },
    //-----------------------------------------

  },

  mounted() {
    this.getPageData(1, 10);
    // getUser().then((response) => {
    //   var _this = this;
    //   _this.tableData = response.data;
    //   _this.queryData = response.data;
    //   console.log(this.tableData);
    // })
  }
}
</script>

<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: auto; //重点，设置成auto
}

.query {
  display: flex;
  justify-content: space-between;
}

.delete {
  margin-right: 20px;
}

.el-descriptions {
  margin-top: 20px;
}

.cell-item {
  display: flex;
  align-items: center;
}

.margin-top {
  margin-top: 20px;
}
</style>
