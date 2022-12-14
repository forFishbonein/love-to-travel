/* 新增计划 */
import httpRequest from "@/request";
import { finalAllCityPlansInfoType } from "@/apis/interface/iPlan";
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
