<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="请输入用户名"/> &nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo_u">查询</el-button>
        </div>

        <div class="query">
          <el-input v-model="input" placeholder="请输入内容"/> &nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo_n">查询</el-button>
        </div>

        <div>
          <el-button class="button" round type="success" @click="openAddDialog">添加</el-button>
          <el-button class="button" round type="warning" @click="multiDelete">删除</el-button>
        </div>

      </div>
    </template>
    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="游记标题" prop="title" width="150"/>
      <el-table-column label="发布者" prop="userName" width="100"/>
      <el-table-column label="相关城市" prop="city" width="80"/>
      <!--      <el-table-column :show-overflow-tooltip="true" label="内容" prop="content" width="100"/>-->
      <el-table-column label="评论量" prop="comment" width="100"/>
      <el-table-column label="点赞量" prop="like" width="100"/>
      <el-table-column label="浏览量" prop="view" width="100"/>
      <el-table-column label="标签" prop="trip" width="150"/>
      <el-table-column fixed="right" label="操作" prop="oppo" width="150">
        <template #default="scope">
          <el-button link size="small" type="primary" @click="openShowDialog(scope.row)">详情</el-button>
          <el-button link size="small" type="primary" @click="singleDelete(scope.row)">删除</el-button>
          <el-button link size="small" type="primary" @click="openUpdateDialog(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :background="true"
        :page-sizes="[3, 4, 5, 10]"
        :total="pageInfo.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />

  </el-card>
  <!-- 对话框：添加修改功能 -->
  <el-dialog v-model="dialogFormVisible" :title="title">
    <el-form :model="form">
      <el-form-item :label-width="formLabelWidth" label="游记标题">
        <el-input v-model="form.title" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="用户名">
        <el-input v-model="form.userName" :disabled="true" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="所属城市">
        <el-input v-model="form.city" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="游记内容">
        <el-input v-model="form.content" autocomplete="off" class="product-buyer-name"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="标签">
        <el-input v-model="form.trip" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="btnAddUpdate">
          {{ btnName }}
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="showVisible" :title="title">
    <el-form :model="showVisibleForm">
      <el-form-item v-html="showVisibleForm.content"/>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="showVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>


</template>


<script lang="ts">
import {delNote} from "../../apis/serviceManage/delnote.js";
import {addNote} from "../../apis/serviceManage/addnote.js";
import {updateNote} from "../../apis/serviceManage/updatenote.js";
import {pageNote} from "../../apis/serviceManage/pagenote.js";
// import {ElMessage,ElMessageBox} from 'element-plus';
export default {
  data() {
    return {
      dialogFormVisible: false, //对话框是否显示
      showVisible: false,
      showVisibleForm: {},
      queryStr: "",  //查询条件
      input: "",
      multipleSelection: [], //多选删除
      tableData: [], //游记信息数据
      pageInfo: {},  //分页信息对象
      pageSize: 10,  //当前页条数
      currentPage: 1, //当前页号
      form: {},   //对话框表单数据
      formLabelWidth: "140px",  //对话框label宽度
      title: "",  //对话框标题
      btnName: ""  //对话框按钮文字
    }
  },
  mounted() {
    // getNote().then((response) => {
    //   var _this = this;
    //   console.log(response.data);
    //   _this.tableData = response.data;
    //
    // })
    this.getPageData(1, 10)
  },

  methods: {
    handleSizeChange(pageSize) {  //选择每一页的记录数
      this.pageSize = pageSize
      this.getPageData(this.currentPage, this.pageSize)
      console.log(pageSize)
    },
    handleCurrentChange(pageNum) { //切换页号时得到当时页号
      this.currentPage = pageNum
      this.getPageData(this.currentPage, this.pageSize)
      console.log(pageNum)
    },
    getPageData(num, size) {
      console.log("dddd")
      num = parseInt(num)
      size = parseInt(size)
      pageNote({pageNum: num, pageSize: size}).then((response) => {
        this.pageInfo = response.data
        console.log(response.data)
        console.log(this.pageInfo.records)
        this.tableData = response.data.records
      })
    },
    openShowDialog(row) {
      this.showVisible = true;
      this.title = row.title
      this.showVisibleForm = row
    },
    openAddDialog() {
      this.btnName = "添加"
      this.title = "添加游记信息"
      this.dialogFormVisible = true
      console.log("openAddDialog")
    },
    openUpdateDialog(row) {
      this.btnName = "修改"
      this.title = "修改游记信息"
      this.dialogFormVisible = true
      console.log(row)
      this.form = row
    },
    updateNote() {
      console.log(this.form)
      var _this = this;
      //this.form.stu_interest = this.form.stu_interest.join(',')
      updateNote(this.form).then((response) => {
        console.log(response.data);
        if (response.data == "修改成功") {
          ElMessage({
            message: '商品信息修改成功',
            type: 'success',
          })
        } else {
          ElMessage({
            message: '商品信息修改失败',
            type: 'warning',
          })
        }
      })
    },
    addShop() {
      var _this = this;
      //this.form.stu_interest = this.form.stu_interest.join(',')
      addNote(this.form).then((response) => {
        console.log(response.data);
        if (response.data == "新增成功") {
          ElMessage({
            message: '商品信息添加成功',
            type: 'success',
          })
        } else {
          ElMessage({
            message: '商品信息添加失败',
            type: 'warning',
          })
        }
      })
    },
    //
    //   //.......

    btnAddUpdate() {
      if (this.btnName == "修改") {
        this.updateNote()
        // console.log("修改。。。")
      }
      if (this.btnName == "添加") {
        this.addShop()
        console.log("添加。。。")
        console.log(this.form)
      }
      this.dialogFormVisible = false
    },
    singleDelete(row) {
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
              if (response.data == "删除成功") {
                ElMessage({
                  type: 'success',
                  message: '删除成功',
                })
              } else {
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

    handleSelectionChange(val) {  //多行选择
      this.multipleSelection = val
      console.log(this.multipleSelection);
    },
    multiDelete() {
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
            var num = 0
            this.multipleSelection.forEach(item => {
              var id = item.id
              console.log(id)
              delNote(id).then((response) => {
                console.log(response.data);
                if (response.data == "删除成功") {
                  num += 1
                }
              })
            })
            ElMessage({
              type: 'success',
              message: '成功删除' + num + '条记录',
            })
          })

          .catch(() => {
            ElMessage({
              type: 'info',
              message: 'Delete canceled',
            })
          })
    },
    queryInfo_u() {
      if (this.queryStr.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.userId).match(this.queryStr.trim()))
      }
      console.log("queryInfo...");
    },
    queryInfo_n() {
      if (this.input.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.id).match(this.input.trim()))
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
.card-header {
  display: flex;
  justify-content: space-between;
}

.query {
  display: flex;
  justify-content: space-between;
}

.product-buyer-name {
  max-width: 100px;
  max-height: 30px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>

<style lang="scss" scoped></style>
