import httpRequest from "@/request";
export const getRouteList = (id: string) => {
  // alert("哈哈" + id);
  return httpRequest({
    method: "get",
    url: "/city",
    loading: true, //显示加载圈
  });
};
