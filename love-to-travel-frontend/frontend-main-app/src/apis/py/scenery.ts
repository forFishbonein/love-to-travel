import httpRequest from "@/request";
/* 获取推荐 */
export const getRecommendScenerysList = (userId: string) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8080/sd/${userId}`,
    // loading: true, //显示加载圈
  });
};
// axios.get("http://localhost:8080/sd/" + store.userInfo.id).then((res) => {
//     alert("获取到了！");
//     console.log("66666666666666666");
//     console.log(res);
//     console.log("66666666666666666");
//   });
export const getRealTimeRecommendScenerysList = (
  id1: string,
  id2: string,
  id3: string
) => {
  return httpRequest({
    method: "get",
    url: `http://localhost:8086/sce/${id1}/${id2}/${id3}`,
    // loading: true, //显示加载圈
  });
};
