import httpRequest from "@/request";
export const getAllProductsInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/product/",
    loading: false, //显示加载圈
  });
};
export const getPageProductsInfo = (page: number, limit: number) => {
  return httpRequest({
    method: "post",
    data: {
      pageNum: page,
      pageSize: limit,
    },
    url: "http://localhost:8082/product/page",
    loading: false, //显示加载圈
  });
};
export const getOneProductInfoById = (productId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/product/${productId}`,
    loading: false, //显示加载圈
  });
};

export const buyOneProduct = (
  pId: string,
  uId: string,
  pCost: string,
  time: string
) => {
  // alert("购买");
  // console.log(pId);
  // console.log(uId);
  // console.log(pCost);
  // console.log(time);
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/product/buy",
    data: {
      productId: pId,
      userId: uId,
      cost: pCost,
      payTime: time,
    },
    // loading: false, //显示加载圈
  });
};

export const searchProductsByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/product/user/${userId}`,
    data: {
      id: userId,
    },
    // loading: false, //显示加载圈
  });
};

/* 查询是否支付成功 */
export const searchProductIsBuy = (orderId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/product/isbuy/${orderId}`,
    // loading: false, //显示加载圈
  });
};
