<script setup lang="ts">
import { ref, reactive, toRefs } from "vue";
import { useRouter } from "vue-router";
import { citysInfoType, planInfoType } from "@/apis/interface/myInterface";
import { getHotCitysInfo } from "@apis/travelService/city";
import { onMounted } from "@vue/runtime-core";
import { utilStore } from "@/store/util";
import { theNotesInfoType } from "@/apis/interface/myInterface";
import { getAllNoteList, getPageNotesInfo } from "@apis/travelService/note";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
import { getRandomArrayElements } from "@/utils/filters/randomArray";
import { getAllProductsInfo } from "@apis/travelService/product";
import { productInfoType } from "@/apis/interface/myInterface";
import { subPlansFormat } from "@/utils/filters/subPlan";
import { recommendStore } from "@/store/recommend";
import { mainStore } from "@/store/user";
import { theCityScenerysInfoType } from "@/apis/interface/myInterface";
const store = utilStore();
const router = useRouter();
const ustore = mainStore();
const rstore = recommendStore();
let citysInfos = [
  {
    cityEname: "beijing",
    cityId: "110000",
    cityName: "北京市",
    introduction:
      "北京，简称“京”，古称燕京、北平，时而时尚繁华、时而古典沉静。皇家古迹、市井胡同、繁华商业区共同构筑了古典，现代，文化，科技，大气的新北京，中国历史文化名城和古都之一。",
    lat: "39.910925",
    lng: "116.413384",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E5%8C%97%E4%BA%AC.jpg",
  },
  {
    cityEname: "shanghai",
    cityId: "310000",
    cityName: "上海市",
    introduction:
      "昔日繁华的“十里洋场”，诉说着旧上海滩的浮华旧梦。上海，一个财富汇聚的商业中心，一个有着“东方巴黎”之称的魔幻城市，也是中华人民共和国省级行政区、直辖市、国家中心城市、超大城市、上海大都市圈核心城市。",
    lat: "31.235929",
    lng: "121.480539",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%B8%8A%E6%B5%B72.jpg",
  },
  {
    cityEname: "hangzhou",
    cityId: "330100",
    cityName: "杭州市",
    introduction:
      "江南忆，最忆是杭州，一半山水一半城，人间天堂悠然生。幸福如杭州，何日更重逢？",
    lat: "30.253083",
    lng: "120.215512",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E8%A5%BF%E6%B9%96.jpg",
  },
  {
    cityEname: "guilin",
    cityId: "450300",
    cityName: "桂林市",
    introduction:
      "桂林风景秀丽，这里的山，平地拔起、千姿百态；漓江的水，蜿蜒曲折、明洁如镜；山洞奇景，瑰丽壮观。",
    lat: "25.241699",
    lng: "110.186195",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E6%BC%93%E6%B1%9F.jpg",
  },
  {
    cityEname: "sanya",
    cityId: "460200",
    cityName: "三亚市",
    introduction:
      "三亚，一个被碧海蓝天、柔和海滩、清新空气和浪漫阳光环绕的南国之城，一个梦想中的度假天堂。",
    lat: "18.258736",
    lng: "109.518557",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%B8%89%E4%BA%9A2.jpg",
  },
  {
    cityEname: "chongqing",
    cityId: "500000",
    cityName: "重庆市",
    introduction:
      "重庆，一座山城，亦是江城，不似北方的粗犷和江南的秀气过甚，它自然矜守着一番西南特有的清婉卓越。",
    lat: "29.568996",
    lng: "106.558434",
    url: "https://img1.qunarzz.com/travel/poi/1806/b9/c604a6124c4fb137.png_r_480x360x95_77e508f8.png",
  },
  {
    cityEname: "chengdu",
    cityId: "510100",
    cityName: "成都市",
    introduction:
      "四川省省会，中国历史文化名城之一，位于省境中部，以汉族为多，有回、蒙古、藏、苗、满、土家等44个少数民族。",
    lat: "30.578994",
    lng: "104.072747",
    url: "https://img1.qunarzz.com/travel/poi/1806/f2/fc87b310b1fe2a37.png_r_480x360x95_e1869a31.png",
  },
  {
    cityEname: "lasa",
    cityId: "540100",
    cityName: "拉萨市",
    introduction:
      "拉萨，日光之城，在这里，体验高原眩晕，感受藏族风情，除了做好防晒，你其实可以什么都不用想。",
    lat: "29.659488",
    lng: "91.178454",
    url: "https://img1.qunarzz.com/travel/poi/1806/d6/91132a33fbddaf37.png_r_480x360x95_946774ae.png",
  },
  {
    cityEname: "rikaze",
    cityId: "540200",
    cityName: "日喀则市",
    introduction:
      "这里是后藏的中心，班禅的驻锡地，有格鲁派四大寺庙之一的扎什伦布寺，西藏最大的青稞田以及世界最高的珠穆朗玛峰，",
    lat: "29.275658",
    lng: "88.893703",
    url: "https://img1.qunarzz.com/travel/poi/201404/04/a7c0f73b37d41292ddb12cfb.jpg_r_480x360x95_946df565.jpg",
  },
];
let hotCitysInfo = ref([] as citysInfoType[]);
let hotCityOne = ref({} as citysInfoType);
let hotCityTwo = ref({} as citysInfoType);
let hotCityThree = ref({} as citysInfoType);
let hotCityFour = ref({} as citysInfoType);
let hotCityFive = ref({} as citysInfoType);
/* 游记内容 */
let notesInfo = ref([] as theNotesInfoType[]);
/* 产品内容 */
let productsInfo = ref([] as productInfoType[]);
productsInfo.value = [
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "100",
    introduction:
      "一价全含+五星酒店+体验旅拍+特色餐食+九寨连住2晚+赠成都住宿接机",
    name: "奢玩2-5人团·九寨沟+毕棚沟+达古冰川+黄龙4日游",
    plan: {
      budget: "5000",
      createTime: "2022-12-16 11:51:34",
      deleted: "0",
      depart: "2022-12-13",
      end: "北京市",
      id: "639beb46c0bc772c3cb5884b",
      start: "呼伦贝尔市",
      subPlans: [
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [116.404177, 39.909652],
                  originName: "天安门广场",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [116.278749, 40.004869],
                  originName: "颐和园",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "秦皇岛市",
          cityId: "130300",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [119.446926, 39.792502],
                  originName: "南戴河仙螺岛",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-16 11:51:34",
      userId: "1",
    },
    planId: "639beb46c0bc772c3cb5884b",
    price: "145",
    tele: "400579579",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%B9%9D%E5%AF%A8%E6%B2%9F.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965100",
    introduction:
      "被誉为“天下第一湾”，是个放松休闲、享受美食、体验水上项目的好地方。 ·拥有约7000米的银白色海滩，沙质细腻、海水洁净透明，可清楚观赏珊瑚，远望呈现几种不同的蓝色。 ·娱乐项目繁多，可乘坐豪华游艇出海，看海狮海豹；玩转帆船、浮潜、垂钓；背上滑翔伞翱翔蓝天。 ·冬季水温可达到20℃左右，可常年下水游泳，周边五星级豪华酒店云集，是一处理想的冬季避寒的休闲度假胜地。",
    name: "三亚亚龙湾热带天堂森林公园门票 可选网红玻璃海景栈道·索桥·丛林飞跃·雨林飞漂项目",
    plan: {
      budget: "50000",
      createTime: "2022-12-15 08:02:20",
      deleted: "0",
      depart: "2022-12-23",
      end: "武汉市",
      id: "639b0ccc1e9513315d02cba3",
      start: "杭州市",
      subPlans: [
        {
          budget: "",
          city: "宁波市",
          cityId: "330200",
          dayLength: 2,
          days: [],
        },
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-15 08:02:20",
      userId: "1",
    },
    planId: "639b0ccc1e9513315d02cba3",
    price: "266",
    tele: "13611000000",
    url: "https://sales.mafengwo.net/mfs/s16/M00/23/CD/CoUBUl8X93qAUKXnAEFECqWo7Is96.jpeg?imageMogr2%2Fthumbnail%2F%21440x260r%2Fgravity%2FCenter%2Fcrop%2F%21440x260%2Fquality%2F100",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965107",
    introduction:
      "本线路为散客拼团，在承诺服务内容和标准不变的前提下，可能会与其他旅行社的客人合并用车，共同游玩。如您提交订单，则视为接受旅行社拼团后统一安排行程。",
    name: "西藏林芝5日游 观景酒店·无人机航拍·布宫旅拍 西藏林芝+来古冰川+巴松措+雅鲁藏布大峡谷+然乌湖+林芝波密+西藏拉萨+拉萨出发",
    plan: {
      budget: "3000",
      createTime: "2022-12-15 08:04:55",
      deleted: "0",
      depart: "2022-12-23",
      end: "22",
      id: "639b0d671e9513315d02cba4",
      start: "杭州市",
      subPlans: [
        {
          budget: "",
          city: "宁波市",
          cityId: "330200",
          dayLength: 3,
          days: [],
        },
        {
          budget: "",
          city: "临沂市",
          cityId: "371300",
          dayLength: 0,
          days: [],
        },
        {
          budget: "",
          city: "天津市",
          cityId: "120000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 1,
                  origin: [0.0, 0.0],
                  originName: "五大道",
                  vehicle: "2",
                },
                {
                  departTime: 1,
                  origin: [0.0, 0.0],
                  originName: "五大道",
                  vehicle: "2",
                },
                {
                  departTime: 2,
                  origin: [106.315583, 29.032396],
                  originName: "石龙峡风景名胜区",
                  vehicle: "4",
                },
                {
                  departTime: 2,
                  origin: [106.315583, 29.032396],
                  originName: "石龙峡风景名胜区",
                  vehicle: "4",
                },
                {
                  departTime: 2,
                  origin: [106.315583, 29.032396],
                  originName: "石龙峡风景名胜区",
                  vehicle: "4",
                },
                {
                  departTime: 2,
                  origin: [106.315583, 29.032396],
                  originName: "石龙峡风景名胜区",
                  vehicle: "4",
                },
                {
                  departTime: 2,
                  origin: [106.315583, 29.032396],
                  originName: "石龙峡风景名胜区",
                  vehicle: "4",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "上海市",
          cityId: "310000",
          dayLength: 0,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [121.728256, 31.061244],
                  originName: "上海野生动物园",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [121.728256, 31.061244],
                  originName: "上海野生动物园",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [121.728256, 31.061244],
                  originName: "上海野生动物园",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [121.728256, 31.061244],
                  originName: "上海野生动物园",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [121.728256, 31.061244],
                  originName: "上海野生动物园",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [121.728256, 31.061244],
                  originName: "上海野生动物园",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [121.728256, 31.061244],
                  originName: "上海野生动物园",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [121.728256, 31.061244],
                  originName: "上海野生动物园",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-15 08:04:55",
      userId: "1",
    },
    planId: "639b0d671e9513315d02cba4",
    price: "107",
    tele: "18000000000",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/note/%E4%BA%A7%E5%93%81%20%283%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965108",
    introduction:
      "湖面海拔4718米，西藏第二大湖泊，也是中国第三大的咸水湖。它是西藏的“三大圣湖”之一，我们会沿着纳木措湖边公路一路前行，前往纳木措湖北岸的景点“圣象天门”景区。",
    name: "西藏珠峰大本营5日游 可选无人机航拍/拉萨市旅拍 纳木措圣象天门+羊湖+奇林峡+扎西半岛+卡若拉冰川+58冰川+西藏拉萨出发",
    plan: {
      budget: "1000",
      createTime: "2022-12-20 12:48:05",
      deleted: "0",
      depart: "2022-12-02T16:00:00.000Z",
      end: "北京",
      id: "63a095c54603680c533e13de",
      start: "北京",
      subPlans: [
        {
          budget: "",
          city: "成都市",
          cityId: "510100",
          dayLength: 4,
          days: [
            {
              route: [
                {
                  departTime: 0,
                  origin: [103.601377, 30.905449],
                  originName: "青城山",
                  vehicle: "自驾",
                },
                {
                  departTime: 0,
                  origin: [113.543026, 27.960472],
                  originName: "赵公山",
                  vehicle: "骑行",
                },
                {
                  departTime: 0,
                  origin: [103.653534, 30.994257],
                  originName: "都江堰景区",
                  vehicle: "骑行",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 0,
                  origin: [116.23186, 40.104117],
                  originName: "故宫",
                  vehicle: "自驾",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-20 12:48:05",
      userId: "6781",
    },
    planId: "63a095c54603680c533e13de",
    price: "345",
    tele: "15500550055",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/note/%E4%BA%A7%E5%93%81%20%282%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965109",
    introduction:
      "如出现不可抗力因素，如国家疫情防控政策通知：当地不能出行，到三亚被隔离，或者海南景区、酒店等已停业，疫情状况已不能接待游客;出示相关通知或者证明申请，是否能退以酒店回复为准。以上情况免服务费。",
    name: "三亚亚特兰蒂斯酒店1-3晚套餐",
    plan: {
      budget: "5000",
      createTime: "2022-12-19 02:58:21",
      deleted: "0",
      depart: "2022-12-01",
      end: "",
      id: "63a00b8d4603680c533e13ce",
      start: "湖州市",
      subPlans: [
        {
          budget: "",
          city: "上海市",
          cityId: "310000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [121.671964, 31.148267],
                  originName: "上海迪士尼度假区",
                  vehicle: "2",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-19 02:58:21",
      userId: "1599354785511882754",
    },
    planId: "63a00b8d4603680c533e13ce",
    price: "218",
    tele: "100500885",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/note/%E4%BA%A7%E5%93%81%20%281%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965111",
    introduction:
      "早上可以睡个懒觉，然后自行用餐，今天上午我们将给大家半日的自由活动时间。驴友们可根据时间自行安排。你可以在【香格里拉古城】感受迷人的香格里拉文化，转动转经筒为自己和家人朋友祈福。也可自费去体验藏族传统文化特色浓郁的【唐卡绘画】，也可选择去参观有着“小布达拉宫”之称的【松赞林寺】，感受云南地区最大的藏传佛教寺院。中午12点集合返回丽江。",
    name: "云南丽江香格里拉梅里雪山雨崩徒步6日游",
    plan: {
      budget: "5000",
      createTime: "2022-12-19 02:15:24",
      deleted: "0",
      depart: "2022-12-08",
      end: "北京",
      id: "63a0017c186c7a3f9f4c2b46",
      start: "北京",
      subPlans: [
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [116.23186, 40.104117],
                  originName: "故宫",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [117.310896, 40.187348],
                  originName: "北京平谷金海湖",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "上海市",
          cityId: "310000",
          dayLength: 0,
          days: [],
        },
      ],
      updateTime: "2022-12-19 02:15:24",
      userId: "1",
    },
    planId: "63a0017c186c7a3f9f4c2b46",
    price: "199",
    tele: "10086",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/note/%E4%BA%A7%E5%93%81%20%284%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965112",
    introduction:
      "支付完成后商家最晚会在9个工作小时内（9:00-18:00）确认是否预定成功\r\n\r\n超时未确认系统将自动退款，预计1-7个工作日退还到支付账户\r\n\r\n本商品无需出团通知书/确认单，预定成功后即可出行",
    name: "珠海御温泉度假村",
    plan: {
      budget: "5000",
      createTime: "2022-12-16 11:51:34",
      deleted: "0",
      depart: "2022-12-13",
      end: "北京市",
      id: "639beb46c0bc772c3cb5884b",
      start: "呼伦贝尔市",
      subPlans: [
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [116.404177, 39.909652],
                  originName: "天安门广场",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [116.278749, 40.004869],
                  originName: "颐和园",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "秦皇岛市",
          cityId: "130300",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [119.446926, 39.792502],
                  originName: "南戴河仙螺岛",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-16 11:51:34",
      userId: "1",
    },
    planId: "639beb46c0bc772c3cb5884b",
    price: "198",
    tele: "10086",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%BA%A7%E5%93%81/%E4%BA%A7%E5%93%81%20%2813%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965371",
    introduction:
      "为了您人身、财产的安全，请您避免在公开场合暴露贵重物品及大量现金。",
    name: "珠海长隆海洋王国度假区企鹅酒店2天1晚/3天2晚度假套餐",
    plan: {
      budget: "5000",
      createTime: "2022-12-16 11:51:34",
      deleted: "0",
      depart: "2022-12-13",
      end: "北京市",
      id: "639beb46c0bc772c3cb5884b",
      start: "呼伦贝尔市",
      subPlans: [
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [116.404177, 39.909652],
                  originName: "天安门广场",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [116.278749, 40.004869],
                  originName: "颐和园",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "秦皇岛市",
          cityId: "130300",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [119.446926, 39.792502],
                  originName: "南戴河仙螺岛",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-16 11:51:34",
      userId: "1",
    },
    planId: "639beb46c0bc772c3cb5884b",
    price: "92",
    tele: "10086",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%BA%A7%E5%93%81/%E4%BA%A7%E5%93%81%20%2812%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965372",
    introduction:
      "1.精选住宿：特色客栈/古北之光/水镇大酒店任您选择，古香古色让您流连忘返\r\n2.安排合理：合理的时间安排，舒适的游览线路，日游夜游一次玩个够\r\n3.保证成团：拍下即默认安排，助您出行无忧\r\n4.真纯玩团：我们保证只玩古北水镇，游遍水镇的每个角落，深度自由活动\r\n\r\n5.出行必要：应北京文旅局要求，疫情期间户外旅行可接待范围①低风险地区②健康宝+行程码正常③72小时内",
    name: "♥北方水镇♥古北水镇纯玩2日游",
    plan: {
      budget: "5000",
      createTime: "2022-12-16 11:51:34",
      deleted: "0",
      depart: "2022-12-13",
      end: "北京市",
      id: "639beb46c0bc772c3cb5884b",
      start: "呼伦贝尔市",
      subPlans: [
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [116.404177, 39.909652],
                  originName: "天安门广场",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [116.278749, 40.004869],
                  originName: "颐和园",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "秦皇岛市",
          cityId: "130300",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [119.446926, 39.792502],
                  originName: "南戴河仙螺岛",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-16 11:51:34",
      userId: "1",
    },
    planId: "639beb46c0bc772c3cb5884b",
    price: "2099",
    tele: "10086",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%BA%A7%E5%93%81/%E4%BA%A7%E5%93%81%20%289%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965373",
    introduction: "出发时间：9:00\r\n集合地点为：故宫东华门集合",
    name: "北京故宫博物院4-6小时全景一日游",
    plan: {
      budget: "5000",
      createTime: "2022-12-16 11:51:34",
      deleted: "0",
      depart: "2022-12-13",
      end: "北京市",
      id: "639beb46c0bc772c3cb5884b",
      start: "呼伦贝尔市",
      subPlans: [
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [116.404177, 39.909652],
                  originName: "天安门广场",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [116.278749, 40.004869],
                  originName: "颐和园",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "秦皇岛市",
          cityId: "130300",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [119.446926, 39.792502],
                  originName: "南戴河仙螺岛",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-16 11:51:34",
      userId: "1",
    },
    planId: "639beb46c0bc772c3cb5884b",
    price: "1058",
    tele: "10086",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%BA%A7%E5%93%81/%E4%BA%A7%E5%93%81%20%288%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965378",
    introduction:
      "穿上古风汉服，在苍茫大漠，圆你江湖梦\r\n辽阔黄沙，蓝天白云，随手就是一张网红级大片\r\n也可以自愿自费体验巴基赛车、ATV/UTV、赛车服穿搭拍照等深度好玩的沙漠项目\r\n日落来临时，感受“长河落日圆”的壮丽～",
    name: "内蒙古库布齐沙漠2日游",
    plan: {
      budget: "5000",
      createTime: "2022-12-16 11:51:34",
      deleted: "0",
      depart: "2022-12-13",
      end: "北京市",
      id: "639beb46c0bc772c3cb5884b",
      start: "呼伦贝尔市",
      subPlans: [
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [116.404177, 39.909652],
                  originName: "天安门广场",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [116.278749, 40.004869],
                  originName: "颐和园",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "秦皇岛市",
          cityId: "130300",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [119.446926, 39.792502],
                  originName: "南戴河仙螺岛",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-16 11:51:34",
      userId: "1",
    },
    planId: "639beb46c0bc772c3cb5884b",
    price: "2599",
    tele: "10086",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%BA%A7%E5%93%81/%E4%BA%A7%E5%93%81%20%287%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965380",
    introduction:
      "车辆所需费用：车辆燃油费、停车费、高速费、车辆使用费、进城费、拥堵费。",
    name: "南京至扬州 包车2日游",
    plan: {
      budget: "5000",
      createTime: "2022-12-16 11:51:34",
      deleted: "0",
      depart: "2022-12-13",
      end: "北京市",
      id: "639beb46c0bc772c3cb5884b",
      start: "呼伦贝尔市",
      subPlans: [
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [116.404177, 39.909652],
                  originName: "天安门广场",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [116.278749, 40.004869],
                  originName: "颐和园",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "秦皇岛市",
          cityId: "130300",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [119.446926, 39.792502],
                  originName: "南戴河仙螺岛",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-16 11:51:34",
      userId: "1",
    },
    planId: "639beb46c0bc772c3cb5884b",
    price: "489",
    tele: "10086",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%BA%A7%E5%93%81/%E4%BA%A7%E5%93%81%20%286%29.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965383",
    introduction:
      "杭州出发：8:50杭州新东方大厦（集合）杭州主城区上门接，具体范围咨询客服",
    name: "嘉兴乌镇+西塘古镇纯玩一日游",
    plan: {
      budget: "5000",
      createTime: "2022-12-16 11:51:34",
      deleted: "0",
      depart: "2022-12-13",
      end: "北京市",
      id: "639beb46c0bc772c3cb5884b",
      start: "呼伦贝尔市",
      subPlans: [
        {
          budget: "",
          city: "北京市",
          cityId: "110000",
          dayLength: 2,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [115.904081, 40.544177],
                  originName: "玉渡山景区",
                  vehicle: "步行",
                },
                {
                  departTime: 2,
                  origin: [116.404177, 39.909652],
                  originName: "天安门广场",
                  vehicle: "3",
                },
                {
                  departTime: 2,
                  origin: [116.278749, 40.004869],
                  originName: "颐和园",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
        {
          budget: "",
          city: "秦皇岛市",
          cityId: "130300",
          dayLength: 3,
          days: [
            {
              route: [
                {
                  departTime: 2,
                  origin: [119.446926, 39.792502],
                  originName: "南戴河仙螺岛",
                  vehicle: "3",
                },
              ],
            },
          ],
        },
      ],
      updateTime: "2022-12-16 11:51:34",
      userId: "1",
    },
    planId: "639beb46c0bc772c3cb5884b",
    price: "889",
    tele: "10086",
    url: "https://eemall.oss-cn-hangzhou.aliyuncs.com/%E4%BA%A7%E5%93%81/%E4%BA%A7%E5%93%81%20%285%29.jpg",
  },
];
const pageParams = reactive({
  page: 1,
  limit: 3,
});
const { page, limit } = toRefs(pageParams);
const requestCitysAndNotesInfo = async () => {
  await getHotCitysInfo()
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功1");
        hotCitysInfo.value = res.data.slice(0, 5);
        // citysInfo.value = res.data;
        console.log(hotCitysInfo);
        hotCityOne.value = hotCitysInfo.value[0];
        hotCityTwo.value = hotCitysInfo.value[1];
        hotCityThree.value = hotCitysInfo.value[2];
        hotCityFour.value = hotCitysInfo.value[3];
        hotCityFive.value = hotCitysInfo.value[4];
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  await getPageNotesInfo(page.value, limit.value)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功2");
        notesInfo.value = res.data.records;
        console.log(notesInfo);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  // await getAllProductsInfo()
  //   .then((res: any) => {
  //     if (res.code != 0) {
  //       //@ts-ignore
  //       ElMessage({
  //         type: "error",
  //         message: res.msg,
  //       });
  //     } else {
  //       // alert("获取成功");
  //       productsInfo.value = res.data.slice(0, 12);
  //       console.log(productsInfo);
  //     }
  //   })
  //   .catch((error) => {
  //     //@ts-ignore
  //     ElMessage({
  //       type: "error",
  //       message: error.message,
  //     });
  //   });
};
requestCitysAndNotesInfo();

const planInfo: planInfoType = reactive({
  fromCity: "",
  toCity: {} as citysInfoType,
  goDate: "",
});

const createPlan = () => {
  console.log(planInfo);
  router.push({
    name: "TrvalPlan",
    params: {
      fromCity: planInfo.fromCity,
      toCity: JSON.stringify(planInfo.toCity),
      goDate: planInfo.goDate,
    },
  });
};

// 保证每次进入HOME页面都会刷新一次的工具方法：
const refresh = () => {
  //refreshFlag为true代表刷新过
  if (!store.refreshFlag) {
    // alert("刷新");
    //还没刷新过
    store.refreshFlag = true; //表示已经刷新了
    // console.log(store.refreshFlag);
    location.reload(); //那就刷新一下
    // setTimeout(function () {
    //   location.reload();
    // }, 1000);
    return;
  } else {
    //已经刷新过了
    store.refreshFlag = false; //表示还没有刷新
    // console.log(store.refreshFlag);

    return; //那就不刷新了
  }
};
refresh();
/* 随机文案 */
/* 随机好评度和库存 */
const stringArray = [
  "每个人心中，都会有一个古镇情怀，流水江南，烟笼人家。",
  "南风未起，念你成疾。",
  "因为有梦，所以勇敢出发，选择出发，便只顾风雨兼程。",
  "人生路上的相聚与别离，是我们生命里最美丽的风景。",
  "人间烟火气，最抚凡人心。",
  "旅行不是浪迹天涯，而是给心找一个小憩的地方。",
  "心存阳光，必有诗和远方。",
] as Array<string>;

let finalStringsArray = [] as Array<string>;
const getShuffleStrings = () => {
  finalStringsArray = getRandomArrayElements(stringArray, 3);
  // alert(finalMedalsArray)
  // console.log(finaStringsArray);
  // alert(finalMedalsArray[0][0])
  // alert(finalMedalsArray[0][1])
};
getShuffleStrings();

let picArray = [
  "三星堆",
  "上海",
  "九寨沟",
  "保定",
  "台湾",
  "大雁塔",
  "布达拉宫",
  "故宫",
  "江苏",
  "江西",
  "洞庭湖",
  "湖南",
  "福建",
  "纳木错",
  "西湖",
  "贡嘎雪山",
  "贵州",
  "重庆",
  "长城",
  "香港",
] as Array<string>;

let finallyPicArray = [] as Array<string>;
const getShufflepic = () => {
  finallyPicArray = getRandomArrayElements(picArray, 3);
};
getShufflepic();
/* 第一次进入 */
const requertRecommendSceneryInfo = () => {
  if (ustore.userInfo.id && !store.refreshFlag) {
    //如果已经刷新过了(刷新过了标志还是false)但是还没获取推荐内容
    if (
      //@ts-ignore
      rstore.getRecommendFlag === false
    ) {
      // alert("获取推荐内容！");
      rstore
        .getRecommendSceneryFromPy(ustore.userInfo.id) // 获得推荐景区
        .then((res) => {
          // alert("得到了");
          console.log("推荐景区：");
          console.log(rstore.recommendscenerys);
          rstore.getRecommendFlag = true;
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "爱宝儿，已为你推荐专属景区~",
          });
          // alert(rstore.getRecommendFlag);
          // scenerysRecommendInfo.value = rstore.recommendscenerys;
          // userLoginFlag.value = true;
        })
        .catch(() => {
          //@ts-ignore
          // ElMessage({
          //   type: "warning",
          //   message: "爱宝儿，你的登录已过期~",
          // });
          rstore.getRecommendFlag = false;
          rstore.recommendscenerys = [] as theCityScenerysInfoType[];
        });
    }
  }
};
requertRecommendSceneryInfo();
onMounted(() => {
  //要放到mounted里面才可以！
  (function ($) {
    /* 视频 */
    if ($(".video-popup").length) {
      $(".video-popup").magnificPopup({
        type: "iframe",
        mainClass: "mfp-fade",
        removalDelay: 160,
        preloader: true,
        fixedContentPos: false,
      });
    }
    // @ts-ignore
  })(jQuery);
});
</script>
<script lang="ts">
/* 控制日历弹出 */
//@ts-ignore
(function ($) {
  $(document).ready(function () {
    /* 绑定日历弹出事件 */
    if ($("#datepicker").length) {
      $("#datepicker").datepicker();
    }
    /* 获取日历变化的值赋值给隐藏表单，从而传输给vue */
    $("#datepicker").bind("input propertychange change", function () {
      $("#hiddenInput").val($("#datepicker").val());
      $("#hiddenInput")[0].dispatchEvent(new Event("input")); //为了触发v-model的绑定
      // console.log($("#hiddenInput").val());
    });

    // Popular Tour Carousel
    if ($(".popular-tours__carousel").length) {
      $(".popular-tours__carousel").owlCarousel({
        loop: true,
        margin: 30,
        nav: false,
        smartSpeed: 500,
        autoHeight: false,
        autoplay: true,
        dots: true,
        autoplayTimeout: 10000,
        navText: [
          '<span class="icon-left-arrow"></span>',
          '<span class="icon-right-arrow"></span>',
        ],
        responsive: {
          0: {
            items: 1,
          },
          600: {
            items: 1,
          },
          800: {
            items: 2,
          },
          1024: {
            items: 2,
          },
          1200: {
            items: 4,
          },
        },
      });
    }
  });
  // /* 视频 */
  // if ($(".video-popup").length) {
  //   $(".video-popup").magnificPopup({
  //     type: "iframe",
  //     mainClass: "mfp-fade",
  //     removalDelay: 160,
  //     preloader: true,
  //     fixedContentPos: false,
  //   });
  // }
  //@ts-ignore
})(jQuery);
</script>

<template>
  <!--Main Slider Start-->
  <section class="main-slider">
    <div
      class="swiper-container thm-swiper__slider"
      data-swiper-options='{"slidesPerView": 1, "loop": true,
    "effect": "fade",
    "pagination": {
        "el": "#main-slider-pagination",
        "type": "bullets",
        "clickable": true
      },
    "navigation": {
        "nextEl": ".main-slider-button-next",
        "prevEl": ".main-slider-button-prev",
        "clickable": true
    },
    "autoplay": {
        "delay": 5000
    }}'
    >
      <div class="swiper-wrapper">
        <div class="swiper-slide">
          <div
            class="image-layer"
            :style="{
              backgroundImage: 'url(/images/bg/' + finallyPicArray[0] + '.jpg)',
            }"
          ></div>
          <div class="image-layer-overlay"></div>
          <div class="container">
            <div class="swiper-slide-inner">
              <div class="row">
                <div class="col-xl-12">
                  <h2>Fall in love with travel</h2>
                  <p class="ali-font-family">{{ finalStringsArray[0] }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="swiper-slide">
          <div
            class="image-layer"
            :style="{
              backgroundImage: 'url(/images/bg/' + finallyPicArray[1] + '.jpg)',
            }"
          ></div>
          <div class="image-layer-overlay"></div>
          <div class="container">
            <div class="swiper-slide-inner">
              <div class="row">
                <div class="col-xl-12">
                  <h2>Fall in love with travel</h2>
                  <p class="ali-font-family">{{ finalStringsArray[1] }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="swiper-slide">
          <div
            class="image-layer"
            :style="{
              backgroundImage: 'url(/images/bg/' + finallyPicArray[2] + '.jpg)',
            }"
          ></div>
          <div class="image-layer-overlay"></div>
          <div class="container">
            <div class="swiper-slide-inner">
              <div class="row">
                <div class="col-xl-12">
                  <h2>Fall in love with travel</h2>
                  <p class="ali-font-family">{{ finalStringsArray[2] }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="main-slider-nav">
        <div class="main-slider-button-prev">
          <span class="icon-right-arrow"></span>
        </div>
        <div class="main-slider-button-next">
          <span class="icon-right-arrow"></span>
        </div>
      </div>
    </div>
  </section>

  <!--Tour Search Start-->
  <section class="tour-search">
    <div class="container">
      <div class="row">
        <div class="col-xl-12">
          <div class="tour-search-box">
            <form
              class="tour-search-one"
              autocomplete="off"
              @submit.prevent="createPlan"
            >
              <div class="tour-search-one__inner">
                <div class="tour-search-one__inputs">
                  <div
                    class="tour-search-one__input-box tour-search-one__input-box-last"
                  >
                    <label for="type" class="ali-font-family">出发</label>
                    <select
                      class="selectpicker"
                      id="type"
                      v-model="planInfo.fromCity"
                      placeholder="出发城市"
                    >
                      <option
                        v-for="item in citysInfos"
                        :value="item.cityName"
                        :key="item.cityId"
                      >
                        {{ item.cityName }}
                      </option>
                      <!-- <option v-for="item in 20" :value="1">
                        {{ item }}
                      </option> -->
                    </select>
                  </div>
                  <div
                    class="tour-search-one__input-box tour-search-one__input-box-last"
                  >
                    <label for="type2" class="ali-font-family">返回</label>
                    <select
                      class="selectpicker"
                      id="type2"
                      v-model="planInfo.toCity"
                      placeholder="返回城市"
                    >
                      <option
                        v-for="item in citysInfos"
                        :value="item"
                        :key="item.cityId"
                      >
                        {{ item.cityName }}
                      </option>
                    </select>
                  </div>
                  <div class="tour-search-one__input-box">
                    <label class="ali-font-family">出发日期</label>
                    <input
                      type="text"
                      placeholder="出发日期"
                      name="Nothing selected"
                      id="datepicker"
                    />
                  </div>
                </div>
                <div class="tour-search-one__btn-wrap">
                  <button class="thm-btn tour-search-one__btn ali-font-family">
                    制定行程
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>

  <input type="text" id="hiddenInput" v-model="planInfo.goDate" />
  <!--Destinations One Start-->
  <section class="destinations-one">
    <div class="container">
      <div class="section-title text-center">
        <span class="section-title__tagline"> The city we yearn for</span>
        <h2 class="section-title__title ali-font-family">向往的城市</h2>
      </div>
      <div class="row masonary-layout">
        <div class="col-xl-3 col-lg-3">
          <div class="destinations-one__single">
            <div
              class="destinations-one__img"
              style="width: 285px; height: 285px"
            >
              <img
                :src="hotCityOne.url"
                alt=""
                style="width: 100%; height: 100%"
              />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">
                  {{ hotCityOne.cityEname }}
                </p>
                <h2 class="destinations-one__title">
                  <router-link
                    :to="`/goTravel/city/detail/${hotCityOne.cityId}`"
                    class="ali-font-family"
                  >
                    {{ hotCityOne.cityName }}</router-link
                  >
                </h2>
              </div>
              <div class="destinations-one__button">
                <router-link :to="`/goTravel/city/detail/${hotCityOne.cityId}`"
                  >查看详情</router-link
                >
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-6 col-lg-6">
          <div class="destinations-one__single">
            <div
              class="destinations-one__img"
              style="width: 580px; height: 285px"
            >
              <img
                :src="hotCityTwo.url"
                alt=""
                style="width: 100%; height: 100%"
              />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">
                  {{ hotCityTwo.cityEname }}
                </p>
                <h2 class="destinations-one__title">
                  <router-link
                    :to="`/goTravel/city/detail/${hotCityTwo.cityId}`"
                    >{{ hotCityTwo.cityName }}</router-link
                  >
                </h2>
              </div>
              <div class="destinations-one__button">
                <router-link :to="`/goTravel/city/detail/${hotCityTwo.cityId}`"
                  >查看详情</router-link
                >
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-3 col-lg-3">
          <div class="destinations-one__single">
            <div
              class="destinations-one__img"
              style="width: 285px; height: 285px"
            >
              <img
                :src="hotCityThree.url"
                alt=""
                style="width: 100%; height: 100%"
              />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">
                  {{ hotCityThree.cityEname }}
                </p>
                <h2 class="destinations-one__title">
                  <router-link
                    :to="`/goTravel/city/detail/${hotCityThree.cityId}`"
                    >{{ hotCityThree.cityName }}</router-link
                  >
                </h2>
              </div>
              <div class="destinations-one__button">
                <router-link
                  :to="`/goTravel/city/detail/${hotCityThree.cityId}`"
                  >查看详情</router-link
                >
              </div>
            </div>
          </div>
        </div>

        <div class="col-xl-6 col-lg-6">
          <div class="destinations-one__single">
            <div
              class="destinations-one__img"
              style="width: 580px; height: 285px"
            >
              <img
                :src="hotCityFour.url"
                alt=""
                style="width: 100%; height: 100%"
              />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">
                  {{ hotCityFour.cityEname }}
                </p>
                <h2 class="destinations-one__title">
                  <router-link
                    :to="`/goTravel/city/detail/${hotCityFour.cityId}`"
                    >{{ hotCityFour.cityName }}</router-link
                  >
                </h2>
              </div>
              <div class="destinations-one__button">
                <router-link :to="`/goTravel/city/detail/${hotCityFour.cityId}`"
                  >查看详情</router-link
                >
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-6 col-lg-6">
          <div class="destinations-one__single">
            <div
              class="destinations-one__img"
              style="width: 580px; height: 285px"
            >
              <img
                :src="hotCityFive.url"
                alt=""
                style="width: 100%; height: 100%"
              />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">
                  {{ hotCityFive.cityEname }}
                </p>
                <h2 class="destinations-one__title">
                  <router-link
                    :to="`/goTravel/city/detail/${hotCityFive.cityId}`"
                    >{{ hotCityFive.cityName }}</router-link
                  >
                </h2>
              </div>
              <div class="destinations-one__button">
                <router-link :to="`/goTravel/city/detail/${hotCityFive.cityId}`"
                  >查看详情</router-link
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!--Destinations One End-->

  <!--News One Start-->
  <section class="news-one">
    <div class="container">
      <div class="news-one__top">
        <div class="row">
          <div class="col-xl-9 col-lg-9">
            <div class="news-one__top-left">
              <div class="section-title text-left">
                <span class="section-title__tagline"
                  >Travels And Strategies</span
                >
                <h2 class="section-title__title ali-font-family">
                  游记 & 攻略
                </h2>
              </div>
            </div>
          </div>
          <div class="col-xl-3 col-lg-3">
            <div class="news-one__top-right">
              <router-link to="/readTravel/note" class="news-one__btn thm-btn"
                >查看全部</router-link
              >
            </div>
          </div>
        </div>
      </div>
      <div class="news-one__bottom">
        <div class="row">
          <div
            class="col-xl-4 col-lg-6 col-md-6 fadeInUp"
            data-wow-delay="100ms"
            v-for="(item, index) in notesInfo"
            :key="item.id"
          >
            <!--News One Single-->
            <div class="news-one__single">
              <div class="news-one__img">
                <img :src="item.url" alt="" class="notes-img" />
                <router-link :to="`/readTravel/note/detail/${item.id}`">
                  <span class="news-one__plus"></span>
                </router-link>
                <div class="news-one__date">
                  <p>
                    <span>{{ timeFormat(item.createTime) }}</span>
                  </p>
                </div>
              </div>
              <div class="news-one__content">
                <ul class="list-unstyled news-one__meta list-unstyled2">
                  <li style="margin-left: 0">
                    <a href="javascript:;"
                      ><el-icon size="20px"><View /></el-icon>浏览:{{
                        numberFormat(item.view)
                      }}</a
                    >
                  </li>
                  <li style="margin-left: 10px">
                    <a href="javascript:;"
                      ><el-icon size="20px"><Pointer /></el-icon>点赞:{{
                        numberFormat(item.like)
                      }}</a
                    >
                  </li>
                  <li style="margin-left: 10px">
                    <a href="javascript:;"
                      ><el-icon size="20px"><Star /></el-icon>收藏:{{
                        numberFormat(item.star)
                      }}</a
                    >
                  </li>
                  <li style="margin-left: 10px">
                    <a href="javascript:;">
                      <el-icon size="20px"><Document /></el-icon>评论:{{
                        numberFormat(item.comment)
                      }}
                    </a>
                  </li>
                  <li style="margin-left: 0">
                    <a
                      href="javascript:;"
                      style="width: 370px; justify-content: left"
                      ><i class="far fa-user-circle"></i
                      ><span class="span-style">{{ item.userName }}</span></a
                    >
                  </li>
                </ul>
                <h3 class="news-one__title">
                  <router-link :to="`/readTravel/note/detail/${item.id}`">{{
                    item.title
                  }}</router-link>
                </h3>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <section class="popular-tours">
    <div class="popular-tours__container">
      <div class="section-title text-center">
        <span class="section-title__tagline">High Quality Products</span>
        <h2 class="section-title__title ali-font-family">优质旅游产品</h2>
      </div>
      <div class="row">
        <div class="col-xl-12">
          <div class="popular-tours__carousel owl-theme owl-carousel">
            <div
              class="col-xl-4 col-lg-6 col-md-6 wow fadeInUp"
              data-wow-delay="100ms"
              v-for="(item, index) in productsInfo"
              :key="index"
            >
              <div class="popular-tours__single">
                <div class="popular-tours__img">
                  <img :src="item.url" alt="" />
                </div>
                <div
                  class="popular-tours__content"
                  style="padding-bottom: 45px"
                >
                  <h3 class="popular-tours__title">
                    <router-link
                      style="display: block; height: 60px; overflow: hidden"
                      :to="`/buyTravel/product/detail/${item.id}`"
                      >{{ item.name }}</router-link
                    >
                  </h3>
                  <p class="popular-tours__rate">
                    <span>¥{{ item.price }}</span>
                  </p>
                  <ul class="popular-tours__meta list-unstyled list-unstyled2">
                    <li>
                      <a href="javascript:;"
                        ><el-icon style="margin-right: 10px"
                          ><OfficeBuilding /></el-icon
                        >途径{{ item.plan?.subPlans?.length }}城市</a
                      >
                    </li>
                    <li>
                      <a href="javascript:;"
                        ><el-icon style="margin-right: 10px"><Clock /></el-icon
                        >历时{{ subPlansFormat(item.plan?.subPlans) }}天</a
                      >
                    </li>
                    <li>
                      <a href="javascript:;"
                        ><el-icon style="margin-right: 10px"><Phone /></el-icon
                        >联系人电话:{{ item.tele }}</a
                      >
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!--Why Choose Start-->
  <section class="why-choose">
    <div class="why-choose__container">
      <div class="why-choose__left">
        <div
          class="why-choose__left-bg"
          style="background-image: url(/images/back/gongga.jpg)"
        ></div>
        <div class="why-choose__toggle">
          <p>
            Notes <br />
            & Tours
          </p>
        </div>
      </div>
      <div class="why-choose__right">
        <div
          class="why-choose__right-map"
          style="background-image: url(/images/shapes/why-choose-right-map.png)"
        ></div>
        <div class="why-choose__right-content">
          <div class="section-title text-left">
            <span class="section-title__tagline">Why choose us</span>
            <h2 class="section-title__title ali-font-family">
              为什么选择爱旅游
            </h2>
          </div>
          <p class="why-choose__right-text">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;爱旅游是一款聚焦于构建旅游资源生态模式的新生代旅游服务产品，旨在为您打造个性化的沉浸式旅游体验。
            您在此系统内可以实现热门城市/景点信息的获取，旅行游记的分享，旅游出行的组队，以及选购我们推出的优质旅游产品。
          </p>
          <ul class="list-unstyled why-choose__list">
            <li>
              <div class="icon" style="width: 235px; height: 150px">
                <span class="icon-travel"></span>
              </div>
              <div class="text">
                <h4>出行无忧</h4>
                <p>
                  我们提供了具有高度自主化的行程定制服务，平台拥有海量城市/景点等数据供您参考。
                  同时您还可以尝试进行旅游组队，找到与您志同道合的队友一起出发。
                  最后本平台还有优质的旅游产品供您选择，让您真正做到出行无忧。
                </p>
              </div>
            </li>
            <li>
              <div class="icon" style="width: 140px; height: 120px">
                <span class="icon-travel-map"></span>
              </div>
              <div class="text">
                <h4>游有所记</h4>
                <p>
                  本平台拥有强大的旅行游记社区，上万篇游记供您参考，最重要的是您可以发布自己的游记，记录美好瞬间和心得，在旅游中感悟人生。
                </p>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </section>
  <!--Why Choose End-->
  <!--Video Two Start-->
  <div class="video-two">
    <div
      class="video-two-bg jarallax"
      data-jarallax
      data-speed="0.2"
      data-imgPosition="50% 0%"
      style="background-image: url(/images/back/shanghai.jpg)"
    ></div>
    <div class="container">
      <div class="row">
        <div class="col-xl-12">
          <div class="video-two__inner">
            <div class="video-one__video-link">
              <!-- href="http://v.qq.com/txp/iframe/player.html?vid=u0133b4ownh&amp;autoplay=true&amp;tiny=0" -->
              <a
                href="http://player.bilibili.com/player.html?aid=691651336&bvid=BV1K24y1S72x&cid=935271938&page=1"
                class="video-popup"
              >
                <div class="video-one__video-icon">
                  <span class="icon-play-button"></span>
                  <i class="ripple"></i>
                </div>
              </a>
            </div>
            <p class="video-one__tagline">Are you ready to travel?</p>
            <h2 class="video-one__title">
              爱旅游——新一代旅游网站<br />
              带给您沉浸式的旅游体验
            </h2>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--Video Two End-->
  <!--Brand One Start-->
  <section class="brand-one">
    <div
      class="brand-one-shape"
      style="background-image: url(/images/shapes/brand-one-shape.png)"
    ></div>
    <div class="container">
      <div class="row">
        <div class="col-xl-3">
          <div class="brand-one__title">
            <h2>Our partners</h2>
          </div>
        </div>
        <div class="col-xl-9">
          <div class="brand-one__main-content">
            <div
              class="thm-swiper__slider swiper-container"
              data-swiper-options='{"spaceBetween": 100, "slidesPerView": 5, "autoplay": { "delay": 5000 }, "breakpoints": {
                        "0": {
                            "spaceBetween": 30,
                            "slidesPerView": 2
                        },
                        "375": {
                            "spaceBetween": 30,
                            "slidesPerView": 2
                        },
                        "575": {
                            "spaceBetween": 30,
                            "slidesPerView": 3
                        },
                        "767": {
                            "spaceBetween": 50,
                            "slidesPerView": 4
                        },
                        "991": {
                            "spaceBetween": 50,
                            "slidesPerView": 5
                        },
                        "1199": {
                            "spaceBetween": 50,
                            "slidesPerView": 5
                        }
                    }}'
            >
              <div class="swiper-wrapper">
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-1.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-2.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-3.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-4.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-5.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-1.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-2.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-3.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-4.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-5.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!--Brand One End-->
</template>

<style scoped lang="scss">
.news-one {
  padding-bottom: 40px;
}
.why-choose__left-bg {
  width: auto;
  height: 900px;
}
.list-unstyled2 {
  display: flex;
  flex-wrap: wrap;
  > li {
    // margin-left: 5px;
    display: flex;
    justify-content: center;
    > a {
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}
.span-style {
  display: inline-block;
  width: auto;
  max-width: 100px;
  overflow: hidden;
  height: 2em;
  color: #ffffff;
  background-color: #e8604c;
  border-radius: 5px;
  padding: 0 5px;
  line-height: 2em;
  margin-right: 5px;
}
.el-pagination {
  display: flex;
  justify-content: center;
}
/* 修改图片那个地方的样式！ */
.notes-img {
  width: 100%;
  height: 100%;
}
.news-one__img {
  width: 350px;
  height: 300px;
}
.tour-search-one__btn-wrap {
  height: 105px;
}
.destinations-one {
  padding: 60px 0 0;
}
.brand-one {
  margin-bottom: 150px;
}
#hiddenInput {
  display: none;
}
/* 产品 */
.popular-tours {
  padding-bottom: 120px;
}
.popular-tours__img {
  width: 288px;
  height: 201px;
  cursor: pointer;
  img {
    width: 100%;
    height: 100%;
  }
}
.popular-tours__single {
  width: 288px;
  height: 510px;
  border-radius: 10px;
  margin-bottom: 10px;
}
.popular-tours__meta {
  flex-direction: column;
  align-items: flex-start;
}
.popular-tours__meta li + li {
  margin-left: 0;
}
.ali-font-family {
  font-family: ali-regular;
}
.popular-tours__content {
  border: 0;
}
</style>
