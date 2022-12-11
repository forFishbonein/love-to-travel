<script setup lang="ts">
import { ref } from "vue";
import { getOneSceneryInfoById } from "@/apis/travelService/scenery";
import { theCityScenerysInfoType } from "@/apis/interface/iPlan";
const props = defineProps<{
  sceneryId: number;
}>();
alert(props.sceneryId);
const sceneryId = props.sceneryId;
const sceneryInfo = ref({} as theCityScenerysInfoType);
const requestOneSceneryInfo = async () => {
  await getOneSceneryInfoById(sceneryId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        sceneryInfo.value = res.data;
        console.log(sceneryInfo.value);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
};
requestOneSceneryInfo();
</script>

<template>
  <div>你好啊</div>
</template>

<style lang="scss" scoped></style>
