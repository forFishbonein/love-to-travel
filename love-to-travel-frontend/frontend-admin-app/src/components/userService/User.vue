<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-button class="delete" round type="warning" @click="multiDelete">多选删除</el-button>
          <el-input v-model="queryStr" placeholder="请输入邮箱"/>&nbsp;&nbsp;
          <el-button class="button" round type="primary" @click="queryInfo">查询</el-button>
        </div>
      </div>
    </template>

    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="用户名" prop="name" width="150"/>
      <el-table-column label="性别" prop="gender" width="60"/>
      <!--      <el-table-column label="状态" prop="status" width="80">-->
      <!--        <template #default="scope">-->
      <!--          {{ scope.row.status == 1 ? '在线' : '离线' }}-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="出生日期" prop="birthday" width="100"/>
      <el-table-column label="邮箱" prop="email" width="180"/>
      <el-table-column label="电话" prop="tele" width="130"/>
      <el-table-column label="等级" prop="grade" width="80"/>
      <el-table-column label="经验" prop="experience" width="80"/>
      <el-table-column label="地址" prop="address" width="100"/>
      <el-table-column label="岗位" prop="post" width="130"/>
      <el-table-column label="职业" prop="profession" width="130"/>
      <el-table-column label="个性签名" prop="signature" width="150"/>
      <el-table-column label="创建时间" prop="createTime" width="160"/>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button link size="small" type="primary" @click="openDetailDialog(scope.row.id)">详情</el-button>
          <el-button link size="small" type="primary" @click="singleDelete(scope.row.id)">删除</el-button>
          <el-button link size="small" type="primary" @click="openUpdateDialog(scope.row)">修改</el-button>
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

  <!-- 对话框:修改功能 -->
  <el-dialog v-model="dialogFormVisible" title="修改用户信息">
    <el-form :model="form">
      <el-form-item :label-width="formLabelWidth" label="用户名称">
        <el-input v-model="form.name" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="用户性别">
        <el-radio-group v-model="form.gender">
          <el-radio label="男" name="type"/>
          <el-radio label="女" name="type"/>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="出生日期">
        <el-date-picker
            v-model="form.birthday"
            placeholder="Pick a date"
            style="width: 100%"
            type="date"
        />
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="地址">
        <el-input v-model="form.address" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="电话">
        <el-input v-model="form.tele" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="邮箱">
        <el-input v-model="form.email" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="岗位">
        <el-input v-model="form.post" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="职业">
        <el-input v-model="form.profession" autocomplete="off"/>
      </el-form-item>
      <el-form-item :label-width="formLabelWidth" label="个性签名">
        <el-input v-model="form.signature" type="textarea"/>
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


  <el-form :model="form">
    <!-- 详情对话框 -->
<!--    <el-image style="width: 100px; height: 100px" :src="form.url" :fit="fill"/>-->
    <el-dialog v-model="dialogDetailVisible" title="用户信息详情">
      <el-descriptions
          class="margin-top"
          :column="2"
          :size="size"
          border
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              用户名
            </div>
          </template>
          {{ form.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              性别
            </div>
          </template>
          {{ form.gender }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              出生日期
            </div>
          </template>
          {{ form.birthday }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              地址
            </div>
          </template>
          {{ form.address }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              电话
            </div>
          </template>
          {{ form.tele }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              创建时间
            </div>
          </template>
          {{ form.createTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              岗位
            </div>
          </template>
          {{ form.post }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              职业
            </div>
          </template>
          {{ form.profession }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              等级
            </div>
          </template>
          <el-tag size="small">{{ form.grade }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              经验
            </div>
          </template>
          {{ form.experience }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              个性签名
            </div>
          </template>
          {{ form.signature }}
        </el-descriptions-item>
      </el-descriptions>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogDetailVisible = false">关闭</el-button>
      </span>
      </template>
    </el-dialog>

  </el-form>


</template>

<script>
// import {ElMessage,ElMessageBox} from 'element-plus';
import {deleteUserInfo, getPageUserInfo, getUserByID, updateUserInfo} from "../../apis/userManage/user";

export default {
  data() {
    return {
      dialogDetailVisible: false,
      dialogFormVisible: false,
      queryStr: "",
      multipleSelection: [],
      tableData: [],
      form: {},
      input: "",
      formLabelWidth: "140px",
      title: "",
      btnName: "",
      pageInfo: {},
      pageSize: 10,
      currentPage: 1,
    };
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
      getPageUserInfo({current: num, size: size}).then((response) => {
        this.pageInfo = response.data;
        this.tableData = this.pageInfo.records;
        console.log(response.data)
        console.log(this.pageInfo.records)
      })
    },
    //-----------------------------------------
    // 查看用户信息详情
    openDetailDialog(id) {
      this.dialogDetailVisible = true;
      var _this = this;
      getUserByID(id).then((response) => {
        console.log(response.data);
        _this.form = response.data;
      })
    },
    //-----------------------------------------
    btnUpdate() {
      this.updateUser();
      console.log("修改操作....");
      this.dialogFormVisible = false;
    },
    openUpdateDialog(row) {
      this.btnName = "修改"
      this.title = "修改用户信息"
      this.dialogFormVisible = true;
      this.form = row;
      console.log("openUpdateDialog....");
    },
    updateUser() {
      console.log(this.form);
      var _this = this;
      updateUserInfo(_this.form).then((response) => {
        console.log(response.data);
        if (response.data == '修改成功') {
          ElMessage({
            message: '用户信息修改成功！',
            type: 'success'
          })
        } else {
          ElMessage({
            message: '用户信息修改失败！',
            type: 'warnings'
          })
        }
      })
      this.form = [];
    },
    queryInfo() {
      if (this.queryStr.trim().length > 0) {
        this.tableData = this.tableData.filter(item => (item.email).match(this.queryStr.trim()))
      }
      // else{
      //     this.tableData=this.queryData;
      // }
      console.log("queryinfo...");
    },
    //-----------------------------------------
    //删除单条数据
    singleDelete(id) {
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
            var _this = this;
            deleteUserInfo(id).then(function (response) {
              console.log(response.data);
              if (response.data == '删除成功') {
                ElMessage({
                  message: '删除成功！',
                  type: 'success'
                })
              } else {
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
    multiDelete() {
      console.log("multiDelete().....");
      ElMessageBox.confirm(
          '您确定要删除目前选中的多条数目吗?',
          'Warning',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            var _this = this;
            var num = 0
            this.multipleSelection.forEach(item => {
              var id = item.id
              console.log(id)
              deleteUserInfo(id).then((response) => {
                console.log(response.data);
                if (response.data == "删除成功") {
                  num = num + 1;
                }
              })
            })
            ElMessage({
              type: 'success',
              message: '您已成功删除！',
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: 'Delete canceled',
            })
          })
    },
    //-----------------------------------------
    //多行选择
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },
  },

  mounted() {
    this.getPageData(1, 10);
    // getUser().then((response) => {
    //   var _this = this;
    //   _this.tableData = response.data;
    //   _this.queryData = response.data;
    //   console.log(this.tableData);
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

.query {
  display: flex;
  justify-content: space-between;
}

.delete {
  margin-right: 20px;
}

.el-descriptions {
  margin-top: 20px;
}

.cell-item {
  display: flex;
  align-items: center;
}

.margin-top {
  margin-top: 20px;
}
</style>
