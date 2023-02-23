import httpRequest from "@/request";

export const getUser = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/user",
    loading: false,
  });
};
export const getUserByID = (id) => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/user/" + id,
    loading: false,
  });
};
export const updateUserInfo = (p) => {
  return httpRequest({
    method: "put",
    url: "http://localhost:8081/user/updateprofile",
    data: p,
    loading: false,
  });
};
export const deleteUserInfo = (id) => {
  return httpRequest({
    method: "delete",
    url: "http://localhost:8081/user/" + id,
    loading: false,
  });
};
export const getPageUserInfo = (page) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8081/user/page",
    data: page,
    loading: false,
  });
};
