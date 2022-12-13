<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="Please input" /> &nbsp;&nbsp;
          <el-button class="button" type="primary" round @click="queryInfo_u">用户ID查询</el-button>
        </div>

        <div class="query">
          <el-input v-model="input" placeholder="Please input" /> &nbsp;&nbsp;
          <el-button class="button" type="primary" round @click="queryInfo_n">游记ID查询</el-button>
        </div>

        <div>
          <el-button class="button" type="success" round @click="openAddDialog">添加</el-button>
          <el-button class="button" type="warning" round @click="multiDelete">删除</el-button>
        </div>

      </div>
    </template>
    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="游记id" width="100" />
      <el-table-column prop="title" label="游记标题" width="100" />
      <el-table-column prop="userId" label="用户ID" width="100" />
      <el-table-column prop="city" label="所属城市" width="150" />
      <el-table-column prop="content" label="游记内容" width="100" :show-overflow-tooltip="true" />
      <el-table-column prop="url" label="图片地址" width="100" />
      <el-table-column prop="comment" label="评论量" width="100" />
      <el-table-column prop="like" label="点赞量" width="100" />
      <el-table-column prop="view" label="浏览量" width="100" />
      <el-table-column prop="trip" label="标签" width="100" />
      <el-table-column prop="planId" label="行程" width="100" />
      <el-table-column prop="deleted" label="逻辑删除" width="100" />

      <el-table-column fixed="right" prop="oppo" label="Operations" width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="singleDelete(scope.row)">删除</el-button>
          <el-button link type="primary" size="small" @click="openUpdateDialog(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <!-- 对话框：添加修改功能 -->
  <el-dialog v-model="dialogFormVisible" :title="title">
    <el-form :model="form">
      <el-form-item label="游记id" :label-width="formLabelWidth">
        <el-input v-model="form.id" autocomplete="off" />
      </el-form-item>
      <el-form-item label="游记标题" :label-width="formLabelWidth">
        <el-input v-model="form.title" autocomplete="off" />
      </el-form-item>
      <el-form-item label="用户ID" :label-width="formLabelWidth">
        <el-input v-model="form.userId" autocomplete="off" />
      </el-form-item>
      <el-form-item label="所属城市" :label-width="formLabelWidth">
        <el-input v-model="form.city" autocomplete="off" />
      </el-form-item>
      <el-form-item label="游记内容" :label-width="formLabelWidth" >
        <el-input class="product-buyer-name" v-model="form.content" autocomplete="off" />
      </el-form-item>
      <el-form-item label="图片地址" :label-width="formLabelWidth">
        <el-input v-model="form.url" autocomplete="off" />
      </el-form-item>
      <el-form-item label="评论量" :label-width="formLabelWidth">
        <el-input v-model="form.comment" autocomplete="off" />
      </el-form-item>
      <el-form-item label="点赞量" :label-width="formLabelWidth">
        <el-input v-model="form.like" autocomplete="off" />
      </el-form-item>
      <el-form-item label="浏览量" :label-width="formLabelWidth">
        <el-input v-model="form.view" autocomplete="off" />
      </el-form-item>
      <el-form-item label="标签" :label-width="formLabelWidth">
        <el-input v-model="form.trip" autocomplete="off" />
      </el-form-item>
      <el-form-item label="行程" :label-width="formLabelWidth">
        <el-input v-model="form.planId" autocomplete="off" />
      </el-form-item>
      <el-form-item label="逻辑删除" :label-width="formLabelWidth">
        <el-input v-model="form.deleted" autocomplete="off" />
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


<script lang="ts">
import {getNote} from "../../apis/serviceManage/note.js";
import {delNote} from "../../apis/serviceManage/delnote.js";
import {addNote} from "../../apis/serviceManage/addnote.js";
// import {ElMessage,ElMessageBox} from 'element-plus';
export default {
  data(){
    return{
      dialogFormVisible:false, //对话框是否显示
      queryStr:"",  //查询条件
      input:"",
      multipleSelection:[], //多选删除
      tableData: [], //游记信息数据
      form:{},   //对话框表单数据
      formLabelWidth:"140px",  //对话框label宽度
      title:"",  //对话框标题
      btnName:""  //对话框按钮文字
    }
  },
  mounted(){
    getNote().then((response) => {
      var _this = this;
      console.log(response.data);
      _this.tableData = response.data;

    })
  },

  methods:{
    openAddDialog(){
      this.btnName = "添加"
      this.title = "添加游记信息"
      this.dialogFormVisible = true
      console.log("openAddDialog")
    },
    openUpdateDialog(){
      this.btnName = "修改"
      this.title = "修改游记信息"
      this.dialogFormVisible = true
      console.log("openUpdateDialog")
    },
      addShop(){
        var _this = this;
        //this.form.stu_interest = this.form.stu_interest.join(',')
        addNote(this.form).then((response) => {
          console.log(response.data);
          if(response.data=="新增成功"){
            ElMessage({
              message: '商品信息添加成功',
              type: 'success',
            })
          }else {
            ElMessage({
              message: '商品信息添加失败',
              type: 'warning',
            })
          }
        })
      },
    //
    //   //.......

      btnAddUpdate(){
        if(this.btnName=="修改"){
          console.log("修改。。。")
        }
        if(this.btnName=="添加"){
          this.addShop()
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
            delNote(row.id).then((response) => {
              var _this = this;
              console.log(response.data);
              if(response.data=="删除成功"){
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

  handleSelectionChange(val){  //多行选择
    this.multipleSelection = val
    console.log(this.multipleSelection);
  },
  multiDelete(){
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
          var num=0
          this.multipleSelection.forEach(item=>{
            var id = item.id
            console.log(id)
            delNote(id).then((response) => {
              console.log(response.data);
              if(response.data=="删除成功"){
                num+=1
              }
            })
          })
          ElMessage({
            type: 'success',
            message: '成功删除'+num+'条记录',
          })
        })

        .catch(() => {
          ElMessage({
            type: 'info',
            message: 'Delete canceled',
          })
        })
  },
  queryInfo_u(){
      if(this.queryStr.trim().length>0){
        this.tableData = this.tableData.filter(item=>(item.userId).match(this.queryStr.trim()))
      }
    console.log("queryInfo...");
  },
    queryInfo_n(){
      if(this.input.trim().length>0){
        this.tableData = this.tableData.filter(item=>(item.id).match(this.input.trim()))
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
.card-header{
  display: flex;
  justify-content: space-between;
}
.query{
  display: flex;
  justify-content: space-between;
}
.product-buyer-name{
  max-width: 100px;
  max-height: 30px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>

<style lang="scss" scoped></style>
