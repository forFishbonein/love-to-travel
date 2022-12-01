//注意：appContext.config.globalProperties与proxy基本上是等价的，这里我们选用globalProperties
import { ComponentInternalInstance, getCurrentInstance } from "vue";
export default function useCurrentInstance() {
  const { appContext } = getCurrentInstance() as ComponentInternalInstance;
  const globalProperties = appContext?.config.globalProperties;
  return {
    globalProperties,
  };
}
