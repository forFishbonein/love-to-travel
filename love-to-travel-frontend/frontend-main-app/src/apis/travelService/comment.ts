import httpRequest from "@/request";
export const getCommentsByNoteId = (noteId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/comment/${noteId}`,
    loading: true, //显示加载圈
  });
};
