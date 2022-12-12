import httpRequest from "@/request";
export const getProvinceInfo = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/scenery",
        loading: true, //显示加载圈
    });
};
