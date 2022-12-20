<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="请输入产品信息"/> &nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo">查询</el-button>
        </div>

<!--        <div class="query">-->
<!--          <el-input v-model="input" placeholder="Please input"/> &nbsp;&nbsp;-->
<!--          <el-button class="button" round type="primary" @click="queryInfo_n">游记ID查询</el-button>-->
<!--        </div>-->

        <div>
          <el-button class="button" round type="success" @click="openAddDialog">添加</el-button>
          <el-button class="button" round type="warning" @click="multiDelete">删除</el-button>
        </div>

      </div>
    </template>
    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="产品名称" prop="name" width="200"/>
      <el-table-column label="产品简介" prop="introduction" /><!--      <el-table-column :show-overflow-tooltip="true" label="内容" prop="content" width="100"/>-->
      <el-table-column label="产品价格" prop="price" width="100"/>

      <el-table-column fixed="right" label="操作" prop="oppo" width="120">
        <template #default="scope">
          <el-button link size="small" type="primary" @click="singleDelete(scope.row)">删除</el-button>
          <el-button link size="small" type="primary" @click="openUpdateDialog(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :background="true"
        :page-sizes="[3, 4, 5, 10]"
        :total="pageInfo.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />

  </el-card>
  <!-- 对话框：添加修改功能 -->
  <el-dialog v-model="dialogFormVisible" :title="title">
    <el-form :model="form">
<!--      <el-form-item :label-width="formLabelWidth" label="产品id">-->
<!--        <el-input v-model="form.id" autocomplete="off"/>-->
<!--      </el-form-item>-->
      <el-form-item :label-width="formLabelWidth" label="产品名称">
        <el-input v-model="form.name" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="产品简介">
        <el-input v-model="form.introduction" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="行程id">
        <el-input v-model="form.plan_id" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="产品价格">
        <el-input v-model="form.price" autocomplete="off"/>
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


</template>


<script lang="ts">

import {getProductInfo,addProductInfo,deleteProductInfo,updateProductInfo,pageProduct} from "../../apis/serviceManage/product.js";

import {ElMessage,ElMessageBox} from 'element-plus';

export default {
  data() {
    return {
      dialogFormVisible: false, //对话框是否显示
      queryStr: "",  //查询条件
      input: "",
      multipleSelection: [], //多选删除
      tableData: [], //游记信息数据
      pageInfo: {},  //分页信息对象
      pageSize: 10,  //当前页条数
      currentPage: 1, //当前页号
      form: {},   //对话框表单数据
      formLabelWidth: "140px",  //对话框label宽度
      title: "",  //对话框标题
      btnName: ""  //对话框按钮文字
    }
  },
  mounted() {
    getProductInfo().then((response) => {
      var _this = this;
      console.log(response.data);
      _this.tableData = response.data;

    })
    this.getPageData(1, 10)
  },

  methods: {
    postSceneryInfo(num, size) {
      num = parseInt(num)
      size = parseInt(size)
      console.log(123)
      pageProduct({pageNum: num, pageSize: size}).then((response) => {
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
      pageProduct({pageNum: num, pageSize: size}).then((response) => {
        this.pageInfo = response.data;
        this.tableData = this.pageInfo.records;
        console.log(response.data)
        console.log(this.pageInfo.records)
      })
    },
    openAddDialog() {
      this.btnName = "添加"
      this.title = "添加产品信息"
      this.dialogFormVisible = true
      console.log("openAddDialog")
    },
    openUpdateDialog(row) {
      this.btnName = "修改"
      this.title = "修改产品信息"
      this.dialogFormVisible = true
      console.log(row)
      this.form = row
    },
    updateNote() {
      console.log(this.form)
      var _this = this;
      //this.form.stu_interest = this.form.stu_interest.join(',')
      updateProductInfo(this.form).then((response) => {
        console.log(response.data);
        if (response.data == "修改成功") {
          ElMessage({
            message: '商品信息修改成功',
            type: 'success',
          })
        } else {
          ElMessage({
            message: '商品信息修改失败',
            type: 'warning',
          })
        }
      })
    },
    addShop() {
      var _this = this;
      //this.form.stu_interest = this.form.stu_interest.join(',')
      addProductInfo(this.form).then((response) => {
        console.log(response.data);
        if (response.data == "新增成功") {
          ElMessage({
            message: '产品信息添加成功',
            type: 'success',
          })
        } else {
          ElMessage({
            message: '产品信息添加失败',
            type: 'warning',
          })
        }
      })
    },
    //
    //   //.......

    btnAddUpdate() {
      if (this.btnName == "修改") {
        this.updateNote()
        // console.log("修改。。。")
      }
      if (this.btnName == "添加") {
        this.addShop()
        console.log("添加。。。")
        console.log(this.form)
      }
      this.dialogFormVisible = false
    },
    singleDelete(row) {
      console.log(row.id)
      ElMessageBox.confirm(
          '您确定要删除这条数目吗?',
          'Warning',
          {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      )
          .then(() => {
            deleteProductInfo(row.id).then((response) => {
              var _this = this;
              console.log(response.data);
              if (response.data == "删除成功") {
                ElMessage({
                  type: 'success',
                  message: '删除成功',
                })
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
    },

    handleSelectionChange(val) {  //多行选择
      this.multipleSelection = val
      console.log(this.multipleSelection);
    },
    multiDelete() {
      console.log("multiDelete()")
      ElMessageBox.confirm(
          '您确定要删除目前选中的多条数目吗?',
          'Warning',
          {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      )
          .then(() => {
            var _this = this;
            var num = 0
            this.multipleSelection.forEach(item => {
              var id = item.id
              console.log(id)
              deleteProductInfo(id).then((response) => {
                console.log(response.data);
                if (response.data == "删除成功") {
                  num += 1
                }
              })
            })
            ElMessage({
              type: 'success',
              message: '成功删除' + num + '条记录',
            })
          })

          .catch(() => {
            ElMessage({
              type: 'info',
              message: 'Delete canceled',
            })
          })
    },
    queryInfo_u() {
      if (this.queryStr.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.userId).match(this.queryStr.trim()))
      }
      console.log("queryInfo...");
    },
    queryInfo_n() {
      if (this.input.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.id).match(this.input.trim()))
      }
      console.log("queryInfo...");
    }
  },

  // mounted(){
  //   var _this = this;
  //   this.$http.get("/shop/shops").then(function(response){
  //     console.log(response.data);
  //     _this.tableData = response.data;
  //   })
  // }

}
</script>

<style>
.card-header {
  display: flex;
  justify-content: space-between;
}

.query {
  display: flex;
  justify-content: space-between;
}

.product-buyer-name {
  max-width: 100px;
  max-height: 30px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>

<style lang="scss" scoped></style>
