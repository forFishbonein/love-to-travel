<script setup lang="ts">
import HelloWorld from "@components/HelloWorld.vue";
import LoginAndRegister from "@/views/LoginAndRegister.vue";
console.log("---环境变量----", import.meta.env);
</script>
<template>
  <!-- <HelloWorld msg="Vite + Vue" /> -->
  <!-- <LoginAndRegister></LoginAndRegister> -->
  <router-view />
</template>

<style scoped></style>
