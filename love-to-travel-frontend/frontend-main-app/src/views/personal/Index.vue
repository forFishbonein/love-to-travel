<script setup lang="ts">
import { ref, inject, onMounted, computed, watch } from "vue";
// import * as echarts from "echarts";
import chinaJson from "@/assets/json/map/china.json";
import { getCitysInfoByName } from "@/apis/travelService/city";
import { citysInfoType } from "@/apis/interface/myInterface";
import { addCityToWant } from "@/apis/travelService/want";
import { addCityToBeen } from "@/apis/travelService/been";
import { mainStore } from "@/store/user";
import { getFootsByUserId } from "@/apis/travelService/foot";
import {
  getUserFollowersNum,
  getUserFolloweesNum,
} from "@/apis/userService/follow";
import emitter from "@/mitt/event";
import { getRandomArrayElements } from "@/utils/filters/randomArray";
import { timeFormat } from "@/utils/filters/time";
import type { UploadProps, UploadUserFile } from "element-plus";
import { client } from "@/utils/oss";
emitter.on("addFoot", () => {
  openAlreadyGoDialog();
});
const store = mainStore();
const activeIndex = ref("/personal/center");
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const keyword = ref("");
const citysResultList = ref([] as citysInfoType[]);
let timer = null;
const hasNoData = computed(() => {
  return !citysResultList.value.length;
});

watch(keyword, (newValue, oldValue) => {
  // alert(newValue);
  if (timer) {
    clearTimeout(timer);
  }
  if (!newValue) {
    citysResultList.value = [] as citysInfoType[];
    return;
  }
  // @ts-ignore
  timer = setTimeout(async () => {
    let result = [] as citysInfoType[];
    await getCitysInfoByName(newValue)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          res.data.forEach((e: citysInfoType) => {
            if (e.cityName.indexOf(newValue) > -1) {
              result.push(e);
            }
          });
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
    citysResultList.value = result;
  }, 100);
});

