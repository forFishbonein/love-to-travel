import { defineStore } from "pinia";
import { getToken, removeToken, setToken } from "@request/token";

export const mainStore = defineStore("main", {
  state: () => {
    return {
      helloPinia: "Hello Pinia!",
      token: getToken(),
    };
  },
  getters: {},
  actions: {
    // alertMessage(this, action, msg) {
    //   this.$message({
    //     message: msg,
    //     type: action,
    //   });
    // },
    fedLogOut() {
      return new Promise((resolve, reject) => {
        try {
          // commit("SET_TEACHEREMAIL", "");
          // commit("SET_TEACHERNAME", "");
          // commit("SET_TEACHERID", "");
          // commit("SET_TEACHERSID", "");
          // commit("SET_TEACHERTITLE", "");
          // commit("SET_TEACHERTELE", "");
          // commit("SET_CREATETIME", "");
          // commit("SET_UPDATETIME", "");
          removeToken();
          // this.helloPinia = "";
          resolve("用户超时登出成功"); //这里传参不能为空，ts严格检查
        } catch (error) {
          reject(error);
        }
      });
    },
  },
});
