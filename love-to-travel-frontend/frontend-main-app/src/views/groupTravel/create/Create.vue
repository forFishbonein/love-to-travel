<script setup lang="ts"></script>

<template>
  <div class="page-header__bottom">
    <div class="container">
      <div class="page-header__bottom-inner">
        <ul class="thm-breadcrumb list-unstyled">
          <li><router-link to="/">首页</router-link></li>
          <li><span>.</span></li>
          <li><a href="#">组旅游</a></li>
          <li><span>.</span></li>
          <li class="active">创建队伍</li>
        </ul>
      </div>
    </div>
  </div>
  <router-view></router-view>
</template>

<style lang="scss" scoped></style>
