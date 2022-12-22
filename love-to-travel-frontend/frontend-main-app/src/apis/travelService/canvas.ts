import httpRequest from "@/request";
export const getCanvasAllScenerysInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/canvas/",
    // loading: true, //显示加载圈
  });
};
export const getCanvasAllCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/canvas/city",
    // loading: true, //显示加载圈
  });
};
export const getCanvasOneCityInfoByName = (cityName: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/canvas/city/${cityName}`,
    // loading: true, //显示加载圈
  });
};
export const getCanvasOneSceneryInfoByName = (sceneryName: string) => {
  // alert(sceneryName);
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/canvas/${sceneryName}`,
    // loading: true, //显示加载圈
  });
};

export const getCanvasOneSceneryInfoByNameAndRelation = (
  rel: string,
  name: string
) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/canvas/query/${rel}/${name}`,
    // loading: true, //显示加载圈
  });
};
