import httpRequest from "@/request";

export const passLogin = (passData: any) => {
  console.log(passData);
  return httpRequest({
    data: passData,
    method: "post",
    url: "/login",
    loading: true,
  });
};

export function codeLogin(codeData: any) {
  console.log(codeData);
  return httpRequest({
    data: codeData,
    method: "post",
    url: "/codelogin",
    loading: true,
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
