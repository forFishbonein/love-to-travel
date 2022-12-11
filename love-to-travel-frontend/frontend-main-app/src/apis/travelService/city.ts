import httpRequest from "@/request";
// import { passLoginInfo, codeLoginInfo } from "@/apis/userService/uInterface";
export const getCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "/travelservice/city",
    loading: true, //显示加载圈
  });
};
export const getHotCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "/travelservice/city/hot",
    loading: true, //显示加载圈
  });
};
