import httpRequest from "@/request";
export const getProvinceInfo = () => {
  return httpRequest({
    method: "get",
    url: "/province",
    loading: true, //显示加载圈
  });
};
export const getCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "/city",
    loading: true, //显示加载圈
  });
};
export const getHotCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "/city/hot",
    loading: true, //显示加载圈
  });
};
