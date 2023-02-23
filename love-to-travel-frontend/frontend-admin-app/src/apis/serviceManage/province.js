import httpRequest from "@/request";

export const getProvinceInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/province",
    loading: false, //显示加载圈
  });
};

export const getPageProvinceInfo = (page) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/province/page",
    data: page,
    loading: false, //显示加载圈
  });
};

export const updateProvinceInfo = (data) => {
  return httpRequest({
    method: "put",
    url: "http://localhost:8082/",
    data,
    loading: false, //显示加载圈
  });
};
