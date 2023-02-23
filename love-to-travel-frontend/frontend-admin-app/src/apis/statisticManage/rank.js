import httpRequest from "@/request";

export const getStatistic = () => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/note/statistic",
    loading: false, //显示加载圈
  });
};

export const getOrder = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/product/orders",
    loading: false, //显示加载圈
  });
};

export const getSales = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/product/sales",
    loading: false, //显示加载圈
  });
};

export const getUse = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/product/use",
    loading: false, //显示加载圈
  });
};

export const getNewSalesNum = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/product/cost/new",
    loading: false, //显示加载圈
  });
};
