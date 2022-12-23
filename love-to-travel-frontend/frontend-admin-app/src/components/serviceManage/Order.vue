<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="请输入产品id"/>&nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo">查询</el-button>
        </div>

        <div class="query">
          <el-input v-model="queryStr1" placeholder="请输入用户id"/>&nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo_u">查询</el-button>
        </div>
      </div>
    </template>

    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="产品id" prop="productId" width="220"/>
      <el-table-column label="用户id" prop="userId" width="200"/>
      <el-table-column label="使用状态" prop="status" width="120">
        <template #default="scope">
               {{ scope.row.status == 1 ? '已使用' : '未使用' }}
             </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="200"/>
      <el-table-column label="金额" prop="cost" width="100"/>
      <el-table-column label="支付状态" prop="paystate" width="150">
        <template #default="scope">
               {{ scope.row.status == 1 ? '已支付' : '未支付' }}
             </template>
      </el-table-column>
      <el-table-column label="支付时间" prop="payTime" width="180"/>
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link size="small" type="primary" @click="openUpdateDialog(scope.row)">修改状态</el-button>
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
  <!-- 对话框:修改使用状态 -->
  <el-dialog v-model="dialogFormVisible" title="修改使用状态">
    <el-form :model="form">
      <el-form-item label="使用状态" :label-width="formLabelWidth">
      <el-radio-group v-model="form.status">
      <el-radio label="1">已使用</el-radio>
      <el-radio label="0">未使用</el-radio>
    </el-radio-group>
    </el-form-item>
    </el-form>
    <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取消</el-button>
      <el-button type="primary" @click="btnUpdate">
        {{ btnName }}
      </el-button>
    </span>
    </template>
  </el-dialog>


</template>

<script>
import {getOrderPage,updateOrderInfo} from "../../apis/serviceManage/order";

export default {
  data() {
    return {
      tableData: [],
      pageInfo: {},
      pageSize: 10,
      currentPage: 1,
      queryStr: "",
      queryStr1: "",
      input: "",
      dialogFormVisible: false,
      form: {},
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
      getOrderPage({pageNum: num, pageSize: size}).then((response) => {
        this.pageInfo = response.data;
        this.tableData = this.pageInfo.records;
        console.log(response.data)
        console.log(this.pageInfo.records)
      })
    },
    queryInfo() {
      if (this.queryStr.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.productId).match(this.queryStr.trim()))
      }
      console.log("queryInfo....");
    },
    queryInfo_u() {
      if (this.queryStr1.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.userId).match(this.queryStr1.trim()))
      }
      console.log("queryInfo....");
    },
    btnUpdate() {
      this.updateOrder();
      console.log("修改操作....");
      this.dialogFormVisible = false;
    },
    openUpdateDialog(row) {
      this.btnName = "修改"
      this.title = "修改使用状态"
      this.dialogFormVisible = true;
      this.form = row;
      console.log("openUpdateDialog....");
    },
    updateOrder() {
      console.log(this.form);
      var _this = this;
      updateOrderInfo(_this.form).then((response) => {
        console.log(response.data);
        if (response.data == '使用成功') {
          ElMessage({
            message: '使用成功！',
            type: 'success'
          })
        } else {
          ElMessage({
            message: '使用失败！',
            type: 'warnings'
          })
        }
      })
      this.form = [];
    },

  },
  mounted() {
    this.getPageData(1, 10);
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
</style>
