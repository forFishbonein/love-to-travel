<script setup lang="ts"></script>

<template>
  <el-scrollbar height="350px">
    <div v-for="item in 20" :key="item" class="right-scrollbar-item">
      <div class="circle">
        <div class="imgBx">
          <img src="@/assets/images/login-pic.jpg" alt="" />
        </div>
      </div>
      <div class="content">
        <div class="add-button">+添加到行程</div>
      </div>
    </div>
  </el-scrollbar>
</template>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
}
.el-scrollbar__view {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
.right-scrollbar-item {
  float: left;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 270px;
  margin: 10px;
  width: 200px;
  text-align: center;
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
  position: relative;
  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
  .add-button {
    position: absolute;
    bottom: 0px;
    border-top: 1px #dcdfe6 solid;
    width: 100%;
    height: 35px;
    cursor: pointer;
    transition: all 0.3s linear;
    border-radius: 0 0 10px 10px;
    font-size: 16px;
  }
  .add-button:hover {
    margin-top: 2px;
    box-shadow: 0 3px 15px 3px rgba(51, 51, 51, 0.5);
    // background-color: rgba(255, 0, 0, 0.5);
  }
}

.circle {
  position: absolute;
  top: -190px;
  left: 50%;
  transform: translateX(-50%);
  width: 350px;
  height: 335px;
  background: #333;
  clip-path: circle();
}
.circle:before {
  content: "";
  position: absolute;
  top: -8px;
  left: -16px;
  width: 100%;
  height: 100%;
  background: transparent;
  box-shadow: 0 0 0 20px rgba(255, 0, 0, 0.5);
  border-radius: 50%;
  z-index: 100;
  pointer-events: none;
}
.circle .imgBx {
  position: absolute;
  left: 50%;
  bottom: 0;
  transform: translateX(-50%);
  width: 340px;
  height: 310px;
  background: white;
}

.circle .imgBx img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: 0.5s;
  transform-origin: bottom;
  transform: translateY(25%); //调整图片位置
  cursor: pointer;
}

.circle .imgBx:hover img {
  transform: scale(1.1);
}

.content {
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 140px;
  // padding: 20px 30px;
}

.fa-linkedin {
  background: #0077b5;
  color: #fff;
  padding: 2px 4px;
  border-radius: 2px;
}

.content h3 {
  font-size: 1.4em;
  color: #333;
  margin-top: 7px;
}

.textIcon {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
  padding: 0 2px;
}

.textIcon h4 {
  color: #e91e63;
  font-weight: 400;
}

.textIcon a {
  color: #e91e63;
}
</style>
