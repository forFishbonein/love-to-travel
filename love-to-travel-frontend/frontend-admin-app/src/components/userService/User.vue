<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="Please input email" />&nbsp;&nbsp;
          <el-button class="button" type="primary" round @click="queryInfo">查询</el-button>
        </div>
        <div>
  
  <el-button type="warning" round @click="multiDelete">多选删除</el-button>
</div>
      </div>
    </template>

    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="用户id" width="100" />
      <el-table-column prop="name" label="用户名称" width="100" />
      <el-table-column prop="gender" label="性别" width="100" />
      <el-table-column prop="birthday" label="出生日期" width="100" />
      <el-table-column prop="grade" label="等级" width="100" />
      <el-table-column prop="experience" label="经验" width="100" />
      <el-table-column prop="address" label="地址" width="100" />
      <el-table-column prop="tele" label="电话" width="100" />
      <el-table-column prop="email" label="邮箱" width="120" />
      <el-table-column prop="post" label="岗位" width="100" />
      <el-table-column prop="profession" label="职业" width="100" />
      <el-table-column prop="signature" label="个性签名" width="100" />
      <el-table-column prop="createTime" label="创建时间" width="100" />
      <el-table-column prop="status" label="状态" width="80" />
      <el-table-column fixed="right" label="Operations" width="150">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="openDetailDialog(scope.row.id)">详情</el-button>
          <el-button link type="primary" size="small" @click="singleDelete(scope.row.id)">删除</el-button>
          <el-button link type="primary" size="small" @click="openUpdateDialog(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

<!-- 对话框:修改功能 -->
<el-dialog v-model="dialogFormVisible" title="修改用户信息">
  <el-form :model="form">
    <el-form-item label="用户名称" :label-width="formLabelWidth">
      <el-input v-model="form.name" autocomplete="off" />
    </el-form-item>
    <el-form-item label="用户性别" :label-width="formLabelWidth">
          <el-radio-group v-model="form.gender">
          <el-radio label="男" name="type" />
          <el-radio label="女" name="type" />
        </el-radio-group>
    </el-form-item>
    <el-form-item label="出生日期" :label-width="formLabelWidth">
          <el-date-picker
            v-model="form.birthday"
            type="date"
            placeholder="Pick a date"
            style="width: 100%"
          />
      </el-form-item>
    <el-form-item label="地址" :label-width="formLabelWidth">
      <el-input v-model="form.address" autocomplete="off" />
    </el-form-item>
    <el-form-item label="电话" :label-width="formLabelWidth">
      <el-input v-model="form.tele" autocomplete="off" />
    </el-form-item>
    <el-form-item label="邮箱" :label-width="formLabelWidth">
      <el-input v-model="form.email" autocomplete="off" />
    </el-form-item>  
    <el-form-item label="岗位" :label-width="formLabelWidth">
      <el-input v-model="form.post" autocomplete="off" />
    </el-form-item>
    <el-form-item label="职业" :label-width="formLabelWidth">
      <el-input v-model="form.profession" autocomplete="off" />
    </el-form-item>
    <el-form-item label="个性签名" :label-width="formLabelWidth">
      <el-input v-model="form.signatur" type="textarea"/>
    </el-form-item>
  </el-form>
  <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取消</el-button>
      <el-button type="primary" @click="btnUpdate">
        {{btnName}}
      </el-button>
    </span>
  </template>
