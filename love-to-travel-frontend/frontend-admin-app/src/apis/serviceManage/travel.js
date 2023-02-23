import httpRequest from "@/request";

export const getProvinceInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/scenery",
    loading: false, //显示加载圈
  });
};
