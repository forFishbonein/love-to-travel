import httpRequest from "@/request";
/* 添加想去 */
export const addCityToWant = (data: any) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/foot/want",
    data: data,
    // loading: false, //显示加载圈
  });
};
