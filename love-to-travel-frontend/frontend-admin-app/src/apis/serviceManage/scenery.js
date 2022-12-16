import httpRequest from "@/request";
export const getSceneryInfo = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/scenery",
        loading: true, //显示加载圈
    });

}

export const postSceneryInfo = (data) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8082/",
        data,
        loading: true, //显示加载圈
    });
}

export const deleteSceneryInfo = ({id}) => {
    return httpRequest({
        method: "delete",
        url: "http://localhost:8082/{id}",
        loading: true, //显示加载圈
    });
}

export const updateSceneryInfo = (data) => {
    return httpRequest({
        method: "put",
        url: "http://localhost:8082/",
        data,
        loading: true, //显示加载圈
    });
}