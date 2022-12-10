<script setup lang="ts">
import { ref, reactive, toRef } from "vue";
import { Place, MapLocation, InfoFilled } from "@element-plus/icons-vue";
import {
  wantCityType,
  finalAllCityPlansInfoType,
  citysInfoType,
  everyCityPlansInfoType,
  routeInfoType,
  everyDayRoutesType,
} from "@apis/interface/iPlan";
import { getHotCitysInfo } from "@apis/travelService/city";
import { onMounted } from "@vue/runtime-core";
import { cityStore } from "@/store/city";
// import router from "@/router";
import { useRouter } from "vue-router";
// 注册全局事件
import emitter from "@/mitt/event";
import axios from "axios";
emitter.on("addPlan", (daysPlanInfo) => {
  alert("添加行程信息到行程");
  //不管怎么样，不管有没有数据，我们要展示的就是days，所以直接赋值就完事了！
  // @ts-ignore
  oneCityDaysDisplay.value = daysPlanInfo;
  console.log("-------");
  console.log(oneCityDaysDisplay.value);
  console.log("-------");
  subPlans.value.forEach((e: everyCityPlansInfoType) => {
    if (e.cityId == store.searchCityId) {
      e.days = oneCityDaysDisplay.value; //将days全部赋值给该城市的days，并且只需要赋值days即可，其他的本来就有了
    }
  });
  console.log("+++++++");
  console.log(subPlans.value);
  console.log("+++++++");
  showFlag.value = true;
});
emitter.on("addAScenery", (routeInfo) => {
  alert("添加景区信息到行程");
  // if (oneCityDaysDisplay.value === ([] as everyDayRoutesType[])) {
  if (oneCityDaysDisplay.value.length == 0) {
    alert("方法1:新增");
    //在该城市没有行程内容的情况下
    //给subPlans中的一项（一个城市）即oneCityDaysDisplay.value的days的route数组新增一个信息
    // 这些应该都不需要，需要我们展示的就只是days的内容而已，所以把citysPlansInfo舍弃换成days即everyDayRoutesType[]
    // citysPlansInfo.value.cityId = store.searchCityId;
    // citysPlansInfo.value.budget = "";
    // citysPlansInfo.value.dayLength = null;
    // citysPlansInfo.value.weather = "";
    // citysPlansInfo.value.city = "";
    let routes = [] as routeInfoType[];
    // @ts-ignore
    routes.push(routeInfo);
    oneCityDaysDisplay.value.push({
      route: routes,
    });
    console.log("-------");
    console.log(oneCityDaysDisplay.value);
    console.log("-------");
    //给subPlans整体新增中符合cityId的一项的days新增信息
    subPlans.value.forEach((e: everyCityPlansInfoType) => {
      if (e.cityId == store.searchCityId) {
        e.days = oneCityDaysDisplay.value; //将days全部赋值给该城市的days，并且只需要赋值days即可，其他的本来就有了
      }
    });
    console.log("+++++++");
    console.log(subPlans.value);
    console.log("+++++++");
    showFlag.value = true;
  } else {
    alert("方法2:合并");
    //在该城市已经有行程内容的情况下
    //把subPlans中的一项（一个城市）即oneCityDaysDisplay.value的days合并
    //给最后一天的route里面加一项
    oneCityDaysDisplay.value[oneCityDaysDisplay.value.length - 1].route.push(
      // @ts-ignore
      routeInfo
    );
    console.log("-------");
    console.log(oneCityDaysDisplay.value);
    console.log("-------");
    //给subPlans整体新增中符合cityId的一项的days的最后一天的route里面加一项
    subPlans.value.forEach((e: everyCityPlansInfoType) => {
      if (e.cityId == store.searchCityId) {
        // @ts-ignore
        e.days[e.days.length - 1].route.push(routeInfo);
        //只需要给最后一天新增一个route即可
      }
    });
    console.log("+++++++");
    console.log(subPlans.value);
    console.log("+++++++");
    // showFlag.value = true; //应该不需要
  }
});
const deleteOneRoute = (index: number, index2: number) => {
  // alert("进入方法");
  // alert(index);
  // alert(index2);
  subPlans.value.forEach((e: everyCityPlansInfoType) => {
    if (e.cityId == store.searchCityId) {
      // alert("有匹配");
      // console.log(e.days[index].route[index2]);
      const r = e.days[index].route.splice(index2, 1); //删除index2
      console.log(r);
      oneCityDaysDisplay.value = e.days;
      // console.log(oneCityDaysDisplay.value);
    }
  });
};
const store = cityStore();
const $router = useRouter();
const props = defineProps<{
  fromTheCity: string;
  wantCitys: string; //json字符串类型
  backCity: string;
  goTheDate: string;
  budget: string;
}>();
// console.log(props);

