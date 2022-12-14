import httpRequest from "@/request";
export const getFootsByUserId = (userId: any) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/foot/user/${userId}`,
    loading: true, //显示加载圈
  });
};
