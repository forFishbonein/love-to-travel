<!-- <script setup>
import { ref } from "vue";
const color = ref("#e8604c");
</script> -->
<script>
import AMapLoader from "@amap/amap-jsapi-loader"; // 使用加载器加载JSAPI，可以避免异步加载、重复加载等常见错误加载错误
import { shallowRef } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
export default {
  // 简单功能的实现
  setup() {
    const router = useRouter();
    /* 定义变量值 */
    const color = ref("#e8604c");
    const color2 = ref("#FFFFFF");
    const color3 = ref("#A8ABB2");
    const value1 = ref("");
    const outerVisible = ref(false);
    const innerVisible = ref(false);
    const formLabelWidth = "100px";
    // const radio1 = ref("1000元左右");
    const radio1 = ref("");

    const budget = reactive({
      money: "",
    });
    const confirmPlan = () => {
      // alert(1111);
      // innerVisible = false; //不需要关闭了，直接跳走即可
      router.replace("/result");
    };
    /* 高德地图实现 */
    // alert(111);
    const map = shallowRef(null);

    // 初始化地图
    function initMap() {
      AMapLoader.load({
        key: "c3e09b00e68a28103b9377d18f5c2101", // 申请好的Web端开发者Key，首次调用 load 时必填
        version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [
          "AMap.Scale", //工具条，控制地图的缩放、平移等
          "AMap.ToolBar", //比例尺，显示当前地图中心的比例尺
          "AMap.Geolocation", //定位，提供了获取用户当前准确位置、所在城市的方法
          "AMap.HawkEye", //鹰眼，显示缩略图
        ], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      })
        .then((AMap) => {
          let map = new AMap.Map("map", {
            //设置地图容器id
            zoom: 4.5, //初始化地图层级
            viewMode: "3D", //是否为3D地图模式
            // center: [116.397436, 39.909165], //初始化地图中心点位置，北京
            center: [105.602725, 37.076636], //初始化地图中心点位置
            dragEnable: true, //禁止鼠标拖拽
            scrollWheel: true, //鼠标滚轮放大缩小
            doubleClickZoom: true, //双击放大缩小
            keyboardEnable: true, //键盘控制放大缩小移动旋转
          });
          map.setDefaultCursor("pointer"); //使用CSS默认样式定义地图上的鼠标样式（default/pointer/move/crosshair）
          map.addControl(new AMap.Scale()); //异步同时加载多个插件
          map.addControl(new AMap.ToolBar());
          map.addControl(new AMap.Geolocation());
          let HawkEye = new AMap.HawkEye({
            position: "RB", //控件停靠位置（LT/RT/LB/RB）
          });
          map.addControl(HawkEye);
          let marker1 = new AMap.Marker({
            // icon: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
            position: map.getCenter(),
            title: "中心点",
          });
          let marker2 = new AMap.Marker({
            position: new AMap.LngLat(114.298572, 30.584355), //2位小数就可以
            title: "武汉",
          });
          let markerList = [marker1, marker2];
          map.add(markerList); // 地图添加标记
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
          function logMapinfo() {
            let zoom = map.getZoom(); //获取当前地图级别
            let center = map.getCenter(); //获取当前地图中心位置
            document.querySelector("#map-zoom").innerText = zoom;
            document.querySelector("#map-center").innerText = center.toString();
          }
          //绑定地图移动与缩放事件
          map.on("moveend", logMapinfo);
          map.on("zoomend", logMapinfo);
          let infoWindow = new AMap.InfoWindow({
            //创建信息窗体
            isCustom: false, //使用自定义窗体
            anchor: "top-right", //信息窗体的三角所在位置
            content: `<h4>信息窗体</h4>`, //信息窗体的内容可以是任意html片段
            offset: new AMap.Pixel(16, -45),
          });
          //为地图注册click事件获取鼠标点击出的经纬度坐标
          // map.on("mouseover", function (e) {
          //   document.getElementById("lnglat").value =
          //     e.lnglat.getLng() + "," + e.lnglat.getLat();
          // });
          map.on("click", function (e) {
            document.getElementById("lnglat").innerText =
              e.lnglat.getLng() + "," + e.lnglat.getLat();
            infoWindow.open(map, [e.lnglat.getLng(), e.lnglat.getLat()]);
          });
          // infoWindow.open(map, [121.939253, 29.905078]); //填写想要窗体信息指示的坐标

          // map.on("click", function (e) {
          //   //监听点标记的点击事件
          //   infoWindow.open(map, marker1.getPosition()); //信息窗体打开
          // });
        })
        .catch((e) => {
          console.log(e);
        });
    }

    // 调用地图初始化函数：onMounted 函数会在 DOM 初始化完成后调用，建议在 mounted 函数中调用地图初始化方法
    onMounted(() => {
      initMap();
    });

    return {
      map,
      initMap,
      color,
      color2,
      color3,
      value1,
      outerVisible,
      innerVisible,
      budget,
      formLabelWidth,
      radio1,
      confirmPlan,
    };
  },
};
(function ($) {
  $(document).ready(function () {
    $(".input-search")
      .focus(function () {
        $(this).css("background-color", "white");
      })
      .blur(function () {
        $(this).css("background-color", "#e0e0e0");
      });
  });
})(jQuery);
</script>
<template>
  <div class="info">
    <p>当前级别：<span id="map-zoom">11</span></p>
    <p>当前中心点：<span id="map-center">121.498586,31.239637</span></p>
    <p>当前经纬度：<span id="lnglat"></span></p>
  </div>
  <div id="map"></div>
  <div class="main">
    <div class="main-left">
      <div class="left-title">
        <p>选择目的地</p>
      </div>
      <div class="left-body">
        <div class="search">
          <div class="search-icon">
            <el-icon :size="15" :color="color">
              <Search />
            </el-icon>
          </div>
          <input type="text" class="input-search" />
        </div>
        <div class="items">
          <el-scrollbar height="400px">
            <div v-for="item in 20" :key="item" class="scrollbar-item">
              <div><img src="../assets/images/login-pic.jpg" /></div>
              <div>
                <div class="title-city">
                  <p><a>上海</a></p>
                  <p>ShangHai</p>
                </div>
                <div class="content-city">20%的人会去，87360人去过</div>
              </div>
              <div>
                <div class="button-city">
                  <el-icon><Plus :size="30" :color="color" /></el-icon>
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
        <el-date-picker
          v-model="value1"
          type="date"
          placeholder="选择出发日期"
          :size="size"
        />
      </div>
      <div class="right-body">
        <div class="plan-container">
          <div class="from-city">
            <div>
              <el-icon><Position /></el-icon>
              <el-input
                v-model="input"
                placeholder="出发城市"
                class="inputel"
              />
            </div>
          </div>
          <div class="plan-items">
            <el-scrollbar max-height="150px">
              <div v-for="item in 20" :key="item" class="scrollbar-item2">
                <div class="left-name-icon">
                  <el-icon :size="23" :color="color3"
                    ><CircleCloseFilled /></el-icon
                  >北京
                </div>
                <div class="right-days"><input type="number" />天</div>
              </div>
            </el-scrollbar>
          </div>
          <div class="back-city">
            <div>
              <el-icon><Position /></el-icon>
              <el-input
                v-model="input"
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
          <el-radio-group v-model="radio1" size="large">
            <el-radio-button label="1000元左右" />
            <el-radio-button label="3000元左右" />
            <el-radio-button label="5000元左右" />
          </el-radio-group>
          <div class="select-confirm">
            <el-button type="primary" @click="confirmPlan"> 确认 </el-button>
          </div>
        </div>
      </div>
      <el-dialog
        v-model="innerVisible"
        title="自定义预算"
        width="30%"
        append-to-body
      >
        <el-form :model="budget">
          <el-form-item label="您的预算：" :label-width="formLabelWidth">
            <el-input
              v-model="budget.money"
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
            <el-button type="primary" @click.native="confirmPlan">
              确认
            </el-button>
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
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
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
  margin: 0px;
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
    .left-title {
      background-color: #e8604c;
      width: 100%;
      height: 10%;
      border: 1px #e8604c solid;
      display: flex;
      justify-content: left;
      align-content: center;
      p {
        font-size: 25px;
        font-weight: 1000;
        color: white;
        margin-top: 8px;
        margin-left: 20px;
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
          border-left: 0;
          padding-left: 10px;
          transition: all 0.3s linear;
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
          background: rgba(232, 96, 76, 0.2);
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
            padding-top: 5px;
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
          width: 340px;
          // border: 1px #e8604c solid;
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
              width: 80px;
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
</style>
<!-- const color2 = ref("#379E65"); -->
