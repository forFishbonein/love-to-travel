import { config } from "@/config";

/**
 * 动态设置浏览器标题
 * @param title
 */
export const setDocumentTitle = (title?: string) => {
  document.title = title || config.title;
};

/**
 * 处理await成功失败信息
 * 参考：https://juejin.cn/post/6844903767129718791
 * @param {*} promise
 * 用法：const [err, data] = await awaitWrap(fetchData()) //fetchData()为return了Promise的函数
 */
export function awaitWrap<T, U = any>(
  promise: Promise<T>
): Promise<[U | null, T | null]> {
  return promise
    .then<[null, T]>((data: T) => [null, data])
    .catch<[U, null]>((err) => [err, null]);
}
