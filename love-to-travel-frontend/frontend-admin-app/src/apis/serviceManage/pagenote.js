import httpRequest from "@/request";

export const pageNote = (page) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8082/note/page",
        data: page,
        loading: true, //显示加载圈
    });
};