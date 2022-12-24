import { defineStore } from "pinia";
import { theCityScenerysInfoType } from "@/apis/interface/myInterface";
import { getRealTimeRecommendScenerysList } from "@/apis/py/scenery";
export const realtimeStore = defineStore("realtime", {
  persist: {
    key: "realtime",
    storage: window.localStorage,
  },
  state: () => ({
    realTimeRecommendscenerys: [] as theCityScenerysInfoType[],
    browseList: [] as Array<string>,
    getRealTimeFlag: false,
  }),
  getters: {},
  actions: {
    getRealTimeRecommendSceneryFromPy(id1: string, id2: string, id3: string) {
      this.$state.browseList = []; //这里保证了在list页面请求未完成之前，进入detail页面不会再次发送请求
      return new Promise((resolve, reject) => {
        try {
          getRealTimeRecommendScenerysList(id1, id2, id3).then((res) => {
            // alert("得到了哈哈");
            console.log(res.data);
            this.$state.realTimeRecommendscenerys = res.data;
            resolve(res);
          });
        } catch (error) {
          console.log(error);
          reject(error);
        }
      });
    },
  },
});
