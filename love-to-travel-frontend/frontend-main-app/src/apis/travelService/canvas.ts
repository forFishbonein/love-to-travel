import httpRequest from "@/request";
export const getCanvasAllInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/canvas/",
    // loading: true, //显示加载圈
  });
};

export const getCanvasAllInfoByName = (name: string) => {
  alert(name);
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/canvas/${name}`,
    // loading: true, //显示加载圈
  });
};

export const getCanvasAllInfoByNameAndRelation = (
  rel: string,
  name: string
) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/canvas/query/${rel}/${name}`,
    // loading: true, //显示加载圈
  });
};
