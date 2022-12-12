<script>
import { getUser } from "../../apis/userManage/user";
export default {
  data() {
    return {
      tableData: [],
      queryData: [],
      dialogDetailVisible: false, //详细对话框
      dialogFormVisible: false, //对话框是否显示
      queryStr: "", //查询条件
      form: {}, //对话框表单数据
      formLabelWidth: "140px", //对话框宽度
      title: "", //对话框标题
      btnName: "", //对话框按钮文字
    };
  },
  methods: {
    // 查询
    queryInfo() {
      if (this.queryStr.trim().length > 0) {
        this.tableData = this.tableData.filter((item) =>
          item.id.match(this.queryStr.trim())
        );
      } else {
        this.tableData = this.queryData;
      }
    },
    // 查看详情
    openDetailDialog(id) {
      this.dialogDetailVisible = true;
      var _this = this;
      this.$http.get("/user/" + id).then(function (response) {
        console.log(response.data);
        _this.form = response.data;
      });
    },
  },
  mounted() {
    getUser().then((response) => {
      var _this = this;
      _this.tableData = response.data;
      console.log(this.tableData);
    });
  },
};
</script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="Please input" />&nbsp;&nbsp;
          <el-button class="button" type="primary" round @click="queryInfo"
            >查询</el-button
          >
        </div>
      </div>
    </template>

    <el-table :data="tableData" style="width: 100%">
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
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button
            link
            type="primary"
            size="small"
            @click="openDetailDialog(scope.row.id)"
            >详情</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <!-- 详情对话框 -->
  <el-dialog v-model="dialogDetailVisible" title="用户详情信息">
    <el-form :model="form">
      <el-form-item label="id" :label-width="formLabelWidth">
        <el-form-item :label="form.id"></el-form-item>
      </el-form-item>
      <el-form-item label="姓名" :label-width="formLabelWidth">
        <el-form-item :label="form.name"></el-form-item>
      </el-form-item>
      <el-form-item label="性别" :label-width="formLabelWidth">
        <el-form-item :label="form.gender"></el-form-item>
      </el-form-item>
      <el-form-item label="头像" :label-width="formLabelWidth">
        <el-form-item :label="url.name"></el-form-item>
      </el-form-item>
      <el-form-item label="出生日期" :label-width="formLabelWidth">
        <el-form-item :label="form.birthday"></el-form-item>
      </el-form-item>
      <el-form-item label="等级" :label-width="formLabelWidth">
        <el-form-item :label="form.grade"></el-form-item>
      </el-form-item>
      <el-form-item label="经验" :label-width="formLabelWidth">
        <el-form-item :label="form.exper"></el-form-item>
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
      <el-form-item label="创建时间" :label-width="formLabelWidth">
        <el-form-item :label="form.createTime"></el-form-item>
      </el-form-item>
      <el-form-item label="状态" :label-width="formLabelWidth">
        <el-form-item :label="form.satus"></el-form-item>
      </el-form-item>
      <el-form-item label="个性签名" :label-width="formLabelWidth">
        <el-form-item :label="form.signature"></el-form-item>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogDetailVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

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
