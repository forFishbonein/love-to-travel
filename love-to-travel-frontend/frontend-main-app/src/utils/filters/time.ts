/* 过滤器 */
import dayjs from "dayjs";
const timeFormat = (value: string) => {
  // @ts-ignore
  let timeStamp = Date.parse(new Date(value));
  return dayjs(timeStamp).format("YYYY-MM-DD");
};
export { timeFormat };
