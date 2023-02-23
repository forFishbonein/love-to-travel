import httpRequest from "@/request";
export const getSceneryInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/scenery",
    loading: false, //显示加载圈
  });
};

export const getSceneryByStr2 = (data) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/scenery/query2",
    data,
    loading: false, //显示加载圈
  });
};

export const postSceneryInfo = (data) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/scenery/page",
    data,
    loading: false, //显示加载圈
  });
};

export const insertProvinceInfo = (data) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/scenery",
    data,
    loading: false, //显示加载圈
  });
};

export const deleteSceneryInfo = (id) => {
  console.log("========");
  console.log("========");
  console.log(id);
  console.log("========");
  console.log("========");
  return httpRequest({
    method: "delete",
    url: "http://localhost:8082/scenery" + id,
    loading: false, //显示加载圈
  });
};

export const updateSceneryInfo = (data) => {
  return httpRequest({
    method: "put",
    url: "http://localhost:8082/scenery",
    data,
    loading: false, //显示加载圈
  });
};
