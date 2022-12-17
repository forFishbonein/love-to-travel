<script setup lang="ts">
import { ref, reactive, toRefs, watch } from "vue";
import { mainStore } from "@/store/user";
import { UserInfo } from "@/apis/userService/uinterface";
import { userInfoModifyType } from "@/apis/interface/iPlan";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import { modifyUserInfo, modifyUserPass } from "@/apis/userService/user";
import { useRouter } from "vue-router";
import { getCode } from "@/utils/getCode";
const router = useRouter();
const store = mainStore();
const theModifyFormInfo = reactive({
  id: store.userInfo.id,
  name: store.userInfo.name,
  tele: store.userInfo.tele,
  birthday: store.userInfo.birthday,
  post: store.userInfo.post,
  profession: store.userInfo.profession,
  signature: store.userInfo.signature,
  gender: store.userInfo.gender,
  address: store.userInfo.address,
} as userInfoModifyType);
const { name, tele, birthday, post, profession, signature, gender, address } =
  toRefs(theModifyFormInfo);
const modifyTheUserInfo = () => {
  modifyUserInfo(theModifyFormInfo)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        //@ts-ignore
        ElMessage({
          type: "success",
          message: "修改成功，请重新登录！",
        });
        //@ts-ignore
        // ElMessage({
        //   type: "warning",
        //   message: "用户信息的展示将在下一次登陆时发生改变",
        // });
        // router.replace("/login");
        store.logout();
        setTimeout(() => {
          router.replace("/login");
        }, 1000);
        // setTimeout(() => {
        //   //   store.userInfo = {} as UserInfo; //清空对象
        //   //   alert(store.userInfo);
        //   store
        //     .getUserInfo()
        //     .then((res) => {
        //       //@ts-ignore
        //       ElMessage({
        //         type: "warning",
        //         message: "用户信息的展示将在下一次登陆时发生改变",
        //       });
        //       //   alert("完毕");
        //     })
        //     .catch(() => {
        //       //@ts-ignore
        //       ElMessage({
        //         type: "danger",
        //         message: "未知错误",
        //       });
        //     });
        // }, 1000);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
};

const updatePasswordParam = reactive({
  id: store.userInfo.id,
  email: store.userInfo.email,
  code: "",
  oldPassword: "",
  newPassword: "",
  token: store.token,
});
const { email, code, oldPassword, newPassword } = toRefs(updatePasswordParam);
const confirmPassword = ref("");
const confirmFlag = ref();
watch(confirmPassword, (newValue, oldValue) => {
  if (newPassword.value !== "" && confirmPassword.value !== "") {
    if (newValue === newPassword.value) {
      confirmFlag.value = true;
    } else {
      confirmFlag.value = false;
    }
  }
});
const modifyTheUserPassword = () => {
  modifyUserPass(updatePasswordParam)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        //@ts-ignore
        ElMessage({
          type: "success",
          message: "修改成功，请重新登录！",
        });
        store.logout();
        setTimeout(() => {
          router.replace("/login");
        }, 1000);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
};
</script>

