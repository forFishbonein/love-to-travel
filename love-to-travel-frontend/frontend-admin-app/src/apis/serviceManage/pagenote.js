import httpRequest from "@/request";

export const pageNote = (page) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/note/page",
    data: page,
    loading: false, //显示加载圈
  });
};

export const queryMore = (data) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/note/query/more",
    data,
    loading: false, //显示加载圈
  });
};
