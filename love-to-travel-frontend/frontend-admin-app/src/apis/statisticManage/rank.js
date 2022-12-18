import httpRequest from "@/request";

export const getStatistic = () => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8082/note/statistic",
        loading: true, //显示加载圈
    });
};

export const getSales = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/product/sales",
        loading: true, //显示加载圈
    });
};