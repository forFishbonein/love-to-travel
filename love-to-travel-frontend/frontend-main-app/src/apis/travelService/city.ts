import httpRequest from "@/request";
// import { passLoginInfo, codeLoginInfo } from "@/apis/userService/uInterface";
export const getCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/city",
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
