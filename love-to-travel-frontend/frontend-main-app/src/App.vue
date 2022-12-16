<script setup lang="ts">
/* 引入全局echarts */
import * as echarts from "echarts";
import { provide } from "vue";
provide("ec", echarts);
console.log("---环境变量----", import.meta.env);
</script>
<template>
  <router-view />
</template>

<style>
.icon {
  width: 1.2em;
  height: 1.2em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
  color: #e8604c;
}
</style>
