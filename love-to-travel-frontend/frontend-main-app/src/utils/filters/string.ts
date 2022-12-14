// 比如：csdn博客呀 超过8个字符就用…表示就是 “csdn博客…”
// 因为我们需要先将中文用charCodeAt转换，而charCodeAt是返回指定位置的字符的 Unicode 编码，也就是说此时中文占两个字节

//字符超过长度用...表示
const strFormat = (content, lengthSize) => {
  var j = 0;
  for (var i = 0; i < content.length; i++) {
    //判断是不是有中文
    if (content.charCodeAt(i) >= 0 && content.charCodeAt(i) <= 255) {
      j += 1;
    } else {
      j += 2; //中文，相当于占两位字符
    }
    if (j >= lengthSize) {
      //如果占的位数超过了最大显示的位数
      return content.substring(0, i + 1) + "...";
    }
  }
  return content; //如果占的位数没有超过最大显示的位数
};
export { strFormat };
