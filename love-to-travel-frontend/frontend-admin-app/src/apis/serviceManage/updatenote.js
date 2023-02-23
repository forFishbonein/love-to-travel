import httpRequest from "@/request";

export const updateNote = (note) => {
  return httpRequest({
    method: "put",
    url: "http://localhost:8082/note/",
    data: note,
    loading: false, //显示加载圈
  });
};
