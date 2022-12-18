<script setup lang="ts"></script>

<template>
  <section class="page-header">
    <div class="page-header__top">
      <div
        class="page-header-bg"
        style="background-image: url(/images/backgrounds/page-header-bg.jpg)"
      ></div>
      <div class="page-header-bg-overly"></div>
      <div class="container">
        <div class="page-header__top-inner">
          <h2 class="ali-font-family">去旅游：热门城市、景区推荐</h2>
        </div>
      </div>
    </div>
  </section>
  <router-view></router-view>
</template>

<style lang="scss" scoped>
.ali-font-family {
  font-family: ali-regular;
}
</style>
