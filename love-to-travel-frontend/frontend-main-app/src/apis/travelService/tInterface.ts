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

/* 加入队伍的参数 */
export interface joinTeamParams {
  teamId: string;
  userId: string;
  userName: string;
  email: string;
  tele: string;
}

/* 队伍参数 */
export interface theTeamParams {
  teamId: string;
  userId: string;
}
