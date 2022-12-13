import httpRequest from "@/request";
export const getUser = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/user",
    loading: true,
  });
};
export const getUserByID = (id) => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/user/"+id,
    loading: true,
  });
};
export const updateUserInfo = (p) => {
  return httpRequest({
    method: "put",
    url: "http://localhost:8081/user/updateprofile",
    data:p,
    loading: true,
  });
};
export const deleteUserInfo = (id) => {
  return httpRequest({
    method: "delete",
    url: "http://localhost:8081/user/"+id,
    loading: true,
  });
};
export const getPageUserInfo = (page) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8081/user/page",
    data:page,
    loading: true,
  });
};
