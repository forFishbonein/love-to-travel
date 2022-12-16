const srcPattern = (imgStr) => {
  //解析富文本获取img里面的src属性值
  let patt = /<img[^>]+src=['"]([^'"]+)['"]+/g;
  let result = [],
    temp;
  while ((temp = patt.exec(imgStr)) != null) {
    // @ts-ignore
    result.push({ url: temp[1] });
  }
  return result;
};

export { srcPattern };
