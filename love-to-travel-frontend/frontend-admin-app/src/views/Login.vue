<template>
  <div class="container">
    <div class="left-board">
      <p>欢迎登录</p>
      <span>爱旅游管理系统</span>
    </div>
    <div class="right-board">
      <div style="margin-top: 90px">
        <input
          class="input-box"
          type="text"
          v-model="email"
          placeholder="请输入邮箱"
        />
      </div>
      <div style="margin-top: 30px">
        <input
          class="input-box"
          type="password"
          v-model="password"
          placeholder="请输入密码"
        />
      </div>
      <div class="login-button" @click="doLogin(this.email, this.password)">
        登录
      </div>
    </div>
  </div>
</template>

<script>
import { login } from "@apis/login.js";

export default {
  name: "Login",
  data() {
    return {
      email: "",
      password: "",
    };
  },
  mounted() {},
  methods: {
    doLogin(email, password) {
      console.log({ email: this.email, password: this.password });
      login({ email: this.email, password: this.password }).then((response) => {
        console.log(response.data === "登陆成功");
        console.log("登陆成功");
        console.log("=====");
        console.log(response.data);
        console.log("=====");
        if (response.code === 0) {
          this.$router.push({
            path: "/home",
            query: { email: this.email },
          });
        }

        // this.$session.set("key", value);
        console.log(response);
      });
    },
  },
};
</script>

<style scoped>
.container {
  margin: 100px auto;
  width: 1200px;
  height: 400px;
  box-sizing: border-box;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  border-radius: 4px;
}

.left-board {
  float: left;
  width: 600px;
  height: 400px;
  background-color: rgba(234, 84, 65, 0.75);
  box-sizing: border-box;
  border-bottom-left-radius: 4px;
  border-top-left-radius: 4px;
}

.left-board p {
  padding-top: 120px;
  padding-left: 20px;
  font-size: 38px;
  font-weight: bold;
  color: white;
}

.left-board span {
  padding-left: 20px;

  font-size: 20px;
  color: white;
}

.right-board {
  float: right;
  width: 600px;
  height: 400px;
  background-color: #fff;
  box-sizing: border-box;
  border-bottom-right-radius: 4px;
  border-top-right-radius: 4px;
}

.input-box {
  margin-left: 20px;
  padding: 8px;
  width: 90%;
  height: 40px;
  font-size: 18px;
  border-bottom: 1px solid #efefef;
}

.login-button {
  margin-top: 20px;
  margin-left: 20px;
  width: 65px;
  height: 30px;
  color: white;
  background-color: #e8604c;
  text-align: center;
  line-height: 30px;
  border-radius: 4px;
}
</style>
