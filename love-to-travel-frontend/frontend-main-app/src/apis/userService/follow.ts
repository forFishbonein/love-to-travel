import httpRequest from "@request/index";
export const getUserFollowersNum = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/sumfollower",
    data: {
      // @ts-ignore
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
    url: "http://localhost:8081/follow/getallfollower",
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
export const followOneUser = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/add",
    data: {
      id: userId,
    },
    method: "post",
    loading: true,
  });
};
export const cancelfollowOneUser = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/remove",
    data: {
      id: userId,
    },
    method: "post",
    loading: true,
  });
};
