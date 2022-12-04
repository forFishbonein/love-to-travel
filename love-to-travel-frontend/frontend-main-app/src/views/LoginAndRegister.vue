<script lang="ts" setup>
import { onMounted, reactive, toRefs, ref } from "vue";
import { loginOrRegister } from "@/assets/js/login";
import { mainStore } from "@/store/user";
import {
  a,
  count,
  curCount,
  InterValObj,
  setRemainTime,
  getCode,
} from "@/utils/getCode";
import { Input } from "postcss";
import { useRouter } from "vue-router";

const router = useRouter();
const store = mainStore();

/**
 * @description 密码验证码登录切换的功能
 */
let flag: boolean = false; //false代表处于密码登录页面
ref(flag);
const changeLogin = () => {
  if (flag === false) {
    //重定向到codeLogin
    router.push("/login/codeLogin");
  } else if (flag === true) {
    //重定向到passLogin
    router.push("/login/passLogin");
  } else {
    return;
  }
  flag = !flag;
  console.log(flag);
};

/**
 * @description 注册功能
 */
interface registerInfo {
  name: string;
  email3: string;
  code2: string;
  password: string;
}
const registerData: registerInfo = reactive({
  name: "",
  email3: "",
  code2: "",
  password: "",
});
const { name, email3, code2, password } = toRefs(registerData);

const register = () => {
  store
    .register({
      name: registerData.name,
      email: registerData.email3,
      code: registerData.code2,
      password: registerData.password,
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
        ElMessage({
          type: "success",
          message: "注册成功",
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
onMounted(() => {
  loginOrRegister();
});
</script>

<template>
  <div class="content">
    <div class="form sign-in">
      <router-view />
      <button type="button" class="fb-btn" @click="changeLogin">
        使用 <span>{{ flag === false ? "验证码" : "密码" }}</span
        >登录
      </button>
    </div>
    <div class="sub-cont">
      <div class="img">
        <div class="img__text m--up">
          <h2>还未注册？</h2>
          <p>立即注册，让我们一起爱上旅游！</p>
        </div>
        <div class="img__text m--in">
          <h2>已有帐号？</h2>
          <p>有帐号就登录吧，好久不见了！</p>
        </div>
        <div class="img__btn">
          <span class="m--up">注 册</span>
          <span class="m--in">登 录</span>
        </div>
      </div>
      <div class="form sign-up">
        <h2>立即注册</h2>
        <label>
          <span>用户名</span>
          <input type="text" v-model="name" />
        </label>
        <label>
          <span>密码</span>
          <input type="password" v-model="password" />
        </label>
        <label>
          <span>邮箱</span>
          <input type="email" v-model="email3" />
        </label>
        <label>
          <span>验证码</span>
          <input type="text" class="passWord" v-model="code2" />
        </label>
        <input
          type="button"
          value="获取验证码"
          class="getCode"
          @click="getCode(registerData.email3)"
        />

        <button type="button" class="submit register" @click="register">
          注册
        </button>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import url(@/assets/css/login.css);
.sign-up > label {
  margin: 0 auto;
}
.passWord {
  position: relative;
}
.getCode {
  margin-top: 15px;
  width: 100px;
  height: 30px;
  background-color: #f5f5f5;
  // float: right;
  position: absolute;

  right: 190px;
  line-height: 30px;
  border-radius: 10px;
  border: 0;
  cursor: pointer;
  color: #d4af7a;
}
.getCode:hover {
  color: black;
  border: 2px solid #d4af7a;
  height: 26px;
  line-height: 26px;
}
.register {
  margin-top: 60px;
}
</style>
