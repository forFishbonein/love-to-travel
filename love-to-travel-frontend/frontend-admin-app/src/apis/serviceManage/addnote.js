import httpRequest from "@/request";

export const addNote = (note) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8082/note/",
        data: note,
        loading: true, //显示加载圈
    });
};