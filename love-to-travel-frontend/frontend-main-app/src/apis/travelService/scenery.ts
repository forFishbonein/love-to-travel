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
export const getPageScenerysInfo = (page: number, limit: number) => {
  return httpRequest({
    method: "post",
    data: {
      pageNum: page,
      pageSize: limit,
    },
    url: "http://localhost:8082/scenery/page",
    loading: true, //显示加载圈
  });
};
export const getOneSceneryInfoById = (sceneryId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/scenery/${sceneryId}`,
    loading: true, //显示加载圈
  });
};
export const getRecommondSceneryByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/scenery/recommond/user/${userId}`,
    loading: true, //显示加载圈
  });
};
export const getRecommondUsersByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8081/user/scenery/recommond/${userId}`,
    loading: true, //显示加载圈
  });
};

/* 根据景区id查询相关游记 */
export const getRecommondNotesBySceneryId = (sceneryId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/note/related/${sceneryId}`,
    loading: true, //显示加载圈
  });
};
