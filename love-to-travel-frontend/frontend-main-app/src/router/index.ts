import { createRouter, createWebHashHistory, createWebHistory } from "vue-router"

// 1. 定义路由组件， 注意，这里一定要使用 文件的全名（包含文件后缀名）
// import countIndex from "../pages/count/countIndex.vue";
// import langshanIndex from "../pages/langshan/langshanIndex.vue";

// 2. 定义路由配置
const routes = [
  {
    path: "/",
    redirect: '/countIndex'
  },
//   { path: "/countIndex", component: countIndex },
//   { path: "/langshanIndex", component: langshanIndex },

];

// 3. 创建路由实例
const router = createRouter({
  // 4. 采用hash 模式
  history: createWebHashHistory(),
  // 采用 history 模式
  // history: createWebHistory(),
  routes, //使用上方定义的路由配置
});

// 4. 导出router
export default router
