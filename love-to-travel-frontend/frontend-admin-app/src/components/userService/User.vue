<script lang="ts">
import { getUser } from "../../apis/userManage/user";
export default {
  Mounted() {
    getUser().then(() => {});
  },
};
</script>

<template>
  <div></div>
</template>

<style lang="scss" scoped></style>
