<template>
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <div class="query">
            <el-input v-model="queryStr" placeholder="Please input email" />&nbsp;&nbsp;
            <el-button class="button" type="primary" round @click="queryInfo">邮箱查询</el-button>
          </div>
        </div>
      </template>
  
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="uuid" label="uuid" width="300" />
        <el-table-column prop="userEmail" label="用户邮箱" width="200" />
        <el-table-column prop="osName" label="操作系统" width="150" />
        <el-table-column prop="browser_name" label="浏览器" width="150" />
        <el-table-column prop="ipAddress" label="ip地址" width="150" />
        <el-table-column prop="createName" label="创建时间" width="150" />
        <!-- <el-table-column fixed="right" label="Operations" width="150">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="openDetailDialog(scope.row.id)">详情</el-button>
          </template>
        </el-table-column> -->
      </el-table>
      <br>
      <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[10, 20, 30, 40]" 
      :background="true"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageInfo.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />


    </el-card>
  </template>
  
<script>
import { getLogInfo } from "../../apis/userManage/log";
export default {
    data() {
        return {
            tableData:[],
            pageInfo:{},
            pageSize:10,
            currentPage:1,
            queryStr:"",
        };
    },
    methods: {
    handleSizeChange(pageSize){
        this.pageSize = pageSize;
        this.getPageData(this.currentPage,this.pageSize);
        console.log("size:",pageSize);
      },
    handleCurrentChange(pageNum){
        this.currentPage = pageNum;
        this.getPageData(this.currentPage,this.pageSize);
        console.log("num:",pageNum);
      },
    getPageData(num,size){
        num = parseInt(num)
        size = parseInt(size)
        getLogInfo({current:num,size:size}).then((response) => {
          this.pageInfo = response.data;
          this.tableData = this.pageInfo.records;
          console.log(response.data)
          console.log(this.pageInfo.records)
        })
      },
      queryInfo(){
        if(this.queryStr.trim().length>0){
          this.tableData = this.tableData.filter(item => (item.userEmail).match(this.queryStr.trim()))
        }
        console.log("queryInfo....");
      },
    },
    mounted() {
      this.getPageData(1,10);
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
