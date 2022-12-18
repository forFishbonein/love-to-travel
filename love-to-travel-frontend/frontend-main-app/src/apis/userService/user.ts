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
    url: "http://localhost:8081/user/token",
    method: "get",
    headers: { Authorization: token },
    loading: true, //这里好像必须开启，不知道为啥！！！这样有token时配合路由监测进主页的时候才不会报错！
  });
};
export const getUserInfoById = (userId: string) => {
  return httpRequest({
    url: `http://localhost:8081/user/${userId}`,
    method: "get",
    loading: true,
  });
};

/* 修改用户信息 */
export const modifyUserInfo = (data: any) => {
  return httpRequest({
    url: "http://localhost:8081/user/updateprofile",
    method: "put",
    data: data,
    loading: true,
  });
};

/* 修改密码 */
export const modifyUserPass = (data: any) => {
  return httpRequest({
    url: "http://localhost:8081/user/updatepassword",
    method: "put",
    data: data,
    loading: true,
  });
};
