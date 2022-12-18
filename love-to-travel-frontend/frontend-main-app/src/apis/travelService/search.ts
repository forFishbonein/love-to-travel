import httpRequest from "@/request";
export const getPageCitysInfoByKeyword = (
  str: string,
  page: number,
  limit: number
) => {
  return httpRequest({
    method: "post",
    data: {
      queryStr: str,
      pageNum: page,
      pageSize: limit,
    },
    url: "http://localhost:8082/city/query",
    loading: true, //显示加载圈
  });
};

export const getPageScenerysInfoByKeyword = (
  str: string,
  page: number,
  limit: number
) => {
  return httpRequest({
    method: "post",
    data: {
      queryStr: str,
      pageNum: page,
      pageSize: limit,
    },
    url: "http://localhost:8082/scenery/query",
    loading: true, //显示加载圈
  });
};

export const getPageNotesInfoByKeyword = (
  str: string,
  page: number,
  limit: number
) => {
  return httpRequest({
    method: "post",
    data: {
      queryStr: str,
      pageNum: page,
      pageSize: limit,
    },
    url: "http://localhost:8082/note/query",
    loading: true, //显示加载圈
  });
};

/* 全局模糊搜索 */
export const getSomeInfoByKeyword = (queryStr: string) => {
  // console.log(str);
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/query/${queryStr}`,
    loading: true, //显示加载圈
  });
};
