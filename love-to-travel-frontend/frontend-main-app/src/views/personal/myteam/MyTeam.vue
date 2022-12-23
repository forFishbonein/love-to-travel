<script setup lang="ts">
import { ref } from "vue";
const activeIndex = ref("myjoin");
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>
<script lang="ts">
window.onbeforeunload = function () {
  var scrollPos;
  if (typeof window.pageYOffset != "undefined") {
    scrollPos = window.pageYOffset;
  } else if (
    typeof document.compatMode != "undefined" &&
    document.compatMode != "BackCompat"
  ) {
    scrollPos = document.documentElement.scrollTop;
  } else if (typeof document.body != "undefined") {
    scrollPos = document.body.scrollTop;
  }
  document.cookie = "scrollTop=" + scrollPos; //存储滚动条位置到cookies中
};

window.onload = function () {
  if (document.cookie.match(/scrollTop=([^;]+)(;|$)/) != null) {
    var arr = document.cookie.match(/scrollTop=([^;]+)(;|$)/); //cookies中不为空，则读取滚动条位置
    // @ts-ignore
    document.documentElement.scrollTop = parseInt(arr[1]);
    // @ts-ignore
    document.body.scrollTop = parseInt(arr[1]);
  }
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
