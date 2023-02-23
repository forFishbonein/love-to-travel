import httpRequest from "@request/index";
export const getUserFollowersNum = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/sumfollower",
    data: {
      // @ts-ignore
      id: userId,
    },
    method: "post",
    // loading: false,
  });
};
export const getUserFolloweesNum = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/sumfollowee",
    data: {
      id: userId,
    },
    method: "post",
    // loading: false,
  });
};
/* 根据用户id获取其所有粉丝的信息 */
export const getUserFollowersInfo = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/getallfollower",
    data: {
      id: userId,
    },
    method: "post",
    // loading: false,
  });
};
/* 根据用户id获取其所有关注的用户的信息 */
export const getUserFolloweesInfo = (userId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/getallfollowee",
    data: {
      id: userId,
    },
    method: "post",
    // loading: false,
  });
};
export const followOneUser = (userId: string, followerId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/add",
    data: {
      id: userId,
      followerId: followerId,
    },
    method: "post",
    // loading: false,
  });
};
export const cancelfollowOneUser = (userId: string, followerId: string) => {
  return httpRequest({
    url: "http://localhost:8081/follow/remove",
    data: {
      id: userId,
      followerId: followerId,
    },
    method: "post",
    // loading: false,
  });
};

export const isFollowOneUser = (userId: string, followerId: string) => {
  return httpRequest({
    url: "  http://localhost:8081/follow/isfollow",
    data: {
      id: userId,
      followerId: followerId,
    },
    method: "post",
    // loading: false,
  });
};
