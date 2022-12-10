import { postCode } from "@/apis/userService/register";
let a: HTMLInputElement = document.createElement("input");
let count = 30;
let curCount = 0;
let InterValObj: number = 0;
const setRemainTime = () => {
  if (curCount == 0) {
    console.log("停止");
    window.clearInterval(InterValObj);
    a.disabled = false;
    a.style.backgroundColor = "#e0e0e0";
    a.setAttribute("value", "重新发送验证码");
  } else {
    curCount--;
    a.setAttribute("value", curCount + "秒后可重新发送");
  }
};
const getCode = (email: string) => {
  if (email === "") {
    //@ts-ignore
    ElMessage({
      type: "warn",
      message: "请将信息填写完整",
    });
    return;
  }
  //@ts-ignore
  a = document.getElementsByClassName("getCode")[0];
  a.disabled = true;
  a.style.backgroundColor = "#f5f5f5";
  //@ts-ignore
  postCode({ email: email })
    .then((res) => {
      if (res.code == 0) {
        //@ts-ignore
        ElMessage({
          type: "success",
          message: "验证码已发送",
        });
      }
    })
    .catch((error) => {
      console.log(error);
    });
  curCount = count;
  InterValObj = window.setInterval(setRemainTime, 1000);
};
export { a, count, curCount, InterValObj, setRemainTime, getCode };
