import httpRequest from "@/request";
import {
  commentParams,
  sceneryCommentParams,
} from "@/apis/travelService/tInterface";
/* 1游记评论 */
export const getCommentsByNoteId = (noteId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/comment/${noteId}`,
    // loading: false, //显示加载圈
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
    // loading: false, //显示加载圈
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
    // loading: false, //显示加载圈
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
    // loading: false, //显示加载圈
  });
};

export const addAComment = (data: commentParams) => {
  return httpRequest({
    method: "post",
    data: data,
    url: "http://localhost:8082/comment",
    // loading: false, //显示加载圈
  });
};

/* 2景区评分评论 */
export const getCommentsBySceneryId = (sceneryId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/scenery/comment/${sceneryId}`,
    // loading: false, //显示加载圈
  });
};

export const addASceneryComment = (data: sceneryCommentParams) => {
  return httpRequest({
    method: "post",
    data: data,
    url: "http://localhost:8082/scenery/comment",
    // loading: false, //显示加载圈
  });
};
/* 删除游记评论 */
export const deleteOneComment = (commentId: string) => {
  return httpRequest({
    method: "delete",
    url: `http://localhost:8082/comment/${commentId}`,
    // loading: false, //显示加载圈
  });
};
