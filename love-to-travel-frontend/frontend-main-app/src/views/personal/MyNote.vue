<script setup lang="ts">
import { ref, inject, onMounted } from "vue";
import { getNotesInfoByUserId } from "@/apis/travelService/note";
import { mainStore } from "@/store/user";
import { theNotesInfoType } from "@/apis/interface/myInterface";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
import { compare } from "@/utils/compareArrayObj";
// import "@/assets/js/echartsTheme";
const store = mainStore();
const userNotesInfo = ref([] as theNotesInfoType[]);
const getNotesInfo = async () => {
  await getNotesInfoByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        userNotesInfo.value = res.data;
        console.log(userNotesInfo.value);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  initEchartsDataInfo(userNotesInfo.value);
};
getNotesInfo();
const formatWanNumber = (num) => {
  num = Number(num);
  // if (num == 0) {
  //   return num;
  // } else if (num > 1 && num < 10000) {
  //   return num;
  // } else {
  return (num / 10000).toFixed(2);
  // }
};
const notesNameAndView = [] as Array<object>;
const likeNumber = [] as Array<number>;
const starNumber = [] as Array<number>;
const commentNumber = [] as Array<number>;
const haveDataFlag = ref(false);
let numLength = 0;
const noteNames = [] as Array<string>;
const initEchartsDataInfo = (notesInfo: theNotesInfoType[]) => {
  let sortNotesInfo = notesInfo.sort(compare("view", false)).slice(0, 7);
  numLength = sortNotesInfo.length;
  sortNotesInfo.forEach((e) => {
    notesNameAndView.push({
      name: e.title,
      view: formatWanNumber(e.view),
    });
    // @ts-ignore
    likeNumber.push(e.like);
    starNumber.push(e.star);
    commentNumber.push(e.comment);
  });
  haveDataFlag.value = true;
  for (let i = 0; i < numLength; i++) {
    noteNames.push("第" + (i + 1) + "篇");
  }
  initEcharts(likeNumber, starNumber, commentNumber);
};
/* echarts */
let echarts = inject("ec"); //引入
const initEcharts = (likeNumbers, starNumbers, commentNumbers) => {
  //需要获取到element,所以是onMounted的Hook
  // @ts-ignore
  let myChart = echarts.init(document.getElementById("note-echarts"));
  var option = {
    color: [
      "#e8604c",
      "#d48265",
      "#bda29a",
      "#d48265",
      "#91c7ae",
      "#749f83",
      "#ca8622",
      "#bda29a",
      "#6e7074",
      "#546570",
      "#c4ccd3",
    ],
    angleAxis: {
      type: "category",
      data: noteNames,
    },
    radiusAxis: {},
    polar: {},
    series: [
      {
        type: "bar",
        data: likeNumbers,
        coordinateSystem: "polar",
        name: "点赞量",
        stack: "a",
        emphasis: {
          focus: "series",
        },
        label: {
          show: true,
          // offset: [-20, -40],
          // formatter:(params)=>{
          //   return params + "万"
          // }
          // verticalAlign: top,
        },
      },
      {
        type: "bar",
        data: starNumbers,
        coordinateSystem: "polar",
        name: "收藏量",
        stack: "a",
        emphasis: {
          focus: "series",
        },
        label: {
          show: true,
          // offset: [-20, -40],
          // verticalAlign: top,
        },
      },
      {
        type: "bar",
        data: commentNumbers,
        coordinateSystem: "polar",
        name: "评论数",
        stack: "a",
        emphasis: {
          focus: "series",
        },
        label: {
          show: true,
          // offset: [-20, -40],
          // verticalAlign: top,
        },
      },
    ],
    legend: {
      show: true,
      data: ["点赞量", "收藏量", "评论数"],
    },
  };
  //4.挂载：必须放在最后
  myChart.setOption(option);
  window.onresize = function () {
    //自适应大小
    myChart.resize();
  };
};
// onMounted(() => {
//   initEcharts();
// });
</script>
<script lang="ts">
window.onbeforeunload = function () {
  var scrollPos;
  if (typeof window.pageYOffset != "undefined") {
    scrollPos = window.pageYOffset;
  } else if (
    typeof document.compatMode != "undefined" &&
    document.compatMode != "BackCompat"
  ) {
    scrollPos = document.documentElement.scrollTop;
  } else if (typeof document.body != "undefined") {
    scrollPos = document.body.scrollTop;
  }
  document.cookie = "scrollTop=" + scrollPos; //存储滚动条位置到cookies中
};

