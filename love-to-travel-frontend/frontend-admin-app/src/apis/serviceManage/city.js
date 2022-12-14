import httpRequest from "@/request";
export const getCityInfo = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/city",
        loading: true, //显示加载圈
    });
}