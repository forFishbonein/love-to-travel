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

<style scoped></style>
