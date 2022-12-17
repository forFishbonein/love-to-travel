<script setup lang="ts">
import { ref } from "vue";
import { getUserAllPlansInfoByUserId } from "@/apis/travelService/plan";
import { finalAllCityPlansInfoType } from "@/apis/interface/myInterface";
import { mainStore } from "@/store/user";
const store = mainStore();
const userAllPlansInfo = ref([] as finalAllCityPlansInfoType[]);
const requestMyPlansInfo = async () => {
  await getUserAllPlansInfoByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        userAllPlansInfo.value = res.data;
        console.log(userAllPlansInfo.value);
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
requestMyPlansInfo();
</script>

<template>
  <el-scrollbar max-height="770px">
    <div
      class="sidebar__single sidebar__category"
      v-for="(cell, index1) in userAllPlansInfo"
      :key="index1"
    >
      <h3 class="sidebar__title">行程表{{ index1 + 1 }}</h3>
      <ul class="sidebar__category-list list-unstyled">
        <li style="font-size: 16px">总预算:{{ cell.budget }}元</li>
        <li>
          <span class="span-style text-amber">行程信息</span>
          <el-scrollbar max-height="300px">
            <el-collapse accordion>
              <el-collapse-item
                :name="index2"
                v-for="(item, index2) in cell.subPlans"
                :key="index2"
              >
                <template #title>
                  <div class="plan-note-header">
                    {{ item.city
                    }}<el-icon class="header-icon" size="16px">
                      <Place />
                    </el-icon>
                    <span style="margin-left: 10px">预算:</span
                    >{{ item.budget }}元
                  </div>
                </template>
                <ul>
                  <li v-for="(i, index3) in item.days" :key="index3">
                    第{{ index3 + 1 }}天
                    <span v-for="(k, index4) in i.route" :key="index4">
                      {{ k.originName }}({{ k.departTime }}小时)
                      <el-icon><Right /></el-icon>
                    </span>
                    结束
                  </li>
                </ul>
              </el-collapse-item>
            </el-collapse>
          </el-scrollbar>
        </li>
      </ul>
    </div>
  </el-scrollbar>
</template>

<style lang="scss" scoped>
.sidebar__single {
  margin: 20px;
}
</style>
