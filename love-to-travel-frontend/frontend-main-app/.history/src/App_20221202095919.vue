<script setup lang="ts">
import HelloWorld from "@components/HelloWorld.vue";
import Login from "@views/Login.vue";
console.log("---环境变量----", import.meta.env);
</script>

<template>
  <!-- <HelloWorld msg="Vite + Vue" /> -->
  <Login></Login>
</template>

<style scoped></style>
