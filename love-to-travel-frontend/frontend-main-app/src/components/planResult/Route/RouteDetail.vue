<script setup lang="ts">
const props = defineProps<{
  id: string;
}>();
console.log("route detail页面" + props);
const cityId = props.id;
</script>

<template>
  <div></div>
</template>

<style lang="scss" scoped></style>
