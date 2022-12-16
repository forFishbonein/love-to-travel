/**
 * @description [ 统一类型接口 ]
 */

export interface commentParams {
  noteId: string;
  content: string;
  userId: string;
  userName: string;
  parentId: string;
}

export interface noteInfoParams {
  userId: string;
  planId: string;
  url: string;
  title: string;
  content: string;
}
