<script setup lang="ts">
/**
 * @description 验证码登录
 */
import { mainStore } from "@/store/user";
import { reactive, toRefs } from "vue";
import {
  a,
  count,
  curCount,
  InterValObj,
  setRemainTime,
  getCode,
} from "@/utils/getCode";

import { useRouter } from "vue-router";

const router = useRouter();
const store = mainStore();

interface codeLoginInfo {
  email2: string;
  code: string;
}
const codeData: codeLoginInfo = reactive({
  email2: "",
  code: "",
});
const { email2, code } = toRefs(codeData);
const codeLogin = () => {
  store
    .codeLogin({
      email: codeData.email2,
      code: codeData.code,
    })
    .then((res: any) => {
      console.log(res.code != 0);
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        //@ts-ignore
        // @ts-ignore
        ElMessage({
          type: "success",
          message: "爱宝儿，欢迎你加入爱旅游的大家庭~",
        });
        router.replace("/");
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
  <h2>欢迎回来</h2>
  <label>
    <span>邮箱</span>
    <input type="email" v-model="email2" />
  </label>
  <label>
    <span>验证码</span>
    <input type="text" class="passWord" v-model="code" />
  </label>
  <input
    type="button"
    value="获取验证码"
    class="getCode"
    @click="getCode(codeData.email2)"
  />
  <p class="forgot-pass">
    <a href="javascript:" class="forgetPass">忘记密码？</a>
  </p>
  <button type="button" class="submit" @click="codeLogin">登 录</button>
</template>

<style lang="scss" scoped>
@import url(@/assets/css/login.css);
.passWord {
  position: relative;
}
// .getCode {
//   margin-top: 15px;
//   width: 100px;
//   height: 30px;
//   background-color: #e0e0e0;
//   // float: right;
//   position: absolute;

//   right: 190px;
//   line-height: 30px;
//   border-radius: 10px;
//   border: 0;
//   cursor: pointer;
// }
.getCode {
  width: 100px;
  height: 26px;
  background-color: #ffffff;
  // float: right;
  // right: 190px;
  line-height: 22px;
  margin-top: 15px;
  border-radius: 10px;
  cursor: pointer;
  color: #d4af7a;
  font-size: 14px;
  border: 2px solid transparent;
  // float: right;
  position: absolute;
  right: 190px;
}
.getCode:hover {
  border-color: #d4af7a;
}
.forgetPass {
  position: absolute;
  left: 200px;
  margin-top: 10px;
}
.submit {
  margin-top: 60px;
}
</style>
