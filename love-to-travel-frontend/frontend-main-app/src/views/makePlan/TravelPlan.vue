<!-- <script setup>
import { ref } from "vue";
const color = ref("#e8604c");
</script> -->
<script lang="ts">
import AMapLoader from "@amap/amap-jsapi-loader"; // 使用加载器加载JSAPI，可以避免异步加载、重复加载等常见错误加载错误
import { shallowRef } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import { ref, reactive, toRefs, computed, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import {
  wantCityWithLalType,
  citysInfoType,
  planCityInfoType,
} from "@/apis/interface/myInterface";
import { getCitysInfo } from "@apis/travelService/city";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import { timeFormat } from "@/utils/filters/time";
import locationData from "@/assets/js/location";
export default {
  props: ["fromCity", "toCity", "goDate"],
  // 简单功能的实现
  setup(props, context) {
    const router = useRouter();
    // const route = useRoute();
    // console.log(route.params);

    /* 定义变量值 */
    const color = ref("#e8604c");
    const color2 = ref("#FFFFFF");
    const color3 = ref("#A8ABB2");
    const outerVisible = ref(false);
    const innerVisible = ref(false);
    const size = ref<"default" | "large" | "small">("default");
    const goDate = props.goDate;
    const fromCity = props.fromCity;
    let toCity = {} as citysInfoType;
    if (props.toCity) {
      toCity = JSON.parse(props.toCity);
    }

    //这个地方是数组的增删改查，就可以用reative，还是会响应到页面上，但是axios请求的数据就必须用ref
    // let wantCitysData: wantCityType[] = reactive([]);
    //@ts-ignore //下面用了toRefs
    let planCityInfo: planCityInfoType = reactive({
      fromTheCity: fromCity,
      wantCitys: [],
      backCity: "",
      goTheDate: goDate,
      budget: "",
    });

    /* 定义方法 */
    // 初始化左边的数据
    // 大坑：只有在用ref定义的数据时，在后面修改的时候才会响应到页面中（否则页面不刷新，那么修改了页面也不会响应到最新的数据）
    let citysInfo = ref([] as citysInfoType[]);
    const requestCitysInfo = async () => {
      await getCitysInfo()
        .then((res: any) => {
          if (res.code != 0) {
            //@ts-ignore
            ElMessage({
              type: "error",
              message: res.msg,
            });
          } else {
            citysInfo.value = res.data.slice(0, 327);
            console.log(citysInfo);
          }
        })
        .catch((error) => {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: error.message,
          });
        });
    };
    // 初始化右边的数据
    const initWantCityInfo = () => {
      if (toCity && toCity.cityName) {
        //要加一下toCity.cityName
        planCityInfo.wantCitys.push({
          //这里必须要转一下，因为数据类型不一样，字段不一样！
          id: toCity.cityId,
          toCity: toCity.cityName,
          days: null,
          lng: toCity.lng,
          lat: toCity.lat,
        });
        console.log(planCityInfo.wantCitys);
        console.log(planCityInfo);
      }
    };

    // 管理右边的数据
    const addToWant = (index: number) => {
      initMap(planCityInfo.wantCitys); //往上面放，因为这个慢
      planCityInfo.wantCitys.push({
        id: citysInfo.value[index].cityId,
        toCity: citysInfo.value[index].cityName,
        // days: null,
        days: 3,
        lng: citysInfo.value[index].lng,
        lat: citysInfo.value[index].lat,
      });
      console.log(planCityInfo);
    };
    /* 城市选择器 */
    const dialogFormVisible = ref(false);
    const openCitySelectDialog = () => {
      dialogFormVisible.value = true;
    };
    const provinces = ref([]);
    const initProvinceData = () => {
      for (let code in locationData) {
        let item = locationData[code];
        provinces.value.push(
          // @ts-ignore
          Object.assign(item, {
            label: item.name,
            code: item.code,
          })
        );
      }
    };
    initProvinceData();
    const citys = ref([]);
    const selectProvince = ref(null);
    const selectCity = ref(null);
    const selectCityId = ref(null);
    const proviceHandle = (value) => {
      // console.log(value);
      const city = [];
      for (let code in value.cities) {
        let item = value.cities[code];
        city.push(
          // @ts-ignore
          Object.assign(item, {
            label: item.name,
            code: item.code,
          })
        );
      }
      citys.value = city;
      selectProvince.value = value.name;
      selectCity.value = null;
    };
    const cityHandle = (value) => {
      selectCity.value = value.name;
      selectCityId.value = value.code;
      console.log("----------");
      console.log(selectCityId.value);
      console.log("------------");
    };
    const address = computed(() => {
      return (
        (selectProvince.value ? selectProvince.value : "") +
        (selectCity.value ? "，" + selectCity.value : "")
      );
    });
    /* 利用城市选择器添加数据 */
    const addToWantByPicker = () => {
      if (selectCity.value && selectCityId.value) {
        let theLng = "";
        let theLat = "";
        citysInfo.value.forEach((e) => {
          if (selectCity.value === e.cityName) {
            theLng = e.lng;
            theLat = e.lat;
            return;
          }
        });
        initMap(planCityInfo.wantCitys); //往上面放，因为这个慢
        planCityInfo.wantCitys.push({
          // @ts-ignore
          id: selectCityId.value,
          // @ts-ignore
          toCity: selectCity.value,
          // days: null,
          days: 3,
          lng: theLng,
          lat: theLat,
        });
        console.log(planCityInfo);
        dialogFormVisible.value = false;
      } else {
        // @ts-ignore
        ElMessage({
          type: "error",
          message: "请将信息选择完整",
        });
      }
    };

    /* 删除city */
    const deleteTheCity = (index: number) => {
      initMap(planCityInfo.wantCitys);
      console.log(planCityInfo.wantCitys);
      planCityInfo.wantCitys.splice(index, 1);
      console.log(planCityInfo.wantCitys);
    };

    // 确认计划
    const confirmPlan = () => {
      innerVisible.value = false;
      // console.log(planCityInfo);
      // console.log(planCityInfo.wantCitys);
      let array = [];
      planCityInfo.wantCitys.forEach((e) => {
        // console.log(e);
        // console.log(JSON.parse(JSON.stringify(e)));
        Reflect.deleteProperty(e, "lng"); //去掉两个字段，因为这两个在第三个页面用不到
        Reflect.deleteProperty(e, "lat");
        // @ts-ignore
        array.push(JSON.parse(JSON.stringify(e)));
      });
      // console.log(array);
      router.replace({
        name: "TrvalPlanResult",
        //@ts-ignore
        params: {
          fromTheCity: planCityInfo.fromTheCity,
          //这里必须要转换成json字符串再传输，因为router的参数只接受数字和字符串类型，否则会被转换成字符串"Object object"
          wantCitys: JSON.stringify(array),
          backCity: planCityInfo.backCity,
          goTheDate: timeFormat(planCityInfo.goTheDate),
          budget: planCityInfo.budget,
        },
      });
    };

    /* 高德地图实现 */
    const map = shallowRef(null);
    // 初始化地图方法
    function initMap(toCitys?: wantCityWithLalType[]) {
      AMapLoader.load({
        key: "c3e09b00e68a28103b9377d18f5c2101", // 申请好的Web端开发者Key，首次调用 load 时必填
        version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [
          "AMap.Scale", //工具条，控制地图的缩放、平移等
          "AMap.ToolBar", //比例尺，显示当前地图中心的比例尺
          // "AMap.Geolocation", //定位，提供了获取用户当前准确位置、所在城市的方法
          // "AMap.HawkEye", //鹰眼，显示缩略图
          // "AMap.Weather", //获取实时天气
        ], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      })
        .then((AMap) => {
          let map = new AMap.Map("map", {
            //设置地图容器id
            zoom: 5, //初始化地图层级
            viewMode: "3D", //是否为3D地图模式
            // center: [116.397436, 39.909165], //初始化地图中心点位置，北京
            // center: [106.55048, 29.5637], //初始化地图中心点位置，成都
            center: [103.834228, 36.060798], //初始化地图中心点位置，兰州
            dragEnable: true, //禁止鼠标拖拽
            scrollWheel: true, //鼠标滚轮放大缩小
            doubleClickZoom: true, //双击放大缩小
            keyboardEnable: true, //键盘控制放大缩小移动旋转
          });
          map.setDefaultCursor("pointer"); //使用CSS默认样式定义地图上的鼠标样式（default/pointer/move/crosshair）
          map.addControl(new AMap.Scale()); //异步同时加载多个插件
          map.addControl(new AMap.ToolBar());
          /* 注释1 */
          // map.addControl(new AMap.Geolocation());
          // let HawkEye = new AMap.HawkEye({
          //   position: "RB", //控件停靠位置（LT/RT/LB/RB）
          // });
          // map.addControl(HawkEye);
          /* 注释1 */
          // let marker1 = new AMap.Marker({
          //   position: map.getCenter(),
          //   title: "中心点",
          // });
          // let marker2 = new AMap.Marker({
          //   position: new AMap.LngLat(114.298572, 30.584355), //2位小数就可以
          //   title: "武汉",
          // });
          // let markerList = [marker1, marker2];
          let Obj = {
            markerList: [],
            windowList: [],
          };
          // let markerList = [];
          if (toCitys) {
            // 封装一下
            toCitys.forEach((e: wantCityWithLalType) => {
              // var weather = new AMap.Weather();
              // let info = {};
              // //执行实时天气信息查询
              // weather.getLive(e.toCity, function (err, data) {
              //   console.log(err, data);
              //   info = data;
              // });
              Obj.markerList.push(
                // @ts-ignore
                new AMap.Marker({
                  position: new AMap.LngLat(e.lng, e.lat),
                  title: e.toCity,
                })
              );
              Obj.windowList.push(
                // @ts-ignore
                new AMap.InfoWindow({
                  //创建信息窗体
                  isCustom: false, //使用自定义窗体
                  anchor: "top-right", //信息窗体的三角所在位置
                  content: `<p style="color:#e8604c;margin:0">目的地：${e.toCity}</p>
                <p style="color:#e8604c;margin:0">经度：${e.lng}</p>
                <p style="color:#e8604c;margin:0">纬度：${e.lat}</p>`, //信息窗体的内容可以是任意html片段
                  offset: new AMap.Pixel(0, 0), //不需要偏移量
                })
              );
            });

            map.add(Obj.markerList); // 地图添加标记
            for (let i = 0; i < Obj.markerList.length; i++) {
              // @ts-ignore
              Obj.markerList[i].on("click", () => {
                // @ts-ignore
                Obj.windowList[i].open(map, Obj.markerList[i].getPosition());
                map.on("click", () => {
                  // @ts-ignore
                  Obj.windowList[i] && Obj.windowList[i].close();
                });
              });
            }
          }
          // let infoWindow = new AMap.InfoWindow({
          //   //创建信息窗体
          //   isCustom: false, //使用自定义窗体
          //   anchor: "top-right", //信息窗体的三角所在位置
          //   content: `<h4>信息窗体</h4>`, //信息窗体的内容可以是任意html片段
          //   offset: new AMap.Pixel(16, -45),
          // });
          // 这样不能个性化显示每个标点的信息
          // Obj.markerList.forEach((marker) => {
          //   // @ts-ignore
          //   marker.on("click", () => {
          //     // @ts-ignore
          //     infoWindow.open(map, marker.getPosition());
          //   });
          // });

          // @ts-ignore
          AMapLoader.load({
            //可多次调用load
            plugins: ["AMap.MapType"],
          })
            .then((AMap) => {
              map.addControl(new AMap.MapType());
            })
            .catch((e) => {
              console.error(e);
            });
          // 显示地图层级与中心点信息
          /* 注释2 */
          // function logMapinfo() {
          //   let zoom = map.getZoom(); //获取当前地图级别
          //   let center = map.getCenter(); //获取当前地图中心位置
          //   // @ts-ignore
          //   document.querySelector("#map-zoom").innerText = zoom;
          //   // @ts-ignore
          //   document.querySelector("#map-center").innerText = center.toString();
          // }
          // //绑定地图移动与缩放事件
          // map.on("moveend", logMapinfo);
          // map.on("zoomend", logMapinfo);
          /* 注释2 */

          //为地图注册mouseover事件获取鼠标放上去就显示经纬度坐标
          // map.on("mouseover", function (e) {
          //   document.getElementById("lnglat").value =
          //     e.lnglat.getLng() + "," + e.lnglat.getLat();
          // });

          // let infoWindow = new AMap.InfoWindow({
          //   //创建信息窗体
          //   isCustom: false, //使用自定义窗体
          //   anchor: "top-right", //信息窗体的三角所在位置
          //   content: `<h4>信息窗体</h4>`, //信息窗体的内容可以是任意html片段
          //   offset: new AMap.Pixel(16, -45),
          // });
          /* 注释3 */
          // map.on("click", function (e) {
          //   // @ts-ignore
          //   document.getElementById("lnglat").innerText =
          //     e.lnglat.getLng() + "," + e.lnglat.getLat();
          //   // infoWindow.open(map, [e.lnglat.getLng(), e.lnglat.getLat()]);
          // });
          /* 注释3 */
          // infoWindow.open(map, [121.939253, 29.905078]); //填写想要窗体信息指示的坐标
        })
        .catch((e) => {
          console.log(e);
        });
    }

    /* 实现搜索功能 */
    const onSearchFlag = ref(false);
    const notOnSearchFlag = ref(true);
    const keyword = ref("");
    // let timer = null;
    const searchResultcitysInfo = ref([] as citysInfoType[]);
    watch(keyword, (newValue, oldValue) => {
      // alert(newValue);
      searchResultcitysInfo.value = [] as citysInfoType[];
      notOnSearchFlag.value = false;
      onSearchFlag.value = true;
      // if (timer) {
      //   clearTimeout(timer);
      // }
      if (!newValue) {
        //如果新的值为空的，即清空了
        searchResultcitysInfo.value = [] as citysInfoType[];
        onSearchFlag.value = false;
        notOnSearchFlag.value = true;
        return;
      }
      citysInfo.value.forEach((e) => {
        if (e.cityName.indexOf(newValue) !== -1) {
          //!==-1那就说明包含
          searchResultcitysInfo.value.push(e);
        }
      });
      // @ts-ignore
      // timer = setTimeout(async () => {

      // }, 100);
    });
    const addToWant2 = (index: number) => {
      initMap(planCityInfo.wantCitys); //往上面放，因为这个慢
      planCityInfo.wantCitys.push({
        id: searchResultcitysInfo.value[index].cityId,
        toCity: searchResultcitysInfo.value[index].cityName,
        // days: null,
        days: 3,
        lng: searchResultcitysInfo.value[index].lng,
        lat: searchResultcitysInfo.value[index].lat,
      });
      console.log(planCityInfo);
    };
    const backToHome = () => {
      router.push("/");
    };
    onMounted(() => {
      initWantCityInfo();
      requestCitysInfo();
      // 调用地图初始化函数：onMounted 函数会在 DOM 初始化完成后调用，建议在 mounted 函数中调用地图初始化方法
      initMap(planCityInfo.wantCitys);
    });

    return {
      map,
      initMap,
      color,
      color2,
      color3,
      outerVisible,
      innerVisible,
      confirmPlan,
      deleteTheCity,
      citysInfo,
      addToWant,
      ...toRefs(planCityInfo),
      size,
      zhCn,
      openCitySelectDialog,
      dialogFormVisible,
      provinces,
      citys,
      selectProvince,
      selectCity,
      proviceHandle,
      cityHandle,
      address,
      addToWantByPicker,
      onSearchFlag,
      keyword,
      searchResultcitysInfo,
      addToWant2,
      notOnSearchFlag,
      backToHome,
    };
  },
  mounted() {
    //@ts-ignore
    (function ($) {
      $(document).ready(function () {
        $(".change-model-button").on("click", function () {
          // console.log($(".main-left").attr("transform"));
          console.log($(".main-left").css("transform"));
          if ($(".main-left").css("transform") === "matrix(1, 0, 0, 1, 0, 0)") {
            // @ts-ignore
            $(this).html(
              `<i data-v-f86fb796="" class="el-icon" style="--color:#d75663;"><svg data-v-f86fb796="" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg"><path fill="currentColor" d="M384 192v640l384-320.064z"></path></svg></i>`
            );
            $(".main-left").css("transform", "translateX(-92%)");
            $(".main-right").css("transform", "translateX(-250%)");
          } else {
            // @ts-ignore
            $(this).html(
              `<i data-v-f86fb796="" class="el-icon" style="--color:#d75663;"><svg data-v-f86fb796="" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg"><path fill="currentColor" d="M672 192 288 511.936 672 832z"></path></svg></i>`
            );
            $(".main-left").css("transform", "translateX(0%)");
            $(".main-right").css("transform", "translateX(0%)");
          }
        });
      });
      //@ts-ignore
    })(jQuery);
  },
};
</script>
<template>
  <div id="map"></div>
  <div class="back-button" @click="backToHome">
    <el-icon><Back /></el-icon>
  </div>
  <div class="main">
    <div class="main-left">
      <div class="left-title">
        <p>选择目的地</p>
        <div class="city-picker-button" @click="openCitySelectDialog">
          城市选择器
        </div>
        <div class="change-model-button">
          <el-icon color="#d75663"> <CaretLeft /></el-icon>
        </div>
      </div>
      <div class="left-body">
        <div class="search">
          <div class="search-icon">
            <el-icon :size="15" :color="color">
              <Search />
            </el-icon>
          </div>
          <input type="text" class="input-search" v-model="keyword" />
        </div>
        <div class="items" v-show="onSearchFlag">
          <el-scrollbar height="460px">
            <div
              v-for="(item, index) in searchResultcitysInfo"
              :key="index"
              class="scrollbar-item"
            >
              <div><img :src="item.url" /></div>
              <div>
                <div class="title-city">
                  <p>
                    <a>{{ item.cityName }}</a>
                  </p>
                  <p>{{ item.cityEname.toUpperCase() }}</p>
                </div>
                <div class="content-city">
                  <el-scrollbar max-height="40px" style="padding-right: 10px">
                    {{ item.introduction }}
                  </el-scrollbar>
                </div>
              </div>
              <div>
                <div class="button-city">
                  <el-icon @click="addToWant2(index)"
                    ><Plus :size="30" :color="color"
                  /></el-icon>
                </div>
              </div>
            </div>
          </el-scrollbar>
        </div>
        <div class="items" v-show="notOnSearchFlag">
          <el-scrollbar height="460px">
            <div
              v-for="(item, index) in citysInfo"
              :key="index"
              class="scrollbar-item"
            >
              <div><img :src="item.url" /></div>
              <div>
                <div class="title-city">
                  <p>
                    <a>{{ item.cityName }}</a>
                  </p>
                  <p>{{ item.cityEname.toUpperCase() }}</p>
                </div>
                <div class="content-city">
                  <el-scrollbar max-height="40px" style="padding-right: 10px">
                    {{ item.introduction }}
                  </el-scrollbar>
                </div>
              </div>
              <div>
                <div class="button-city">
                  <el-icon @click="addToWant(index)"
                    ><Plus :size="30" :color="color"
                  /></el-icon>
                </div>
              </div>
            </div>
          </el-scrollbar>
        </div>
      </div>
    </div>
    <div class="main-right">
      <div class="right-title">
        <p>我的行程</p>
        <el-config-provider :locale="zhCn">
          <el-date-picker
            v-model="goTheDate"
            type="date"
            placeholder="选择出发日期"
            :size="size"
          />
        </el-config-provider>
      </div>
      <div class="right-body">
        <div class="plan-container">
          <div class="from-city">
            <div>
              <el-icon><Position /></el-icon>
              <el-input
                v-model="fromTheCity"
                placeholder="出发城市"
                class="inputel"
              />
            </div>
          </div>
          <div class="plan-items">
            <el-scrollbar max-height="150px">
              <div
                v-for="(item, index) in wantCitys"
                :key="item.id"
                class="scrollbar-item2"
              >
                <div class="left-name-icon">
                  <el-icon
                    :size="23"
                    :color="color3"
                    @click="deleteTheCity(index)"
                    ><CircleCloseFilled /></el-icon
                  >{{ item.toCity }}
                </div>
                <div class="right-days">
                  <input type="number" v-model.number="item.days" />天
                </div>
              </div>
            </el-scrollbar>
          </div>
          <div class="back-city">
            <div>
              <el-icon><Position /></el-icon>
              <el-input
                v-model="backCity"
                placeholder="返回城市"
                class="inputel"
              />
            </div>
          </div>
        </div>
        <div class="next-button" @click="outerVisible = true">
          <div class="content-next">
            <span class="next-text">下一步</span>
            <span class="next-icon">
              <el-icon :size="30" :color="color2">
                <CaretRight />
              </el-icon>
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
  <el-dialog v-model="outerVisible" title="智能推荐行程方案">
    <template #default>
      <div class="select-budget">
        <div>请选择您的旅行预算</div>
        <div class="select-body">
          <el-radio-group v-model="budget" size="large">
            <el-radio-button label="1000" />
            <el-radio-button label="3000" />
            <el-radio-button label="5000" />
          </el-radio-group>
        </div>
        <div class="select-confirm">
          <el-button type="primary" @click="confirmPlan"> 确认 </el-button>
        </div>
      </div>
      <el-dialog
        v-model="innerVisible"
        title="自定义预算"
        width="30%"
        append-to-body
      >
        <el-form :model="budget">
          <el-form-item label="您的预算：" label-width="100px">
            <el-input
              v-model="budget"
              autocomplete="off"
              type="number"
              class="money-input"
            />
            元
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <!-- <el-button @click="dialogFormVisible = false">Cancel</el-button> -->
            <el-button type="primary" @click="confirmPlan">确认</el-button>
          </span>
        </template>
      </el-dialog>
    </template>
    <template #footer>
      <div class="dialog-footer">
        <div class="custom-plan">
          <div>没有您想要的方案？</div>
          <el-button type="primary" @click="innerVisible = true">
            自定义
          </el-button>
        </div>
      </div>
    </template>
  </el-dialog>
  <el-dialog v-model="dialogFormVisible" title="城市选择器">
    <div class="city-select-machine">
      <el-select class="m-2" placeholder="请选择省份" v-model="selectProvince">
        <el-option
          v-for="item in provinces"
          :key="
            // @ts-ignore
            item.code
          "
          :value="
            // @ts-ignore
            item.name
          "
          :label="
            // @ts-ignore
            item.name
          "
          @click="proviceHandle(item)"
        />
      </el-select>
      <el-select
        class="m-2"
        placeholder="请选择城市"
        v-model="selectCity"
        v-if="citys.length > 0"
      >
        <el-option
          v-for="item in citys"
          :key="
            // @ts-ignore
            item.code
          "
          :value="
            // @ts-ignore
            item.name
          "
          :label="
            // @ts-ignore
            item.name
          "
          @click="cityHandle(item)"
        />
      </el-select>
    </div>
    <div v-if="address" class="result-select-address">
      您选择的是：<span class="text-select">{{ address }}</span>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addToWantByPicker"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
