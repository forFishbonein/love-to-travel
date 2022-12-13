<script setup lang="ts">
import { ref, inject, onMounted } from "vue";
// import * as echarts from "echarts";
import chinaJson from "@/assets/json/map/china.json";
const activeIndex = ref("1");
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};

let echarts = inject("ec"); //引入
const initEcharts = () => {
  //需要获取到element,所以是onMounted的Hook
  // @ts-ignore
  let myChart = echarts.init(document.getElementById("map-container"));
  let scatterData = [
    {
      name: "北京",
      value: [117.283042, 31.86119],
    },
    {
      name: "上海",
      value: [112.982279, 28.19409],
    },
  ];
  // @ts-ignore
  echarts.registerMap("chinaMap", chinaJson);
  var option = {
    tooltip: {
      padding: 0,
      enterable: true,
      transitionDuration: 0,
      textStyle: {
        color: "#000",
        decoration: "none",
      },
      // 弹层
      extraCssText: "white-space:pre-wrap",
      formatter: function (params: any) {
        let name = params.name;
        return `<div style="width: 200px;
                min-height: 200px;
                  display: flex;
              flex-direction: column;
                background-color: #f7f2ea;
            border-radius: 5px;
              overflow: hidden;
            box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);">
        <div style="  display: flex;
        justify-content: center;
        align-items: center;
        width: 200px;
      height: 30px;
       background-color: #e8604c;"><span style="color:#ffffff;font-weight: 600;">我的足迹：${name}</span></div>
        <div style="  display: flex;
        flex-direction: column;
      width: 200px;
        padding: 10px;
        box-sizing: border-box;">
            <p style="  margin: 0;
            line-height: 1.7em;"><span style="color:#e8604c;font-weight: 600;">英文名：</span>BEIJING</p>
            <p style="  margin: 0;
                line-height: 1.7em;"><span style="color:#e8604c;font-weight: 600;">简介：</span>东大寺达娃大碗大碗大碗大碗大碗大大伟大大达瓦达瓦吊带袜大大我
            </p>
        </div>
    </div>`;
      },
    },
    geo: {
      map: "chinaMap",
      show: true,
      roam: false,
      zoom: 1.3, //当前视角的缩放比例
      aspectScale: 0.86,
      layoutCenter: ["50%", "50%"], //地图位置
      layoutSize: "100%",
      itemStyle: {
        normal: {
          shadowColor: "rgba(80,225,247,0.6)",
          shadowOffsetX: 1,
          shadowOffsetY: 5,
          opacity: 1,
          shadowBlur: 10,
        },
        emphasis: {
          areaColor: "rgba(0,243,255,1)",
        },
      },
      regions: [
        {
          name: "南海诸岛",
          itemStyle: {
            areaColor: "rgb(17,37,76)",

            borderColor: "rgb(17,37,76)",
            normal: {
              opacity: 0,
              label: {
                show: false,
                color: "#009cc9",
              },
            },
          },
          label: {
            show: false,
            color: "#FFFFFF",
            fontSize: 12,
          },
        },
      ],
    },

    series: [
      {
        name: "散点",
        type: "effectScatter",
        coordinateSystem: "geo",
        // 显示的点
        // data: this.convertData(data),
        data: scatterData,
        // 气泡大小
        symbolSize: 9,
        symbol: "circle",
        label: {
          //   显示位置
          normal: {
            show: false,
          },
          emphasis: {
            show: false,
          },
        },
        showEffectOn: "render",
        itemStyle: {
          normal: {
            color: {
              type: "radial",
              x: 0.5,
              y: 0.5,
              r: 0.5,
              colorStops: [
                {
                  offset: 0,
                  color: "rgba(12,46,80,0.1)",
                },
                {
                  offset: 0.8,
                  color: "rgba(14,245,209,0.2)",
                },
                {
                  offset: 1,
                  color: "rgba(14,245,209,1)",
                },
              ],
              // 圆点圈的显示
              global: false, // 缺省为 false
            },
          },
        },
      },

      // 常规地图
      {
        type: "map",
        map: "chinaMap",
        geoIndex: 1,
        aspectScale: 0.85,
        layoutCenter: ["50%", "50%"], //地图位置
        layoutSize: "100%",
        showLegendSymbol: false, // 存在legend时显示
        selectedMode: "false",
        zoom: 1.3, //当前视角的缩放比例
        // roam: true, //是否开启平游或缩放
        scaleLimit: {
          //滚轮缩放的极限控制
          min: 1,
          max: 4,
        },
        tooltip: {
          show: false,
        },
        label: {
          normal: {
            show: false,
          },
          emphasis: {
            show: false,
          },
        },
        roam: false,
        itemStyle: {
          normal: {
            areaColor: {
              type: "radial",
              x: 0.5,
              y: 0.5,
              r: 0.8,
              colorStops: [
                {
                  offset: 0.26,
                  color: "rgba(16,27,63,1)", // 0% 处的颜色
                },
                {
                  offset: 1,
                  color: "rgba(21,73,121,1)", // 100% 处的颜色
                },
              ],
              global: false, // 缺省为 false
            },

            borderColor: "rgba(0,243,255,0.4)",
            borderWidth: 2.5,
          },
          emphasis: {
            areaColor: "rgb(16,27,63)",
            label: {
              color: "#fff",
            },
          },
        },
      },
    ],
  };
  //4.挂载：必须放在最后
  myChart.setOption(option);
  window.onresize = function () {
    //自适应大小
    myChart.resize();
  };
};
onMounted(() => {
  initEcharts();
});
</script>

