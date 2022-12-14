import httpRequest from "@/request";

export const delNote = (id) => {
    return httpRequest({
        method: "delete",
        url: "http://localhost:8082/note/" + id,
        //data: id,
        loading: true, //显示加载圈
    });
};