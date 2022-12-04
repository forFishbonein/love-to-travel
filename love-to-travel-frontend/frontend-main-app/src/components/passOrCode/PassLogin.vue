<script setup lang="ts">
/**
 * @description  密码登录
 */
import { mainStore } from "@/store/user";
import { reactive, toRefs } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

interface passLoginInfo {
  email: string;
  password: string;
}
const passData: passLoginInfo = reactive({
  email: "",
  password: "",
});
const { email, password } = toRefs(passData);
const store = mainStore();
const passLogin = () => {
  store
    .passLogin(passData)
    .then((res: any) => {
      // console.log(res.code != 0);
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
          message: "登录成功",
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
    <input type="email" v-model="email" />
  </label>
  <label>
    <span>密码</span>
    <input type="password" v-model="password" />
  </label>
  <p class="forgot-pass"><a href="javascript:">忘记密码？</a></p>
  <button type="button" class="submit" @click="passLogin">登 录</button>
</template>

<style lang="scss" scoped>
@import url(@/assets/css/login.css);
</style>