</el-dialog>


  <!-- 详情对话框 -->
  <el-dialog v-model="dialogDetailVisible" title="用户信息详情">
    <el-form :model="form">
      <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-form-item :label="form.name" ></el-form-item>
        </el-form-item>
      <el-form-item label="性别" :label-width="formLabelWidth">
        <el-form-item :label="form.gender"></el-form-item>
      </el-form-item>
      <el-form-item label="头像" :label-width="formLabelWidth">
        <el-form-item :label="form.url"></el-form-item>
      </el-form-item>
      <el-form-item label="出生日期" :label-width="formLabelWidth">
        <el-form-item :label="form.birthday"></el-form-item>
      </el-form-item>
      <el-form-item label="等级" :label-width="formLabelWidth">
        <el-form-item :label="form.grade"></el-form-item>
      </el-form-item>
      <el-form-item label="经验" :label-width="formLabelWidth">
        <el-form-item :label="form.experience"></el-form-item>
      </el-form-item>
      <el-form-item label="地址" :label-width="formLabelWidth">
        <el-form-item :label="form.address"></el-form-item>
      </el-form-item>
      <el-form-item label="电话" :label-width="formLabelWidth">
        <el-form-item :label="form.tele"></el-form-item>
      </el-form-item>
      <el-form-item label="邮箱" :label-width="formLabelWidth">
        <el-form-item :label="form.email"></el-form-item>
      </el-form-item>
      <el-form-item label="岗位" :label-width="formLabelWidth">
        <el-form-item :label="form.post"></el-form-item>
      </el-form-item>
      <el-form-item label="职业" :label-width="formLabelWidth">
        <el-form-item :label="form.profession"></el-form-item>
      </el-form-item>
      <el-form-item label="个性签名" :label-width="formLabelWidth">
        <el-form-item :label="form.signature"></el-form-item>
      </el-form-item>
      <el-form-item label="创建时间" :label-width="formLabelWidth">
        <el-form-item :label="form.createTime"></el-form-item>
      </el-form-item>
      <el-form-item label="状态" :label-width="formLabelWidth">
        <el-form-item :label="form.status"></el-form-item>
      </el-form-item>
     
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogDetailVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
  
</template>

<script>
// import {ElMessage,ElMessageBox} from 'element-plus';
import { getUser } from "../../apis/userManage/user";
import { updateUserInfo,deleteUserInfo,getUserByID } from "../../apis/userManage/user";
export default {
  data() {
    return {
          dialogDetailVisible:false,//详细对话框
          dialogFormVisible:false,//对话框是否显示
          queryStr:"",//查询条件
          multipleSelection:[],//多选删除
          tableData:[],//学生信息数据
          queryData:[],
          pageInfo:{},//分页信息对象
          currentPage:1,//当前页号
          form:{

          },//对话框表单数据
          formLabelWidth:"140px",//对话框宽度
          title:"",//对话框标题
          btnName:"",//对话框按钮文字
    };
  },
  methods: {

    // 查看详情
    openDetailDialog(id){
          this.dialogDetailVisible=true;
          var _this=this;
          getUserByID(id).then((response) => {
              console.log(response.data);
              console.log(res.code);
              _this.form=response.data;
          })
      },
    //-----------------------------------------
    // 修改用户信息
    btnUpdate(){
      this.updateUser();
      console.log("修改操作....");
      this.dialogFormVisible=false;
      },
    //  打开修改对话框
    openUpdateDialog(row){
        this.btnName = "修改"
        this.title = "修改用户信息"
        this.dialogFormVisible = true;
        // console.log(row);
        this.form=row;  //得到需要修改的数据并回显
        console.log("openUpdateDialog....");
          },
    //  修改功能
    updateUser(){
    console.log(this.form);
    var _this=this;
    updateUserInfo(this.form).then(function(response){
        console.log(response.data);
        if(response.data=='删除成功'){
            ElMessage({
                message: '用户信息修改成功！',
                type: 'success'
            })
        }else{
            ElMessage({
                message: '用户信息修改失败！',
                type: 'warnings'
            })
        }
    })
    this.form=[];
   },
  
      // 查询用户信息
      queryInfo(){
          if(this.queryStr.trim().length>0){
              this.tableData=this.tableData.filter(item=>(item.email).match(this.queryStr.trim()))
          }else{
              this.tableData=this.queryData;
          }
          console.log("queryinfo...");
      },

    //-----------------------------------------
    //删除单条数据
      singleDelete(id){
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
                  var _this=this;
                  deleteUserInfo(id).then(function(response){
                      console.log(response.data);
                      if(response.data=='删除成功'){
                      ElMessage({
                      message: '删除成功！',
                      type: 'success'
                      })
                  }else{
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
      multiDelete(){
        console.log("multiDelete().....");

        this.multipleSelection.forEach(item => {
          var _this=this;
          var id = item.id
            deleteUserInfo(id).then(function(response){
                console.log(response.data);
            })
          })
      },

    //-----------------------------------------
    //多行选择
    handleSelectionChange(val){            
      this.multipleSelection = val;
      console.log(this.multipleSelection);
     },
    },
      
  mounted() {
    getUser().then((response) => {
      var _this = this;
      _this.tableData = response.data;
      _this.queryData = response.data;
      console.log(this.tableData);
    })
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
</style>
