<script setup lang="ts">
import { ref } from "vue";
import { getFootsByUserId } from "@/apis/travelService/foot";
import { mainStore } from "@/store/user";
import { wantInfoType, beenInfoType } from "@/apis/interface/iPlan";
let wantInfo = ref([] as wantInfoType[]);
let beenInfo = ref([] as beenInfoType[]);
const store = mainStore();
/* 获取初始化点标记数据 */
const getTheFootsInfo = async () => {
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
          wantInfo.value = res.data.wants;
        }
        if (res.data && res.data.beens) {
          beenInfo.value = res.data.beens;
        }
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
getTheFootsInfo();
</script>

<template>
  <div class="hasgo-city">
    <div class="has-title">我去过的</div>
    <el-scrollbar max-height="450px">
      <div class="has-body">
        <div v-for="(item, index) in beenInfo" :key="index" class="one">
          <div class="two">
            <span style="color: #ffffff; font-weight: 600"
              ><router-link :to="`/goTravel/city/detail/${item.cityId}`">{{
                item.cityName
              }}</router-link></span
            >
          </div>
          <div class="three">
            <p class="four">
              <el-image :src="item.url" class="city-img">
                <template #error>
                  <div class="image-slot">
                    <el-icon><icon-picture /></el-icon>
                  </div>
                </template>
              </el-image>
            </p>
            <p class="five">
              <span style="color: #e8604c; font-weight: 600">我的评分：</span
              >{{ item.score }}
            </p>
            <p class="five">
              <el-scrollbar max-height="85px">
                <span style="color: #e8604c; font-weight: 600; font-size: 16px"
                  >简介：</span
                ><span style="font-size: 16px">{{ item.introduction }}</span>
              </el-scrollbar>
            </p>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </div>
  <div class="wantgo-city">
    <div class="want-title">我想去的</div>
    <el-scrollbar max-height="450px">
      <div class="want-body">
        <div v-for="(item, index) in wantInfo" :key="index" class="one">
          <div class="two">
            <span style="color: #ffffff; font-weight: 600"
              ><router-link :to="`/goTravel/city/detail/${item.cityId}`">{{
                item.cityName
              }}</router-link></span
            >
          </div>
          <div class="three">
            <p class="four">
              <el-image :src="item.url" class="city-img">
                <template #error>
                  <div class="image-slot">
                    <el-icon><icon-picture /></el-icon>
                  </div>
                </template>
              </el-image>
            </p>
            <p class="five">
              <el-scrollbar max-height="85px">
                <span style="color: #e8604c; font-weight: 600; font-size: 16px"
                  >简介：</span
                ><span style="font-size: 16px">{{ item.introduction }}</span>
              </el-scrollbar>
            </p>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </div>
</template>

<style lang="scss" scoped>
.one {
  width: 200px;
  height: 270px;
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  border-radius: 5px;
  overflow: hidden;
  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
  float: left;
  margin-bottom: 20px;
}
.two {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 200px;
  height: 30px;
  background-color: #e8604c;
  a {
    color: #e6e8eb;
  }
  a:hover {
    color: #ffffff;
  }
}
.three {
  display: flex;
  flex-direction: column;
  width: 200px;
  padding: 10px;
  box-sizing: border-box;
}
.four {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}
.five {
  margin: 0;
  line-height: 1.7em;
  font-size: 16px;
}
.hasgo-city {
  width: 100%;
  min-height: 400px;
  .has-title {
    width: 100%;
    height: 60px;
    border-bottom: 2px solid #e8604c;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    color: #606266;
    font-weight: 600;
  }
  .has-body {
    width: 100%;
    min-height: 340px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    padding: 20px 40px;
    padding-bottom: 10px;
  }
  border-bottom: 1px solid #dcdfe6;
}
.wantgo-city {
  width: 100%;
  min-height: 400px;
  .want-title {
    width: 100%;
    height: 60px;
    border-bottom: 2px solid #e8604c;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    color: #606266;
    font-weight: 600;
  }
  .want-body {
    width: 100%;
    min-height: 340px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    padding: 20px 40px;
    padding-bottom: 10px;
  }
}
.city-img {
  width: 100px;
  height: 100px;
  border-radius: 5px;
}
</style>
