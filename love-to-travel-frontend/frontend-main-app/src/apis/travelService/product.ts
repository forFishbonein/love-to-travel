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
export const getOneProductInfoById = (productId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/product/${productId}`,
    loading: true, //显示加载圈
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
      createTime: time,
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
