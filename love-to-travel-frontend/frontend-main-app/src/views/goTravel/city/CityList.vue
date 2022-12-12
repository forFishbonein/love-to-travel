<script setup lang="ts">
import { ref } from "vue";
import { citysInfoType } from "@apis/interface/iPlan";
import { getCitysInfo } from "@apis/travelService/city";
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
        // alert("获取成功");
        // citysInfo.value = res.data.slice(0, 5);
        citysInfo.value = res.data;
        console.log(citysInfo);
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
requestCitysInfo();
</script>

<template>
  <section class="destinations-one destinations-page">
    <div class="container">
      <div class="row masonary-layout">
        <div class="col-xl-3 col-lg-3" v-for="(item, index) in citysInfo">
          <div class="destinations-one__single">
            <div class="destinations-one__img">
              <img src="/images/destination/destination-1-1.png" alt="" />
              <div class="destinations-one__content">
                <h2 class="destinations-one__title">
                  <router-link :to="`detail/${item.cityId}`">{{
                    item.cityName
                  }}</router-link>
                </h2>
              </div>
              <div class="destinations-one__button">
                <router-link :to="`detail/${item.cityId}`"
                  >查看详情</router-link
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style lang="scss" scoped></style>
