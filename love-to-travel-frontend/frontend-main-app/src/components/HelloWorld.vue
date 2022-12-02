<script setup lang="ts">
import { ref, h } from "vue";
import { onMounted } from "vue";
import { apiGetUserInfo } from "@apis/user";
import { mainStore } from "@/store/user";
import useCurrentInstance from "@/utils/useCurrentInstance";
defineProps({
  msg: String,
});

const count = ref(0);

const { globalProperties } = useCurrentInstance();
function getUserInfo() {
  const param = {
    userID: "10001",
    userName: "Mike",
  };
  apiGetUserInfo(param).then((res) => {
    console.log(res);
  });
}
//建议用箭头函数写法
const showMessage = () => {
  globalProperties.$ElMessage.success("成功");
  globalProperties.$ElMessage.error("失败");
};
const store = mainStore();

onMounted(() => {
  getUserInfo();
});
</script>

<template>
  <h1>{{ (msg, store.token || "hello world") }}</h1>

  <div class="card">
    <button type="button" @click="showMessage">按钮</button>
    <button type="button" @click="count++">count is {{ count }}</button>
    <p>
      Edit
      <code>components/HelloWorld.vue</code> to test HMR
    </p>
  </div>

  <p>
    Check out
    <a href="https://vuejs.org/guide/quick-start.html#local" target="_blank"
      >create-vue</a
    >, the official Vue + Vite starter
  </p>
  <p>
    Install
    <a href="https://github.com/johnsoncodehk/volar" target="_blank">Volar</a>
    in your IDE for a better DX
  </p>
  <p class="read-the-docs">Click on the Vite and Vue logos to learn more</p>
</template>

<style scoped></style>