/* 中间页面的显示效果 */
// 这个是要展示在中间的内容！等价于subPlans的一项内容！
//这个可能要改成ref的，页面要想自动刷新就必须用ref，如果只是获取一次值不再更改就可以用reactive
let oneCityDaysDisplay = ref([] as everyDayRoutesType[]);
let showFlag = ref(false);

/* 最终方案对象 */
let finalPlansInfo: finalAllCityPlansInfoType = reactive({
  budget: props.budget, //前面写的预算在这里当作总的预算
  id: "",
  subPlans: [],
  userId: "",
  start: props.fromTheCity,
  end: props.backCity,
  depart: props.goTheDate,
});
// 这个是用于修改整体预算的
const budget = toRef(finalPlansInfo, "budget");
// 这个是用于展示在左边的内容！
const subPlans = toRef(finalPlansInfo, "subPlans"); //这里也不是最终的内容，也是用于展示的，后面保存行程的时候要拼接给final
const initSubPlans = () => {
  // console.log(props.wantCitys);
  if (props.wantCitys) {
    //在这里再把json字符串转回来，成为wantCityType类型数组
    JSON.parse(props.wantCitys).forEach(async (item: wantCityType) => {
      let weather = "";
      await axios
        .get(
          `https://restapi.amap.com/v3/weather/weatherInfo?city=${item.id}&key=73a9f4e1b08fd89992856d6ae4075f9b`
        )
        .then((res) => {
          weather = res.data.lives[0].weather;
        });
      // alert(weather);
      subPlans.value.push({
        city: item.toCity,
        cityId: item.id,
        dayLength: item.days,
        days: [],
        budget: "",
        weather: weather,
      });
    });
    console.log(subPlans.value);
  }
};
const dialogFormVisible = ref(false);
let citysInfo = ref([] as citysInfoType[]);
const requestCitysInfo = async () => {
  await getHotCitysInfo()
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // citysInfo.value = res.data.slice(0, 10);
        citysInfo.value = res.data;
        // console.log(citysInfo);
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

//定义全局变量
let theFlag = 0; //为了标志添加：在之前添加/在之后添加
let theIndex = 0; //为了标志索引
const openCityDialog = (flag: number = 0, index: number = 0) => {
  theFlag = flag;
  theIndex = index;
  dialogFormVisible.value = true;
  requestCitysInfo();
  // console.log(theFlag);
  // console.log(theIndex);
};
let aCityInfo = {} as citysInfoType;
const aCityName = ref("");
const assignACityInfo = (item: citysInfoType) => {
  aCityInfo = item;
};
const addACity = async () => {
  let weather2 = "";
  await axios
    .get(
      `https://restapi.amap.com/v3/weather/weatherInfo?city=${aCityInfo.cityId}&key=73a9f4e1b08fd89992856d6ae4075f9b`
    )
    .then((res) => {
      weather2 = res.data.lives[0].weather;
    });
  if (theFlag === 0) {
    //在最后添加
    subPlans.value.push({
      city: aCityInfo.cityName,
      cityId: aCityInfo.cityId,
      dayLength: null,
      days: [],
      budget: "",
      weather: weather2,
    });
  } else if (theFlag === 1) {
    //在前面插入
    subPlans.value.splice(theIndex, 0, {
      city: aCityInfo.cityName,
      cityId: aCityInfo.cityId,
      dayLength: null,
      days: [],
      budget: "",
      weather: weather2,
    });
  } else if (theFlag === 2) {
    //在后面插入
    subPlans.value.splice(theIndex + 1, 0, {
      city: aCityInfo.cityName,
      cityId: aCityInfo.cityId,
      dayLength: null,
      days: [],
      budget: "",
      weather: weather2,
    });
  }
  theFlag = 0;
  theIndex = 0;
  dialogFormVisible.value = false;
};
const deleteCity = (index: any) => {
  subPlans.value.splice(index, 1);
};
const activeIndex = ref("/result/route/list");
const handleSelect = (key: string, keyPath: string[]) => {
  // console.log(key, keyPath);
};

/* 实现根据城市id查询路线和景点 */
// 全局变量
// let searchCityId: string; //不在这里定义了
let searchCityName = ref("");
const searchRoutes = (cityId: string, cityName: string) => {
  store.searchCityId = cityId;
  // alert(store.searchCityId);
  searchCityName.value = cityName;
  // 有可能subPlans里面在这个城市下本来就有值，那么需要赋值给展示对象
  let flag = false; //标志着是否被赋值了
  subPlans.value.forEach((e: everyCityPlansInfoType) => {
    if (e.cityId == cityId && e.days.length != 0) {
      // alert(1); //调试用的
      showFlag.value = true;
      oneCityDaysDisplay.value = e.days;
      flag = true;
      // alert("赋值了"); //在输入城市预算input的时候也会触发，因为也相当点击了，但是没有影响！
      console.log(".........");
      console.log(oneCityDaysDisplay.value);
      console.log(".........");
      console.log("+++++++++");
      console.log(subPlans.value);
      console.log("+++++++++");
    }
  });
  if (flag === false) {
    // alert(2);
    // 也可以不清空
    oneCityDaysDisplay.value = [] as everyDayRoutesType[]; //每次要初始化一下展示内容的对象
    showFlag.value = false; //只有在这里才会变为false
  }
  //如果没有，说明这个城市下还没有制定任何行程
  $router.push({
    //默认跳转到routelist页面
    name: "RouteList",
    params: {
      searchCityId: store.searchCityId,
    },
  });
};

onMounted(() => {
  initSubPlans();
});
</script>

<template>
  <div class="base-header">Aron的行程</div>
  <div class="base-container">
    <div class="body-left">
      <div class="left-header">出发城市：{{ fromTheCity }}</div>
      <div class="left-body">
        <el-scrollbar max-height="400px">
          <div
            v-for="(item, index) in subPlans"
            :key="item.cityId"
            class="left-scrollbar-item"
            @click="searchRoutes(item.cityId, item.city)"
          >
            <div class="item-left">
              <el-icon :size="35"><LocationInformation /></el-icon>
              <div class="item-left-text">
                <p>{{ item.city }}</p>
                <p>
                  旅行<input
                    v-model="item.dayLength"
                    type="number"
                    placeholder="天数"
                    class="budget-city-input"
                  />天
                </p>
                <p>
                  预算<input
                    v-model="item.budget"
                    type="number"
                    placeholder="金额"
                    class="budget-city-input"
                  />元
                </p>
                <p>当前天气：{{ item.weather }}</p>
              </div>
            </div>
            <div class="item-right">
              <el-dropdown trigger="click">
                <el-button type="primary">
                  <el-icon size="15"><arrow-down /></el-icon>
                </el-button>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="openCityDialog(1, index)">
                      <el-icon><Plus /></el-icon> 之前添加一个城市
                    </el-dropdown-item>
                    <el-dropdown-item @click="openCityDialog(2, index)">
                      <el-icon><Plus /></el-icon> 之后添加一个城市
                    </el-dropdown-item>
                    <el-dropdown-item @click="deleteCity(index)">
                      <el-icon><DeleteFilled /></el-icon> 删除该城市
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
          <div class="add-city" @click="openCityDialog(0, 0)">+ 添加城市</div>
        </el-scrollbar>
      </div>
      <div class="left-footer">
        返回城市：{{ backCity }}
        <div>
          总预算：<el-input
            v-model="budget"
            type="number"
            placeholder="金额"
            clearable
            class="footer-input"
          />元
        </div>
      </div>
    </div>
    <div class="body-middle">
      <div class="middle-header">
        <p>{{ goTheDate }}</p>
        <p>
          <span>{{ searchCityName }}</span>
        </p>
      </div>
      <div class="middle-body">
        <el-scrollbar max-height="320px" v-if="showFlag">
          <div
            v-for="(item, index) in oneCityDaysDisplay"
            :key="index"
            class="scrollbar-middle-items"
          >
            <div class="theDay">第{{ index + 1 }}天</div>
            <div v-for="(i, index2) in item.route" :key="index2">
              <div class="item-step-content">
                <div
                  @click="deleteOneRoute(index, index2)"
                  style="width: 50px; height: 50px; background-color: red"
                >
                  <el-icon><CloseBold /></el-icon>
                </div>
                <el-steps :space="50" :active="1" simple class="item-step">
                  <el-step title="出发地" :icon="MapLocation" />
                  <el-step :title="i.originName" :icon="Place" />
                </el-steps>
                <el-collapse accordion class="item-content">
                  <el-collapse-item name="1">
                    <template #title>
                      <span class="content-header-text">详细信息</span
                      ><el-icon class="header-icon content-header-icon">
                        <info-filled />
                      </el-icon>
                    </template>
                    <div>交通工具：{{ i.vehicle }}</div>
                    <div>停留时间：{{ i.departTime }} 小时</div>
                    <div>经纬度：{{ i.origin }}</div>
                  </el-collapse-item>
                </el-collapse>
              </div>
            </div>
          </div>
        </el-scrollbar>
        <el-empty description="该城市还未安排行程" :image-size="150" v-else />
      </div>
    </div>
    <div class="body-right">
      <div class="right-header">
        <span>{{ searchCityName }}</span>
        <div class="input-box">
          <input type="text" class="search-input" />
          <span class="search-span">
            <el-icon><Search /></el-icon>
          </span>
        </div>
      </div>
      <div class="right-nav">
        <el-menu
          :default-active="activeIndex"
          class="el-menu"
          mode="horizontal"
          @select="handleSelect"
          router="true"
        >
          <!-- 前面加/就是覆盖路径，不加就是在后面添加路径 -->
          <el-menu-item :index="`/result/route/list/${store.searchCityId}`"
            >路线</el-menu-item
          >
          <el-menu-item :index="`/result/scenicSpot/list/${store.searchCityId}`"
            >游玩</el-menu-item
          >
        </el-menu>
      </div>
      <div class="right-body">
        <router-view></router-view>
      </div>
    </div>
  </div>
  <el-dialog v-model="dialogFormVisible" title="添加一个城市">
    <el-form>
      <el-form-item label="热门城市" label-width="140px">
        <el-select v-model="aCityName" placeholder="选择一个城市">
          <el-option
            :label="item.cityName"
            :value="item.cityName"
            v-for="(item, index) in citysInfo"
            :key="item.cityId"
            @click="assignACityInfo(item)"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addACity"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.budget-city-input {
  width: 45px;
  height: 20px;
  color: #606266;
  margin: 0 5px;
}
.base-header {
  width: 100%;
  height: 10%;
  // border: 1px #e8604c solid;
  background-color: #ffffff;
  border-bottom: 2px #e8604c solid;
  // box-shadow: 0px 20px 10px rgba(0, 0, 0, 0.9);
  display: flex;
  align-items: center;
  padding-left: 20px;
}
.base-container {
  width: 100%;
  height: 90%;
  // border: 1px #e8604c solid;
  position: relative;
  background-color: #f2f3f5;
  .body-left {
    width: 250px;
    height: 470px;
    position: absolute;
    // border: 1px #e8604c solid;
    left: 20px;
    top: 30px;
    border-radius: 5px;
    background-color: #ffffff;
    box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
    .left-header {
      width: auto;
      height: 12%;
      border-bottom: 2px #e8604c solid;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 16px;
    }
    .left-footer {
      width: auto;
      height: 18%;
      // border: 1px #e8604c solid;
      border-top: 2px #e8604c solid;
      display: flex;
      justify-content: space-around;
      align-items: center;
      flex-direction: column;
      font-size: 16px;
      div {
        width: 200px;
        height: 32px;
        display: flex;
        align-items: center;
        line-height: 30px;
        .footer-input {
          width: 120px;
          height: 30px;
          padding-right: 10px;
        }
        padding-bottom: 5px;
      }
    }
    .left-body {
      width: auto;
      height: 70%;
      // border: 1px #e8604c solid;
      .left-scrollbar-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 90px;
        // border: 1px #e8604c solid;
        background: rgba(255, 255, 255);
        cursor: pointer;
        transition: all 0.3s linear;
        padding-right: 15px;
        padding-left: 10px;
        border-bottom: 1px #dcdfe6 solid;
        .item-left {
          width: 100px;
          height: 80px;
          display: flex;
          justify-content: space-around;
          align-items: center;
          padding-left: 30px;
          i {
            color: #303133;
          }
          .item-left-text {
            p {
              font-size: 15px;
              margin: 0;
              line-height: 1.4em;
            }
            > p:first-child {
              color: #303133;
              font-weight: 700;
            }
            > p:nth-child(3) {
              width: 100px;
            }
          }
        }
        .item-right {
          button {
            width: 30px;
            height: 30px;
            span {
              padding: 0 2px;
            }
          }
        }
      }
      .left-scrollbar-item:hover {
        background: rgba(245, 245, 245);
      }
      .add-city {
        width: auto;
        height: 10%;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        background: rgba(255, 255, 255);
        transition: all 0.3s linear;
        background-color: #f2f6fc;
      }
      .add-city:hover {
        background: rgba(245, 245, 245);
      }
    }
  }
  .body-middle {
    width: 480px;
    height: 400px;
    position: absolute;
    // border: 1px #e8604c solid;
    left: 300px;
    top: 30px;
    border-radius: 5px;
    background-color: #ffffff;
    box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
    .middle-header {
      width: auto;
      height: 80px;
      padding-left: 20px;
      // border: 1px #e8604c solid;
      border-bottom: 1px #dcdfe6 solid;
      padding-top: 10px;
      p {
        margin: 0;
        line-height: 30px;
      }
      > p:first-child {
        font-size: 15px;
      }
      > p:nth-child(2) {
        > span:first-child {
          font-size: 20px;
          color: #303133;
          font-weight: 700;
          margin-right: 30px;
        }
        > span:nth-child(2) {
          font-size: 15px;
          color: #606266;
          font-weight: 700;
          margin-right: 30px;
          display: inline-block;
          line-height: 25px;
        }
      }
    }
    .middle-body {
      width: auto;
      height: 320px;
      .scrollbar-middle-items {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        .theDay {
          width: 450px;
          height: 40px;
          line-height: 40px;
          text-align: center;
          font-weight: 800;
          font-size: 18px;
          letter-spacing: 0.5em;
        }
        .item-step-content {
          width: 450px;
          display: flex;
          flex-direction: column;
          justify-content: space-around;
          min-height: 70px;
          margin-bottom: 10px;
          .item-step {
            width: 450px;
            height: 40px;
          }
          .item-content {
            .content-header-text {
              margin-left: 10px;
              color: #303133;
            }
            .content-header-icon {
              margin-left: 5px;
              color: #f48f80;
            }
          }
        }
      }
    }
  }
  .body-right {
    width: 450px;
    height: 470px;
    position: absolute;
    // border: 1px #e8604c solid;
    box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
    left: 810px;
    top: 30px;
    background-color: #ffffff;
    border-radius: 5px;
    .right-header {
      width: auto;
      height: 50px;
      border-bottom: 1px #dcdfe6 solid;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 20px;
      > span:first-child {
        color: #303133;
        font-size: 16px;
      }
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
          transition: width 0.5s;
          transition-delay: 0.1s;
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
      }
    }
    .right-nav {
      // border: 1px #e8604c solid;
      // padding-left: 150px;
      .el-menu {
        > li:first-child {
          margin-left: 90px;
        }
        li {
          padding: 0 50px;
        }
      }
    }
    .right-body {
      width: auto;
      height: 350px;
      // border: 1px #e8604c solid;
    }
  }
}
</style>
