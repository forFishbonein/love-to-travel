import httpRequest from "@/request";
// import { passLoginInfo, codeLoginInfo } from "@/apis/userService/uInterface";
export const getCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/city",
    loading: true, //显示加载圈
  });
};
export const getPageCitysInfo = (page: number, limit: number) => {
  return httpRequest({
    method: "post",
    data: {
      pageNum: page,
      pageSize: limit,
    },
    url: "http://localhost:8082/city/page",
    loading: true, //显示加载圈
  });
};
export const getHotCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/city/hot",
    loading: true, //显示加载圈
  });
};
export const getOneCityInfoById = (cityId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/city/${cityId}`,
    loading: true, //显示加载圈
  });
};

export const getCitysInfoByName = (cityName: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/city/name/${cityName}`,
    // loading: true, //显示加载圈
  });
};
