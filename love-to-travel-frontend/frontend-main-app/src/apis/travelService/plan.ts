/* 新增计划 */
import httpRequest from "@/request";
import { finalAllCityPlansInfoType } from "@/apis/interface/myInterface";
export const saveFinalPlansInfo = (
  finalPlansInfo: finalAllCityPlansInfoType
) => {
  //   alert("进来了");
  console.log(finalPlansInfo);
  return httpRequest({
    method: "post",
    data: finalPlansInfo,
    url: "http://localhost:8082/plan",
    loading: true, //显示加载圈
  });
};

export const getOneUserPlansInfoById = (planId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/plan/${planId}`,
    loading: true, //显示加载圈
  });
};

/* 根据用户id查询其全部行程 */
export const getUserAllPlansInfoByUserId = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8082/plan/user/${userId}`,
    loading: true, //显示加载圈
  });
};
