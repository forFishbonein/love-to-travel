import {createRouter, createWebHistory,} from "vue-router";
import {routes} from "./router.config";


// 3. 创建路由实例
const router = createRouter({
    history: createWebHistory(),
    routes,
});

// // 4. 配置路由守卫
// router.beforeEach((to, from, next) => {
//   // 如果本地存在token
//   if (getToken() && getToken() !== "") {
//     if (
//       to.path === "/login" ||
//       to.path === "/login/passLogin" ||
//       to.path === "/login/codeLogin"
//     ) {
//       //如果是从内部由路径跳转到登录页面，拦截拦截，回到首页
//       next({ path: "/" });
//     } else {
//       if (
//         //@ts-ignore
//         store.flag == false
//       ) {
//         // 如果还没有获取到用户信息
//         // alert("获取用户信息");
//         store
//           .getUserInfo() // 获取用户信息
//           .then((res) => {
//             // alert("获取用户信息成功");
//             console.log("用户信息：");
//             console.log(store.userInfo);
//             console.log(store.userInfo.email);
//             // console.log(store.userInfo.email.length);
//             // alert("跳转");
//             store.flag = true;
//             setFlag(true);
//             next();
//           })
//           .catch(() => {
//             //@ts-ignore
//             ElMessage({
//               type: "warning",
//               message: "登录已过期",
//             });
//             next();
//           });
//       } else {
//         // 如果已经获取到了用户信息
//         // alert("跳转2");
//         next();
//       }
//     }
//   } else {
//     //如果本地不存在token //但如果是要去到需要登录的页面
//     if (to.matched.some((r) => r.meta.requireLogin)) {
//       //@ts-ignore
//       ElMessage({
//         type: "warning",
//         message: "未登录",
//       });
//       //@ts-ignore //不知道会不会出错
//       router.push(-1);
//     } else {
//       // alert("跳转3");
//       next(); //其余不需要登录的页面就直接放开
//     }
//   }
// });

// router.afterEach((to, from, next) => {});

// 5. 导出router
export default router;
