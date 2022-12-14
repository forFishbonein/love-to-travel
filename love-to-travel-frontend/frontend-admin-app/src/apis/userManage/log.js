import httpRequest from "@/request";

export const getLogInfo = (page) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8081/log",
        data: page,
        loading: true,
    });
};