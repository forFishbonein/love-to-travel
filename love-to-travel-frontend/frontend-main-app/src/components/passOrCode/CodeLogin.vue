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
} from "@/utils/getCode2";

import { useRouter } from "vue-router";
import i18n from "@/locales";
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
  if (email2.value !== "" && code.value !== "") {
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
            message: i18n.global.t("other.welcomeToBack"),
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
  } else {
    // @ts-ignore
    ElMessage({
      type: "warning",
      message: i18n.global.t("other.pleaseOver"),
    });
  }
};
</script>

<template>
  <h2 class="font-white-style">{{ $t("titles.welcome") }}</h2>
  <label>
    <span>{{ $t("loginForm.email") }}</span>
    <input type="email" v-model="email2" required />
  </label>
  <label>
    <span>{{ $t("main.code") }}</span>
    <input type="text" class="passWord" v-model="code" required />
  </label>
  <input
    type="button"
    :value="$t('main.getcode')"
    class="getCode"
    @click="getCode(codeData.email2)"
  />
  <p class="forgot-pass">
    <a href="javascript:;" class="forgetPass">{{
      $t("messages.forgetPass")
    }}</a>
  </p>
  <button type="button" class="submit" @click="codeLogin">
    {{ $t("buttons.login") }}
  </button>
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
  margin-top: 15px;
  width: 100px;
  height: 26px;
  background-color: #ffffff;
  // float: right;
  // right: 190px;
  line-height: 22px;
  margin-top: 15px;
  border-radius: 10px;
  cursor: pointer;
  color: #909399;
  font-size: 14px;
  border: 2px solid transparent;
  // float: right;
  position: absolute;
  right: 190px;
  transition: all 0.3s linear;
}
.getCode:hover {
  // border-color: #e8604c;
  background-color: #e8604c;
  color: #ffffff;
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
