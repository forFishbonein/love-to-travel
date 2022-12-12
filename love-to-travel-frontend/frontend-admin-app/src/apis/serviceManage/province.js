import httpRequest from "@/request";
export const getProvinceInfo = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/province",
        loading: true, //显示加载圈
    });
}
