import { defineStore } from "pinia";
export const imageStore = defineStore("image", {
  state: () => ({
    imageNameUrl: "shanghai",
  }),
  getters: {},
  actions: {},
  persist: {
    key: "image",
    storage: window.localStorage,
  },
});
