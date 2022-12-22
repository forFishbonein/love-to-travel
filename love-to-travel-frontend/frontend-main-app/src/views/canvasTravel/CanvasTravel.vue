<script setup lang="ts">
import { ref, computed } from "vue";
import locationData from "@/assets/js/location";
import { useRouter } from "vue-router";
const router = useRouter();
const dialogFormVisible = ref(false);
const openCitySelectDialog = () => {
  dialogFormVisible.value = true;
};
const provinces = ref([]);
const initProvinceData = () => {
  for (let code in locationData) {
    let item = locationData[code];
    provinces.value.push(
      // @ts-ignore
      Object.assign(item, {
        label: item.name,
        code: item.code,
      })
    );
  }
};
initProvinceData();
const citys = ref([]);
const selectProvince = ref(null);
const selectCity = ref(null);
const selectCityId = ref(null);
const proviceHandle = (value) => {
  // console.log(value);
  const city = [];
  for (let code in value.cities) {
    let item = value.cities[code];
    city.push(
      // @ts-ignore
      Object.assign(item, {
        label: item.name,
        code: item.code,
      })
    );
  }
  citys.value = city;
  selectProvince.value = value.name;
  selectCity.value = null;
};
const cityHandle = (value) => {
  selectCity.value = value.name;
  selectCityId.value = value.code;
  console.log("----------");
  console.log(selectCityId.value);
  console.log("------------");
};
const address = computed(() => {
  return (
    (selectProvince.value ? selectProvince.value : "") +
    (selectCity.value ? "，" + selectCity.value : "")
  );
});

const goToSeeTheCitysInfo = () => {
  dialogFormVisible.value = false;
  router.push({
    name: "CanvasDetail",
    //@ts-ignore
    params: {
      cityName: selectCity.value,
    },
  });
};
const sceneryName = ref("");
const goToSeeTheSceneryInfo = () => {
  router.push({
    name: "CanvasScenery",
    //@ts-ignore
    params: {
      sceneryName: sceneryName.value,
    },
  });
};
</script>

<template>
  <el-card class="box-card detail-panel" shadow="hover">
    <div slot="header" class="clearfix card-header">
      <span style="font-size: 14px; font-weight: 700">操作面板</span>
    </div>
    <div class="city-picker-button" @click="openCitySelectDialog">
      城市选择器
    </div>
    <div class="scenery-select-container">
      <el-input v-model="sceneryName" placeholder="输入景区名" clearable />
      <div class="scenery-button" @click="goToSeeTheSceneryInfo">搜索</div>
    </div>
  </el-card>
  <el-dialog v-model="dialogFormVisible" title="城市选择器">
    <div class="city-select-machine">
      <el-select class="m-2" placeholder="请选择省份" v-model="selectProvince">
        <el-option
          v-for="item in provinces"
          :key="
            // @ts-ignore
            item.code
          "
          :value="
            // @ts-ignore
            item.name
          "
          :label="
            // @ts-ignore
            item.name
          "
          @click="proviceHandle(item)"
        />
      </el-select>
      <el-select
        class="m-2"
        placeholder="请选择城市"
        v-model="selectCity"
        v-if="citys.length > 0"
      >
        <el-option
          v-for="item in citys"
          :key="
            // @ts-ignore
            item.code
          "
          :value="
            // @ts-ignore
            item.name
          "
          :label="
            // @ts-ignore
            item.name
          "
          @click="cityHandle(item)"
        />
      </el-select>
    </div>
    <div v-if="address" class="result-select-address">
      您选择的是：<span class="text-select">{{ address }}</span>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="goToSeeTheCitysInfo">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
  <router-view :key="$route.fullPath"></router-view>
</template>

<style lang="scss" scoped>
.city-picker-button {
  width: 100px;
  height: 30px;
  float: left;
  background-color: #e8604c;
  // margin-left: 20px;
  font-size: 15px;
  font-weight: 700;
  color: #ffffff;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  position: absolute;
  left: 10px;
  margin-top: 20px;
}
.city-picker-button:hover {
  background-color: #e74128;
}
.card-header {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  height: 40px;
  padding: 0;
  padding-top: 5px;
}

.box-card {
  width: 120px;
  /* display: flex;
    flex-direction: column;
    justify-content: space-between; */
  background-color: rgba(255, 255, 255, 0.6);
  position: relative;
}
.detail-panel {
  position: fixed;
  left: 3px;
  top: 150px;
  width: 120px;
  height: 250px;
  cursor: pointer;
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
}
.city-select-machine {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: start;
  padding-left: 75px;
}
.result-select-address {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: center;
  font-size: 16px;
  font-weight: 700;
  margin-top: 20px;
  .text-select {
    color: #e8604c;
  }
}
.scenery-select-container {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 140px;
  left: 0px;
  .el-input {
    width: 100px;
  }
  .scenery-button {
    width: 80px;
    height: 25px;
    float: left;
    background-color: #e8604c;
    // margin-left: 20px;
    font-size: 15px;
    font-weight: 700;
    color: #ffffff;
    border-radius: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    // position: absolute;
    // left: 10px;
    // top: 10px;
    margin-top: 10px;
  }
  .scenery-button:hover {
    background-color: #e74128;
  }
}
</style>
