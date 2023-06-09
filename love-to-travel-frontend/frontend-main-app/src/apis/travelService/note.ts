import httpRequest from "@/request";
// import { passLoginInfo, codeLoginInfo } from "@/apis/userService/uInterface";
import {
  noteInfoParams,
  noteInfoParams2,
} from "@/apis/travelService/tInterface";
export const getAllNoteList = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/note",
    // loading: false, //显示加载圈
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
    // loading: false, //显示加载圈
  });
};
export const getOneNoteInfoById = (noteId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/note/${noteId}`,
    // loading: false, //显示加载圈
  });
};
/* 根据用户id获取游记 */
export const getNotesInfoByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/note/user/${userId}`,
    // loading: false, //显示加载圈
  });
};

/* 点赞 */
export const likeTheNote = (noteId: string, userId: string) => {
  console.log(noteId);
  console.log(userId);
  return httpRequest({
    method: "post",
    data: {
      noteId: noteId,
      userId: userId,
    },
    url: "http://localhost:8082/note/like",
    // loading: false, //显示加载圈
  });
};

export const islikeTheNote = (noteId: string, userId: string) => {
  return httpRequest({
    method: "post",
    data: {
      noteId: noteId,
      userId: userId,
    },
    url: "http://localhost:8082/note/islike",
    // loading: false, //显示加载圈
  });
};
export const unlikeTheNote = (noteId: string, userId: string) => {
  return httpRequest({
    method: "post",
    data: {
      noteId: noteId,
      userId: userId,
    },
    url: "http://localhost:8082/note/unlike",
    // loading: false, //显示加载圈
  });
};

/* 收藏 */
export const starTheNote = (noteId: string, userId: string) => {
  return httpRequest({
    method: "post",
    data: {
      noteId: noteId,
      userId: userId,
    },
    url: "http://localhost:8082/note/star",
    // loading: false, //显示加载圈
  });
};

export const isstarTheNote = (noteId: string, userId: string) => {
  return httpRequest({
    method: "post",
    data: {
      noteId: noteId,
      userId: userId,
    },
    url: "http://localhost:8082/note/isstar",
    // loading: false, //显示加载圈
  });
};
export const unstarTheNote = (noteId: string, userId: string) => {
  return httpRequest({
    method: "post",
    data: {
      noteId: noteId,
      userId: userId,
    },
    url: "http://localhost:8082/note/unstar",
    // loading: false, //显示加载圈
  });
};

/* 根据用户id查询收藏 */
export const getStarNotesInfoByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/note/star/${userId}`,
    // loading: false, //显示加载圈
  });
};

/* 发布游记 */
export const publishOneNote = (data: noteInfoParams) => {
  // alert("发布游记");
  // console.log(data);
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/note/",
    data: data,
    // loading: false, //显示加载圈
  });
};

/* 删除游记 */
export const deleteOneNote = (noteId: string) => {
  return httpRequest({
    method: "delete",
    url: `http://localhost:8082/note/${noteId}`,
    // loading: false, //显示加载圈
  });
};

/* 游记的相关游记 */
export const getRelatedOtherNotes = (noteId: string) => {
  return httpRequest({
    method: "post",
    url: `http://localhost:8082/note/related/note/${noteId}`,
    // loading: false, //显示加载圈
  });
};

/* 修改游记 */
export const modifyOneNote = (data: noteInfoParams2) => {
  // alert("修改游记");
  // console.log(data);
  return httpRequest({
    method: "put",
    data: data,
    url: "http://localhost:8082/note/",
    // loading: false, //显示加载圈
  });
};
