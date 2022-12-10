<script setup lang="ts">
import { ref } from "vue";
import { getRouteList } from "@apis/travelService/route";
import { onMounted } from "@vue/runtime-core";
import emitter from "@/mitt/event";
const props = defineProps<{
  id: string;
}>();
console.log("route页面" + props);
const cityId = props.id;
const routeListInfo = ref([]); //改类型
const requestRouteListInfo = async () => {
  await getRouteList(cityId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        routeListInfo.value = res.data.slice(0, 10);
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
const addToTheCityPlan = () => {
  emitter.emit("addPlan", routeListInfo[0]);
};
onMounted(() => {
  requestRouteListInfo();
});
</script>

<template>
  <el-scrollbar max-height="350px">
    <div v-for="item in 4" :key="item" class="right-scrollbar-item2">
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
      <div class="route-buttons">
        <div>
          <el-icon :size="15"><Document /></el-icon>
          查看详情
        </div>
        <div @click="addToTheCityPlan">+ 添加到行程</div>
      </div>
    </div>
  </el-scrollbar>
</template>

<style lang="scss" scoped>
.el-scrollbar__view {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
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
  border: 1px #dcdfe6 solid;
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
  .route-buttons {
    position: absolute;
    width: 100%;
    height: 40px;
    bottom: 0;
    border-top: 1px #dcdfe6 solid;
    > div {
      width: 50%;
      height: 40px;
      cursor: pointer;
      transition: all 0.2s linear;
      line-height: 40px;
      color: #606266;
      font-size: 16px;
    }
    > div:hover {
      background-color: #e8604c;
      color: #ffffff;
    }
    > div:first-child {
      float: left;
      border-right: 1px #dcdfe6 solid;
      display: flex; //为了让图标居中
      align-items: center;
      justify-content: center;
      i {
        margin-right: 5px;
      }
    }
    > div:last-child {
      float: right;
      //   border: 1px #e8604c solid;
    }
  }
}
</style>
