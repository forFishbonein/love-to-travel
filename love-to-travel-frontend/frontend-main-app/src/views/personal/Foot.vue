<script setup lang="ts"></script>

<template>
  <div class="hasgo-city">
    <div class="has-title">我去过的</div>
    <div class="has-body">
      <div
        style="
          width: 200px;
          min-height: 200px;
          display: flex;
          flex-direction: column;
          background-color: #f7f2ea;
          border-radius: 5px;
          overflow: hidden;
          box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
        "
      >
        <div
          style="
            display: flex;
            justify-content: center;
            align-items: center;
            width: 200px;
            height: 30px;
            background-color: #e8604c;
          "
        >
          <span style="color: #ffffff; font-weight: 600">地点：${name}</span>
        </div>
        <div
          style="
            display: flex;
            flex-direction: column;
            width: 200px;
            padding: 10px;
            box-sizing: border-box;
          "
        >
          <p style="margin: 0; line-height: 1.7em; font-size: 16px">
            <span style="color: #e8604c; font-weight: 600">类型：</span>去过
          </p>
          <p style="margin: 0; line-height: 1.7em; font-size: 16px">
            <span style="color: #e8604c; font-weight: 600">我的评分：</span
            >${score}
          </p>
          <p style="margin: 0; line-height: 1.7em; font-size: 16px">
            <span style="color: #e8604c; font-weight: 600">简介：</span
            >${introduction}
          </p>
        </div>
      </div>
    </div>
  </div>
  <div class="wantgo-city">
    <div class="want-title">我想去的</div>
    <div class="want-body"></div>
  </div>
</template>

<style lang="scss" scoped>
.hasgo-city {
  width: 100%;
  min-height: 400px;
  display: flex;
  .has-title {
    width: 100%;
    height: 60px;
    border-bottom: 2px solid #e8604c;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    color: #606266;
    font-weight: 600;
  }
  .has-body {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
  }
  border-bottom: 1px solid #dcdfe6;
}
.wantgo-city {
  width: 100%;
  min-height: 400px;
  display: flex;
  .want-title {
    width: 100%;
    height: 60px;
    border-bottom: 2px solid #e8604c;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    color: #606266;
    font-weight: 600;
  }
}
</style>
