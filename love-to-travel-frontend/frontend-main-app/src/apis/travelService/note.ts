import httpRequest from "@/request";
// import { passLoginInfo, codeLoginInfo } from "@/apis/userService/uInterface";
export const getAllNoteList = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/note",
    loading: true, //显示加载圈
  });
};
export const getPageNotesInfo = (page: number, limit: number) => {
  return httpRequest({
    method: "post",
    data: {
      pageNum: page,
      pageSize: limit,
    },
    url: "http://localhost:8082/note/page",
    loading: true, //显示加载圈
  });
};
export const getOneNoteInfoById = (noteId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/note/${noteId}`,
    loading: true, //显示加载圈
  });
};
export const getNotesInfoByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/note/user/${userId}`,
    loading: true, //显示加载圈
  });
};
