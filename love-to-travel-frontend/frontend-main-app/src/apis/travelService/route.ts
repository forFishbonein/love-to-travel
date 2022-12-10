import httpRequest from "@/request";
export const getRouteList = (id: string) => {
  // alert("哈哈" + id);
  return httpRequest({
    method: "get",
    url: "/travelservice/city",
    loading: true, //显示加载圈
  });
};