<template>
  <div id="map-container"></div>
  <div class="personal-info-container">
    <div class="button-already">添加去过</div>
    <div class="button-want">添加想去</div>
    <div class="avater-container">
      <img src="@/assets/images/login-pic.jpg" class="avater-img" />
    </div>
  </div>
  <div class="header-nav-container">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item index="1">我的中心</el-menu-item>
      <el-menu-item index="2">足迹与点评</el-menu-item>
      <el-menu-item index="3">游记</el-menu-item>
      <el-menu-item index="4">点赞</el-menu-item>
      <el-menu-item index="5">问答</el-menu-item>
      <el-menu-item index="6">行程</el-menu-item>
      <el-menu-item index="7">队伍</el-menu-item>
      <el-menu-item index="8">时光机</el-menu-item>
    </el-menu>
  </div>
  <div class="main-container">
    <div class="main-left">
      <div class="follower">
        <div>
          <div><router-link to="/">我的关注：</router-link></div>
        </div>
        <div>
          <div><router-link to="/">我的粉丝：</router-link></div>
        </div>
      </div>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>我的勋章</span>
          </div>
        </template>
        <div v-for="o in 2" :key="o" class="text item">
          {{ "List item " + o }}
        </div>
      </el-card>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>留言板</span>
          </div>
        </template>
        <div class="comment-form__input-box">
          <textarea
            name="message"
            placeholder="Write Comment"
            class="textarea-message"
          ></textarea>
        </div>
        <button type="submit" class="thm-btn comment-form__btn">发送</button>
      </el-card>
    </div>
    <div class="main-body">
      <router-view></router-view>
    </div>
  </div>
</template>

<style lang="scss" scoped>
#map-container {
  width: 100%;
  height: 700px;
  margin: 0px auto;
  margin-top: 10px;
  // border: 1px #e8604c solid;
  // background-color: rgba(0, 243, 255, 0.1);
  // background-color: rgba(244, 143, 128, 0.1);
  // background-color: rgba(49,48,65, 0.1);
  // background-color: rgba(39,106,206, 0.1);
  background: linear-gradient(
    -90deg,
    rgba(41, 189, 217, 0.5) 0%,
    rgba(39, 106, 206, 0.5) 100%
  );
}
.personal-info-container {
  width: 100%;
  height: 150px;
  margin: 0 auto;
  // margin-bottom: 10px;
  // border: 1px #e8604c solid;

  // background-color: #f7f2ea;
  background-color: rgba(49, 48, 65, 0.1);
  position: relative;
  .button-already {
    width: 150px;
    height: 40px;
    // border: 1px #e8604c solid;
    position: absolute;
    top: -160px;
    right: 30px;
    z-index: 1000;
    background-color: #e8604c;
    border-radius: 10px;
    cursor: pointer;
    color: #ffffff;
    font-weight: 700;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.3s linear;
  }
  .button-already:hover {
    box-shadow: 0 16px 32px 0 rgba(48, 55, 66, 0.15);
    transform: translate(0, -3px);
  }
  .button-want {
    width: 150px;
    height: 40px;
    // border: 1px #e8604c solid;
    position: absolute;
    top: -100px;
    right: 30px;
    z-index: 1000;
    background-color: #e8604c;
    border-radius: 10px;
    cursor: pointer;
    color: #ffffff;
    font-weight: 700;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.3s linear;
  }
  .button-want:hover {
    box-shadow: 0 16px 32px 0 rgba(48, 55, 66, 0.15);
    transform: translate(0, -3px);
  }
  .avater-container {
    width: 150px;
    height: 150px;
    border-radius: 75px;
    position: absolute;
    top: -75px;
    // border: 1px #e8604c solid;
    margin-left: 30px;
    .avater-img {
      width: 100%;
      height: 100%;
      display: block;
      border-radius: 75px;
    }
  }
}
.header-nav-container {
  width: 100%;
  height: 60px;
  // margin: 0 auto;
  // border: 1px #e8604c solid;
  .el-menu-demo {
    display: flex;
    justify-content: space-around;
  }
}
.main-container {
  display: flex;
  justify-content: space-between;
  width: 1100px;
  min-height: 500px;
  margin: 0px auto;
  // border: 1px #e8604c solid;
  // box-shadow: var(--el-box-shadow-light);
  margin-top: 10px;
  .main-left {
    width: 220px;
    // border: 1px #e8604c solid;
    box-shadow: var(--el-box-shadow-lighter);
    background-color: #f7f2ea;
    display: flex;
    flex-direction: column;
    align-items: center;
    .follower {
      width: 200px;
      height: 140px;
      // border: 1px #e8604c solid;
      margin-top: 20px;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      > div {
        width: 200px;
        height: 60px;
        // border: 1px #e8604c solid;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #ffffff;
        box-shadow: var(--el-box-shadow-light);
        border-radius: 3px;

        > div {
          width: 155px;
          border-radius: 5px;
          height: 45px;
          background-color: #e8604c;
          display: flex;
          justify-content: center;
          align-items: center;
          a {
            color: #ffffff;
            font-size: 16px;
            font-weight: 600;
          }
          a:hover {
            color: #303133;
          }
        }
      }
    }
    .card-header {
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: #e8604c;
      border-radius: 5px;
      span {
        font-size: 16px;
        font-weight: 600;
        color: #ffffff;
      }
    }
    .text {
      font-size: 14px;
    }
    .item {
      margin-bottom: 18px;
    }
    .box-card {
      width: 200px;
      margin: 5px 0;
    }
  }
  .main-body {
    width: 850px;
    // border: 1px #e8604c solid;
    box-shadow: var(--el-box-shadow-lighter);
    background-color: #f7f2ea;
  }
  .thm-btn {
    width: 100px;
    height: 35px;
    padding: 0;
  }
  .textarea-message {
    padding: 10px 20px;
  }
}
</style>
