<script setup lang="ts">
import { ref, reactive, toRefs } from "vue";
import { citysInfoType } from "@apis/interface/iPlan";
import { getCitysInfo, getPageCitysInfo } from "@apis/travelService/city";
// ElConfigProvider 组件
// import { ElConfigProvider } from "element-plus";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
/* 分页查询的实现 */
let citysInfo = ref([] as citysInfoType[]);
const pageParams = reactive({
  total: 0,
  page: 1,
  limit: 12,
});
const { total, page, limit } = toRefs(pageParams);
//得到分页数据
const requestPageCitysInfo = async () => {
  await getPageCitysInfo(page.value, limit.value)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        citysInfo.value = res.data.records;
        total.value = res.data.total;
        console.log(citysInfo);
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
requestPageCitysInfo();
// 回调函数1：每页记录数改变时调用，size：回调参数，表示当前选中的“每页条数”
const changePageSize = (size: number) => {
  limit.value = size; //将页面大小改变
  requestPageCitysInfo(); //请求新的数据
};

// 回调函数2：改变页码时调用，page：回调参数，表示当前选中的“页码”
const changeCurrentPage = (p: number) => {
  page.value = p; //将当前页数改变
  requestPageCitysInfo(); //请求新的数据
};
/* 查询所有 */
// let citysInfo = ref([] as citysInfoType[]);
// const requestCitysInfo = async () => {
//   await getCitysInfo()
//     .then((res: any) => {
//       if (res.code != 0) {
//         //@ts-ignore
//         ElMessage({
//           type: "error",
//           message: res.msg,
//         });
//       } else {
//         // alert("获取成功");
//         // citysInfo.value = res.data.slice(0, 5);
//         citysInfo.value = res.data;
//         console.log(citysInfo);
//       }
//     })
//     .catch((error) => {
//       //@ts-ignore
//       ElMessage({
//         type: "error",
//         message: error.message,
//       });
//     });
// };
// requestCitysInfo();
</script>

<template>
  <section class="destinations-one destinations-page">
    <div class="container">
      <div class="row masonary-layout">
        <div class="col-xl-3 col-lg-3" v-for="(item, index) in citysInfo">
          <div class="destinations-one__single">
            <div class="destinations-one__img">
              <img :src="item.url" alt="" />
              <div class="destinations-one__content">
                <h2 class="destinations-one__title">
                  <router-link :to="`detail/${item.cityId}`">{{
                    item.cityName
                  }}</router-link>
                </h2>
              </div>
              <div class="destinations-one__button">
                <router-link :to="`detail/${item.cityId}`"
                  >查看详情</router-link
                >
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-config-provider :locale="zhCn">
        <el-pagination
          :current-page="page"
          :total="total"
          :page-size="limit"
          :page-sizes="[8, 12, 20, 30, 40, 50, 100]"
          style="padding: 30px 0; text-align: center"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @size-change="changePageSize"
          @current-change="changeCurrentPage"
          hide-on-single-page
          pager-count="10"
          prev-icon="Back"
          next-icon="Right"
          default-page-size="12"
        />
      </el-config-provider>
    </div>
  </section>
</template>

<style lang="scss" scoped>
// .pagination-button {
//   width: 100%;
//   height: auto;
//   display: flex;
//   justify-content: center;
// }
/* 分页按钮居中 */
.el-pagination {
  display: flex;
  justify-content: center;
}
.destinations-one__img {
  width: 285px;
  height: 250px;
  img {
    width: 100%;
    height: 100%;
  }
}
</style>
