import { RouteRecordRaw } from "vue-router";
// import Layout from "@/views/layouts/index.vue";
import HelloWorld from "@components/HelloWorld.vue";
export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    redirect: "/home",
    meta: {
      title: "首页",
      keepAlive: false,
    },
    component: HelloWorld,
    children: [
      //   {
      //     path: "/home",
      //     name: "Home",
      //     component: () => import("@/views/tabBar/Home.vue"),
      //     meta: { title: "首页", keepAlive: false, showTab: true },
      //   },
      //   {
      //     path: "/demo",
      //     name: "Demo",
      //     component: () => import("@/views/tabBar/Demo.vue"),
      //     meta: { title: "测试案例", keepAlive: false, showTab: true },
      //   },
      //   {
      //     path: "/pkgInfo",
      //     name: "PkgInfo",
      //     component: () => import("@/views/tabBar/PkgInfo.vue"),
      //     meta: { title: "项目信息", keepAlive: false, showTab: true },
      //   },
    ],
  },
  //   { path: "/countIndex", component: countIndex },
  //   { path: "/langshanIndex", component: langshanIndex },
];
