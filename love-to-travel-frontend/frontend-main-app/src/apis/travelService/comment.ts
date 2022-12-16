import httpRequest from "@/request";
import { commentParams } from "@/apis/travelService/tInterface";
export const getCommentsByNoteId = (noteId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/comment/${noteId}`,
    // loading: true, //显示加载圈
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
    // loading: true, //显示加载圈
  });
};

export const islikeTheComment = (commentId: string, userId: string) => {
  return httpRequest({
    method: "post",
    data: {
      commentId: commentId,
      userId: userId,
    },
    url: "http://localhost:8082/comment/islike",
    // loading: true, //显示加载圈
  });
};
export const unlikeTheComment = (commentId: string, userId: string) => {
  return httpRequest({
    method: "post",
    data: {
      commentId: commentId,
      userId: userId,
    },
    url: "http://localhost:8082/comment/unLike",
    // loading: true, //显示加载圈
  });
};

export const addAComment = (data: commentParams) => {
  return httpRequest({
    method: "post",
    data: data,
    url: "http://localhost:8082/comment",
    // loading: true, //显示加载圈
  });
};
