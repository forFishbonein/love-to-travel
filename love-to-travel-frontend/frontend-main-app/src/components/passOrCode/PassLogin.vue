<script setup lang="ts">
/**
 * @description  密码登录
 */
import { mainStore } from "@/store/user";
import { reactive, toRefs } from "vue";
import { useRouter } from "vue-router";
import i18n from "@/locales";
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
  if (email.value !== "" && password.value !== "") {
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
    <input
      type="email"
      v-model="email"
      style="background-color: Transparent"
      required
    />
  </label>
  <label>
    <span>{{ $t("main.password") }}</span>
    <input
      type="password"
      v-model="password"
      style="background-color: Transparent"
      required
    />
  </label>
  <p class="forgot-pass">
    <a href="javascript:;">{{ $t("messages.forgetPass") }}</a>
  </p>
  <button type="button" class="submit" @click="passLogin">
    {{ $t("buttons.login") }}
  </button>
</template>

<style lang="scss" scoped>
@import url(@/assets/css/login.css);
.font-pass {
  margin: 0;
}
</style>
