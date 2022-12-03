import { RouteRecordRaw } from "vue-router";
import Index from "@/Index.vue";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Index", //App中包裹Index
    meta: {
      title: "首页",
      keepAlive: false,
      // requireLogin: true, //先加在这里，表示需要登录！
    },
    component: Index,
    // redirect: "/login", //Index中包裹LoginAndRegister
    children: [
      {
        path: "/login",
        name: "LoginAndRegister",
        component: () => import("@/views/LoginAndRegister.vue"),
        meta: {
          title: "登录页",
          keepAlive: false,
          showTab: true,
        },
        redirect: "/login/passLogin", //LoginAndRegister中包裹passLogin
        children: [
          {
            path: "passLogin",
            name: "PassLogin",
            component: () => import("@/components/passOrCode/PassLogin.vue"),
            meta: { title: "测试案例", keepAlive: false, showTab: true },
          },
          {
            path: "codeLogin",
            name: "CodeLogin",
            component: () => import("@/components/passOrCode/CodeLogin.vue"),
            meta: { title: "测试案例", keepAlive: false, showTab: true },
          },
        ],
      },
      {
        path: "/index",
        name: "Index",
        component: () => import("@/views/tabBar/Demo.vue"),
        meta: { title: "测试案例", keepAlive: false, showTab: true },
      },
      {
        path: "/pkgInfo",
        name: "PkgInfo",
        component: () => import("@/views/tabBar/PkgInfo.vue"),
        meta: { title: "项目信息", keepAlive: false, showTab: true },
      },
    ],
  },
  //   { path: "/countIndex", component: countIndex },
  //   { path: "/langshanIndex", component: langshanIndex },
];
