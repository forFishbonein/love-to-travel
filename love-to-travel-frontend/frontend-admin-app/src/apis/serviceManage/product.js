import httpRequest from "@/request";
export const getProductInfo = () => {
    return httpRequest({
        method: "get",
        url: "http://localhost:8082/product/",
        loading: true, //显示加载圈
    });

}



export const addProductInfo = (data) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8080/product/",
        data,
        loading: true, //显示加载圈
    });
}

export const deleteProductInfo = ({id}) => {
    return httpRequest({
        method: "delete",
        url: "http://localhost:8080/product/{id}",
        loading: true, //显示加载圈
    });
}

export const updateProductInfo = (data) => {
    return httpRequest({
        method: "put",
        url: "http://localhost:8080/",
        data,
        loading: true, //显示加载圈
    });
}

export const pageProduct = (page) => {
    return httpRequest({
        method: "post",
        url: "http://localhost:8080/product/page",
        data: page,
        loading: true, //显示加载圈
    });
}