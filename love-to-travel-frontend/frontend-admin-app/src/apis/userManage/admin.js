import httpRequest from "@/request";

export const getAdmin = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8081/admin",
        loading: true,
    });
};
export const deleteAdminInfo = (id) => {
    return httpRequest({
        method: "delete",
        url: "http://localhost:8081/admin/" + id,
        loading: true,
    });
};
export const getPageAdminInfo = (page) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8081/admin/page",
        data: page,
        loading: true,
    });
};
