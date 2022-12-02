export const loginOrRegister = () => {
  document.querySelector(".img__btn").addEventListener("click", function () {
    //@ts-ignore
    document.querySelector(".content").classList.toggle("s--signup");
  });
};
