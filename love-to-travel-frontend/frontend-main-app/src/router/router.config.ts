import { RouteRecordRaw } from "vue-router";
import Index from "@/Index.vue";
import LoginAndRegister from "@views/LoginAndRegister.vue";
import Home from "@views/Home.vue";
import GoTravel from "@views/goTravel/GoTravel.vue";
import City from "@/views/goTravel/city/City.vue";
import Scenery from "@/views/goTravel/scenery/Scenery.vue";
import TravelPlan from "@/views/makePlan/TravelPlan.vue";
import TravelPlanResult from "@/views/makePlan/TravelPlanResult.vue";
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
      {
        path: "/goTravel",
        name: "GoTravel",
        component: GoTravel,
        meta: { title: "去旅游", keepAlive: false, showTab: true },
        // redirect: "/goTravel/city",
        children: [
          {
            path: "city",
            name: "City",
            // component: () => import("@/views/goTravel/city/City.vue"),
            component: City,
            meta: {
              title: "城市页",
              keepAlive: false,
              showTab: true,
            },
            redirect: "/goTravel/city/list",
            children: [
              {
                path: "list",
                name: "CityList",
                component: () => import("@/views/goTravel/city/CityList.vue"),
                meta: {
                  title: "城市列表",
                  keepAlive: false,
                  showTab: true,
                },
              },
              {
                path: "detail",
                name: "CityDetail/:cityId",
                component: () => import("@/views/goTravel/city/CityDetail.vue"),
                meta: {
                  title: "城市详情",
                  keepAlive: false,
                  showTab: true,
                },
                props(route) {
                  return {
                    cityId: route.params.cityId,
                  };
                },
              },
            ],
          },
          {
            path: "scenery",
            name: "Scenery",
            component: Scenery,
            meta: {
              title: "景区页",
              keepAlive: false,
              showTab: true,
            },
            redirect: "/goTravel/scenery/list",
            children: [
              {
                path: "list",
                name: "SceneryList",
                component: () =>
                  import("@/views/goTravel/scenery/SceneryList.vue"),
                meta: {
                  title: "景区列表",
                  keepAlive: false,
                  showTab: true,
                },
              },
              {
                path: "detail",
                name: "SceneryDetail/:sceneryId",
                component: () =>
                  import("@/views/goTravel/scenery/SceneryDetail.vue"),
                meta: {
                  title: "景区详情",
                  keepAlive: false,
                  showTab: true,
                },
                props(route) {
                  return {
                    sceneryId: route.params.sceneryId,
                  };
                },
              },
            ],
          },
        ],
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
    //把route对象直接拆出属性
    props({ params: { fromCity, toCity, goDate } }) {
      return {
        fromCity,
        toCity,
        goDate,
      };
    },
  },
  {
    path: "/result",
    name: "TrvalPlanResult",
    component: TravelPlanResult,
    meta: {
      title: "行程制定结果页",
      keepAlive: false,
      showTab: true,
    },
    // redirect: "/result/route/list", //这里不能重定向，否则下面传的props就没了
    // 把route对象直接拆出属性
    props({ params: { fromTheCity, wantCitys, backCity, goTheDate, budget } }) {
      return {
        fromTheCity,
        wantCitys,
        backCity,
        goTheDate,
        budget,
      };
    },
    children: [
      {
        path: "route",
        name: "Route",
        component: () => import("@/components/planResult/Route/Route.vue"),
        meta: { title: "路线", keepAlive: false, showTab: true },
        // redirect: "/result/route/list",
        children: [
          {
            path: "list/:searchCityId",
            name: "RouteList",
            component: () =>
              import("@/components/planResult/Route/RouteList.vue"),
            meta: { title: "路线列表", keepAlive: false, showTab: true },
            beforeEnter: (to, from, next) => {
              // 路由独享守卫
              // alert(99999);
              next();
            },
            props(route) {
              return {
                id: route.params.searchCityId,
              };
            },
          },
          {
            path: "detail",
            name: "RouteDetail",
            component: () =>
              import("@/components/planResult/Route/RouteDetail.vue"),
            meta: { title: "路线详情", keepAlive: false, showTab: true },
            props(route) {
              return {
                routeDetailInfoString: route.params.routeDetailInfo,
              };
            },
          },
        ],
      },
      {
        path: "scenicSpot",
        name: "ScenicSpot",
        component: () =>
          import("@/components/planResult/ScenicSpot/ScenicSpot.vue"),
        meta: { title: "景区", keepAlive: false, showTab: true },
        // redirect: "/result/route/detail",
        children: [
          {
            path: "list/:searchCityId",
            name: "ScenicSpotList",
            component: () =>
              import("@/components/planResult/ScenicSpot/ScenicSpotList.vue"),
            meta: { title: "景区列表", keepAlive: false, showTab: true },
            props(route) {
              return {
                id: route.params.searchCityId,
              };
            },
          },
          {
            path: "detail",
            name: "ScenicSpotDetail",
            component: () =>
              import("@/components/planResult/ScenicSpot/ScenicSpotDetail.vue"),
            meta: { title: "景区详情", keepAlive: false, showTab: true },
            props(route) {
              return {
                sceneryDetailInfoString: route.params.sceneryDetailInfo,
              };
            },
          },
        ],
      },
    ],
  },
];
