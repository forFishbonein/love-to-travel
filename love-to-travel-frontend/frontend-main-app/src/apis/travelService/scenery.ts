import httpRequest from "@/request";
export const getSceneryList = (id: string) => {
  //   alert("哈哈" + id);
  return httpRequest({
    method: "get",
    url: `/travelservice/scenery/city/${id}`,
    loading: true, //显示加载圈
  });
};
