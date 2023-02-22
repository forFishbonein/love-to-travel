import httpRequest from "@/request";

export const login = (data) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8080/user/login",
        data,
        loading: true, //显示加载圈
    });
};