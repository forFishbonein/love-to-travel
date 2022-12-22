import { defineStore } from "pinia";
export const canvasStore = defineStore("canvas", {
  state: () => ({
    modelFlag: false,
  }),
  getters: {},
  actions: {},
  persist: {
    key: "canvas",
    storage: window.localStorage,
  },
});
