<!-- <script lang="ts" setup></script> -->

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="请输入查询省份"/> &nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo">查询</el-button>
        </div>


      </div>
    </template>


      <el-card class="province-card" v-for="item in tableData" :body-style="{ padding: '0px' }">
        <img
            :src="item.url"
            height="154"
            class="image"/>
        <div style="padding: 14px">
          <span></span>
          <div class="bottom">
            <time class="time">{{ item.provinceName }}</time>
            <el-button text class="button" @click="getDetails(item)">查看</el-button>
          </div>
        </div>

       
      </el-card>
      <br>
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :background="true"
        :page-sizes="[6, 9, 18]"
        :total="pageInfo.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </el-card>


  <el-dialog v-model="showVisible" :title="title">
    <el-form :model="showVisibleForm">

    </el-form>

    <el-descriptions
        :title="showVisibleForm.name"
        direction="vertical"
        :column="4"
        :size="size"
        border
    >

      <el-descriptions-item label="简介" :span="4">
        {{ showVisibleForm.introduction }}
      </el-descriptions-item>

    </el-descriptions>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="showVisible=false">关闭</el-button>
      </span>
    </template>
  </el-dialog>


</template>

<script>
// import {ElMessage} from 'element-plus'
import {getProvinceInfo,getPageProvinceInfo} from '../../apis/serviceManage/province.js'
// import {updateSceneryInfo} from "@apis/serviceManage/scenery.js";


export default {
  data() {
    return {
      dialogFormVisible: false, //对话框是否显示
      queryStr: "",  //查询条件
      tableData: [], //学生信息数据
      form: {},   //对话框表单数据
      formLabelWidth: "140px",  //对话框label宽度
      title: "",  //对话框标题
      btnName: "", //对话框按钮文字
      pageInfo: {},
      pageSize: 9,
      currentPage: 1,
      showVisible: false,
      showVisibleForm: {}

    }
  },
  mounted() {
    this.getPageData(1, 9);
    // getProvinceInfo().then((response) => {
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
      getPageProvinceInfo({pageNum: num, pageSize: size}).then((response) => {
        this.pageInfo = response.data;
        this.tableData = this.pageInfo.records;
        console.log(response.data)
        console.log(this.pageInfo.records)
      })
    },

    getDetails(item) {
      // console.log(this.tableData)
      this.showVisibleForm= {};
      this.showVisible = true;
      this.title = item.provinceName
      this.showVisibleForm = item

    },

    openUpdateDialog(row) {
      this.btnName = "修改"
      this.title = "修改省份信息"
      this.dialogFormVisible = true
      console.log(row);
      this.form = row  //得到要修改的数据，并回显到对话框

    },

    updateScenery() {
      console.log(this.form)
      var _this = this;
      //this.form.stu_interest = this.form.stu_interest.join(',')
      updateSceneryInfo(_this.form).then((response) => {
        var _this = this;
        console.log(response.data === 1);
        if (response.data !== 1) {
          ElMessage({
            message: '景区信息修改成功',
            type: 'success',
          })
        } else {
          ElMessage({
            message: '景区信息修改失败',
            type: 'warning',
          })
        }
      })
    },

    //.......
    btnAddUpdate() {
      console.log(this.form)
      this.dialogFormVisible = false
    },
    queryInfo() {
      // console.log("queryInfo...");
      if (this.queryStr.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.provinceName).match(this.queryStr.trim()))
      } else {
        this.tableData = this.queryData
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      console.log(this.multipleSelection)
    }
  }
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

.time {
  font-size: 22px;
  color: #6c6c6c;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  display: block;
}

.province-card {
  width: 300px;
  padding: 10px;
  margin: 10px 40px;
  display: inline-block;
}
</style>

<style lang="scss" scoped></style>
