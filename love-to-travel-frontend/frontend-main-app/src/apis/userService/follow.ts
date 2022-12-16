import httpRequest from "@request/index";
export const getUserFollowersNum = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/sumfollower",
    data: {
      id: userId,
    },
    method: "post",
    loading: true,
  });
};
export const getUserFolloweesNum = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/sumfollowee",
    data: {
      id: userId,
    },
    method: "post",
    loading: true,
  });
};
export const getUserFollowersInfo = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/sumfollower",
    data: {
      id: userId,
    },
    method: "post",
    loading: true,
  });
};
export const getUserFolloweesInfo = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/getallfollowee",
    data: {
      id: userId,
    },
    method: "post",
    loading: true,
  });
};
export const followeOneUser = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/add",
    data: {
      id: userId,
    },
    method: "post",
    loading: true,
  });
};
export const cancelfolloweOneUser = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/remove",
    data: {
      id: userId,
    },
    method: "post",
    loading: true,
  });
};
