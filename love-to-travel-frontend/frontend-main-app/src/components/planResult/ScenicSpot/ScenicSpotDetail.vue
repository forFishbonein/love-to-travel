<script setup lang="ts">
import { useRouter } from "vue-router";
const router = useRouter();
const props = defineProps<{
  routeListInfo: [];
}>();
console.log("scenic detail页面" + props.routeListInfo);
const routeListInfo = props.routeListInfo;
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
  <el-scrollbar max-height="300px" class="detail-main">
    <div class="detail-main-header">
      <div class="left-img">
        <img src="@/assets/images/login-pic.jpg" alt="" />
      </div>
      <div class="right-info"></div>
    </div>
    <div class="detail-main-body">
      <div class="title">
        <el-icon><Document /></el-icon>详情信息
      </div>
      <div class="content">内容</div>
    </div>
  </el-scrollbar>
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
.detail-main {
  width: 100%;
  margin: 0 auto;
  height: 300px;
  .detail-main-header {
    margin: 10px auto;
    display: flex;
    justify-content: space-around;
    // align-items: center;
    width: 420px;
    border: 1px #dcdfe6 solid;
    .left-img {
      width: 180px;
      height: 150px;
      // border: 1px #e8604c solid;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .right-info {
      width: 240px;
      height: 150px;
      // border: 1px #e8604c solid;
    }
  }
  .detail-main-body {
    width: 420px;
    margin: 0 auto;
    .title {
      width: auto;
      height: 40px;
      // border: 1px #e8604c solid;
      display: flex;
      align-items: center;
      font-size: 16px;
      color: #e8604c;
      border: 1px #dcdfe6 solid;
      i {
        margin: 0 5px;
      }
    }
    .content {
      width: auto;
      display: flex;
      min-height: 150px;
      border: 1px #e8604c solid;
    }
  }
}
</style>
