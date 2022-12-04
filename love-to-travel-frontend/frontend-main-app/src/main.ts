import { createApp } from "vue";
import pinia from "@/store";
import App from "./App.vue";
import router from "@/router";
// 引入element-plus的封装方法
import { setupElementPlus } from "@plugins/elementPlus/index";
// 引入全局样式
import "./styles/index.scss";
import { mainStore } from "@/store/user";
// 引入全局基本样式
import "@/style.css";
//创建实例
const setupAll = async () => {
  const app = createApp(App);
  //@ts-ignore
  // window.vm = app;
  setupElementPlus(app);
  app.use(router);
  app.use(pinia);
  app.mount("#app");
};

setupAll();
export const store = mainStore();
