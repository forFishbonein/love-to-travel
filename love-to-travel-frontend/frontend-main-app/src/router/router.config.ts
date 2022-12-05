import { RouteRecordRaw } from "vue-router";
import Index from "@/Index.vue";
import LoginAndRegister from "@views/LoginAndRegister.vue";
import Home from "@views/Home.vue";
import TravelPlan from "@views/TravelPlan.vue";
import PassLogin from "@/components/passOrCode/PassLogin.vue";
import CodeLogin from "@/components/passOrCode/CodeLogin.vue";
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
    redirect: "/home", //Index中包裹Home等组件
    children: [
      {
        path: "/home",
        name: "Index",
        // component: () => import("@/views/Home.vue"), //这个不行，会加载不出来
        component: Home,
        meta: { title: "首页", keepAlive: true, showTab: true },
      },
    ],
  },
  {
    path: "/login",
    name: "LoginAndRegister",
    component: LoginAndRegister,
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
        // component: () => import("@/components/passOrCode/PassLogin.vue"),
        component: PassLogin,
        meta: { title: "密码登录", keepAlive: false, showTab: true },
      },
      {
        path: "codeLogin",
        name: "CodeLogin",
        // component: () => import("@/components/passOrCode/CodeLogin.vue"),
        component: CodeLogin,
        meta: { title: "验证码登录", keepAlive: false, showTab: true },
      },
    ],
  },
  {
    path: "/plan",
    name: "TrvalPlan",
    component: TravelPlan,
    meta: {
      title: "行程制定地图页",
      keepAlive: false,
      showTab: true,
    },
    // redirect: "/login/passLogin", //LoginAndRegister中包裹passLogin
    children: [
      // {
      //   path: "passLogin",
      //   name: "PassLogin",
      //   // component: () => import("@/components/passOrCode/PassLogin.vue"),
      //   component: PassLogin,
      //   meta: { title: "密码登录", keepAlive: false, showTab: true },
      // },
    ],
  },
];
