import httpRequest from "@/request";
export const getNote = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/note",
        // data: tableData,
        loading: true, //显示加载圈
    });
};