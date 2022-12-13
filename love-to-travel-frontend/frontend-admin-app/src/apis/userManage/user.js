import httpRequest from "@/request";
export const getUser = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8081/user",
    // data: p,
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
export const updateUserInfo = () => {
  return httpRequest({
    method: "put",
    url: "http://localhost:8081/user/updateprofile",
    // data:id,
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
