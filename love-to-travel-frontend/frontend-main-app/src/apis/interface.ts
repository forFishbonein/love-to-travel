/**
 * @description [ 统一类型接口 ]
 */

// export interface IResponseType<P = Record<string, unknown>> {
//   code: number;
//   msg: string;
//   data: P;
// }

// 用户信息类型
export interface UserInfo {
  author?: string;
  avator?: string;
  projectAddress?: string;
  demoUrl?: string;
  demoCodeUrl?: string;
  email?: string;
}
// 密码登录参数类型
export interface passLoginInfo {
  email: string;
  password: string;
}
// 验证码登录参数类型
export interface codeLoginInfo {
  email: string;
  code: string;
}
// 注册参数类型
export interface registerInfo {
  name: string;
  email: string;
  code: string;
}
