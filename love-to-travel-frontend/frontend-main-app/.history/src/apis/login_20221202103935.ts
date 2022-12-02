import httpRequest from "@/request";

export function codeLogin(data) {
  console.log(data);
  return httpRequest({
    data: data,
    method: "post",
    url: "/t/login",
  });
}

export function passLogin(login) {
  // console.log(login);
  return httpRequest({
    data: login,
    method: "post",
    url: "/t/passLogin",
  });
}

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
