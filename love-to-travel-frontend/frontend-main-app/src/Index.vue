<script setup lang="ts">
// import BaseHeader from "@/components/common/BaseHeader.vue";
// import BaseFooter from "@/components/common/BaseFooter.vue";
// import Home from "@/views/Home.vue";
import { onMounted } from "vue";
import { useRoute } from "vue-router";
import { utilStore } from "@/store/util";

const store = utilStore();
const route = useRoute();
onMounted(() => {});
</script>

<template>
  <!-- 加载圈 -->
  <!-- <div class="preloader">
    <img class="preloader__image" width="60" src="/images/loader.png" alt="" />
  </div> -->
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
