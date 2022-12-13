<script setup lang="ts"></script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="请输入查询省份" /> &nbsp;&nbsp;
          <el-button class="button" type="primary" round @click="queryInfo">查询</el-button>
        </div>


      </div>
    </template>
    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />

      <el-table-column prop="provinceId" label="省份id" width="100" />
      <el-table-column prop="provinceName" label="省份名称" width="100" />
      <el-table-column prop="url" label="省份图片url" width="200" />
      <el-table-column prop="introduction" label="省份简介" width="150" />
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="openUpdateDialog(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <el-dialog v-model="dialogFormVisible" :title="title">
    <el-form :model="form">
      <el-form-item label="省份id" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="省份名称" :label-width="formLabelWidth">
        <el-input v-model="form.introduction" autocomplete="off" />
      </el-form-item>
      <el-form-item label="省份图片url" :label-width="formLabelWidth">
        <el-input v-model="form.score" autocomplete="off" />
      </el-form-item>
      <el-form-item label="省份简介" :label-width="formLabelWidth">
        <el-input v-model="form.ticket" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="btnAddUpdate">
          {{btnName}}
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
// import {ElMessage} from 'element-plus'
import {getProvinceInfo} from '@/apis/serviceManage/province.js'
// import {updateSceneryInfo} from "@apis/serviceManage/scenery.js";


export default {

  data() {
    return {
      dialogFormVisible:false, //对话框是否显示
      queryStr: "",  //查询条件
      tableData: [], //学生信息数据
      form:{},   //对话框表单数据
      formLabelWidth:"140px",  //对话框label宽度
      title:"",  //对话框标题
      btnName:""  //对话框按钮文字
    }
  },
  mounted() {
    getProvinceInfo().then((response) => {
      var _this = this;
      console.log(response.data);
      _this.tableData = response.data;
      _this.queryData = response.data;
    });
  },
  methods:{
    openUpdateDialog(row){
      this.btnName = "修改"
      this.title = "修改省份信息"
      this.dialogFormVisible = true
      console.log(row);
      this.form=row  //得到要修改的数据，并回显到对话框

    },

    updateScenery(){
      console.log(this.form)
      var _this = this;
      //this.form.stu_interest = this.form.stu_interest.join(',')
      updateSceneryInfo(_this.form).then((response) => {
        var _this = this;
        console.log(response.data===1);
        if(response.data!==1){
          ElMessage({
            message: '景区信息修改成功',
            type: 'success',
          })
        }else {
          ElMessage({
            message: '景区信息修改失败',
            type: 'warning',
          })
        }
      })
    },

  //.......
  btnAddUpdate(){
    console.log(this.form)
    this.dialogFormVisible = false
  },
  queryInfo(){
    // console.log("queryInfo...");
    if(this.queryStr.trim().length>0){
      this.tableData=this.tableData.filter(item =>(item.provinceName).match(this.queryStr.trim()))
    }else {
      this.tableData=this.queryData
    }
  },
  handleSelectionChange(val){
    this.multipleSelection = val
    console.log(this.multipleSelection)
  }
}
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
