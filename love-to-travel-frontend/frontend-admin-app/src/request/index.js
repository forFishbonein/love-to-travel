/**
 * @description [ axios 请求封装]
 */
// 根据环境不同引入不同api地址
import axios from "axios";
import { ElLoading, ElMessage } from "element-plus";

const service = axios.create({
  // baseURL: "http://localhost:8080", // 所有的请求地址前缀部分
  timeout: 10000, // 请求超时时间毫秒
  // withCredentials: true, // 异步请求携带cookie
  headers: {
    // 设置后端需要的传参类型
    "Content-Type": "application/json",
    // token: "your token",
    "X-Requested-With": "XMLHttpRequest",
  },
});

service.interceptors.request.use(
  (config) => {
    // alert(store.pinia); //最早在这里可以获取
    // 加载动画
    if (config.loading) {
      ElLoading.service({
        lock: true,
        text: "Loading...",
        background: "rgba(0, 0, 0, 0.5)",
      });
    }

    // 在此处添加请求头等，如添加 token，这样登录之后每次请求都会自动带上token，实际上在apis中就不需要写token了
    // if (store.token) {
    //   //@ts-ignore
    //   config.headers["Authorization"] = store.token;
    // }

    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

service.interceptors.response.use(
  (response) => {
    //并不会创建一个新的loading
    const loading = ElLoading.service({
      lock: true,
      text: "Loading",
      background: "rgba(0, 0, 0, 0.5)",
    });
    loading.close();

    const res = response.data; //返回数据就是Promise的data，即整个后端返回的对象
    if (res.code !== 0) {
      console.log(res.code);
      console.log(response);
      console.log(res);
      // token 过期
      if (res.code === 401) {
        // 警告提示窗
        ElMessage({
          type: "warning",
          message: res.msg,
        });
        return;
      }
      if (res.code == 403) {
        ElMessage({
          type: "warning",
          message: res.msg,
        });
        return;
      }
      // 若后台返回错误值，此处返回对应错误对象，下面 error 就会接收
      return Promise.reject(new Error(res.msg || "Error"));
    } else {
      // 注意返回值
      return response.data;
    }
  },
  (error) => {
    if (error && error.response) {
      switch (error.response.status) {
        case 400:
          error.message = "请求错误(400)";
          break;
        case 401:
          error.message = "未授权,请登录(401)";
          break;
        case 403:
          error.message = "拒绝访问(403)";
          break;
        case 404:
          error.message = `请求地址出错: ${error.response.config.url}`;
          break;
        case 405:
          error.message = "请求方法未允许(405)";
          break;
        case 408:
          error.message = "请求超时(408)";
          break;
        case 500:
          error.message = "服务器内部错误(500)";
          break;
        case 501:
          error.message = "服务未实现(501)";
          break;
        case 502:
          error.message = "网络错误(502)";
          break;
        case 503:
          error.message = "服务不可用(503)";
          break;
        case 504:
          error.message = "网络超时(504)";
          break;
        case 505:
          error.message = "HTTP版本不受支持(505)";
          break;
        default:
          error.message = `连接错误: ${error.message}`;
      }
    } else {
      if (error.message == "Network Error") {
        error.message == "网络异常，请检查后重试！";
      }
      // error.message = "连接到服务器失败，请联系管理员qq：1558637209";
    }
    ElMessage({
      type: "warning",
      message: error.message,
    });
    return Promise.reject(error);
  }
);

export default service;