window.onload = function () {
  if (document.cookie.match(/scrollTop=([^;]+)(;|$)/) != null) {
    var arr = document.cookie.match(/scrollTop=([^;]+)(;|$)/); //cookies中不为空，则读取滚动条位置
    // @ts-ignore
    document.documentElement.scrollTop = parseInt(arr[1]);
    // @ts-ignore
    document.body.scrollTop = parseInt(arr[1]);
  }
};
</script>
<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>游记统计面板</span>
      </div>
    </template>
    <el-scrollbar max-height="600px">
      <div class="notes-echarts-text" v-show="haveDataFlag">
        <div class="text-title-echarts">浏览量排行前{{ numLength }}的游记</div>
        <p v-for="(item, index) in notesNameAndView" :key="index">
          <span class="span-style-front">第{{ index + 1 }}篇:</span
          >{{
            // @ts-ignore
            item.name
          }}<span class="span-style-front" style="margin-left: 15px"
            >浏览量:</span
          >{{
            // @ts-ignore
            item.view
          }}万
        </p>
      </div>
      <div id="note-echarts"></div>
    </el-scrollbar>
  </el-card>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>我发布的游记</span>
        <el-button class="button" text>发游记</el-button>
      </div>
    </template>
    <el-scrollbar max-height="600px">
      <div
        class="card-item-container"
        v-for="(item, index) in userNotesInfo"
        :key="index"
      >
        <div class="item-img">
          <img :src="item.url" />
        </div>
        <div class="item-content">
          <div class="content-left">
            <div>
              <router-link :to="`/readTravel/note/detail/${item.id}`">{{
                item.title
              }}</router-link>
            </div>
            <div><span class="span-style">相关城市:</span>{{ item.city }}</div>
            <div>
              <span class="span-style">发表时间:</span
              >{{ timeFormat(item.createTime) }}
            </div>
          </div>
          <div class="content-right">
            <div style="padding-left: 10px">
              <el-icon size="16px"><View /></el-icon>浏览:{{
                numberFormat(item.view)
              }}
              &nbsp;&nbsp; <el-icon size="16px"><Pointer /></el-icon>点赞:{{
                numberFormat(item.like)
              }}&nbsp;&nbsp; <el-icon size="16px"><Star /></el-icon>收藏:{{
                numberFormat(item.star)
              }}&nbsp;&nbsp; <el-icon size="16px"><Document /></el-icon>评论:{{
                numberFormat(item.comment)
              }}
            </div>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </el-card>
</template>

<style lang="scss" scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.box-card {
  width: 800px;
  margin-left: 25px;
  margin-top: 30px;
  margin-bottom: 20px;
  .card-item-container {
    width: 700px;
    height: auto;
    // border: 1px #e8604c solid;
    border: 1px #dcdfe6 solid;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    margin-bottom: 25px;
    .item-img {
      height: 300px;
      width: 300px;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .item-content {
      width: 400px;
      height: 300px;
      display: flex;
      justify-content: space-around;
      flex-direction: column;
      .content-left {
        width: 400px;
        height: 170px;
        // border: 1px #e8604c solid;
        padding-top: 10px;
        .span-style {
          color: #ffffff;
          background-color: #e8604c;
          border-radius: 5px;
          padding: 5px;
          margin-right: 5px;
        }
        > div {
          height: 50px;
          width: 400px;
          padding-left: 30px;
        }
        > div:first-child {
          font-size: 22px;
          font-weight: 700;
          height: auto;
        }
        > div:nth-child(2) {
          margin-top: 15px;
          font-size: 16px;
        }
        > div:nth-child(3) {
          font-size: 16px;
        }
      }
      .content-right {
        width: 400px;
        height: 100px;
        // border: 1px #e8604c solid;
        display: flex;
        flex-direction: column;
        justify-content: end;
        div {
          text-align: right;
          // border: 1px #e8604c solid;
          font-size: 16px;
          display: flex;
          align-items: center;
          justify-content: end;
          padding-right: 10px;
        }
      }
    }
  }
}
#note-echarts {
  width: 700px;
  height: 400px;
  // border: 1px #e8604c solid;
  border: 1px #dcdfe6 solid;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  // margin-bottom: 25px;
  padding-top: 10px;
  // padding-left: 20px;
}
.notes-echarts-text {
  width: 700px;
  height: auto;
  // margin-bottom: 20px;
  margin: 0 auto;
  margin-bottom: 20px;
  .text-title-echarts {
    width: 700px;
    height: 40px;
    border: 2px #e8604c dashed;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: 700;
    margin-bottom: 20px;
    // background-color: #e6e8eb;
  }
  p {
    margin: 0;
    line-height: 1.5em;
    font-size: 16px;
    display: flex;
    align-items: center;
    width: 700px;
    height: 40px;
    .span-style-front {
      display: inline-block;
      min-width: 50px;
      padding: 5px;
      height: 30px;
      line-height: 20px;
      color: #ffffff;
      font-size: 14px;
      font-weight: 600;
      background-color: #e8604c;
      border-radius: 5px;
      margin-right: 10px;
    }
  }
}
</style>
