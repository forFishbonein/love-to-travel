import {
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from "vue-router";

//router中使用pinia：
import pinia from "@/store"; // 实际上这个pinia是createPinia()，这里必须传入，因为router获取不到全局的pinia
import { mainStore } from "@/store/user";
import { getToken } from "@/request/token";
const store = mainStore(pinia);

// 1. 定义路由组件， 注意，这里一定要使用 文件的全名（包含文件后缀名）
// import countIndex from "../pages/count/countIndex.vue";

// 2. 定义路由配置
import { routes } from "./router.config";

// 3. 创建路由实例
const router = createRouter({
  // history: createWebHashHistory(import.meta.env.VITE_PUBLIC_PATH),
  history: createWebHistory(import.meta.env.VITE_PUBLIC_PATH),
  routes,
  strict: true,
  scrollBehavior: () => ({ left: 0, top: 0 }),
});

// 4. 配置路由守卫
router.beforeEach((to, from, next) => {
  // 如果本地存在token
  if (getToken()) {
    if (
      to.path === "/login" ||
      to.path === "/login/passLogin" ||
      to.path === "/login/codeLogin"
    ) {
      //如果是从内部由路径跳转到登录页面，拦截拦截，回到首页
      next({ path: "/" });
    } else {
      //如果不是跳转到登录页面！那么获取用户信息！
      if (
        //@ts-ignore
        store.userInfo.email.length === 0 ||
        store.userInfo.email === undefined
      ) {
        //如果还没有用户信息
        store
          .getUserInfo() //获取用户信息
          .then((res) => {
            // alert("获取用户信息成功");
            console.log(store.userInfo);
            next();
          })
          .catch(() => {
            //@ts-ignore
            ElMessage({
              type: "warning",
              message: "登录已过期",
            });
            next({ path: "/" });
          });
      } else {
        next();
      }
    }
  } else {
    //如果本地不存在token //但如果是要去到需要登录的页面
    if (to.matched.some((r) => r.meta.requireLogin)) {
      //@ts-ignore
      ElMessage({
        type: "warning",
        message: "未登录",
      });
      //@ts-ignore //不知道会不会出错
      router.push(-1);
    } else {
      next(); //其余不需要登录的页面就直接放开
    }
  }
});

router.afterEach((to, from, next) => {});

// 5. 导出router
export default router;
