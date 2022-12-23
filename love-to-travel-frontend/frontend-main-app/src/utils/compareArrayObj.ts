/* 对象数组，基于某个字段进行排序 */
/** 两个参数： 参数1 是排序用的字段， 参数2 是：是否升序排序 true 为升序，false为降序*/
const compare = (attr, rev) => {
  // console.log(attr, rev)
  if (rev == undefined) {
    rev = 1;
  } else {
    rev = rev ? 1 : -1;
  }
  return (a, b) => {
    a = a[attr];
    b = b[attr];
    if (a < b) {
      return rev * -1;
    }
    if (a > b) {
      return rev * 1;
    }
    return 0;
  };
};
export { compare };
