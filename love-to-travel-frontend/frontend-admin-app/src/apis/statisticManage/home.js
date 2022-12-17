import httpRequest from "@/request";

export const getNewUserNum = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8081/user/new",
        loading: true, //显示加载圈
    });
};

export const getNewLogNum = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8081/log/new",
        loading: true, //显示加载圈
    });
};

export const getAllUser = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8081/user/all",
        loading: true, //显示加载圈
    });
};

export const getAllLog = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8081/log/all",
        loading: true, //显示加载圈
    });
};

export const getNewProductNum = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/product/new",
        loading: true, //显示加载圈
    });
};

export const getAllProductBuy = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/product/all",
        loading: true, //显示加载圈
    });
};