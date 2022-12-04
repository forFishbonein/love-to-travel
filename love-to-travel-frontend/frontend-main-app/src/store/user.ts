import { defineStore } from "pinia";
import { getToken, removeToken, setToken } from "@request/token";
import { UserInfo } from "@/apis/interface";
import { passLogin, codeLogin, logout } from "@/apis/login";
import { register } from "@/apis/register";
import { getUserInfo } from "@/apis/user";

export interface UserState {
  userInfo: UserInfo;
  token: string;
  // pinia: string;
}
export const mainStore = defineStore("main", {
  state: () =>
    ({
      userInfo: {
        email: "",
      },
      token: getToken() || "",
      // pinia: "hello world", //测试
    } as UserState),
  getters: {},
  actions: {
    passLogin(passData: any) {
      return new Promise((resolve, reject) => {
        try {
          passLogin(passData).then((res) => {
            console.log(res.data);
            this.$state.token = res.data;
            setToken(res.data);
            resolve(res);
          });
        } catch (error) {
          console.log(error);
          reject(error);
        }
      });
    },
    codeLogin(codeData: any) {
      return new Promise((resolve, reject) => {
        try {
          codeLogin(codeData).then((res) => {
            console.log(res.data);
            this.$state.token = res.data;
            setToken(res.data);
            resolve(res);
          });
        } catch (error) {
          console.log(error);
          reject(error);
        }
      });
    },
    getUserInfo() {
      return new Promise((resolve, reject) => {
        getUserInfo(this.$state.token)
          .then((res) => {
            if (res.code === 0) {
              this.$state.userInfo = res.data;
              // alert("调用store方法获取用户信息");
              console.log(this.$state.userInfo);
              resolve(res);
            } else {
              //@ts-ignore
              this.$state.userInfo = {}; //清空对象
              removeToken();
              resolve(res);
            }
          })
          .catch((error) => {
            //@ts-ignore
            this.$state.userInfo = {}; //清空对象
            removeToken();
            reject(error);
          });
      });
    },
    register(registerData: any) {
      return new Promise((resolve, reject) => {
        try {
          register(registerData).then((res) => {
            console.log(res.data);
            this.$state.token = res.data;
            setToken(res.data);
            resolve(res);
          });
        } catch (error) {
          console.log(error);
          reject(error);
        }
      });
    },
    fedLogOut() {
      return new Promise((resolve, reject) => {
        try {
          removeToken();
          // this.helloPinia = "";
          resolve("用户超时登出成功"); //这里传参不能为空，ts严格检查
        } catch (error) {
          reject(error);
        }
      });
    },
    logout() {
      return new Promise((resolve, reject) => {
        logout(this.$state.token)
          .then((res) => {
            console.log(res);
            if (res) {
              //@ts-ignore
              this.$state.userInfo = {}; //清空对象
              console.log(this.$state.userInfo);
              removeToken();
              resolve(res);
            }
          })
          .catch((error) => {
            console.log(error);
            reject(error);
          });
      });
    },
  },
  persist: true, //开启缓存
});