.select-budget {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}
.select-budget > div:first-child {
  font-size: 18px;
  margin-right: 50px;
}
.select-body {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  .select-confirm {
    margin-left: 20px;
  }
}
.custom-plan {
  display: flex;
  justify-content: end;
  button {
    background-color: #4ec483 !important;
    border: 1px solid #4ec483;
  }
  button:hover {
    background-color: #71d09c !important;
    border: 1px solid #71d09c;
  }
}
.money-input {
  width: 200px;
  margin-right: 10px;
}
#map {
  margin: 0px;
  width: 100%;
  // height: 600px;
  height: 100%;
  padding: 0px;
}
.info {
  position: fixed;
  top: 20px;
  left: 750px;
  z-index: 1000;
  background-color: rgba(49, 48, 65, 0.2);
  /* opacity: 0.2; */
  border-radius: 20px;
  color: #e8604c;
  padding: 15px 20px 0;
}
.main {
  width: 700px;
  height: 85%;
  position: fixed;
  top: 5%;
  left: 20px;
  // border: 1px #e8604c solid;
  z-index: 1000;
  .main-left {
    border-radius: 5px;
    overflow: hidden; //这个必须要加，要不然圆角失效
    height: 100%;
    width: 350px;
    float: left;
    box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
    // border: 1px #e8604c solid;
    transition: 0.8s cubic-bezier(1, -2.02, 0.38, 2.05);

    .left-title {
      background-color: #e8604c;
      width: 100%;
      height: 10%;
      // line-height: 50px;
      border: 1px #e8604c solid;
      display: flex;
      justify-content: center;
      align-items: center;
      position: relative;
      p {
        display: flex;
        align-items: center;
        font-size: 25px;
        font-weight: 1000;
        color: white;
        // margin-top: 8px;
        // margin-left: 20px;
        margin: 0;
      }
      .city-picker-button {
        width: 100px;
        height: 30px;
        float: left;
        background-color: #ffffff;
        margin-left: 20px;
        font-size: 16px;
        font-weight: 700;
        color: #e8604c;
        border-radius: 5px;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
      }
    }
    .left-body {
      box-sizing: border-box;
      padding-top: 20px;
      width: 100%;
      height: 90%;
      background-color: white;
      .search {
        margin: 0 auto;
        width: 90%;
        height: 10%;
        // border: 1px #e8604c solid;
        .search-icon {
          width: 10%;
          height: 34px;
          float: left;
          display: flex;
          justify-content: center;
          align-items: center;
          border: 1px #e8604c solid;
          border-top-left-radius: 4px;
          border-bottom-left-radius: 4px;
          border-right: 0;
          transition: all 0.3s linear;
          cursor: pointer;
        }
        .search-icon:hover {
          background-color: #e4e4e4;
        }
        .input-search {
          width: 90%;
          background-color: #e4e4e4;
          outline: none !important;
          border: 0;
          float: right;
          font-size: 15px;
          line-height: 30px;
          border: 1px #e8604c solid;
          border-top-right-radius: 4px;
          border-bottom-right-radius: 4px;
          border-left: 0;
          padding-left: 10px;
          transition: all 0.3s linear;
        }
        .input-search:hover,
        .input-search:focus {
          background-color: #ffffff;
        }
      }
      .items {
        margin: 10px auto;
        width: 90%;
        height: 80%;
        // border: 1px #e8604c solid;
        .scrollbar-item {
          display: flex;
          align-items: center;
          justify-content: space-around;
          height: 100px;
          // width: 80%;
          width: 300px;
          // margin: 10px;
          margin: 10px 0;
          border-radius: 4px;
          background: rgba(253, 252, 176, 0.2);
          box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
          color: var(--el-color-primary);
        }
        .scrollbar-item > div:first-child {
          // border: 1px #e8604c solid;
          width: 80px;
          height: 80px;
          img {
            width: 80px;
            height: 80px;
          }
        }
        .scrollbar-item > div:nth-child(2) {
          // border: 1px #e8604c solid;
          width: 140px;
          height: 80px;
          display: flex;
          flex-direction: column;
          align-content: space-between;
          .title-city {
            width: 140px;
            height: 40px;
            // border: 1px #e8604c solid;
            p {
              margin: 0;
              line-height: 1em;
              height: 20px;
              font-size: 12px;
              color: #333333;
            }
          }
          .title-city > p:first-child {
            font-size: 15px;
            color: black;
            a:hover {
              color: #e8604c;
              cursor: pointer;
            }
          }
          .content-city {
            color: #333333;
            // padding-top: 5px;
            width: 135px;
            height: 40px;
            // border: 1px #e8604c solid;
            font-size: 12px;
            line-height: 1em;
          }
        }
        .scrollbar-item > div:nth-child(3) {
          // border: 1px #e8604c solid;
          width: 45px;
          height: 80px;
          padding-top: 20px;
          .button-city {
            width: 40px;
            height: 40px;
            // border: 1px #e8604c solid;
            line-height: 45px;
            text-align: center;
            background-color: #f5f5f5;
            border-radius: 3px;
            transition: all 0.3s linear;
            cursor: pointer;
          }
          .button-city:hover {
            box-shadow: 0 10px 10px -10px rgba(0, 0, 0, 0.5);
            -webkit-transform: scale(1.1);
            transform: scale(1.1);
          }
        }
      }
    }
  }
  .main-right {
    height: 75%;
    width: 340px;
    float: right;
    // border: 1px #e8604c solid;
    border-radius: 5px;
    box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
    transition: 0.8s cubic-bezier(1, -2.02, 0.38, 2.05);

    .right-title {
      background-color: #e8604c;
      width: 100%;
      height: 11%;
      // border: 1px #e8604c solid;
      display: flex;
      justify-content: left;
      align-content: center;
      background-color: #e4e4e4;
      padding-top: 4px;
      p {
        font-size: 20px;
        font-weight: 500;
        color: #606266;
        margin-top: -2px;
        margin-left: 20px;
        margin-right: 15px;
      }
    }
    .right-body {
      width: 100%;
      height: 89%;
      background-color: rgba(255, 255, 255, 0.9);
      .plan-container {
        width: 100%;
        height: 80%;
        // border: 1px #e8604c solid;
        position: relative;
        div {
          display: flex;
          justify-content: center;
          align-items: center;
        }
        .from-city {
          height: 20%;
          width: 340px;
          // border: 1px #e8604c solid;
          .inputel {
            width: 300px;
            margin-left: 10px;
          }
          position: absolute;
          top: 10px;
        }
        .back-city {
          height: 20%;
          width: 340px;
          // border: 1px #e8604c solid;
          .inputel {
            width: 300px;
            margin-left: 10px;
          }
          position: absolute;
          bottom: 10px;
        }
        .plan-items {
          height: 60%;
          width: 450px;
          padding: 16px;
          // border: 1px #e8604c solid;
          justify-content: left;
          position: absolute;
          top: 52px;
          .scrollbar-item2 {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 10px;
            height: 50px;
            width: 300px;
            // margin: 10px;
            margin: 10px 0;
            border-radius: 4px;
            background: rgba(232, 96, 76, 0.2);
            color: var(--el-color-primary);
            .left-name-icon {
              // border: 1px #e8604c solid;
              width: 120px;
              height: 50px;
              font-size: 15px;
              display: flex;
              justify-content: space-around;
              align-items: center;
              color: #000000;
              i {
                border-radius: 16px;
                transition: all 0.3s linear;
                cursor: pointer;
              }
              i:hover {
                box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1),
                  0 0 4px 0 rgba(0, 0, 0, 0.1);
                color: #e8604c;
              }
            }
            .right-days {
              width: 50px;
              height: 50px;
              // border: 1px #e8604c solid;
              font-size: 15px;
              color: #606266;
              input {
                color: #606266;
                font-size: 15px;
                width: 25px;
                height: 20px;
                outline: none;
                border: 0;
                margin-right: 5px;
                border-radius: 2px;
                transition: all 0.1s linear;
              }
              input:focus {
                border: 1px #e8604c solid;
              }
            }
          }
        }
      }
      .next-button {
        width: 100%;
        height: 20%;
        // border: 1px #e8604c solid;
        background-color: #4ec483;
        position: relative;
        cursor: pointer;
        .content-next {
          position: absolute;
          top: 50%;
          left: 50%;
          height: 40px;
          width: 100px;
          transform: translate(-50%, -50%);
          display: flex;
          justify-content: space-around;
          .next-text {
            display: inline-block;
            // padding-bottom: 10px;
            line-height: 40px;
            color: white;
            font-weight: 700;
          }
          .next-icon {
            display: inline-block;
            // border: 1px #e8604c solid;
            width: 40px;
            height: 40px;
            padding: 5px;
            i {
              width: 30px;
              height: 30px;
              background-color: #e8604c;
              border-radius: 30px;
            }
          }
        }
      }
      .next-button:hover {
        background-color: rgba(78, 196, 131, 0.8);
      }
    }
  }
}
.city-select-machine {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: start;
  padding-left: 75px;
}
.result-select-address {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: center;
  font-size: 16px;
  font-weight: 700;
  margin-top: 20px;
  .text-select {
    color: #e8604c;
  }
}
.back-button {
  position: fixed;
  bottom: 90px;
  right: 20px;
  width: 30px;
  height: 30px;
  // border: 1px #e8604c solid;
  border-radius: 30px;
  background-color: #4ec483;
  color: #ffffff;
  font-size: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: all 0.3s linear;
  font-weight: 700;
}
.back-button:hover {
  background-color: rgba(78, 196, 131, 0.8);
  color: #e0e0e0;
}

//切换模式的按钮
.change-model-button {
  width: 28px;
  height: 28px;
  border-radius: 28px;
  background-color: #e0e0e0;
  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.2);
  position: absolute;
  right: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.1s linear;
}
.change-model-button:hover {
  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.3);
  // background-color: #cc0033;
}
</style>
