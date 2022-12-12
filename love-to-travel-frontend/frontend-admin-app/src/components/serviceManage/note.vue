<script setup lang="ts"></script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="Please input" /> &nbsp;&nbsp;
          <el-button class="button" type="primary" round @click="queryInfo">查询</el-button>
        </div>
        <div>
          <el-button class="button" type="success" round @click="openAddDialog">添加</el-button>
          <el-button class="button" type="warning" round @click="multiDelete">删除</el-button>
        </div>



      </div>
    </template>
    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />

      <el-table-column prop="data.id" label="游记id" width="100" />
      <el-table-column prop="data.title" label="游记标题" width="100" />
      <el-table-column prop="data.userId" label="用户ID" width="100" />
      <el-table-column prop="data.city" label="所属城市" width="150" />
      <el-table-column prop="data.content" label="游记内容" width="100" />
      <el-table-column prop="data.url" label="图片地址" width="100" />
      <el-table-column prop="data.comment" label="评论量" width="100" />
      <el-table-column prop="data.like" label="点赞量" width="100" />
      <el-table-column prop="data.view" label="浏览量" width="100" />
      <el-table-column prop="data.trip" label="标签" width="100" />
      <el-table-column prop="data.planId" label="行程" width="100" />
      <el-table-column fixed="right" label="Operations" width="120">
        <template #default>
          <el-button link type="primary" size="small" @click="singleDelete">删除</el-button
          >
          <el-button link type="primary" size="small" @click="openUpdateDialog">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import {ElMessage} from 'element-plus'
export default {
  data(){
    return{
      dialogFormVisible:false, //对话框是否显示
      queryStr:"",  //查询条件
      multipleSelection:[], //多选删除
      tableData: [], //学生信息数据
      form:{},   //对话框表单数据
      formLabelWidth:"140px",  //对话框label宽度
      title:"",  //对话框标题
      btnName:""  //对话框按钮文字
    }
  }
  // methods:{
  //   openAddDialog(){
  //     this.btnName = "添加"
  //     this.title = "添加商品信息"
  //     this.dialogFormVisible = true
  //     console.log("openAddDialog")
  //   },
  //   openUpdateDialog(){
  //     this.btnName = "修改"
  //     this.title = "修改商品信息"
  //     this.dialogFormVisible = true
  //     console.log("openUpdateDialog")
  //   },
  //   addShop(){
  //     var _this = this;
  //     //this.form.stu_interest = this.form.stu_interest.join(',')
  //     this.$http.post("/shop/shops",this.form).then(function(response){
  //       console.log(response.data);
  //       if(response.data==1){
  //         ElMessage({
  //           message: '商品信息添加成功',
  //           type: 'success',
  //         })
  //       }else {
  //         ElMessage({
  //           message: '商品信息添加失败',
  //           type: 'warning',
  //         })
  //       }
  //     })
  //   },
  //
  //   //.......
  //   btnAddUpdate(){
  //     if(this.btnName=="修改"){
  //
  //       console.log("修改。。。")
  //
  //     }
  //     if(this.btnName=="添加"){
  //       this.addShop()
  //
  //       console.log("添加。。。")
  //       console.log(this.form)
  //     }
  //     this.dialogFormVisible = false
  //   },
  //   singleDelete(){
  //     console.log("singleDelete()")
  //   },
  //   multiDelete(){
  //     console.log("multiDelete()")
  //   },
  //   queryInfo(){
  //     console.log("queryInfo...");
  //   },
  //   handleSelectionChange(val){
  //     this.multipleSelection = val
  //     console.log(this.multipleSelection)
  //   }
  // },
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
.card-header{
  display: flex;
  justify-content: space-between;
}
.query{
  display: flex;
  justify-content: space-between;
}
</style>

<style lang="scss" scoped></style>
