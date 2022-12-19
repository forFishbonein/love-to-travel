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
const store = utilStore();
const router = useRouter();
let citysInfos = [
  {
    cityId: "110000",
    cityName: "北京市",
    lng: "110",
    lat: "33",
    url: null,
    introduction: null,
  },
  {
    cityId: "140700",
    cityName: "晋中市",
    lng: "112.759595",
    lat: "37.692839",
    url: null,
    introduction: null,
  },
  {
    cityId: "150700",
    cityName: "呼伦贝尔市",
    lng: "119.784869",
    lat: "49.172733",
    url: null,
    introduction: null,
  },
  {
    cityId: "210200",
    cityName: "大连市",
    lng: "121.621632",
    lat: "38.918954",
    url: null,
    introduction: null,
  },
  {
    cityId: "310000",
    cityName: "上海市",
    lng: "121.480539",
    lat: "31.235929",
    url: null,
    introduction: null,
  },
  {
    cityId: "330100",
    cityName: "杭州市",
    lng: "120.215512",
    lat: "30.253083",
    url: null,
    introduction: null,
  },
  {
    cityId: "330200",
    cityName: "宁波市",
    lng: "121.630843",
    lat: "29.866068",
    url: null,
    introduction: null,
  },
  {
    cityId: "330500",
    cityName: "湖州市",
    lng: "120.094517",
    lat: "30.898964",
    url: null,
    introduction: null,
  },
  {
    cityId: "361100",
    cityName: "上饶市",
    lng: "117.94946",
    lat: "28.460626",
    url: null,
    introduction: null,
  },
  {
    cityId: "460100",
    cityName: "海口市",
    lng: "110.20672",
    lat: "20.052113",
    url: null,
    introduction: null,
  },
  {
    cityId: "500000",
    cityName: "重庆市",
    lng: "106.558434",
    lat: "29.568996",
    url: null,
    introduction: null,
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
    id: "1603681436596965100",
    introduction: "高级套餐",
    name: "高级套餐4",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965105",
    introduction: "高级套餐",
    name: "高级套餐5",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965106",
    introduction: "高级套餐",
    name: "高级套餐6",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965107",
    introduction: "高级套餐",
    name: "高级套餐7",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965108",
    introduction: "高级套餐",
    name: "高级套餐8",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965109",
    introduction: "高级套餐",
    name: "高级套餐9",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965110",
    introduction: "高级套餐",
    name: "高级套餐10",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965111",
    introduction: "高级套餐",
    name: "高级套餐11",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965112",
    introduction: "高级套餐",
    name: "高级套餐12",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965371",
    introduction: "高级套餐",
    name: "高级套餐1",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965372",
    introduction: "高级套餐",
    name: "高级套餐2",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965373",
    introduction: "高级套餐",
    name: "高级套餐3",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
  },
  {
    cost: "",
    createTime: "2022-12-16 17:20:24",
    id: "1603681436596965378",
    introduction: "高级套餐",
    name: "高级套餐",
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
    price: "500",
    tele: "10086",
    url: "https://img1.qunarzz.com/travel/poi/201405/15/31897e5ea444843eddb12cfb.jpg_r_480x360x95_f8287a40.jpg",
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

onMounted(() => {});
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
            style="
              background-image: url(/images/backgrounds/main-slider-1-1.jpg);
            "
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
            style="
              background-image: url(/images/backgrounds/main-slider-1-2.jpg);
            "
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
            style="
              background-image: url(/images/backgrounds/main-slider-1-3.jpg);
            "
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
            <form class="tour-search-one" action="tour-sidebar.html">
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
                      placeholder="Nothing selected"
                      name="Nothing selected"
                      id="datepicker"
                    />
                  </div>
                </div>
                <div class="tour-search-one__btn-wrap">
                  <button
                    @click="createPlan"
                    class="thm-btn tour-search-one__btn ali-font-family"
                  >
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
                <div class="popular-tours__content">
                  <h3 class="popular-tours__title">
                    <router-link :to="`/buyTravel/product/detail/${item.id}`">{{
                      item.name
                    }}</router-link>
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
          style="
            background-image: url(/images/backgrounds/why-choose-left-bg.jpg);
          "
        ></div>
        <div class="why-choose__toggle">
          <p>
            Trips <br />
            & tours
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
  height: 480px;
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
</style>