const dialogToWantVisible = ref(false);
const confirmDialogVisible = ref(false);
const theSelectedCity = ref({} as citysInfoType);
let wantOrBeenFlag = false;
const scoreValue = ref(4.5);
const openAlreadyGoDialog = () => {
  wantOrBeenFlag = true;
  dialogToWantVisible.value = true;
};
const openToWantDialog = () => {
  wantOrBeenFlag = false;
  dialogToWantVisible.value = true;
};
const openTheConfirmDialog = (index: number) => {
  confirmDialogVisible.value = true;
  theSelectedCity.value = citysResultList.value[index];
};
const reSelect = () => {
  confirmDialogVisible.value = false;
  theSelectedCity.value = {} as citysInfoType;
};
const addOneCityToWant = async () => {
  if (wantOrBeenFlag === false) {
    await addCityToWant({
      userId: store.userInfo.id,
      cityId: theSelectedCity.value.cityId,
    })
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          confirmDialogVisible.value = false;
          dialogToWantVisible.value = false;
          keyword.value = "";
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "添加想去成功",
          });
          getThescattersInfo();
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  } else if (wantOrBeenFlag === true) {
    await addCityToBeen({
      userId: store.userInfo.id,
      cityId: theSelectedCity.value.cityId,
      score: scoreValue.value,
    })
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          confirmDialogVisible.value = false;
          dialogToWantVisible.value = false;
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "添加去过成功",
          });
          getThescattersInfo();
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
    // @ts-ignore
    ElMessage({
      type: "error",
      message: "未知错误",
    });
  }
};
let scatterDataWant = [];
let scatterDataBeen = [];
/* 获取初始化点标记数据 */
const getThescattersInfo = async () => {
  // alert(222);
  await getFootsByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
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
                  color: "rgba(42,144,205,0.54)",
                },
                {
                  offset: 1,
                  color: "rgb(7,24,189)",
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
                  color: "rgba(57,246,169,0.3)",
                },
                {
                  offset: 1,
                  color: "rgb(5,232,113)",
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
                  color: "rgb(245,220,200)", // 0% 处的颜色
                },
                {
                  offset: 1,
                  color: "rgb(252,184,148)", // 100% 处的颜色
                },
              ],
              global: false, // 缺省为 false
            },

            borderColor: "rgb(229,159,105)",
            borderWidth: 2.5,
          },
          emphasis: {
            areaColor: "rgb(232, 96, 76)",
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
  getUserFollowersNum(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
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
  getUserFolloweesNum(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
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
onMounted(() => {
  // alert(111);
  // alert(store2.getUserFlag);
  // alert(JSON.stringify(store2.userInfo));
});
/* 随机勋章 */
const medalsArray = [
  "/static/images//medal/medal1.jpg",
  "/static/images//medal/medal2.jpg",
  "/static/images//medal/medal3.jpg",
  "/static/images//medal/medal4.jpg",
  "/static/images//medal/medal5.jpg",
  "/static/images//medal/medal6.jpg",
] as Array<string>;
let finalMedalsArray = [] as Array<string>;
const getShuffleMedals = () => {
  finalMedalsArray = getRandomArrayElements(
    medalsArray,
    Math.floor(Math.random() * 6) + 1
  );
};
getShuffleMedals();

/* 更换头像 */
// const fileList = ref<UploadUserFile[]>([
//   {
//     name: "element-plus-logo.svg",
//     url: "https://element-plus.org/images/element-plus-logo.svg",
//   },
//   {
//     name: "element-plus-logo2.svg",
//     url: "https://element-plus.org/images/element-plus-logo.svg",
//   },
// ]);

const handlePreview: UploadProps["onPreview"] = (uploadFile) => {
  console.log(uploadFile);
};
const httpRequest = (e) => {
  // console.log(e)
  let file = e.file; // 文件信息
  if (!file) {
    return false;
  } else if (!/\.(png|jpg)$/.test(file.name.toLowerCase())) {
    // 格式根据自己需求定义
    //@ts-ignore
    ElMessage({
      type: "error",
      message: "上传格式不正确，请上传png或者jpg格式",
    });
    return false;
  }
  let filename = file.name;
  console.log(filename);
  const times = new Date().getTime();
  console.log(times);
  client.put(`${times}.png`, file);
  let avaterUrl =
    "http://noteimg123.oss-cn-hangzhou.aliyuncs.com/" + times + ".png";
  // setTimeout(() => {
  //   client.list().then((res) => {
  //     console.log(res);
  //     res.objects.forEach((e) => {
  //       if (e.name === times + ".png") {
  //         alert(e.name);
  //       }
  //     });
  //   });
  // }, 1000);
};
const uploadAvater = () => {
  alert(11111);
  // avaterUrl;
  // axios
  //   .get("http://127.0.0.1:5000/", {
  //     params: {
  //       usrNo: store.userInfo.id,
  //     },
  //   })
  //   .then((res) => {
  //     console.log(res);
  //   });
};
</script>

<template>
  <div id="map-container"></div>
  <div class="personal-info-container">
    <div class="button-already" @click="openAlreadyGoDialog">添加去过</div>
    <div class="button-want" @click="openToWantDialog">添加想去</div>
    <div class="avater-container">
      <img :src="store.userInfo?.url" class="avater-img" />
    </div>
    <div class="change-avater">
      <el-upload
        class="upload-demo"
        action=""
        multiple
        accept=".png,.jpg"
        :show-file-list="false"
        :http-request="httpRequest"
        :on-preview="handlePreview"
        :limit="1"
      >
        <el-button type="primary">更换头像</el-button>
      </el-upload>
    </div>
    <div class="info-border">
      <el-descriptions :title="store.userInfo.name">
        <el-descriptions-item label="访问量"
          ><span class="visit-num">{{
            store.userInfo.visits
          }}</span></el-descriptions-item
        >
        <el-descriptions-item label="邮箱">{{
          store.userInfo.email
        }}</el-descriptions-item>
        <el-descriptions-item label="所在地">{{
          store.userInfo.address
        }}</el-descriptions-item>
        <el-descriptions-item label="等级">
          <el-tag size="medium" style="font-weight: 600">{{
            store.userInfo.grade
          }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="个性签名">{{
          store.userInfo.signature
        }}</el-descriptions-item>
        <el-descriptions-item label="加入爱旅游时间">{{
          // @ts-ignore
          timeFormat(store.userInfo.createTime)
        }}</el-descriptions-item>
      </el-descriptions>
    </div>
    <div class="more-info-border">
      <el-collapse>
        <el-collapse-item title="详细资料">
          <div>
            <el-descriptions>
              <el-descriptions-item label="性别">{{
                store.userInfo.gender
              }}</el-descriptions-item>
              <el-descriptions-item label="电话">{{
                store.userInfo.tele
              }}</el-descriptions-item>
              <el-descriptions-item label="出生日期">{{
                store.userInfo.birthday
              }}</el-descriptions-item>
              <el-descriptions-item label="经验值">{{
                store.userInfo.experience
              }}</el-descriptions-item>
              <el-descriptions-item label="岗位">{{
                store.userInfo.post
              }}</el-descriptions-item>
              <el-descriptions-item label="职业">{{
                store.userInfo.profession
              }}</el-descriptions-item>
            </el-descriptions>
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
  <div class="header-nav-container">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      router="true"
    >
      <el-menu-item index="/personal/center">爱窝儿</el-menu-item>
      <el-menu-item index="/personal/foot">足迹</el-menu-item>
      <el-menu-item index="/personal/mynote">游记</el-menu-item>
      <el-menu-item index="/personal/star">收藏</el-menu-item>
      <el-menu-item index="/personal/myroute">行程</el-menu-item>
      <el-menu-item index="/personal/myteam">队伍</el-menu-item>
      <el-menu-item index="/personal/follow">粉丝/关注</el-menu-item>
      <el-menu-item index="/personal/buy">订单</el-menu-item>
      <el-menu-item index="/personal/setup">设置</el-menu-item>
    </el-menu>
  </div>
  <div class="main-container">
    <div class="main-left">
      <div class="follower">
        <div>
          <div>
            <router-link to="/personal/follow"
              >我的关注：{{ followeeNum }}</router-link
            >
          </div>
        </div>
        <div>
          <div>
            <router-link to="/personal/follow"
              >我的粉丝：{{ followerNum }}</router-link
            >
          </div>
        </div>
      </div>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>我的勋章</span>
          </div>
        </template>
        <div class="all-media-container">
          <div
            v-for="(item, index) in finalMedalsArray"
            :key="index"
            class="text item media-img-container"
          >
            <img :src="item" />
          </div>
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
      <router-view></router-view>
    </div>
  </div>
  <el-dialog v-model="dialogToWantVisible" title="搜索城市">
    <template #default>
      <el-dialog
        v-model="confirmDialogVisible"
        width="30%"
        title="确认面板"
        append-to-body
      >
        <p>
          <span style="color: #e8604c">您选择的城市为：</span
          ><el-tag class="mx-1" size="large">{{
            theSelectedCity.cityName
          }}</el-tag>
        </p>
        <p v-show="wantOrBeenFlag">
          <span style="color: #e8604c">选择评分：</span
          ><el-rate
            v-model="scoreValue"
            :texts="['1分', '2分', '3分', '4分', '5分']"
            show-text
            allow-half
          />
        </p>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="reSelect">重新选择</el-button>
            <el-button type="primary" @click="addOneCityToWant">
              确认选择
            </el-button>
          </span>
        </template>
      </el-dialog>
      <div class="search-citys">
        <div class="input-box">
          <input
            type="text"
            class="search-input"
            v-model="keyword"
            placeholder="请输入城市名"
          />
          <span class="search-span">
            <el-icon><Search /></el-icon>
          </span>
          <div class="search-result-content" v-show="keyword">
            <ul class="result-list">
              <li
                v-for="(item, index) in citysResultList"
                :key="index"
                @click="openTheConfirmDialog(index)"
              >
                {{ item.cityName }}
              </li>
              <li v-show="hasNoData">没有找到匹配数据</li>
            </ul>
          </div>
        </div>
      </div>
    </template>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogToWantVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.all-media-container {
  width: 100%;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  .media-img-container {
    cursor: pointer;
    float: left;
    width: 30px;
    height: 40px;
    margin: 5px;
    img {
      width: 100%;
      height: 100%;
    }
  }
}
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
  // background: linear-gradient(to right, #ff9569 0%, #e92758 100%);
  background: linear-gradient(
    -90deg,
    rgba(238, 83, 22, 0.3) 0%,
    rgba(213, 62, 20, 0.6) 100%
  );
}
// ::v-deep .el-descriptions {
//   width: 900px;
// }
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
  .info-border {
    padding-top: 15px;
    width: 1000px;
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
    width: 1000px;
    height: auto;
    margin-left: 200px;
  }
  .button-already {
    width: 150px;
    height: 40px;
    // border: 1px #e8604c solid;
    position: absolute;
    top: -160px;
    right: 30px;
    z-index: 100;
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
    z-index: 100;
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
      background-color: #f7f2ea;
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
.search-citys {
  display: flex;
  justify-content: center;
  .input-box {
    position: relative;
    display: inline-block;
    .search-input {
      padding: 0 40px 0 20px;
      width: 160px;
      height: 38px;
      font-size: 14px;
      border: 1px solid #eee;
      border-radius: 40px;
      background: #eee;
      transition: width 0.2s;
      transition-delay: 0.1s;
      color: #606266;
    }
    .search-input:focus {
      width: 350px;
      outline: none;
      box-shadow: none;
    }
    .search-input:focus + .search-span {
      background-color: #f48f80;
      color: #fff;
    }
    .search-span {
      position: absolute;
      top: 4px;
      right: 5px;
      width: 30px;
      height: 30px;
      line-height: 30px;
      padding: 0;
      color: #d4d7de;
      text-align: center;
      background: #e8604c;
      border-radius: 50%;
      font-size: 15px;
      cursor: pointer;
      i {
        height: 30px; /* 让图标居中的方案 */
      }
    }
    .search-result-content {
      width: auto;
      height: auto;
      // border: 1px #e8604c solid;
      // background-color: rgba(0, 0, 0, 0.311);
      border-radius: 5px;
      .result-list {
        padding-left: 10px;
        padding-right: 10px;
        > li {
          list-style: none;
          line-height: 2em;
          padding-top: 5px;
          cursor: pointer;
          transition: all 0.1s linear;
          padding-left: 5px;
          border-bottom: 1px solid #e0e0e0;
        }
        > li:hover {
          // background-color: #f48f80;
          color: #e8604c;
          font-weight: 700;
        }
      }
    }
  }
}
/* 更改头像 */
.change-avater {
  width: 100px;
  height: 50px;
  position: absolute;
  top: 90px;
  // border: 1px #e8604c solid;
  margin-left: 55px;
  display: flex;
  justify-content: center;
}
</style>
