import httpRequest from "@/request";
import { registerInfo } from "@/apis/interface";
export const postCode = (email: string) => {
  console.log(email);
  return httpRequest({
    data: email,
    method: "post",
    url: "/send",
  });
};

export const register = (registerData: registerInfo) => {
  console.log(registerData);
  return httpRequest({
    data: registerData,
    method: "post",
    url: "/user/register",
    loading: true,
  });
};
