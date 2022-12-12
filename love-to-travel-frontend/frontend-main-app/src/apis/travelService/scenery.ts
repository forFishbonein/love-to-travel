import httpRequest from "@/request";
export const getSceneryListByCityId = (id: string) => {
  //   alert("哈哈" + id);
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/scenery/city/${id}`,
    loading: true, //显示加载圈
  });
};

export const getAllSceneryList = () => {
  //   alert("哈哈" + id);
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/scenery",
    loading: true, //显示加载圈
  });
};

export const getOneSceneryInfoById = (sceneryId: number) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/scenery/${sceneryId}`,
    loading: true, //显示加载圈
  });
};
