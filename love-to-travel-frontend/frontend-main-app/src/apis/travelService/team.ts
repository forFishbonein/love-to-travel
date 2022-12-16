import {
  teamInfoParams,
  joinTeamParams,
  theTeamParams,
} from "@/apis/travelService/tInterface";
import { teamModifyParams } from "@/apis/interface/iPlan";
import httpRequest from "@/request";
/* 创建队伍 */
export const createOneTeam = (data: teamInfoParams) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/team/",
    data: data,
    loading: true, //显示加载圈
  });
};

/* 查询所有队伍 */
export const getAllTeams = () => {
  return httpRequest({
    method: "get",
    url: "http://localhost:8082/team/",
    loading: true, //显示加载圈
  });
};
/* 分页查询 */
export const getPageTeamsInfo = (page: number, limit: number) => {
  return httpRequest({
    method: "post",
    data: {
      pageNum: page,
      pageSize: limit,
    },
    url: "http://localhost:8082/team/page",
    loading: true, //显示加载圈
  });
};
/* 根据队伍id查询队伍信息 */

export const getTeamInfoById = (teamId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/team/${teamId}`,
    loading: true, //显示加载圈
  });
};
/* 查询是否加入了队伍 */
export const isJoinTheTeam = (data: theTeamParams) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/team/isjoin",
    data: data,
    loading: true, //显示加载圈
  });
};
/* 加入队伍 */
export const joinOneTeam = (data: theTeamParams) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/team/join",
    data: data,
    loading: true, //显示加载圈
  });
};
/* 退出队伍 */
export const leaveOneTeam = (data: theTeamParams) => {
  return httpRequest({
    method: "post",
    url: "http://localhost:8082/team/leave",
    data: data,
    loading: true, //显示加载圈
  });
};
/* 移除成员 */
export const kickOneMember = (data: any) => {
  return httpRequest({
    method: "delete",
    url: "http://localhost:8082/team/kick",
    data: data,
    loading: true, //显示加载圈
  });
};
/* 发送邀请邮件 */
export const postInviteEmail = (email: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/team/invite/${email}`,
    loading: true, //显示加载圈
  });
};

/* 解散队伍 */
export const deleteTheTeam = (teamId: string) => {
  return httpRequest({
    method: "delete",
    url: `http://localhost:8082/team/${teamId}`,
    loading: true, //显示加载圈
  });
};

/* 修改队伍信息 */
export const modifyTheTeamInfo = (data: teamModifyParams) => {
  alert("修改队伍信息");
  console.log(data);
  return httpRequest({
    method: "put",
    url: "http://localhost:8082/team/",
    data: data,
    loading: true, //显示加载圈
  });
};

/* 根据用户id查询加入的队伍 */
export const searchJoinTeamByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/team/user/join/${userId}`,
    loading: true, //显示加载圈
  });
};

/* 根据用户id查询创建的队伍 */
export const searchCreateTeamByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/team/user/create/${userId}`,
    loading: true, //显示加载圈
  });
};
