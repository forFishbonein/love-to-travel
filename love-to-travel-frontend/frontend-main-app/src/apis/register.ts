import httpRequest from "@/request";

export const postCode = (email: any) => {
  console.log(email);
  return httpRequest({
    data: email,
    method: "post",
    url: "/send",
  });
};

export const register = (registerData: any) => {
  console.log(registerData);
  return httpRequest({
    data: registerData,
    method: "post",
    url: "/user/register",
    loading: true,
  });
};
