<script setup lang="ts">
// import BaseHeader from "@/components/common/BaseHeader.vue";
// import BaseFooter from "@/components/common/BaseFooter.vue";
// import Home from "@/views/Home.vue";
import { onMounted } from "vue";
import { useRoute } from "vue-router";
// import { utilStore } from "@/store/util";
import { useRouter } from "vue-router";
// const store = utilStore();
const route = useRoute();
const router = useRouter();
const goToQuestion = () => {
  router.push("/goTravel/question");
};
onMounted(() => {});
</script>
<script lang="ts">
(function ($) {
  $(document).ready(function () {
    // 监听滚动事件
    $(window).on("scroll", function () {
      //判断向下滑动的距离
      // if ($(window).scrollTop() >= 100) {
      //   //将顶部导航栏固定
      //   $(".header").addClass("fixed").fadeIn(1000);
      // } else {
      //   $(".header").removeClass("fixed");
      // }
      /*注：fadeIn和FadeOut必须同时使用，才可以无限产生效果，如果没有用fadeOut，那么下一次的fadeIn就没有用了*/
      if ($(window).scrollTop() >= 500) {
        $(".fixed-button").fadeIn(700).css("display", "flex");
      } else {
        $(".fixed-button").fadeOut(300, function () {
          $(".fixed-button").css("display", "none");
        });
      }
    });
  });
  //@ts-ignore
})(jQuery);
</script>

<template>
  <!-- 加载圈 -->
  <!-- <div class="preloader">
    <img class="preloader__image" width="60" src="/images/loader.png" alt="" />
  </div> -->
  <div class="page-wrapper">
    <BaseHeader></BaseHeader>
    <router-view v-slot="{ Component }">
      <keep-alive v-if="route.meta.keepAlive">
        <component :is="Component" />
      </keep-alive>
      <component :is="Component" v-else />
    </router-view>
    <BaseFooter></BaseFooter>
  </div>
  <div class="fixed-button">
    <el-tooltip
      effect="light"
      content="<span style='color:#e8604c'><strong>智能问答</strong></span>"
      raw-content
      placement="top-start"
    >
      <el-icon :size="20" @click="goToQuestion"><ChatLineRound /></el-icon>
    </el-tooltip>
  </div>
</template>

<style lang="scss" scoped>
// ::v-deep .el-popper.is-customized {
//   padding: 6px 12px;
//   background: rgba(255, 255, 255, 0.8);
// }

// ::v-deep .el-popper.is-customized .el-popper__arrow::before {
//   background: rgba(255, 255, 255, 0.8);
//   right: 0;
// }
.fixed-button {
  width: 40px;
  height: 40px;
  display: none;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  position: fixed;
  bottom: 100px;
  right: 10px;
  // border: 1px #e8604c solid;
  z-index: 1000;
  background-color: rgba(255, 255, 255, 0.5);
  color: #e8604c;
  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
  cursor: pointer;
}
.fixed-button:hover {
  background-color: rgba(232, 96, 76, 0.8);
  color: #ffffff;
}
</style>
