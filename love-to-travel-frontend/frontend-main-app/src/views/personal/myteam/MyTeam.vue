<script setup lang="ts">
import { ref } from "vue";
const activeIndex = ref("myjoin");
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>

<template>
  <el-menu
    :default-active="activeIndex"
    class="el-menu-demo"
    mode="horizontal"
    @select="handleSelect"
    router
  >
    <el-menu-item index="myjoin">我加入的</el-menu-item>
    <el-menu-item index="mycreate">我创建的</el-menu-item>
  </el-menu>
  <router-view :key="$route.fullPath"></router-view>
</template>

<style lang="scss" scoped></style>
