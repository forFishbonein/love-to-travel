// let OSS = require("ali-oss");
import OSS from "ali-oss";
export let client = new OSS({
  region: "oss-cn-hangzhou",
  accessKeyId: "LTAI5tRM3FjZEZjXbNoTAUEu",
  accessKeySecret: "6xyVEBQPktBIYxqRE7T77rcg1zFNdW",
  bucket: "noteimg123",
});
