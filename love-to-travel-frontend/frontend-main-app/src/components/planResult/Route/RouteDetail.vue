<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { everyCityPlansInfoType } from "@/apis/interface/iPlan";
const router = useRouter();
const props = defineProps<{
  routeDetailInfoString: string;
}>();
console.log("route detail页面");
console.log(props.routeDetailInfoString);
let routeDetailInfo = ref({} as everyCityPlansInfoType);
if (props.routeDetailInfoString) {
  routeDetailInfo.value = JSON.parse(props.routeDetailInfoString);
}
const backToList = () => {
  router.go(-1);
};
</script>

<template>
  <div class="detail-header">
    <div class="back-button" @click="backToList">
      <el-icon :size="30"><Close /></el-icon>
    </div>
  </div>
  <div class="right-scrollbar-item2">
    <div class="route-body">
      <div class="body-left">
        <el-icon :size="30"><Collection /></el-icon>
      </div>
      <div class="body-right">
        <p class="body-title">上海暴走三日游</p>
        <div class="body-content">
          大苏打实打实大苏打撒大苏打撒大苏打撒旦大撒大撒大撒大撒大大
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.detail-header {
  width: 100%;
  height: 50px;
  border: 1px #dcdfe6 solid;
  .back-button {
    width: 50px;
    height: 50px;
    float: left;
    // border: 1px #e8604c solid;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
  .back-button:hover {
    background-color: #f48f80;
    i {
      color: white;
    }
  }
}
.right-scrollbar-item2 {
  float: left;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 200px;
  margin: 10px;
  width: 420px;
  text-align: center;
  background: #fff;
  overflow: hidden;
  position: relative;
  // border: 1px #dcdfe6 solid;
  .route-body {
    width: 100%;
    height: 160px;
    position: absolute;
    top: 0;
    display: flex;
    justify-content: space-between;
    padding: 20px 10px;
    .body-left {
      width: 50px;
      height: 40px;
      //   border: 1px #e8604c solid;
      float: left;
      margin-top: 10px;
      i {
        color: #e8604c;
      }
    }
    .body-right {
      width: 350px;
      height: 120px;
      //   border: 1px #e8604c solid;
      float: right;
      display: flex;
      flex-direction: column;
      justify-content: start;
      .body-title {
        width: 200px;
        height: 40px;
        float: left;
        // border: 1px #e8604c solid;
        text-align: left;
        margin: 0;
        color: #303133;
      }
      .body-content {
        width: 350px;
        height: 80px;
        float: left;
        // border: 1px #e8604c solid;
        font-size: 14px;
        text-align: left;
        line-height: 1.5em;
        overflow: hidden;
      }
    }
  }
}
</style>
