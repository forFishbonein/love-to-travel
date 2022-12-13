import httpRequest from "@/request";
// import { passLoginInfo, codeLoginInfo } from "@/apis/userService/uInterface";
export const getAllNoteList = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/note",
    loading: true, //显示加载圈
  });
};
export const getOneNoteInfoById = (noteId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/city/${noteId}`,
    loading: true, //显示加载圈
  });
};
