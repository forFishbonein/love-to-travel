<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="Please input email"/>&nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo">邮箱查询</el-button>
        </div>
      </div>
    </template>

    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="uuid" prop="uuid" width="300"/>
      <el-table-column label="用户邮箱" prop="userEmail" width="200"/>
      <el-table-column label="操作系统" prop="osName" width="200"/>
      <el-table-column label="浏览器" prop="browser_name" width="150"/>
      <el-table-column label="ip地址" prop="ipAddress" width="150"/>
      <el-table-column label="创建时间" prop="createName" width="150"/>
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
import {getLogInfo} from "../../apis/userManage/log";

export default {
  data() {
    return {
      tableData: [],
      pageInfo: {},
      pageSize: 10,
      currentPage: 1,
      queryStr: "",
      input: "",
    };
  },
  methods: {
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.getPageData(this.currentPage, this.pageSize);
      console.log("size:", pageSize);
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.getPageData(this.currentPage, this.pageSize);
      console.log("num:", pageNum);
    },
    getPageData(num, size) {
      num = parseInt(num)
      size = parseInt(size)
      getLogInfo({current: num, size: size}).then((response) => {
        this.pageInfo = response.data;
        this.tableData = this.pageInfo.records;
        console.log(response.data)
        console.log(this.pageInfo.records)
      })
    },
    queryInfo() {
      if (this.queryStr.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.userEmail).match(this.queryStr.trim()))
      }
      console.log("queryInfo....");
    },
  },
  mounted() {
    this.getPageData(1, 10);
    // getLogInfo().then((response) => {
    //     var _this = this;
    //     _this.tableData = response.data;
    //     console.log(this.tableData);
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
</style>
