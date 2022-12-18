<script setup lang="ts"></script>

<template>
  <div class="buy-title">我购买的产品</div>
  <div class="buy-container">
    <el-card :body-style="{ padding: '0px', width: '220px' }">
      <img
        src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
        class="image"
      />
      <div style="padding: 14px">
        <span>Yummy hamburger</span>
        <div class="bottom">
          <time class="time">2020年11月11日</time>
          <el-button text class="button">Operating</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<style lang="scss" scoped>
.buy-title {
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e8604c;
  color: #ffffff;
  font-weight: 700;
  border-radius: 10px 10px 0 0;
}
.time {
  font-size: 12px;
  color: #999;
}
.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.button {
  padding: 0;
  min-height: auto;
}
.image {
  width: 220px;
  height: 220px;
  display: block;
}
.el-card {
  float: left;
  margin-bottom: 25px;
}
.buy-container {
  padding: 20px 50px;
  width: 100%;
  height: auto;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}
</style>
