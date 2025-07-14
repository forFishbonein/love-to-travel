// let OSS = require("ali-oss");
import OSS from "ali-oss";
export let client = new OSS({
  region: "oss-cn-hangzhou",
  accessKeyId: "xxx",
  accessKeySecret: "xxx",
  bucket: "noteimg123",
});
