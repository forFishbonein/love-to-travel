<script lang="ts" setup>
import { onMounted, reactive, toRefs } from "vue";
import { loginOrRegister } from "@/assets/js/login";
import { mainStore } from "@/store/user";
import { postCode } from "@/apis/register";
import { Input } from "postcss";
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
          message: "登录成功",
        });
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

// interface codeLoginInfo {
//   email2: string;
//   code: string;
// }
// const codeData: codeLoginInfo = reactive({
//   email2: "",
//   code: "",
// });
// const { email2, code } = toRefs(codeData);
// const codeLogin = () => {
//   store
//     .codeLogin(codeData)
//     .then((res: any) => {
//       console.log(res.code != 0);
//       if (res.code != 0) {
//         //@ts-ignore
//         ElMessage({
//           type: "error",
//           message: res.msg,
//         });
//       } else {
//         //@ts-ignore
//         ElMessage({
//           type: "success",
//           message: res.msg,
//         });
//       }
//     })
//     .catch((error) => {
//       //@ts-ignore
//       ElMessage({
//         type: "error",
//         message: error.message,
//       });
//     });
// };

interface registerInfo {
  name: string;
  email3: string;
  code2: string;
}
const registerData: registerInfo = reactive({
  name: "",
  email3: "",
  code2: "",
});
const { name, email3, code2 } = toRefs(registerData);

let a: HTMLInputElement = document.createElement("input");
let count = 30;
let curCount = 0;
let InterValObj: number = 0;
const setRemainTime = () => {
  if (curCount == 0) {
    console.log("停止");
    window.clearInterval(InterValObj);
    a.disabled = false;
    a.style.backgroundColor = "#e0e0e0";
    a.setAttribute("value", "重新发送验证码");
  } else {
    curCount--;
    a.setAttribute("value", curCount + "秒后可重新发送");
  }
};
const getCode = () => {
  if (email3.value === "") {
    //@ts-ignore
    ElMessage({
      type: "warn",
      message: "请将信息填写完整",
    });
    return;
  }
  //@ts-ignore
  a = document.getElementsByClassName("getCode")[0];
  a.disabled = true;
  a.style.backgroundColor = "#f5f5f5";
  postCode({ email: registerData.email3 })
    .then((res) => {
      if (res.code == 0) {
        //@ts-ignore
        ElMessage({
          type: "success",
          message: "验证码已发送",
        });
      }
    })
    .catch((error) => {
      console.log(error);
    });
  curCount = count;
  InterValObj = window.setInterval(setRemainTime, 1000);
};
const register = () => {
  store
    .register({
      name: registerData.name,
      email: registerData.email3,
      code: registerData.code2,
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
      <button type="button" class="fb-btn">
        使用 <span>facebook</span>帐号登录
      </button>
    </div>
    <div class="sub-cont">
      <div class="img">
        <div class="img__text m--up">
          <h2>还未注册？</h2>
          <p>立即注册，发现大量机会！</p>
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
          @click="getCode"
        />

        <button type="button" class="submit register" @click="register">
          注册
        </button>
        <!-- <button type="button" class="fb-btn">
          使用 <span>facebook</span> 帐号注册
        </button> -->
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import url(@/assets/css/login.css);
.passWord {
  position: relative;
}
.getCode {
  margin-top: 15px;
  width: 100px;
  height: 30px;
  background-color: #e0e0e0;
  // float: right;
  position: absolute;

  right: 190px;
  line-height: 30px;
  border-radius: 10px;
  border: 0;
  cursor: pointer;
}
.register {
  margin-top: 60px;
}
</style>
