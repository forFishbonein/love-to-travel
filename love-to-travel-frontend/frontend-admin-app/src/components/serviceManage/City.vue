<!-- <script lang="ts" setup></script> -->

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="请输入查询城市"/> &nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo">查询</el-button>
        </div>
        <!--        <div>-->
        <!--          <el-button class="button" type="success" round @click="openAddDialog">添加</el-button>-->
        <!--          <el-button class="button" type="warning" round @click="multiDelete">删除</el-button>-->
        <!--        </div>-->


      </div>
    </template>
    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="城市名称" prop="cityName" width="90"/>
      <el-table-column label="Pinyin" prop="cityEname" width="120"/>
      <el-table-column label="经度" prop="lng" width="150"/>
      <el-table-column label="纬度" prop="lat" width="150"/>
      <el-table-column label="简介" prop="introduction"/>
      <el-table-column fixed="right" label="操作" width="120">
        <template #default>
          <el-button link size="small" type="primary" @click="singleDelete">删除
          </el-button
          >
          <el-button link size="small" type="primary" @click="openUpdateDialog">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :background="true"
        :page-sizes="[10, 20, 20,30]"
        :total="pageInfo.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </el-card>
</template>

<script>
import {getCityInfo,getPageCityInfo} from "@apis/serviceManage/city.js";

export default {
  data() {
    return {
      dialogFormVisible: false, //对话框是否显示
      queryStr: "",  //查询条件
      multipleSelection: [], //多选删除
      tableData: [], //学生信息数据
      form: {},   //对话框表单数据
      formLabelWidth: "140px",  //对话框label宽度
      title: "",  //对话框标题
      btnName: "", //对话框按钮文字
      pageInfo: {},
      pageSize: 10,
      currentPage: 1,
    }
  },
  mounted() {
    this.getPageData(1, 10);
    // getCityInfo().then((response) => {
    //   var _this = this;
    //   console.log(response.data);
    //   _this.tableData = response.data;
    //   _this.queryData = response.data;
    // });
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
      getPageCityInfo({pageNum: num, pageSize: size}).then((response) => {
        this.pageInfo = response.data;
        this.tableData = this.pageInfo.records;
        console.log(response.data)
        console.log(this.pageInfo.records)
      })
    },
    queryInfo() {
      if (this.queryStr.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.cityName).match(this.queryStr.trim()))
      } else {
        this.tableData = this.queryData
      }
    },
  }
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
</style>

<style lang="scss" scoped></style>
