<script setup lang="ts">
import { ref } from "vue";
const activeIndex = ref("1");
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>

<template>
  <div class="map-container"></div>
  <div class="personal-info-container">
    <div class="avater-container">
      <img src="@/assets/images/login-pic.jpg" class="avater-img" />
    </div>
  </div>
  <div class="header-nav-container">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item index="1">我的中心</el-menu-item>
      <el-menu-item index="2">足迹与点评</el-menu-item>
      <el-menu-item index="3">游记</el-menu-item>
      <el-menu-item index="4">点赞</el-menu-item>
      <el-menu-item index="5">问答</el-menu-item>
      <el-menu-item index="6">行程</el-menu-item>
      <el-menu-item index="7">队伍</el-menu-item>
      <el-menu-item index="8">时光机</el-menu-item>
    </el-menu>
  </div>
  <div class="main-container">
    <div class="main-left">
      <div class="follower">
        <div>
          <div><router-link to="/">我的关注：</router-link></div>
        </div>
        <div>
          <div><router-link to="/">我的粉丝：</router-link></div>
        </div>
      </div>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>我的勋章</span>
          </div>
        </template>
        <div v-for="o in 2" :key="o" class="text item">
          {{ "List item " + o }}
        </div>
      </el-card>
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>留言板</span>
          </div>
        </template>
        <div class="comment-form__input-box">
          <textarea
            name="message"
            placeholder="Write Comment"
            class="textarea-message"
          ></textarea>
        </div>
        <button type="submit" class="thm-btn comment-form__btn">发送</button>
      </el-card>
    </div>
    <div class="main-body">
      <router-view></router-view>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.map-container {
  width: 100%;
  height: 500px;
  margin: 0px auto;
  margin-top: 10px;
  border: 1px #e8604c solid;
}
.personal-info-container {
  width: 100%;
  height: 150px;
  margin: 0 auto;
  // margin-bottom: 10px;
  border: 1px #e8604c solid;
  position: relative;
  .avater-container {
    width: 150px;
    height: 150px;
    border-radius: 75px;
    position: absolute;
    top: -75px;
    border: 1px #e8604c solid;
    margin-left: 20px;
    .avater-img {
      width: 100%;
      height: 100%;
      display: block;
      border-radius: 75px;
    }
  }
}
.header-nav-container {
  width: 100%;
  height: 60px;
  // margin: 0 auto;
  border: 1px #e8604c solid;
  .el-menu-demo {
    display: flex;
    justify-content: space-around;
  }
}
.main-container {
  display: flex;
  justify-content: space-between;
  width: 1100px;
  min-height: 500px;
  margin: 0px auto;
  // border: 1px #e8604c solid;
  // box-shadow: var(--el-box-shadow-light);
  margin-top: 10px;
  .main-left {
    width: 220px;
    // border: 1px #e8604c solid;
    box-shadow: var(--el-box-shadow-lighter);
    background-color: #f7f2ea;
    display: flex;
    flex-direction: column;
    align-items: center;
    .follower {
      width: 200px;
      height: 140px;
      // border: 1px #e8604c solid;
      margin-top: 20px;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      > div {
        width: 200px;
        height: 60px;
        // border: 1px #e8604c solid;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #ffffff;
        box-shadow: var(--el-box-shadow-light);
        border-radius: 3px;

        > div {
          width: 155px;
          border-radius: 5px;
          height: 45px;
          background-color: #e8604c;
          display: flex;
          justify-content: center;
          align-items: center;
          a {
            color: #ffffff;
            font-size: 16px;
            font-weight: 600;
          }
          a:hover {
            color: #303133;
          }
        }
      }
    }
    .card-header {
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: #e8604c;
      border-radius: 5px;
      span {
        font-size: 16px;
        font-weight: 600;
        color: #ffffff;
      }
    }
    .text {
      font-size: 14px;
    }
    .item {
      margin-bottom: 18px;
    }
    .box-card {
      width: 200px;
      margin: 5px 0;
    }
  }
  .main-body {
    width: 850px;
    // border: 1px #e8604c solid;
    box-shadow: var(--el-box-shadow-lighter);
    background-color: #f7f2ea;
  }
  .thm-btn {
    width: 100px;
    height: 35px;
    padding: 0;
  }
  .textarea-message {
    padding: 10px 20px;
  }
}
</style>
