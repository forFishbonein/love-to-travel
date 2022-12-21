import { postCode } from "@/apis/userService/register";
import i18n from "@/locales";
let a: HTMLInputElement = document.createElement("input");
let count = 30;
let curCount = 0;
let InterValObj: number = 0;
const setRemainTime = () => {
  if (curCount == 0) {
    console.log("停止");
    window.clearInterval(InterValObj);
    a.disabled = false;
    a.style.color = "#ffffff";
    a.style.backgroundColor = "#e8604c";
    a.style.width = "120px";
    a.setAttribute("value", i18n.global.t("other.getCodeRe"));
    i18n.global.t("menus.home");
  } else {
    curCount--;
    a.setAttribute("value", curCount + " " + i18n.global.t("other.wait"));
  }
};
const getCode = (email: string) => {
  if (email === "") {
    //@ts-ignore
    ElMessage({
      type: "warning",
      message: i18n.global.t("other.pleaseOver"),
    });
    return;
  }
  //@ts-ignore
  a = document.getElementsByClassName("getCode")[0];
  a.disabled = true;
  a.style.backgroundColor = "#f48f80";
  a.style.color = "#ffffff";
  a.style.width = "150px";
  //@ts-ignore
  postCode({ email: email })
    .then((res) => {
      if (res.code == 0) {
        //@ts-ignore
        ElMessage({
          type: "success",
          message: i18n.global.t("other.hasSend"),
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
