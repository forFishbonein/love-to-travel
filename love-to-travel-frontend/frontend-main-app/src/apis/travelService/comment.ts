import httpRequest from "@/request";
export const getCommentsByNoteId = (noteId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/comment/${noteId}`,
    loading: true, //显示加载圈
  });
};

export const likeTheComment = (commentId: string, userId: string) => {
  return httpRequest({
    method: "post",
    data: {
      commentId: commentId,
      userId: userId,
    },
    url: "http://localhost:8082/comment/like",
    loading: true, //显示加载圈
  });
};
