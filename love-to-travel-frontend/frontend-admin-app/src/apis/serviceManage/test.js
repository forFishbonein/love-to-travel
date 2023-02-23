import httpRequest from "@/request";

export const getProvinceInfo = () => {
  return httpRequest({
    method: "get",
    url: "/province",
    loading: false, //显示加载圈
  });
};
export const getCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "/city",
    loading: false, //显示加载圈
  });
};
export const getHotCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "/city/hot",
    loading: false, //显示加载圈
  });
};
