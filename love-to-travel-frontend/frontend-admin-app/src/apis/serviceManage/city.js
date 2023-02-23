import httpRequest from "@/request";
export const getCityInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/city",
    loading: false, //显示加载圈
  });
};

export const getPageCityInfo = (page) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/city/page",
    data: page,
    loading: false, //显示加载圈
  });
};
