import { defineStore } from "pinia";
export const cityStore = defineStore("city", {
  state: () => ({
    searchCityId: "",
  }),
  getters: {},
  actions: {},
  persist: true,
});
