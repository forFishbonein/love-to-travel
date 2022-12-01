import axios from "axios";
import { getToken } from "@request/token";
import { mainStore } from "@store/store";
import pinia from "@store/index"; // 实际上这个pinia是createPinia()，这里必须传入，因为axios和router都获取不到全局的pinia

const store = mainStore(pinia);

const service = axios.create({
  // baseURL: "/static/json",
  baseURL: "http://localhost:8888", // 所有的请求地址前缀部分
  timeout: 60000, // 请求超时时间毫秒
  withCredentials: true, // 异步请求携带cookie
  headers: {
    // 设置后端需要的传参类型
    "Content-Type": "application/json",
    token: "your token",
    "X-Requested-With": "XMLHttpRequest",
  },
});

service.interceptors.request.use(
  (config) => {
    // config.headers['Content-Type'] = 'application/json;charset=utf-8';
    if (store.token) {
      config.headers["Authorization"] = getToken();
    }
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

service.interceptors.response.use(
  (response) => {
    if (response.headers["session_time_out"] == "timeout") {
      store.fedLogOut();
      // store.dispatch("fedLogOut"); //vuex的写法
    }

    const res = response.data; //返回数据就是Promise的data，即整个后端返回的对象
    if (res.code !== 0) {
      console.log(response);
      console.log(res);
      console.log("code不为0,默认报错!!!!要加一下code");

      //90001 Session超时
      // if (res.code === 90001) {
      //   return Promise.reject("error");
      // }

      //90002 用户未登录
      // if (res.code === 90002) {
      // Message({
      //   type: "warning",
      //   showClose: true,
      //   message: res.msg
      // });

      //   return Promise.reject("error");
      // }

      return Promise.reject(res.msg);
    } else {
      return res; //成功的时候返回res，后面在使用数据的时候直接res.data
    }
  },
  (error) => {
    // Message({
    //   type: "warning",
    //   showClose: true,
    //   message: "连接超时"
    // });
    return Promise.reject("error");
  }
);

export default service;
