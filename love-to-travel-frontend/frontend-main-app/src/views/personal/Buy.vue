<script setup lang="ts">
import { ref } from "vue";
import { searchProductsByUserId } from "@/apis/travelService/product";
import { mainStore } from "@/store/user";
import { productBuyInfoType } from "@/apis/interface/myInterface";
const store = mainStore();
let userProductsInfoNotPay = ref([] as productBuyInfoType[]);
let userProductsInfoHasPay = ref([] as productBuyInfoType[]);
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
        res.data.forEach((e: productBuyInfoType) => {
          if (e.paystate === "0") {
            userProductsInfoNotPay.value.push(e);
          } else if (e.paystate === "1") {
            userProductsInfoHasPay.value.push(e);
          }
        });
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
<script lang="ts">
window.onbeforeunload = function () {
  var scrollPos;
  if (typeof window.pageYOffset != "undefined") {
    scrollPos = window.pageYOffset;
  } else if (
    typeof document.compatMode != "undefined" &&
    document.compatMode != "BackCompat"
  ) {
    scrollPos = document.documentElement.scrollTop;
  } else if (typeof document.body != "undefined") {
    scrollPos = document.body.scrollTop;
  }
  document.cookie = "scrollTop=" + scrollPos; //存储滚动条位置到cookies中
};

window.onload = function () {
  if (document.cookie.match(/scrollTop=([^;]+)(;|$)/) != null) {
    var arr = document.cookie.match(/scrollTop=([^;]+)(;|$)/); //cookies中不为空，则读取滚动条位置
    // @ts-ignore
    document.documentElement.scrollTop = parseInt(arr[1]);
    // @ts-ignore
    document.body.scrollTop = parseInt(arr[1]);
  }
};
</script>
<template>
  <div class="buy-title">我购买的产品</div>
  <el-scrollbar max-height="770px">
    <el-tabs type="border-card">
      <el-tab-pane label="未支付">
        <div class="buy-container">
          <el-card
            :body-style="{ padding: '0px', width: '220px' }"
            v-for="(item, index) in userProductsInfoNotPay"
            :key="index"
          >
            <img :src="item.url" class="image" />
            <div style="padding: 14px">
              <router-link
                :to="`/buyTravel/product/detail/${item.productId}`"
                style="font-weight: 600"
                >{{ item.name }}</router-link
              >
              <p
                style="
                  color: #e8604c;
                  font-weight: 600;
                  font-size: 15px;
                  margin: 0;
                  display: flex;
                  align-items: center;
                "
              >
                花费{{ item.cost }}元
                <a
                  class="to-pay-button"
                  :href="`http://localhost:8082/alipay/pay?id=${item.id}&cost=${item.cost}`"
                  target="_blank"
                  >点击支付</a
                >
              </p>
              <div class="bottom">
                <time class="time">购买时间:&nbsp;{{ item.createTime }}</time>
                <el-button text class="button" style="padding: 5px"
                  ><router-link
                    :to="`/buyTravel/product/detail/${item.productId}`"
                    >查看详情</router-link
                  ></el-button
                >
              </div>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane label="已支付">
        <div class="buy-container">
          <el-card
            :body-style="{ padding: '0px', width: '220px' }"
            v-for="(item, index) in userProductsInfoHasPay"
            :key="index"
          >
            <img :src="item.url" class="image" />
            <div style="padding: 14px">
              <router-link
                :to="`/buyTravel/product/detail/${item.productId}`"
                style="font-weight: 600"
                >{{ item.name }}</router-link
              >
              <p
                style="
                  color: #e8604c;
                  font-weight: 600;
                  font-size: 15px;
                  margin: 0;
                "
              >
                花费{{ item.cost }}元
                <span style="color: #303133; margin-left: 10px"
                  >状态:{{ item.status === "0" ? "未使用" : "已使用" }}</span
                >
              </p>
              <div class="bottom">
                <time class="time">购买时间:&nbsp;{{ item.createTime }}</time>
                <el-button text class="button" style="padding: 5px"
                  ><router-link
                    :to="`/buyTravel/product/detail/${item.productId}`"
                    >查看详情</router-link
                  ></el-button
                >
              </div>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
    </el-tabs>
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
.to-pay-button {
  width: 70px;
  height: 25px;
  display: flex;
  float: right;
  justify-content: center;
  align-items: center;
  background-color: #e8604c;
  color: #ffffff;
  font-weight: 700;
  margin-left: 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 15px;
}
</style>
