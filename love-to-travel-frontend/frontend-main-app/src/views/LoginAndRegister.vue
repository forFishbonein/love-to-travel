<script lang="ts" setup>
import { onMounted, reactive, toRefs, ref, computed } from "vue";
import { loginOrRegister } from "@/assets/js/login";
import { mainStore } from "@/store/user";
import {
  a,
  count,
  curCount,
  InterValObj,
  setRemainTime,
  getCode,
} from "@/utils/getCode2";
import { Input } from "postcss";
import { useRouter } from "vue-router";
import { hasScopeRef } from "@vue/compiler-core";
import { imageStore } from "@/store/image";

const router = useRouter();
const store = mainStore();
const imgStore = imageStore();
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
  if (
    name.value !== "" &&
    email3.value !== "" &&
    code2.value !== "" &&
    password.value !== ""
  ) {
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
          // @ts-ignore
          ElMessage({
            type: "warning",
            message: "欢迎回来，亲爱的爱宝儿~",
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
      message: "请先将信息填写完整哦~",
    });
  }
};
// const background = computed(() => {
//   return import(`@/assets/images/shanghai.png`);
// });
// computed:{
//     itemImg() {
//       return (item) => {
//         return require(`../assets/image/main/menu_${item.id}.png`)
//       }
//     }
// }
const imgNameList = [
  {
    name: "上海",
    urlName: "shanghai",
  },
  {
    name: "贡嘎雪山",
    urlName: "gongga",
  },
  {
    name: "黄鹤楼",
    urlName: "huanghelou",
  },
  {
    name: "九寨沟",
    urlName: "jiuzhaigou",
  },
  {
    name: "泰山",
    urlName: "taishan",
  },
  {
    name: "珠穆朗玛峰",
    urlName: "zhufeng",
  },
];

const selectBackName = ref(imgStore.imageNameUrl);
const background = ref(`url("/images/back/${imgStore.imageNameUrl}.jpg")`); //在js的ref中和html中路径只能是static下的，在css中可以是assets下的
const selectBackGround = () => {
  // alert(selectBackName.value);
  imgStore.imageNameUrl = selectBackName.value;
  location.reload();
};

onMounted(() => {
  loginOrRegister();
});
/*
或者：
    :style="{
      backgroundImage: 'url(' + background + ')',
      backgroundRepeat: 'no-repeat',
      backgroundPosition: 'fixed',
      backgroundSize: 'cover',
    }" */
/* :style="{ backgroundImage: 'url(' + background + ')' } */
</script>

<template>
  <div class="img-selecter">
    <el-select
      v-model="selectBackName"
      class="m-2"
      placeholder="请选择背景图片"
    >
      <el-option
        v-for="item in imgNameList"
        :key="item.urlName"
        :label="item.name"
        :value="item.urlName"
        @click="selectBackGround"
      />
    </el-select>
  </div>
  <div id="login-warp">
    <div class="content">
      <div class="form sign-in">
        <!-- router所在地 -->
        <router-view />
        <button type="button" class="fb-btn" @click="changeLogin">
          使用 <span>{{ flag === false ? "验证码" : "密码" }}</span> 登录
        </button>
      </div>
      <div class="sub-cont">
        <div class="img">
          <div class="img__text m--up">
            <h2 style="color: #ffffff !important">还未注册？</h2>
            <p>立即注册，成为爱宝儿！</p>
          </div>
          <div class="img__text m--in">
            <h2 style="color: #ffffff !important">已有帐号？</h2>
            <p>有帐号就登录吧，好久不见了！</p>
          </div>
          <div class="img__btn">
            <span class="m--up" style="font-weight: 700">注 册</span>
            <span class="m--in" style="font-weight: 700">登 录</span>
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
  </div>
</template>

<style scoped lang="scss">
@import url(@/assets/css/login.css);
#login-warp {
  width: 100%;
  height: 100%;
  // background-image: url("@/assets/images/shanghai.jpg");
  background-image: v-bind(background);
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;
}
.sign-up > label {
  margin: 0 auto;
}
.passWord {
  position: relative;
}
.getCode {
  margin-top: 15px;
  width: 100px;
  height: 26px;
  background-color: #ffffff;
  position: absolute;
  right: 190px;
  line-height: 22px;
  border-radius: 10px;
  border: 2px solid transparent;
  cursor: pointer;
  color: #909399;
  font-size: 14px;
  border: 2px solid transparent;
  transition: all 0.3s linear;
}
.getCode:hover {
  border-color: #e8604c;
  background-color: #e8604c;
  color: #ffffff;
}
.register {
  margin-top: 60px;
}
.img-selecter {
  width: 160px;
  height: 40px;
  position: fixed;
  top: 20px;
  right: 20px;
}
.el-select-dropdown__item {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
