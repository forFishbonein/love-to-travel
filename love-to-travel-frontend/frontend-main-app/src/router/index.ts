import {
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from "vue-router";

// 1. 定义路由组件， 注意，这里一定要使用 文件的全名（包含文件后缀名）
// import countIndex from "../pages/count/countIndex.vue";

// 2. 定义路由配置
import { routes } from "./router.config";

// 3. 创建路由实例
const router = createRouter({
  history: createWebHashHistory(import.meta.env.VITE_PUBLIC_PATH),
  routes,
  strict: true,
  scrollBehavior: () => ({ left: 0, top: 0 }),
});

// 4. 配置路由守卫
router.beforeEach((to, from, next) => {
  next();
});

router.afterEach((to, from, next) => {});

// 5. 导出router
export default router;
