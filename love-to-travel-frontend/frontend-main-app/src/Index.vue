<script setup lang="ts">
// import BaseHeader from "@/components/common/BaseHeader.vue";
// import BaseFooter from "@/components/common/BaseFooter.vue";
// import Home from "@/views/Home.vue";
import { onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import { utilStore } from "@/store/util";
import router from "./router";
const store = utilStore();
const route = useRoute();
// const refresh = () => {
//   if (!store.refreshFlag) {
//     //还没刷新过
//     store.refreshFlag = true;
//     location.reload(); //那就刷新一下
//     // setTimeout(function () {
//     //   location.reload();
//     // }, 1000);
//     return;
//   } else {
//     //已经刷新过了
//     store.refreshFlag = false;
//     return; //那就不刷新了
//   }
// };
// watch(router.currentRoute, () => {
//   refresh();
// });
// 保证每次进入页面都会刷新一次的工具方法：
// const refresh = () => {
//   if (!store.refreshFlag) {
//     //还没刷新过
//     store.refreshFlag = true;
//     // location.reload(); //那就刷新一下
//     setTimeout(function () {
//       location.reload();
//     }, 1000);
//     return;
//   } else {
//     //已经刷新过了
//     store.refreshFlag = false;
//     return; //那就不刷新了
//   }
// };
// refresh();
// onMounted(() => {
//   refresh();
// });
</script>

<template>
  <div class="preloader">
    <img class="preloader__image" width="60" src="/images/loader.png" alt="" />
  </div>
  <!-- /.preloader -->
  <div class="page-wrapper">
    <BaseHeader></BaseHeader>
    <router-view v-slot="{ Component }">
      <keep-alive v-if="route.meta.keepAlive">
        <component :is="Component" />
      </keep-alive>
      <component :is="Component" v-else />
    </router-view>
    <BaseFooter></BaseFooter>
  </div>
  <!-- /.page-wrapper -->
</template>

<style lang="scss" scoped></style>
