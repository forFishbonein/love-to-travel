import httpRequest from "@/request";

export function codeLogin(data) {
  console.log(data);
  return httpRequest({
    data: data,
    method: "post",
    url: "/t/login",
  });
}

export const passLogin = (codeData) => {
  return httpRequest({
    data: codeData,
    method: "post",
    url: "/login",
    loading: true,
  });
};

export function getUserInfo(token) {
  return httpRequest({
    headers: { Authorization: token },
    url: "/t/user",
    method: "get",
  });
}

export function logout(token) {
  return httpRequest({
    headers: { Authorization: token },
    url: "/t/logout",
    method: "post",
  });
}
