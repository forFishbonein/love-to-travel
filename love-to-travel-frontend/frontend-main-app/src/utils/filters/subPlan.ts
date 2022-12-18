import { everyCityPlansInfoType } from "@/apis/interface/myInterface";
const subPlansFormat = (subPlans: everyCityPlansInfoType[]) => {
  if (subPlans) {
    let num = 0;
    subPlans.forEach((e) => {
      e.days.forEach((e2) => {
        num += 1;
      });
    });
    return num;
  }
};

export { subPlansFormat };
