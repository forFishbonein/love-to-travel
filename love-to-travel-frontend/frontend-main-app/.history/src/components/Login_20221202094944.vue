<script setup lang="ts"></script>

<template lang="pug">
.content
  .form.sign-in
    h2 欢迎回来
    label
      span 邮箱
      input(type="email")
    label
      span 密码
      input(type="password")
    p.forgot-pass
      a(href="javascript:")
        | 忘记密码？
    button.submit(type="button")
      | 登 录
    button.fb-btn(type="button")
      | 使用
      span facebook
      | 帐号登录
  .sub-cont
    .img
      .img__text.m--up
        h2 还未注册？
        p 立即注册，发现大量机会！
      .img__text.m--in
        h2 已有帐号？
        p 有帐号就登录吧，好久不见了！
      .img__btn
        span.m--up 注 册
        span.m--in 登 录
    .form.sign-up
      h2 立即注册
      label
        span 用户名
        input(type="text")
      label
        span 邮箱
        input(type="email")
      label
        span 密码
        input(type="password")
      button.submit(type="button")
        | 注册
      button.fb-btn(type="button")
        | 使用
        span facebook
        | 帐号注册
</template>

<style lang="scss" scoped></style>
