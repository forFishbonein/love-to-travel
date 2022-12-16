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
//4.四舍五入保留2位小数（若第二位小数为0，则保留一位小数）
function keepTwoDecimal(num) {
  var result = parseFloat(num);
  if (isNaN(result)) {
    // alert("传递参数错误，请检查！");
    return false;
  }
  result = Math.round(num * 100) / 100;
  return result;
}
export { numberFormat, keepTwoDecimal };
