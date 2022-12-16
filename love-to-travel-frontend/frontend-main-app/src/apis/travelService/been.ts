import httpRequest from "@/request";
/* 添加去过 */
export const addCityToBeen = (data: any) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/foot/been",
    data: data,
    loading: true, //显示加载圈
  });
};
