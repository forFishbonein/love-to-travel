<script setup lang="ts">
import { ref } from "vue";
import { searchProductsByUserId } from "@/apis/travelService/product";
import { mainStore } from "@/store/user";
import { productBuyInfoType } from "@/apis/interface/myInterface";
const store = mainStore();
let userProductsInfo = ref([] as productBuyInfoType[]);
const requestTheUserBuy = () => {
  searchProductsByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("得到数据了2！");
        userProductsInfo.value = res.data;
        console.log(userProductsInfo.value);
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
requestTheUserBuy();
</script>

<template>
  <div class="buy-title">我购买的产品</div>
  <el-scrollbar max-height="770px">
    <div class="buy-container">
      <el-card
        :body-style="{ padding: '0px', width: '220px' }"
        v-for="(item, index) in userProductsInfo"
        :key="index"
      >
        <img :src="item.url" class="image" />
        <div style="padding: 14px">
          <router-link
            :to="`/buyTravel/product/detail/${item.id}`"
            style="font-weight: 600"
            >{{ item.name }}</router-link
          >
          <p
            style="color: #e8604c; font-weight: 600; font-size: 15px; margin: 0"
          >
            花费{{ item.cost }}元
          </p>
          <div class="bottom">
            <time class="time">购买时间:&nbsp;{{ item.createTime }}</time>
            <el-button text class="button" style="padding: 5px"
              ><router-link :to="`/buyTravel/product/detail/${item.id}`"
                >查看详情</router-link
              ></el-button
            >
          </div>
        </div>
      </el-card>
    </div>
  </el-scrollbar>
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
  height: 200px;
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
