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
import { imageStore } from "@/store/image";
// import zhCn from "element-plus/lib/locale/lang/zh-cn";
// import en from "element-plus/lib/locale/lang/en";
import { useI18n } from "vue-i18n";
import i18n from "@/locales";
import { useDark, useToggle } from "@vueuse/core";
const { locale } = useI18n();

/* 暗黑模式 */
const isDark = useDark();
const toggleDark = useToggle(isDark);

// i18n.global.t("menus.home");

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
const reg =
  /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
const register = () => {
  if (
    name.value !== "" &&
    email3.value !== "" &&
    code2.value !== "" &&
    password.value !== ""
  ) {
    if (reg.test(email3.value)) {
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
              type: "success",
              message: i18n.global.t("other.welcomeToJoin"),
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
        message: i18n.global.t("other.emailwarning"),
      });
    }
  } else {
    // @ts-ignore
    ElMessage({
      type: "warning",
      message: i18n.global.t("other.pleaseOver"),
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

/* 切换语言：i18n */
type localType = "zhCn" | "en";
// const local = ref<localType>("zhCn");
// const localLanguage = computed(() => {
//   switch (local.value) {
//     case "en":
//       return en;
//     case "zhCn":
//       return zhCn;
//   }
// });
const changeLanguage = (type: localType): void => {
  // local.value = type;
  locale.value = type; //只有在methods这才能拿到this，setup里面拿不到
};
onMounted(() => {
  loginOrRegister();
  var text = document.getElementById("password");
  var r = document.getElementById("r");
  var z = document.getElementById("z");
  var q = document.getElementById("q");
  // @ts-ignore
  text.onkeyup = function () {
    var reg = [];
    // @ts-ignore
    reg[0] = /[^a-zA-Z0-9]/g; //特殊
    // @ts-ignore
    reg[1] = /[a-zA-Z]/g; //字母
    // @ts-ignore
    reg[2] = /[0-9]/g; //数字
    // @ts-ignore
    var str = text.value;
    var len = str.length;
    var sec = 0; //密码强度
    if (len >= 6) {
      for (var i = 0; i < reg.length; i++) {
        if (str.match(reg[i])) {
          sec++;
        }
      }
    }
    if (sec == 0) {
      // @ts-ignore
      r.style.backgroundColor = "white";
      // @ts-ignore
      z.style.backgroundColor = "white";
      // @ts-ignore
      q.style.backgroundColor = "white";
    } else if (sec == 1) {
      // @ts-ignore
      r.style.backgroundColor = "#e8604c";
      // @ts-ignore
      z.style.backgroundColor = "white";
      // @ts-ignore
      q.style.backgroundColor = "white";
    } else if (sec == 2) {
      // @ts-ignore
      r.style.backgroundColor = "#E6A23C";
      // @ts-ignore
      z.style.backgroundColor = "#E6A23C";
      // @ts-ignore
      q.style.backgroundColor = "white";
    } else if (sec == 3) {
      // @ts-ignore
      r.style.backgroundColor = "#67C23A";
      // @ts-ignore
      z.style.backgroundColor = "#67C23A";
      // @ts-ignore
      q.style.backgroundColor = "#67C23A";
    }
  };
});
const backToHome = () => {
  router.push("/");
};
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
  <div class="lang-selecter">
    <el-dropdown @command="changeLanguage">
      <el-button type="primary">
        {{ $t("main.change") }}<i class="el-icon-arrow-down el-icon--right" />
      </el-button>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="zhCn">
            {{ $t("language.chinese") }}
          </el-dropdown-item>
          <el-dropdown-item command="en">
            {{ $t("language.english") }}
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
  <div class="model-selecter">
    <span
      @click.stop="toggleDark()"
      style="cursor: pointer"
      class="font-black-style"
      >暗黑模式</span
    >
    <el-switch size="small" v-model="isDark" style="margin-left: 10px" />
  </div>
  <div class="img-selecter">
    <el-select
      v-model="selectBackName"
      class="m-2"
      :placeholder="$t('main.pleaseSelect')"
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
  <div class="back-index" @click="backToHome">
    <a href="javascript:;">回到首页</a>
  </div>
  <div id="login-warp">
    <div class="content">
      <div class="form sign-in">
        <!-- router所在地 -->
        <router-view />
        <button type="button" class="fb-btn" @click="changeLogin">
          {{ $t("main.use") }}
          <span>{{
            flag === false ? $t("main.code") : $t("main.password")
          }}</span>
          {{ $t("buttons.login") }}
        </button>
      </div>
      <div class="sub-cont">
        <div class="img">
          <div class="img__text m--up">
            <h2 style="color: #ffffff !important">
              {{ $t("messages.notRegister") }}
            </h2>
            <p>{{ $t("messages.register") }}</p>
          </div>
          <div class="img__text m--in">
            <h2 style="color: #ffffff !important">
              {{ $t("messages.haveAccount") }}
            </h2>
            <p>{{ $t("messages.login") }}</p>
          </div>
          <div class="img__btn">
            <span class="m--up" style="font-weight: 700">{{
              $t("buttons.register")
            }}</span>
            <span class="m--in" style="font-weight: 700">{{
              $t("buttons.login")
            }}</span>
          </div>
        </div>
        <div class="form sign-up">
          <h2 class="font-white-style">{{ $t("titles.nowRegister") }}</h2>
          <!-- <form> -->
          <label>
            <span>{{ $t("loginForm.user") }}</span>
            <input type="text" v-model="name" required />
          </label>
          <label>
            <span>{{ $t("main.password") }}</span>
            <input
              type="password"
              v-model="password"
              id="password"
              maxlength="16"
              required
            />
            <div id="qiangdu">
              密码强度:
              <span id="r">弱</span>
              <span id="z">中</span>
              <span id="q">强</span>
            </div>
          </label>
          <label>
            <span>{{ $t("loginForm.email") }}</span>
            <input type="email" v-model="email3" required />
          </label>
          <label>
            <span>{{ $t("main.code") }}</span>
            <input type="text" class="passWord" v-model="code2" required />
          </label>
          <input
            type="button"
            :value="$t('main.getcode')"
            class="getCode"
            @click="getCode(registerData.email3)"
          />
          <button type="button" class="submit register" @click="register">
            {{ $t("buttons.register") }}
          </button>
          <!-- </form> -->
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import url(@/assets/css/login.css);
#password {
  position: relative;
}
#qiangdu {
  width: 170px;
  // border: 1px #e8604c solid;
  height: auto;
  margin-top: 4px;
  position: absolute;
  bottom: 10px;
  right: 10px;
  font-size: 14px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #303133;
  // font-weight: 700;
  > span:first-child {
    margin-left: 10px;
    border-right: 0;
  }
  > span:nth-child(2) {
    border-right: 0;
  }
  span {
    border: 1px #d4d7de solid;
    font-weight: 700;
    float: right;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 10px;
    width: 30px;
    height: 30px;
    text-align: center;
    color: #303133;
  }
}

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
  top: 25px;
  left: 20px;

  // .el-select {
  //   // background-color: #e8604c;
  //   border: 1px #e8604c solid;
  //   border-radius: ;
  // }
}
.model-selecter {
  width: 150px;
  height: 80px;
  position: fixed;
  top: 100px;
  left: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  // flex-direction: column;
  color: #ffffff;
  font-size: 16px;
}
.lang-selecter {
  width: 140px;
  height: 30px;
  position: fixed;
  top: 80px;
  left: 30px;
  .el-dropdown {
    width: 140px;
    height: 30px;
    .el-button {
      border: 2px #e8604c solid;
    }
  }
}
.back-index {
  width: 100px;
  height: 30px;
  position: fixed;
  top: 30px;
  right: 30px;
  // border: 1px #e8604c solid;
  border-radius: 20px;
  background-color: rgba(255, 255, 255, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  color: #606266;
  font-size: 15px;
  font-weight: 700;
  transition: 0.4s cubic-bezier(1, -2.02, 0.38, 2.05);
  animation: tittlemove3 alternate infinite 1.5s ease-in-out;
  cursor: pointer;
}
@keyframes tittlemove3 {
  0% {
  }
  50% {
    transform: translateY(-2px);
  }
  100% {
    transform: translateY(2px);
  }
}
.back-index:hover {
  transform: scale(1.08);
}
.el-select-dropdown__item {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
