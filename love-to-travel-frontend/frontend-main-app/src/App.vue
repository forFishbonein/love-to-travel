<script setup lang="ts">
// import BaseHeader from "@/components/common/BaseHeader.vue";
console.log("---环境变量----", import.meta.env);
</script>
<template>
  <!-- <div class="preloader">
    <img
      class="preloader__image"
      width="60"
      src="assets/images/loader.png"
      alt=""
    />
  </div> -->
  <!-- /.preloader -->
  <!-- <div class="page-wrapper"> -->
  <!-- <BaseHeader></BaseHeader> -->
  <!-- <router-view /> -->
  <!-- </div> -->
  <!-- 你好！ -->
  <router-view />
</template>

<style scoped></style>
