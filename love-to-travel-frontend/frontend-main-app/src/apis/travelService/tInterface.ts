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

export interface teamInfoParams {
  planId: string;
  teamName: string;
  ownerId: string;
  place: Array<string>;
  depart: string;
  day: string;
  budget: string;
  introduction: string;
  slogan: string;
  num: string;
}
