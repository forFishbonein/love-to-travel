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
