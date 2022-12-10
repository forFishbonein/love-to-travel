<script setup lang="ts">
// import BaseHeader from "@/components/common/BaseHeader.vue";
// import BaseFooter from "@/components/common/BaseFooter.vue";
// import Home from "@/views/Home.vue";
import { onMounted } from "vue";
import { useRoute } from "vue-router";
import { utilStore } from "@/store/util";

const store = utilStore();
const route = useRoute();
// 保证每次进入页面都会刷新一次的工具方法：
const refresh = () => {
  if (!store.refreshFlag) {
    // alert("刷新");
    //还没刷新过
    store.refreshFlag = true;
    // console.log(store.refreshFlag);
    location.reload(); //那就刷新一下
    // setTimeout(function () {
    //   location.reload();
    // }, 1000);
    return;
  } else {
    //已经刷新过了
    store.refreshFlag = false;
    // console.log(store.refreshFlag);

    return; //那就不刷新了
  }
};
refresh();
onMounted(() => {});
</script>

<template>
  <div class="preloader">
    <img class="preloader__image" width="60" src="/images/loader.png" alt="" />
  </div>
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
</template>

<style lang="scss" scoped></style>
