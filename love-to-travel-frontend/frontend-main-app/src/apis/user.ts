import httpRequest from "@request/index";

// // 定义接口的传参
// interface UserInfoParam {
//   userID: string;
//   userName: string;
// }

// 获取用户信息
// export function apiGetUserInfo(param: UserInfoParam) {
//   return httpRequest({
//     url: "your api url",
//     method: "post",
//     data: param,
//   });
// }
export const getUserInfo = (token: string) => {
  return httpRequest({
    url: "/user/token",
    method: "get",
    headers: { Authorization: token },
  });
};
