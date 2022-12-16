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
  cityEname: string;
  lng: string;
  lat: string;
  url: string;
  introduction: string;
}

/* 3 */
/* 一个路线，对应route里面的每一项 */
export interface routeInfoType {
  departTime: string;
  origin: Array<number | null>; //目的地
  originName: string;
  vehicle: string;
}
/* 一个城市每一天的所有路线 */
//对应days里面的每一项
export interface everyDayRoutesType {
  route: routeInfoType[]; //对应route
}
//要展示的只是这个
/* 所有城市的每一天的所有路线 */
//对应subPlans里面的每一项
export interface everyCityPlansInfoType {
  city: string;
  cityId: string;
  dayLength: number | null; //游玩的天数
  days: everyDayRoutesType[]; //对应days
  budget: string;
  weather: string;
}
export interface finalAllCityPlansInfoType {
  userId: string;
  budget: string;
  depart: string;
  start: string;
  end: string;
  subPlans: everyCityPlansInfoType[]; //对应subPlans
  // fromCity: string;
  // backCity: string;
  // goDate: string;
}

export interface theGivenAllCityPlansInfoType {
  id: string;
  userId: string;
  budget: string;
  depart: string;
  start: string;
  end: string;
  subPlans: everyCityPlansInfoType[]; //对应subPlans
}
// export interface displayACityPlanInfo {
//   city: string;
//   cityId: string;
//   time: number | null; //游玩的天数
//   days: routesType[];
// }

/* 4 */
/* 景区的类型 */
export interface theCityScenerysInfoType {
  name: string;
  introduction: string | null;
  score: string | null;
  ticket: string | null;
  opening: string | null;
  lng: number | null;
  lat: number | null;
  level: string | null;
  address: string;
  season: string | null;
  tips: string | null;
  cityId: string;
  url: string;
  tele: string | null;
  deleted: string;
  createTime: string | null;
  updateTime: string | null;
  cluster: string | null;
  id: number;
}

/* 5 游记类型*/
/* 需要改Username */
export interface theNotesInfoType {
  city: string;
  comment: number;
  content: string;
  createTime: string;
  deleted: string;
  id: string;
  like: number;
  planId: string;
  star: number;
  title: string;
  trip: Array<string>;
  updateTime: string;
  url: string;
  userId: string;
  userName: string;
  view: number;
}

/* 6 评论类型 */
export interface theNoteComment {
  content: string;
  createTime: string;
  id: string;
  like: number;
  noteId: string;
  parentId: string;
  reply: number;
  userId: string;
  userName: string;
}

export interface secondComment {
  id: string;
  userId: string;
  userName: string;
  content: string;
  like: number;
  createTime: string;
  reply: number;
}

export interface tranformComments {
  id: string;
  userId: string;
  userName: string;
  content: string;
  like: number;
  createTime: string;
  reply: number;
  son: secondComment[];
}

/* 足迹类型 */
export interface wantInfoType {
  cityId: string;
  cityName: string;
  createTime: string;
  introduction: string;
  pos: Array<number>;
  url: string;
}
export interface beenInfoType {
  cityId: string;
  cityName: string;
  createTime: string;
  introduction: string;
  score: string;
  pos: Array<number>;
  url: string;
}

/* 组队 */
export interface teamMemberInfoType {
  email: string;
  tele: string;
  userId: string;
  userName: string;
}

export interface theTeamInfoType {
  budget: string;
  createTime: string;
  day: string;
  deleted: string;
  depart: string;
  id: string;
  introduction: string;
  members: teamMemberInfoType[];
  num: string;
  ownerId: string;
  place: Array<string>;
  planId: string;
  slogan: string;
  teamName: string;
  updateTime: string;
}
