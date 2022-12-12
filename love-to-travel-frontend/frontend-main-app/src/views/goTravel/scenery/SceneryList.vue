<script setup lang="ts">
import { ref } from "vue";
import { theCityScenerysInfoType } from "@apis/interface/iPlan";
import { getAllSceneryList } from "@apis/travelService/scenery";
let scenerysInfo = ref([] as theCityScenerysInfoType[]);
const requestScenerysInfo = async () => {
  await getAllSceneryList()
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        // citysInfo.value = res.data.slice(0, 5);
        scenerysInfo.value = res.data;
        console.log(scenerysInfo);
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
requestScenerysInfo();
</script>

<template>
  <section class="popular-tours-two">
    <div class="container">
      <div class="row">
        <div
          class="col-xl-4 col-lg-6 col-md-6 wow fadeInUp"
          data-wow-delay="100ms"
          v-for="item in scenerysInfo"
        >
          <!--Popular Tours Two Single-->
          <div class="popular-tours__single">
            <div class="popular-tours__img">
              <img :src="item.url" alt="" />
              <div class="popular-tours__icon">
                <router-link :to="`detail/${item.id}`">
                  <el-icon><View /></el-icon>
                </router-link>
              </div>
            </div>
            <div class="popular-tours__content">
              <div class="popular-tours__stars">
                <i class="fa fa-star"></i> {{ item.score }} 评分
              </div>
              <h3 class="popular-tours__title">
                <router-link :to="`detail/${item.id}`">{{
                  item.name
                }}</router-link>
              </h3>
              <p class="popular-tours__rate">
                <span>{{ item.ticket }}</span
                >元 / 每人
              </p>
              <ul class="popular-tours__meta list-unstyled">
                <li>
                  <el-scrollbar max-height="100px" class="scrollbar-content">
                    <span style="color: #303133"> 推荐理由：</span
                    >{{ item.season }}
                  </el-scrollbar>
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
.scrollbar-content {
  padding-right: 15px;
  width: 250px;
  height: 110px;
  font-size: 14px;
}
</style>
