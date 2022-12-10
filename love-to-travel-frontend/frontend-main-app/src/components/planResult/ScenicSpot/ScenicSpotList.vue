<script setup lang="ts">
import { ref } from "vue";
import { onMounted } from "@vue/runtime-core";
import { getSceneryList } from "@apis/travelService/scenery";
import { useRouter } from "vue-router";
import { theCityScenerysInfoType } from "@/apis/interface/iPlan";
const router = useRouter();
const props = defineProps<{
  id: string;
}>();
console.log("scenic页面" + props);
const cityId = props.id;
const sceneryListInfo = ref([] as theCityScenerysInfoType[]); //改类型
const requestSceneryListInfo = async () => {
  await getSceneryList(cityId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        sceneryListInfo.value = res.data;
        console.log(sceneryListInfo.value);
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
let sceneryDetailInfo = {} as theCityScenerysInfoType;
const seeTheDetail = (index: number) => {
  // alert(index);
  sceneryDetailInfo = sceneryListInfo.value[index]; //这里必须要.value才可以
  console.log(sceneryDetailInfo);
  // sceneryListInfo.value.forEach((element) => {});
  router.push({
    name: "ScenicSpotDetail",
    params: {
      sceneryDetailInfo: JSON.stringify(sceneryDetailInfo),
    },
  });
};
onMounted(() => {
  requestSceneryListInfo();
});
</script>

<template>
  <el-scrollbar height="350px">
    <div
      v-for="(item, index) in sceneryListInfo"
      :key="item.id"
      class="right-scrollbar-item"
    >
      <div class="circle">
        <div class="imgBx" @click="seeTheDetail(index)">
          <img :src="item.url" alt="" />
        </div>
      </div>
      <div class="content">
        <el-scrollbar max-height="80px" class="content-body">
          <p>{{ item.name }}</p>
          <p>
            <span style="color: #303133">地址：</span>
            {{ item.address }}
          </p>
          <p>
            <span style="color: #303133">简介：</span>
            北京故宫看看看看破Koop近几年来可能萨
          </p>
        </el-scrollbar>
        <div class="add-button">+ 添加到行程</div>
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
  .content-body {
    margin-top: 20px;
    width: 200px;
    height: 80px;
    // border: 1px #e8604c solid;
    padding: 0 8px;
    p {
      margin: 0;
      line-height: 1.2em;
      margin: 3px 0;
    }
    p:first-child {
      font-size: 15px;
      color: #e8604c;
      font-weight: 700;
    }
    p:nth-child(2) {
      font-size: 12px;
    }
    p:nth-child(3) {
      font-size: 12px;
    }
  }
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
