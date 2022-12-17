import httpRequest from "@/request";

export const login = (data) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8081/admin/login",
        data,
        loading: true, //显示加载圈
    });
};