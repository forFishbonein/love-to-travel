export interface IResponseType<P = Record<string, unknown>> {
  code: number;
  msg: string;
  data: P;
}

// 用户信息类型
export interface UserInfo {
  author?: string;
  avator?: string;
  projectAddress?: string;
  demoUrl?: string;
  demoCodeUrl?: string;
}
