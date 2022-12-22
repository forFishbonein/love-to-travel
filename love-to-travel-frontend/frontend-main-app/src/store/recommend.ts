import { defineStore } from "pinia";
import { theCityScenerysInfoType } from "@/apis/interface/myInterface";
import { getRecommendScenerysList } from "@/apis/py/scenery";
export const recommendStore = defineStore("recommend", {
  persist: {
    key: "recommend",
    storage: window.localStorage,
  },
  state: () => ({
    recommendscenerys: [] as theCityScenerysInfoType[],
    getRecommendFlag: false,
  }),
  getters: {},
  actions: {
    getRecommendSceneryFromPy(userId: string) {
      return new Promise((resolve, reject) => {
        try {
          getRecommendScenerysList(userId).then((res) => {
            // alert("得到了哈哈");
            console.log(res.data);
            this.$state.recommendscenerys = res.data;
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
