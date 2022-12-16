import httpRequest from "@/request";
export const getCityInfo = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/city",
        loading: true, //显示加载圈
    });
}

export const getPageCityInfo = (page) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8082/city/page",
        data:page,
        loading: true, //显示加载圈
    });
}