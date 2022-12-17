<script setup lang="ts">
import { ref } from "vue";
import { getUserAllPlansInfoByUserId } from "@/apis/travelService/plan";
import { finalAllCityPlansInfoType } from "@/apis/interface/myInterface";
import { mainStore } from "@/store/user";
import AMapLoader from "@amap/amap-jsapi-loader"; // 使用加载器加载JSAPI，可以避免异步加载、重复加载等常见错误加载错误
import { shallowRef } from "@vue/reactivity";
import { productInfoType, routeInfoType } from "@/apis/interface/myInterface";
const store = mainStore();
const userAllPlansInfo = ref([] as finalAllCityPlansInfoType[]);
const requestMyPlansInfo = async () => {
  await getUserAllPlansInfoByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        userAllPlansInfo.value = res.data;
        console.log(userAllPlansInfo.value);
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
requestMyPlansInfo();

/* 高德地图实现 */
const map = shallowRef(null);
// 初始化地图方法
function initMap(routeInfo?: routeInfoType[]) {
  AMapLoader.load({
    key: "c3e09b00e68a28103b9377d18f5c2101", // 申请好的Web端开发者Key，首次调用 load 时必填
    version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: [
      "AMap.Scale", //工具条，控制地图的缩放、平移等
      "AMap.ToolBar", //比例尺，显示当前地图中心的比例尺
    ], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
  })
    .then((AMap) => {
      let map = new AMap.Map("map", {
        //设置地图容器id
        zoom: 8.5, //初始化地图层级
        viewMode: "3D", //是否为3D地图模式
        // center: [116.397436, 39.909165], //初始化地图中心点位置，北京
        // @ts-ignore
        center: routeInfo[0].origin, //初始化地图中心点位置，其中一个景点
        dragEnable: true, //禁止鼠标拖拽
        scrollWheel: true, //鼠标滚轮放大缩小
        doubleClickZoom: true, //双击放大缩小
        keyboardEnable: true, //键盘控制放大缩小移动旋转
      });
      map.setDefaultCursor("pointer"); //使用CSS默认样式定义地图上的鼠标样式（default/pointer/move/crosshair）
      map.addControl(new AMap.Scale()); //异步同时加载多个插件
      map.addControl(new AMap.ToolBar());

      let Obj = {
        markerList: [],
        windowList: [],
      };
      if (routeInfo) {
        // 封装一下
        routeInfo.forEach((e: routeInfoType) => {
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
              position: new AMap.LngLat(e.origin[0], e.origin[1]),
            })
          );
          Obj.windowList.push(
            // @ts-ignore
            new AMap.InfoWindow({
              //创建信息窗体
              isCustom: false, //使用自定义窗体
              anchor: "top-right", //信息窗体的三角所在位置
              content: `<p style="color:#e8604c;margin:0">目的景区：${e.originName}</p>
            <p style="color:#e8604c;margin:0">出发时间：${e.departTime}</p>
            <p style="color:#e8604c;margin:0">交通工具：${e.vehicle}</p>`, //信息窗体的内容可以是任意html片段
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
    })
    .catch((e) => {
      console.log(e);
    });
}
const theMapVisible = ref(false);
// let pointArray = [] as routeInfoType[]
const openTheMapForOneDay = (routeInfo: routeInfoType[]) => {
  // pointArray = [] as routeInfoType[]
  // route.forEach((e) => {
  //   pointArray.push(e)
  // });
  initMap(routeInfo);
  theMapVisible.value = true;
};
</script>

<template>
  <el-scrollbar max-height="770px">
    <div
      class="sidebar__single sidebar__category"
      v-for="(cell, index1) in userAllPlansInfo"
      :key="index1"
    >
      <h3 class="sidebar__title">行程表{{ index1 + 1 }}</h3>
      <ul class="sidebar__category-list list-unstyled">
        <li style="font-size: 16px">总预算:{{ cell.budget }}元</li>
        <li>
          <span class="span-style text-amber">行程信息</span>
          <el-scrollbar max-height="300px">
            <el-collapse accordion>
              <el-collapse-item
                :name="index2"
                v-for="(item, index2) in cell.subPlans"
                :key="index2"
              >
                <template #title>
                  <div class="plan-note-header">
                    {{ item.city
                    }}<el-icon class="header-icon" size="16px">
                      <Place />
                    </el-icon>
                    <span style="margin-left: 10px">预算:</span
                    >{{ item.budget }}元
                  </div>
                </template>
                <ul>
                  <li v-for="(i, index3) in item.days" :key="index3">
                    第{{ index3 + 1 }}天
                    <span v-for="(k, index4) in i.route" :key="index4">
                      {{ k.originName }}({{ k.departTime }}小时)
                      <el-icon><Right /></el-icon>
                    </span>
                    结束
                    <el-button
                      type="primary"
                      size="small"
                      style="margin-left: 10px"
                      @click="openTheMapForOneDay(i.route)"
                      >查看地图</el-button
                    >
                  </li>
                </ul>
              </el-collapse-item>
            </el-collapse>
          </el-scrollbar>
        </li>
      </ul>
    </div>
  </el-scrollbar>
  <el-dialog v-model="theMapVisible" :show-close="false">
    <template #header="{ close, titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass">景区行程地图</h4>
        <el-button type="danger" @click="close">
          <el-icon class="el-icon--left"><CircleCloseFilled /></el-icon>
          关闭
        </el-button>
      </div>
    </template>
    <div id="map"></div>
  </el-dialog>
</template>

<style lang="scss" scoped>
.sidebar__single {
  margin: 20px;
}
/* 地图 */
#map {
  margin: 0px;
  width: 100%;
  height: 550px;
  // height: 100%;
  padding: 0px;
}
.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>