<template>
  <el-tabs tab-position="left" style="height: 800px" class="demo-tabs">
    <el-tab-pane label="编辑资料">
      <div class="create-team-container">
        <div class="create-team">
          <div class="base-row">
            <span class="span-style-front">用户名</span>
            <el-input v-model="name" class="w-50 m-2" placeholder="请输入内容">
              <template #suffix>
                <el-icon><UserFilled /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">性别</span>
            <div style="margin-left: 10px">
              <el-radio-group v-model="gender">
                <el-radio label="男" border>男</el-radio>
                <el-radio label="女" border>女</el-radio>
              </el-radio-group>
            </div>
          </div>
          <div class="base-row">
            <span class="span-style-front">电话</span>
            <el-input v-model="tele" class="w-50 m-2" placeholder="请输入内容">
              <template #suffix>
                <el-icon><Phone /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">所在地</span>
            <el-input
              v-model="address"
              class="w-50 m-2"
              placeholder="请输入内容"
            >
              <template #suffix>
                <el-icon><MapLocation /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">岗位</span>
            <el-input v-model="post" class="w-50 m-2" placeholder="请输入内容">
              <template #suffix>
                <el-icon><Flag /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">职业</span>
            <el-input
              v-model="profession"
              class="w-50 m-2"
              placeholder="请输入内容"
            >
              <template #suffix>
                <el-icon><Suitcase /></el-icon>
              </template>
            </el-input>
          </div>

          <div class="base-row">
            <span class="span-style-front">出生日期</span>
            <el-config-provider :locale="zhCn">
              <el-date-picker
                type="date"
                placeholder="选择日期"
                size="large"
                v-model="birthday"
              />
            </el-config-provider>
          </div>
          <div class="base-row">
            <span class="span-style-front">签名</span>
            <el-input
              :autosize="{ minRows: 2, maxRows: 4 }"
              type="textarea"
              placeholder="请输入内容"
              v-model="signature"
            />
          </div>
          <div class="base-row flex-right">
            <el-button type="primary" @click="modifyTheUserInfo"
              >保存</el-button
            >
          </div>
        </div>
      </div></el-tab-pane
    >
    <el-tab-pane label="修改密码">
      <div class="create-team-container">
        <div class="create-team">
          <div class="base-row">
            <span class="span-style-front">邮箱</span>
            <el-input
              v-model="name"
              class="w-50 m-2"
              :placeholder="store.userInfo.email"
              disabled
            >
              <template #suffix>
                <el-icon><Message /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">验证码</span>
            <el-input v-model="code" class="w-50 m-2" placeholder="请输入内容">
              <template #suffix>
                <el-icon><InfoFilled /></el-icon>
              </template>
            </el-input>
            <input
              type="button"
              value="获取验证码"
              class="getCode"
              @click="getCode(email)"
            />
          </div>
          <div class="base-row">
            <span class="span-style-front">旧密码</span>
            <el-input
              v-model="oldPassword"
              class="w-50 m-2"
              placeholder="请输入内容"
              type="password"
              show-password
            >
              <template #suffix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">新密码</span>
            <el-input
              v-model="newPassword"
              class="w-50 m-2"
              placeholder="请输入内容"
              type="password"
              show-password
            >
              <template #suffix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">确认密码</span>
            <el-input
              v-model="confirmPassword"
              class="w-50 m-2"
              placeholder="请输入内容"
              type="password"
              show-password
            >
              <template #suffix>
                <el-icon v-if="confirmFlag === true" :color="`green`"
                  ><SuccessFilled
                /></el-icon>
                <el-icon v-else-if="confirmFlag === false" :color="`red`"
                  ><WarningFilled
                /></el-icon>
                <el-icon v-else><Lock /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row flex-right-pass">
            <el-button type="primary" @click="modifyTheUserPassword"
              >确定</el-button
            >
          </div>
        </div>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<style lang="scss" scoped>
.getCode {
  min-width: 100px;
  height: 26px;
  background-color: #ffffff;
  // float: right;
  // right: 190px;
  line-height: 22px;
  border-radius: 10px;
  cursor: pointer;
  color: #d4af7a;
  font-size: 14px;
  border: 2px solid transparent;
}
.getCode:hover {
  border-color: #d4af7a;
}
.flex-right {
  display: flex;
  justify-content: right !important;
}
.flex-right-pass {
  display: flex;
  justify-content: left !important;
}
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.el-tabs--right .el-tabs__content,
.el-tabs--left .el-tabs__content {
  height: 100%;
}
.el-tabs {
  --el-tabs-header-height: 50px;
}
::v-deep .el-tabs__nav-scroll {
  background-color: #ffffff !important;
}
.create-team-container {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: start;
  padding-left: 30px;
  .create-team {
    min-width: 600px;
    height: auto;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    .base-row {
      width: 100%;
      height: 60px;
      // border: 1px #e8604c solid;
      display: flex;
      justify-content: start;
      align-items: center;
      .el-input {
        width: 60px;
      }
      .span-style-front {
        display: inline-block;
        min-width: 50px;
        padding: 5px;
        height: 30px;
        line-height: 20px;
        color: #e8604c;
        font-size: 14px;
        font-weight: 600;
        background-color: #ffffff;
        border-radius: 5px;
        margin-right: 10px;
      }
    }
  }
}
</style>
