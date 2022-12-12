import httpRequest from "@/request";
export const getUser = (p) => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/user",
    data: p,
    loading: true, //显示加载圈
  });
};
