import Index from "@/Index.vue";
import Home from "@/views/Home.vue";

export const routes = [
  {
    path: "/",
    name: "Index", //App中包裹Index
    meta: {
      title: "首页",
      keepAlive: false,
    },
    component: Index,
    redirect: "/home", //Index中包裹Home等组件
    children: [
      {
        path: "/home",
        name: "Index",
        component: Home,
        meta: { title: "首页" },
      },
      {
        path: "/province",
        name: "Province",
        component: () => import("@/components/serviceManage/Province.vue"),
        meta: { title: "省份管理" },
      },
      {
        path: "/city",
        name: "City",
        component: () => import("@/components/serviceManage/City.vue"),
        meta: { title: "城市管理" },
      },
      // {
      //   path: "/city",
      //   name: "City",
      //   component: () => import("@/components/serviceManage/city.vue"),
      //   meta: { title: "城市管理" },
      // },
    ],
  },
];
