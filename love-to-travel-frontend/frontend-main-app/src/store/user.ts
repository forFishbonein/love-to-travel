import { defineStore } from "pinia";
import { getToken, removeToken, setToken } from "@request/token";
import { UserInfo } from "@/apis/interface";
import { passLogin, codeLogin, getUserInfo, logout } from "@/apis/login";
import { register } from "@/apis/register";
export interface UserState {
  userInfo: UserInfo;
  token: string;
  // pinia: string;
}
export const mainStore = defineStore("main", {
  state: () =>
    ({
      userInfo: {},
      token: getToken(),
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
  },
  persist: true,
});
