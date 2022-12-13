<script setup lang="ts">
import { ref } from "vue";

const currentDate = ref(new Date());
</script>

<template>
  <ul class="main-button">
    <li class="tour-types__single wow fadeInUp" data-wow-delay="100ms">
      <div class="tour-types__content">
        <div class="tour-types__icon">
          <el-icon><EditPen /></el-icon>
        </div>
        <h4 class="tour-types__title">写游记</h4>
      </div>
    </li>
    <li class="tour-types__single wow fadeInUp" data-wow-delay="200ms">
      <router-link to="/"
        ><div class="tour-types__content">
          <div class="tour-types__icon">
            <el-icon><ChatDotRound /></el-icon>
          </div>
          <h4 class="tour-types__title">问答</h4>
        </div>
      </router-link>
    </li>
    <li class="tour-types__single wow fadeInUp" data-wow-delay="300ms">
      <div class="tour-types__content">
        <div class="tour-types__icon">
          <el-icon><MapLocation /></el-icon>
        </div>
        <h4 class="tour-types__title">添加足迹</h4>
      </div>
    </li>
    <li class="tour-types__single wow fadeInUp" data-wow-delay="400ms">
      <div class="tour-types__content">
        <div class="tour-types__icon">
          <el-icon><User /></el-icon>
        </div>
        <h4 class="tour-types__title">找队友</h4>
      </div>
    </li>
  </ul>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>我的旅行游记</span>
        <el-button class="button" text>发游记</el-button>
      </div>
    </template>
    <el-scrollbar max-height="420px">
      <div class="card-item-container">
        <div class="item-img">
          <img src="@/assets/images/login-pic.jpg" />
        </div>
        <div class="item-content">
          <div class="content-left">
            <div><router-link to="/">I love 北京</router-link></div>
            <div><span class="span-style">相关城市:</span>北京</div>
            <div><span class="span-style">发表时间:</span>2022年12月12日</div>
          </div>
          <div class="content-right">
            <div>
              <el-icon size="16px"><View /></el-icon>观看:1000 &nbsp;&nbsp;
              <el-icon size="16px"><Pointer /></el-icon>点赞:100&nbsp;&nbsp;
              <el-icon size="16px"><Star /></el-icon>收藏:5&nbsp;&nbsp;
              <el-icon size="16px"><Document /></el-icon>评论:10
            </div>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </el-card>
</template>

<style lang="scss" scoped>
.main-button {
  width: 100%;
  margin: 0 auto;
  // border: 1px #e8604c solid;
  height: 210px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: 20px;
  .tour-types__single {
    margin-bottom: 0;
    cursor: pointer;
  }
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.box-card {
  width: 800px;
  margin-left: 25px;
  margin-top: 30px;
  .card-item-container {
    width: 700px;
    height: 300px;
    // border: 1px #e8604c solid;
    border: 1px #dcdfe6 solid;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    .item-img {
      height: 300px;
      width: 300px;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .item-content {
      width: 400px;
      height: 300px;
      display: flex;
      justify-content: space-around;
      flex-direction: column;
      .content-left {
        width: 400px;
        height: 170px;
        // border: 1px #e8604c solid;
        padding-top: 10px;
        .span-style {
          color: #ffffff;
          background-color: #e8604c;
          border-radius: 5px;
          padding: 5px;
          margin-right: 5px;
        }
        > div {
          height: 50px;
          width: 400px;
          padding-left: 30px;
        }
        > div:first-child {
          font-size: 22px;
          font-weight: 700;
        }
        > div:nth-child(2) {
          font-size: 16px;
        }
        > div:nth-child(3) {
          font-size: 16px;
        }
      }
      .content-right {
        width: 400px;
        height: 100px;
        // border: 1px #e8604c solid;
        display: flex;
        flex-direction: column;
        justify-content: end;
        div {
          text-align: right;
          // border: 1px #e8604c solid;
          font-size: 16px;
          display: flex;
          align-items: center;
          justify-content: end;
          padding-right: 10px;
        }
      }
    }
  }
}
</style>
