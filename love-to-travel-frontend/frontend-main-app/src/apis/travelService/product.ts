/* 新增计划 */
import httpRequest from "@/request";
export const getPageProductsInfo = (page: number, limit: number) => {
  return httpRequest({
    method: "post",
    data: {
      pageNum: page,
      pageSize: limit,
    },
    url: "http://localhost:8082/product/page",
    loading: true, //显示加载圈
  });
};
export const getOneProductInfoById = (productId: number) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/product/${productId}`,
    loading: true, //显示加载圈
  });
};

export const buyOneProduct = (pId: string, uId: string) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/product/buy",
    data: {
      productId: pId,
      userId: uId,
    },
    loading: true, //显示加载圈
  });
};

export const searchProductsByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/product/user/${userId}`,
    data: {
      id: userId,
    },
    loading: true, //显示加载圈
  });
};
