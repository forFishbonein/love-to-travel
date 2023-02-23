import httpRequest from "@/request";
export const getOrderPage = (page) => {
  return httpRequest({
    method: "post",
    data: page,
    url: "http://localhost:8082/product/page/order",
    loading: false,
  });
};
export const updateOrderInfo = (id) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/product/use/" + id,
    loading: false,
  });
};
