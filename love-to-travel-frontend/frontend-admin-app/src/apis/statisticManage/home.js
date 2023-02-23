import httpRequest from "@/request";

export const getNewUserNum = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/user/new",
    loading: false, //显示加载圈
  });
};

export const getNewLogNum = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/log/new",
    loading: false, //显示加载圈
  });
};

export const getAllUser = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/user/all",
    loading: false, //显示加载圈
  });
};

export const getAllLog = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/log/all",
    loading: false, //显示加载圈
  });
};

export const getNewProductNum = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/product/new",
    loading: false, //显示加载圈
  });
};

export const getAllProductBuy = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/product/all",
    loading: false, //显示加载圈
  });
};

export const getNewProductCost = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/product/cost/new",
    loading: false, //显示加载圈
  });
};

export const getAllProductCost = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/product/cost/all",
    loading: false, //显示加载圈
  });
};
