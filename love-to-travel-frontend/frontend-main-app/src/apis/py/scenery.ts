import httpRequest from "@/request";
export const getRecommendScenerys = (userId) => {
  return httpRequest({
    method: "get",
    url: `http://127.0.0.1:8080/sce/${userId}`,
    loading: true, //显示加载圈
  });
};
