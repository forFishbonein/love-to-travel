import httpRequest from "@/request";
import { passLoginInfo, codeLoginInfo } from "@/apis/interface";
export const passLogin = (passData: passLoginInfo) => {
  console.log(passData);
  return httpRequest({
    data: passData,
    method: "post",
    url: "/login",
    loading: true, //显示加载圈
  });
};

export function codeLogin(codeData: codeLoginInfo) {
  console.log(codeData);
  return httpRequest({
    data: codeData,
    method: "post",
    url: "/codelogin",
    loading: true,
  });
}

export function logout(token: string) {
  return httpRequest({
    data: token,
    url: "/logout",
    method: "post",
    loading: true,
  });
}
