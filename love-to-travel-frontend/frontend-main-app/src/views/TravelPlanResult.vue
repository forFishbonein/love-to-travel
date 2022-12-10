<script setup lang="ts">
import { ref, reactive, toRef } from "vue";
import { Place, MapLocation, InfoFilled } from "@element-plus/icons-vue";
import {
  wantCityType,
  finalPlansInfoType,
  citysInfoType,
  citysPlansInfoType,
} from "@apis/interface/iPlan";
import { getCitysInfo } from "@apis/travelService/city";
import { onMounted } from "@vue/runtime-core";
import { cityStore } from "@/store/city";
// import router from "@/router";
import { useRouter } from "vue-router";
// 注册全局事件
import emitter from "@/mitt/event";
emitter.on("addPlan", (e) => {
  // alert(e);
  citysPlansInfo.cityId = store.searchCityId;
  // @ts-ignore
  citysPlansInfo.days = e;
  //修改数组中某一个对象的值？
  // subPlans.value.push({
  //       city: item.toCity,
  //       cityId: item.id,
  //       time: item.days,
  //       days: [],
  //     });
});

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
// 这个是要展示在中间的内容！
//这个可能要改成ref的，页面要想自动刷新就必须用ref，如果只是获取一次值不再更改就可以用reactive
let citysPlansInfo: citysPlansInfoType = reactive({
  city: "",
  cityId: "",
  time: null,
  days: [],
});
let showFlag = ref(true);

/* 最终方案对象 */
let finalPlansInfo: finalPlansInfoType = reactive({
  budget: props.budget,
  id: "",
  subPlans: [],
  userId: "",
  fromCity: props.fromTheCity,
  backCity: props.backCity,
  goDate: props.goTheDate,
});
const budget = toRef(finalPlansInfo, "budget");
// 这个是用于展示在左边的内容！
const subPlans = toRef(finalPlansInfo, "subPlans");
const initSubPlans = () => {
  // console.log(props.wantCitys);
  if (props.wantCitys) {
    //在这里再把json字符串转回来，成为wantCityType类型数组
    JSON.parse(props.wantCitys).forEach((item: wantCityType) => {
      subPlans.value.push({
        city: item.toCity,
        cityId: item.id,
        time: item.days,
        days: [],
      });
    });
    console.log(subPlans.value);
  }
};
const dialogFormVisible = ref(false);
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
        citysInfo.value = res.data.slice(0, 10);
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
const addACity = () => {
  if (theFlag === 0) {
    //在最后添加
    subPlans.value.push({
      city: aCityInfo.cityName,
      cityId: aCityInfo.cityId,
      time: 1,
      days: [],
    });
  } else if (theFlag === 1) {
    //在前面插入
    subPlans.value.splice(theIndex, 0, {
      city: aCityInfo.cityName,
      cityId: aCityInfo.cityId,
      time: 1,
      days: [],
    });
  } else if (theFlag === 2) {
    //在后面插入
    subPlans.value.splice(theIndex + 1, 0, {
      city: aCityInfo.cityName,
      cityId: aCityInfo.cityId,
      time: 1,
      days: [],
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
  console.log(key, keyPath);
};

/* 实现根据城市id查询路线和景点 */
// 全局变量
// let searchCityId: string; //不在这里定义了
let searchCityName = ref("");
const searchRoutes = (cityId: string, cityName: string) => {
  store.searchCityId = cityId;
  // alert(store.searchCityId);
  searchCityName.value = cityName;
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
  <div class="base-header">header</div>
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
                <p>旅行{{ item.time }}天</p>
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
      <div class="left-footer">返回城市：{{ backCity }}</div>
    </div>
    <div class="body-middle">
      <div class="middle-header">
        <p>{{ goTheDate }}</p>
        <p>
          <span>{{ searchCityName }}</span>
          <span> {{ budget }}元预算 </span>
        </p>
      </div>
      <div class="middle-body">
        <el-scrollbar max-height="320px" v-if="showFlag">
          <div v-for="item in 20" :key="item" class="scrollbar-middle-items">
            <h2>标题{{ item }}</h2>
            <div v-for="item in 10" :key="item">
              <div class="item-step-content">
                <el-steps :space="50" :active="1" simple class="item-step">
                  <el-step :title="item" :icon="MapLocation" />
                  <el-step title="Step 2" :icon="Place" />
                </el-steps>
                <el-collapse accordion class="item-content">
                  <el-collapse-item name="1">
                    <template #title>
                      <span class="content-header-text">详细信息</span
                      ><el-icon class="header-icon content-header-icon">
                        <info-filled />
                      </el-icon>
                    </template>
                    <div>
                      Consistent with real life: in line with the process and
                      logic of real life, and comply with languages and habits
                      that the users are used to;
                    </div>
                    <div>
                      Consistent within interface: all elements should be
                      consistent, such as: design style, icons and texts,
                      position of elements, etc.
                    </div>
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
.base-header {
  width: 100%;
  height: 10%;
  // border: 1px #e8604c solid;
  background-color: #ffffff;
  border-bottom: 2px #e8604c solid;
  // box-shadow: 0px 20px 10px rgba(0, 0, 0, 0.9);
}
.base-container {
  width: 100%;
  height: 90%;
  // border: 1px #e8604c solid;
  position: relative;
  background-color: #f2f3f5;
  .body-left {
    width: 250px;
    height: 450px;
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
    }
    .left-footer {
      width: auto;
      height: 12%;
      // border: 1px #e8604c solid;
      border-top: 2px #e8604c solid;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .left-body {
      width: auto;
      height: 76%;
      // border: 1px #e8604c solid;
      .left-scrollbar-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 80px;
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
          i {
            color: #303133;
          }
          .item-left-text {
            p {
              font-size: 15px;
              margin: 0;
              line-height: 1.4em;
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
