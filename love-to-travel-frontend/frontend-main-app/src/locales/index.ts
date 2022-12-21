// i18n.ts
import { App } from "vue";
import { createI18n } from "vue-i18n";
import zhCn from "./zh-cn";
import en from "./en";

const i18n = createI18n({
  legacy: false,
  globalInjection: true, // 全局模式，可以直接使用 $t 调用 i18n
  locale: "zhCn", //默认为中文模式
  fallbackLocale: "en",
  // @ts-ignore
  messages: {
    zhCn,
    en,
  },
});
export default i18n;
