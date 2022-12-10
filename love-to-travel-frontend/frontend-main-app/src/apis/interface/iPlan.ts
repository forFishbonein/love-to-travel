/**
 * @description [ 统一类型接口 ]
 */
export interface planInfoType {
  fromCity: string;
  toCity: citysInfoType;
  goDate: string;
}
/* 1 */
//带经纬度的
export interface wantCityWithLalType {
  id: string;
  toCity: string;
  days: number | null;
  lng: string;
  lat: string;
}
//不带经纬度的
export interface wantCityType {
  id: string;
  toCity: string;
  days: number | null;
}
export interface planCityInfoType {
  fromTheCity: string;
  wantCitys: wantCityWithLalType[];
  backCity: string;
  goTheDate: string;
  budget: string;
}

/* 2 */
export interface citysInfoType {
  cityId: string;
  cityName: string;
  lng: string;
  lat: string;
  url: null;
  introduction: null;
}

/* 3 */
export interface routeInfoType {
  budget: string;
  departTime: string;
  destination: string;
  destinationName: string;
  origin: string;
  originName: string;
  vehicle: string;
  weather: string;
}
export interface routesType {
  route: routeInfoType[];
}
//要展示的只是这个
export interface citysPlansInfoType {
  city: string;
  cityId: string;
  time: number | null; //游玩的天数
  days: routesType[];
}
export interface finalPlansInfoType {
  budget: string;
  id: string;
  subPlans: citysPlansInfoType[];
  userId: string;
  fromCity: string;
  backCity: string;
  goDate: string;
}
// export interface displayACityPlanInfo {
//   city: string;
//   cityId: string;
//   time: number | null; //游玩的天数
//   days: routesType[];
// }
