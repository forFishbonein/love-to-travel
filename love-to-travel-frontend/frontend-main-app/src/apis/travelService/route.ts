import httpRequest from "@/request";
export const getRouteList = (id: string) => {
  // alert("哈哈" + id);
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/plan/city/${id}`,
    loading: true, //显示加载圈
  });
};
