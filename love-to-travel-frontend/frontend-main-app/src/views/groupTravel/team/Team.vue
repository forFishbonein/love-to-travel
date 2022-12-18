<script setup lang="ts"></script>

<template>
  <div class="page-header__bottom">
    <div class="container">
      <div class="page-header__bottom-inner">
        <ul class="thm-breadcrumb list-unstyled">
          <li><router-link to="/">首页</router-link></li>
          <li><span>.</span></li>
          <li><router-link to="/groupTravel/team">组旅游</router-link></li>
          <li><span>.</span></li>
          <li class="active">旅游组队</li>
        </ul>
      </div>
    </div>
  </div>
  <router-view></router-view>
  <section class="book-now">
    <div
      class="book-now-shape"
      style="background-image: url(assets/images/shapes/book-now-shape.png)"
    ></div>
    <div class="container">
      <div class="row">
        <div class="col-xl-12">
          <div class="book-now__inner">
            <div class="book-now__left">
              <p>Fall in love with travel</p>
              <h2>想要创建自己的旅游小分队吗？</h2>
            </div>
            <div class="book-now__right">
              <a href="#" class="thm-btn book-now__btn">创建队伍</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style lang="scss" scoped></style>
