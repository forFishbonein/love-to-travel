import type { App } from "vue";

//引入二次封装的ElMessage
import { Message } from "@/utils/resetMessage";

//@ts-ignore
const plugins = [ElLoading];

//@ts-ignore
const components = [ElScrollbar];

//定义函数对app进行封装，绑定一些二次封装的El组件
export const setupElementPlus = (app: App<Element>) => {
  plugins.forEach((plugin) => {
    app.use(plugin);
  });

  components.forEach((component) => {
    app.component(component.name, component);
  });

  const ElMessageCfg = { duration: 2000, customClass: "globalElMessageStyle" };
  app.config.globalProperties.$ElMessage = (msg) => {
    //@ts-ignore
    return Message({ message: msg, ...ElMessageCfg });
  };
  app.config.globalProperties.$ElMessage.success = (msg) => {
    //@ts-ignore
    return Message.success({ message: msg, ...ElMessageCfg });
  };
  app.config.globalProperties.$ElMessage.warning = (msg) => {
    //@ts-ignore
    return Message.warning({ message: msg, ...ElMessageCfg });
  };
  app.config.globalProperties.$ElMessage.info = (msg) => {
    //@ts-ignore
    return Message.info({ message: msg, ...ElMessageCfg });
  };
  app.config.globalProperties.$ElMessage.error = (msg) => {
    //@ts-ignore
    return Message.error({ message: msg, ...ElMessageCfg });
  };
};
