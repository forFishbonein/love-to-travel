import Index from "@/Index.vue";
import Home from "@/views/Home.vue";

export const routes = [
    {
        path: "/",
        name: "Index", //App中包裹Index
        meta: {title: "首页"},
        component: Index,
        redirect: "/home", //Index中包裹Home等组件
        children: [
            {
                path: "/home",
                name: "Index",
                component: Home,
                meta: {title: "首页"},
            },
            {
                path: "/province",
                name: "Province",
                component: () => import("@/components/serviceManage/Province.vue"),
                meta: {title: "省份管理"},
            },
            {
                path: "/city",
                name: "City",
                component: () => import("@/components/serviceManage/City.vue"),
                meta: {title: "城市管理"},
            },
            {
                path: "/note",
                name: "Note",
                component: () => import("@/components/serviceManage/note.vue"),
                meta: {title: "游记管理"},
            },
            {
                path: "/user",
                name: "User",
                component: () => import("@/components/userService/User.vue"),
                meta: {title: "用户管理"},
            },
            {
                path: "/scenery",
                name: "Scenery",
                component: () => import("@/components/serviceManage/Scenery.vue"),
                meta: {title: "景区管理"},
            },
            {
              path: "/log",
              name: "Log",
              component: () => import("@/components/userService/Log.vue"),
              meta: {title: "登录日志"},
          },
            
        ],
    },
];
