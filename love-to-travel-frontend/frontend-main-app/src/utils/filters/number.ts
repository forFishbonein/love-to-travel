/* 过滤器 */
const numberFormat = (value) => {
  let unit = "";
  var k = 10000,
    sizes = ["", "万+", "亿+", "万亿+"],
    i;
  if (value < k) {
    value = value;
  } else {
    i = Math.floor(Math.log(value) / Math.log(k));
    value = (value / Math.pow(k, i)).toFixed(2);
    unit = sizes[i];
  }
  return value + unit;
};
export { numberFormat };
