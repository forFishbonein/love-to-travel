<script setup lang="ts"></script>

<script>
import {deleteSceneryInfo, getSceneryInfo, postSceneryInfo, updateSceneryInfo} from '@/apis/serviceManage/scenery.js'
// import {ElMessage, ElMessageBox} from "element-plus";


export default {
  data() {
    return {
      dialogFormVisible:false, //对话框是否显示
      queryStr:"",  //查询条件
      multipleSelection:[], //多选删除
      tableData: [], //省份信息数据
      queryData: [],//保存查询数据
      form:{},   //对话框表单数据
      formLabelWidth:"140px",  //对话框label宽度
      title:"",  //对话框标题
      btnName:""  //对话框按钮文字
    }
  },
  mounted() {
    getSceneryInfo().then((response) => {
      var _this = this;
      console.log(response.data);
      _this.tableData = response.data;
      _this.queryData = response.data;
    });
  },
  methods:{
    handleSizeChange(pageSize){
      console.log("size:",pageSize);
    },
    handleCurrentChange(pageNum){
      console.log("num:",pageNum);
    },
    getPageData(num,size){

    },
    openAddDialog(){
      this.btnName = "添加"
      this.title = "添加景区信息"
      this.dialogFormVisible = true
      console.log("openAddDialog")
    },
    openUpdateDialog(row){
      this.btnName = "修改"
      this.title = "修改景区信息"
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

    addScenery(){
      var _this = this;
      //this.form.stu_interest = this.form.stu_interest.join(',')
      postSceneryInfo(_this.form).then((response) => {
        var _this = this;
        console.log(response===1);
        if(response.data!==1){
          ElMessage({
            message: '景区信息添加成功',
            type: 'success',
          })
        }else {
          ElMessage({
            message: '景区信息添加失败',
            type: 'warning',
          })
        }
      })
    },

    //.......
    btnAddUpdate(){
      if(this.btnName==="修改"){
        console.log("修改。。。")
        this.updateScenery()
      }
      if(this.btnName==="添加"){
        this.addScenery()

        console.log("添加。。。")
        console.log(this.form)
      }
      this.dialogFormVisible = false
    },
    singleDelete(row){
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
            deleteSceneryInfo(row.id).then((response) => {
              var _this = this;
              console.log(response.data);
              if(response.data==="删除成功"){
                ElMessage({
                  type: 'success',
                  message: '删除成功',
                })
              }else {
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
    multiDelete(){
      console.log("multiDelete()")
    },
    queryInfo(){
      if(this.queryStr.trim().length>0){
        this.tableData=this.tableData.filter(item =>(item.name).match(this.queryStr.trim()))
      }else {
        this.tableData=this.queryData
      }


      console.log("queryInfo...");
    },
    handleSelectionChange(val){
      this.multipleSelection = val
      console.log(this.multipleSelection)
    }
  },

}
</script>



<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="请输入查询景点" /> &nbsp;&nbsp;
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

      <el-table-column prop="id" label="景区id" width="50" />
      <el-table-column prop="name" label="景区名称" width="80" />
      <el-table-column prop="introduction" label="景区简介" width="600" />
      <el-table-column prop="score" label="景区评分" width="50" />
      <el-table-column prop="ticket" label="景区门票" width="60" />
      <el-table-column prop="opening" label="开放时间" width="150" />
      <el-table-column prop="lng" label="经度" width="80" />
      <el-table-column prop="lat" label="纬度" width="80" />
      <el-table-column prop="level" label="景区等级" width="50" />
      <el-table-column prop="address" label="地址" width="80" />
      <el-table-column prop="season" label="适宜季节" width="150" />
      <el-table-column prop="tips" label="温馨提醒" width="200" />
      <el-table-column prop="city_id" label="所属城市id" width="80" />
      <el-table-column prop="url" label="图片url" width="150" />
      <el-table-column prop="tele" label="电话" width="100" />

      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="singleDelete(scope.row)"
          >删除</el-button
          >
          <el-button link type="primary" size="small" @click="openUpdateDialog(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage2"
        :page-sizes="[3, 4, 5, 10]"
        :background="true"
        :page-size="100"
        layout="total, sizes, prev, pager, next, jumper"
        :total="1000">
    </el-pagination>
  </el-card>

  <!-- 对话框：添加修改功能 -->
  <el-dialog v-model="dialogFormVisible" :title="title">
    <el-form :model="form">
      <el-form-item label="景区名称" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="景区简介" :label-width="formLabelWidth">
        <el-input v-model="form.introduction" autocomplete="off" />
      </el-form-item>
      <el-form-item label="景区评分" :label-width="formLabelWidth">
        <el-input v-model="form.score" autocomplete="off" />
      </el-form-item>
      <el-form-item label="景区门票" :label-width="formLabelWidth">
        <el-input v-model="form.ticket" autocomplete="off" />
      </el-form-item>
      <el-form-item label="开放时间" :label-width="formLabelWidth">
        <el-input v-model="form.opening" autocomplete="off" />
      </el-form-item>
      <el-form-item label="经度" :label-width="formLabelWidth">
        <el-input v-model="form.lng" autocomplete="off" />
      </el-form-item>
      <el-form-item label="纬度" :label-width="formLabelWidth">
        <el-input v-model="form.lat" autocomplete="off" />
      </el-form-item>
      <el-form-item label="景区等级" :label-width="formLabelWidth">
        <el-input v-model="form.level" autocomplete="off" />
      </el-form-item>
      <el-form-item label="地址" :label-width="formLabelWidth">
        <el-input v-model="form.address" autocomplete="off" />
      </el-form-item>
      <el-form-item label="适宜季节" :label-width="formLabelWidth">
        <el-input v-model="form.season" autocomplete="off" />
      </el-form-item>
      <el-form-item label="温馨提醒" :label-width="formLabelWidth">
        <el-input v-model="form.tips" autocomplete="off" />
      </el-form-item>
      <el-form-item label="所属城市id" :label-width="formLabelWidth">
        <el-input v-model="form.address" autocomplete="off" />
      </el-form-item>
      <el-form-item label="电话" :label-width="formLabelWidth">
        <el-input v-model="form.tele" autocomplete="off" />
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


<style scoped>

</style>