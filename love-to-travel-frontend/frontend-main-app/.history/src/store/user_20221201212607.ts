import { defineStore } from "pinia";
import { getToken, removeToken, setToken } from "@request/token";
import { UserInfo } from "@/apis/interface";
export interface UserState {
  userInfo: UserInfo;
  token: string;
}
export const mainStore = defineStore("main", {
  state: () =>
    ({
      userInfo: {},
      token: getToken(),
    } as UserState),
  getters: {},
  actions: {
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
  // persist: {
  //   key: "main",
  //   storage: window.localStorage,
  //   // paths: ['name'],
  //   overwrite: true,
  // },
});
