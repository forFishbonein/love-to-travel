<script setup lang="ts">
import { ref } from "vue";
import { getOneCityInfoById } from "@/apis/travelService/city";
import { getSceneryListByCityId } from "@apis/travelService/scenery";
import {
  citysInfoType,
  theCityScenerysInfoType,
} from "@/apis/interface/myInterface";
import CityCanvas from "@/views/goTravel/city/CityCanvas.vue";
const props = defineProps<{
  cityId: string;
}>();
// alert(props.cityId);
const cityId = props.cityId;
const cityInfo = ref({} as citysInfoType);
const cityInfoFlag = ref(false);
const requestOneCityInfo = async () => {
  await getOneCityInfoById(cityId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("得到数据了2！");
        cityInfo.value = res.data;
        console.log(cityInfo.value);
        cityInfoFlag.value = true;
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
requestOneCityInfo();

const sceneryListInfo = ref([] as theCityScenerysInfoType[]);
const requestSceneryListInfo = async () => {
  await getSceneryListByCityId(cityId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("得到数据了3！");
        sceneryListInfo.value = res.data.slice(0, 6);
        console.log(sceneryListInfo.value);
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
requestSceneryListInfo();
</script>

<template>
  <section class="destinations-details">
    <div class="container">
      <div class="row">
        <div class="col-xl-8 col-lg-7">
          <div class="destinations-details__left">
            <div class="destinations-details__img">
              <img :src="cityInfo.url" alt="" />
            </div>
            <div class="destinations-details__discover">
              <h3 class="destinations-details__title">
                {{ cityInfo.cityName }}
                <div class="canvas-button-container">
                  <div class="canvas-button">
                    <router-link
                      :to="`/canvasTravel/detail/${cityInfo.cityName}`"
                      >相关知识图谱</router-link
                    >
                  </div>
                </div>
              </h3>

              <p class="destinations-details__discover-text-1">
                {{ cityInfo.introduction }}
              </p>
            </div>
            <div class="city-canvas-container" v-if="cityInfoFlag">
              <CityCanvas :cityName="cityInfo.cityName"></CityCanvas>
              <!-- <CityCanvas></CityCanvas> -->
            </div>
          </div>
        </div>
        <div class="col-xl-4 col-lg-5">
          <div class="destinations-details__right">
            <div
              class="tour-details-two__last-minute tour-details-two__last-minute-2"
            >
              <h3 class="tour-details-two__sidebar-title">热门景区</h3>
              <ul class="tour-details-two__last-minute-list list-unstyled">
                <li v-for="(item, index) in sceneryListInfo" :key="index">
                  <div class="tour-details-two__last-minute-image">
                    <img :src="item.url" alt="" />
                  </div>
                  <div class="tour-details-two__last-minute-content">
                    <h6>¥{{ item.ticket }}</h6>
                    <h5>
                      <router-link
                        :to="`/goTravel/scenery/detail/${item.id}`"
                        >{{ item.name }}</router-link
                      >
                    </h5>
                    <p>评分：{{ item.score }}</p>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style lang="scss" scoped>
.destinations-details {
  padding-top: 70px;
}
.tour-details-two__last-minute-content {
  margin-left: 20px;
  padding-top: 10px;
}
.tour-details-two__last-minute-image {
  width: 80px;
  height: 80px;
  img {
    width: 100%;
    height: 100%;
  }
}
.canvas-button {
  width: 100px;
  height: 30px;
  border-radius: 5px;
  background-color: #e8604c;
  transition: all 0.3s linear;
  border: 2px #e8604c solid;
  font-size: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 90px;
}
.canvas-button > a {
  color: #ffffff;
}
.canvas-button:hover {
  background-color: #f7f2ea;

  border: 2px #e8604c solid;
}
.canvas-button:hover > a {
  color: #e8604c;
}
.canvas-button-container {
  width: 200px;
  height: 55px;
  display: flex;
  align-items: center;
  float: right;
}
.city-canvas-container {
  width: 100%;
  // border: 1px #e8604c solid;
  height: auto;
}
</style>
