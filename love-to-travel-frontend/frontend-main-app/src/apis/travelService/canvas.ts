import httpRequest from "@/request";
export const getCanvasAllScenerysInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/canvas/",
    // loading: false, //显示加载圈
  });
};
export const getCanvasAllCitysInfo = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/canvas/city",
    // loading: false, //显示加载圈
  });
};
export const getCanvasOneCityInfoByName = (cityName: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/canvas/city/${cityName}`,
    // loading: false, //显示加载圈
  });
};
export const getCanvasOneSceneryInfoByName = (sceneryName: string) => {
  // alert(sceneryName);
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/canvas/${sceneryName}`,
    // loading: false, //显示加载圈
  });
};

export const getCanvasOneSceneryInfoByNameAndRelation = (
  rel: string,
  name: string
) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/canvas/query/${rel}/${name}`,
    // loading: false, //显示加载圈
  });
};
