<script setup lang="ts">
import { ref, inject } from "vue";
import chinaJson from "@/assets/json/map/china.json";
import { mainStore } from "@/store/user";
import { getFootsByUserId } from "@/apis/travelService/foot";
import {
  getUserFollowersNum,
  getUserFolloweesNum,
} from "@/apis/userService/follow";
import { UserInfo } from "@/apis/userService/uinterface";
import { getUserInfoById } from "@/apis/userService/user";
import {
  isFollowOneUser,
  followOneUser,
  cancelfollowOneUser,
} from "@/apis/userService/follow";
import { getNotesInfoByUserId } from "@/apis/travelService/note";
import { theNotesInfoType } from "@apis/interface/iPlan";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
const theUserInfo = ref({} as UserInfo);
const props = defineProps<{
  userId: string;
}>();
const userId = props.userId;
const store = mainStore();
// alert(userId);
const requestUserAllInfo = async () => {
  await getUserInfoById(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        theUserInfo.value = res.data;
        console.log(theUserInfo.value);
        // alert(authorInfo.value.url);
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
requestUserAllInfo();

let scatterDataWant = [];
let scatterDataBeen = [];
/* 获取初始化点标记数据 */
const getThescattersInfo = async () => {
  await getFootsByUserId(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("成功1");
        if (res.data && res.data.wants) {
          // alert(333);
          res.data.wants.forEach((e) => {
            // @ts-ignore
            scatterDataWant.push({
              name: e.cityName,
              value: e.pos,
              introduction: e.introduction,
            });
          });
        }
        if (res.data && res.data.beens) {
          res.data.beens.forEach((e) => {
            // @ts-ignore
            scatterDataBeen.push({
              name: e.cityName,
              value: e.pos,
              score: e.score,
              introduction: e.introduction,
            });
          });
        }
        console.log(scatterDataWant);
        console.log(scatterDataBeen);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  initEcharts(scatterDataWant, scatterDataBeen); //要获取数据之后再初始化
};
getThescattersInfo();

/* echarts地图 */
let echarts = inject("ec"); //引入
const initEcharts = (scatterDataWant, scatterDataBeen) => {
  //需要获取到element,所以是onMounted的Hook
  // @ts-ignore
  let myChart = echarts.init(document.getElementById("map-container"));

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
        // console.log("=========");
        // console.log(params);
        // console.log("=========");
        let name = params.data.name;
        let score = params.data.score;
        let introduction = params.data.introduction;
        if (score) {
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
       background-color: #e8604c;"><span style="color:#ffffff;font-weight: 600;">地点：${name}</span></div>
        <div style="  display: flex;
        flex-direction: column;
      width: 200px;
        padding: 10px;
        box-sizing: border-box;">
        <p style="  margin: 0;
            line-height: 1.7em;"><span style="color:#e8604c;font-weight: 600;">类型：</span>去过</p>
            <p style="  margin: 0;
            line-height: 1.7em;"><span style="color:#e8604c;font-weight: 600;">我的评分：</span>${score}</p>
            <p style="  margin: 0;
                line-height: 1.7em;"><span style="color:#e8604c;font-weight: 600;">简介：</span>${introduction}
            </p>
        </div>
    </div>`;
        } else {
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
       background-color: #e8604c;"><span style="color:#ffffff;font-weight: 600;">地点：${name}</span></div>
        <div style="  display: flex;
        flex-direction: column;
      width: 200px;
        padding: 10px;
        box-sizing: border-box;">
        <p style="  margin: 0;
            line-height: 1.7em;"><span style="color:#e8604c;font-weight: 600;">类型：</span>想去</p>
            <p style="  margin: 0;
                line-height: 1.7em;"><span style="color:#e8604c;font-weight: 600;">简介：</span>${introduction}
            </p>
        </div>
    </div>`;
        }
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
        name: "散点WantTo",
        type: "effectScatter",
        coordinateSystem: "geo",
        // 显示的点
        // data: this.convertData(data),
        data: scatterDataWant,
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
      {
        name: "散点BeenTo",
        type: "effectScatter",
        coordinateSystem: "geo",
        // 显示的点
        // data: this.convertData(data),
        data: scatterDataBeen,
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
                  color: "rgba(244, 143, 128, 0.3)",
                },
                {
                  offset: 1,
                  color: "rgba(231,65,40)",
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

/* 用户基本信息 */
const followerNum = ref(0);
const followeeNum = ref(0);
const getFollowerNum = () => {
  //   alert("得到粉丝方法" + theUserInfo.value.id);
  getUserFollowersNum(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("成功2");
        followerNum.value = res.data;
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
getFollowerNum();
const getFolloweeNum = () => {
  //   alert("得到关注方法" + theUserInfo.value.id);
  getUserFolloweesNum(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("成功3");
        followeeNum.value = res.data;
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
getFolloweeNum();

/* 关注和取消关注 */
const isFollowFlag = ref(false);
const searchIsFollow = () => {
  if (store.userInfo.id) {
    isFollowOneUser(userId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          isFollowFlag.value = res.data;
          console.log(isFollowFlag.value);
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  }
};
searchIsFollow();

const followTheUser = () => {
  if (store.userInfo.id) {
    followOneUser(userId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "关注成功",
          });
          isFollowFlag.value = true;
          setTimeout(
            () => {
              searchIsFollow();
              getFollowerNum();
            },
            //更新评论
            1000
          );
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  } else {
    //@ts-ignore
    ElMessage({
      type: "error",
      message: "请先登录",
    });
  }
};
const cancelFollowTheUser = () => {
  if (store.userInfo.id) {
    cancelfollowOneUser(userId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "取消关注成功",
          });
          isFollowFlag.value = false;
          setTimeout(
            () => {
              searchIsFollow();
              getFollowerNum();
            },
            //更新评论
            1000
          );
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  }
};

/* 游记 */
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
};
getNotesInfo();
</script>

<template>
  <div id="map-container"></div>
  <div class="personal-info-container">
    <div class="avater-container">
      <img :src="theUserInfo.url" class="avater-img" />
    </div>
    <div class="follow-button">
      <el-button
        type="danger"
        round
        v-if="isFollowFlag"
        @click="cancelFollowTheUser"
        >取消关注</el-button
      >
      <el-button type="primary" round v-else @click="followTheUser"
        >关注</el-button
      >
    </div>
    <div class="info-border">
      <el-descriptions :title="theUserInfo.name">
        <el-descriptions-item label="访问量"
          ><span class="visit-num">{{
            theUserInfo.visits
          }}</span></el-descriptions-item
        >
        <el-descriptions-item label="邮箱">{{
          theUserInfo.email
        }}</el-descriptions-item>
        <el-descriptions-item label="所在地">{{
          theUserInfo.address
        }}</el-descriptions-item>
        <el-descriptions-item label="等级">
          <el-tag size="medium" style="font-weight: 600">{{
            theUserInfo.grade || "人在旅途"
          }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="个性签名">{{
          theUserInfo.signature
        }}</el-descriptions-item>
        <el-descriptions-item label="加入爱旅游时间">{{
          theUserInfo.createTime
        }}</el-descriptions-item>
      </el-descriptions>
    </div>
    <div class="more-info-border">
      <el-collapse>
        <el-collapse-item title="详细资料">
          <div>
            <el-descriptions>
              <el-descriptions-item label="性别">{{
                theUserInfo.gender
              }}</el-descriptions-item>
              <el-descriptions-item label="电话">{{
                theUserInfo.tele
              }}</el-descriptions-item>
              <el-descriptions-item label="出生日期">{{
                theUserInfo.birthday
              }}</el-descriptions-item>
              <el-descriptions-item label="经验值">{{
                theUserInfo.experience
              }}</el-descriptions-item>
              <el-descriptions-item label="岗位">{{
                theUserInfo.post
              }}</el-descriptions-item>
              <el-descriptions-item label="职业">{{
                theUserInfo.profession
              }}</el-descriptions-item>
            </el-descriptions>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
  <div class="main-container">
    <div class="main-left">
      <div class="follower">
        <div>
          <div>
            <a href="javascript:;">ta的关注：{{ followeeNum }}</a>
          </div>
        </div>
        <div>
          <div>
            <a href="javascript:;">ta的粉丝：{{ followerNum }}</a>
          </div>
        </div>
      </div>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>ta的勋章</span>
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
            placeholder="你想说的话"
            class="textarea-message"
          ></textarea>
        </div>
        <button type="submit" class="thm-btn comment-form__btn">发送</button>
      </el-card>
    </div>
    <div class="main-body">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>ta发布的游记</span>
            <el-button class="button" text
              ><router-link to="/readTravel/write"
                >发游记</router-link
              ></el-button
            >
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
                <div>
                  <span class="span-style">相关城市:</span>{{ item.city }}
                </div>
                <div>
                  <span class="span-style">发表时间:</span
                  >{{ timeFormat(item.createTime) }}
                </div>
              </div>
              <div class="content-right">
                <div>
                  <el-icon size="16px"><View /></el-icon>浏览:{{
                    numberFormat(item.view)
                  }}
                  &nbsp;&nbsp; <el-icon size="16px"><Pointer /></el-icon>点赞:{{
                    numberFormat(item.like)
                  }}&nbsp;&nbsp; <el-icon size="16px"><Star /></el-icon>收藏:{{
                    numberFormat(item.star)
                  }}&nbsp;&nbsp;
                  <el-icon size="16px"><Document /></el-icon>评论:{{
                    numberFormat(item.comment)
                  }}
                </div>
              </div>
            </div>
          </div>
        </el-scrollbar>
      </el-card>
    </div>
  </div>
</template>

<style lang="scss" scoped>
#map-container {
  width: 100%;
  height: 600px;
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
  min-height: 150px;
  margin: 0 auto;
  // margin-bottom: 10px;
  border-top: 2px #e8604c solid;
  border-bottom: 2px #e8604c solid;

  // background-color: #f7f2ea;
  // background-color: rgba(49, 48, 65, 0.1);
  background-color: #ffffff;
  position: relative;
  display: flex;
  justify-content: center;
  flex-direction: column;
  .follow-button {
    width: 100px;
    height: 80px;
    position: absolute;
    top: 90px;
    left: 55px;
    // border: 1px #e8604c solid;
    display: flex;
    justify-content: center;
    .el-button {
      width: 80px;
    }
  }
  .info-border {
    padding-top: 15px;
    width: 800px;
    height: auto;
    float: left;
    margin-left: 200px;
    .visit-num {
      font-size: 20px;
      font-weight: 700;
      color: #e8604c;
    }
  }
  .more-info-border {
    width: 800px;
    height: auto;
    margin-left: 200px;
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
      background-color: #f7f2ea;
    }
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
</style>
