<script setup lang="ts">
import { ref, reactive, toRefs } from "vue";
import { theCityScenerysInfoType } from "@apis/interface/iPlan";
import {
  getAllSceneryList,
  getPageScenerysInfo,
} from "@apis/travelService/scenery";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
/* 分页获取数据 */
let scenerysPageInfo = ref([] as theCityScenerysInfoType[]);
const pageParams = reactive({
  total: 0,
  page: 1,
  limit: 12,
});
const { total, page, limit } = toRefs(pageParams);
const requestPageScenerysInfo = async () => {
  await getPageScenerysInfo(page.value, limit.value)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        // citysInfo.value = res.data.slice(0, 5);
        scenerysPageInfo.value = res.data.records; //注意这里是records
        total.value = res.data.total;
        console.log(scenerysPageInfo.value);
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
requestPageScenerysInfo();
// 回调函数1：每页记录数改变时调用，size：回调参数，表示当前选中的“每页条数”
const changePageSize = (size: number) => {
  limit.value = size; //将页面大小改变
  requestPageScenerysInfo(); //请求新的数据
};

// 回调函数2：改变页码时调用，page：回调参数，表示当前选中的“页码”
const changeCurrentPage = (p: number) => {
  page.value = p; //将当前页数改变
  requestPageScenerysInfo(); //请求新的数据
};
/* 获取所有数据 */
// let scenerysInfo = ref([] as theCityScenerysInfoType[]);
// const requestScenerysInfo = async () => {
//   await getAllSceneryList()
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
//         scenerysInfo.value = res.data;
//         console.log(scenerysInfo);
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
// requestScenerysInfo();
</script>

<template>
  <section class="popular-tours-two">
    <div class="container">
      <div class="row">
        <div
          class="col-xl-4 col-lg-6 col-md-6 wow fadeInUp margindiv"
          data-wow-delay="100ms"
          v-for="(item, index) in scenerysPageInfo"
          :key="item.id"
        >
          <!--Popular Tours Two Single-->
          <div class="popular-tours__single">
            <div class="popular-tours__img">
              <img :src="item.url" alt="" />
              <div class="popular-tours__icon">
                <router-link :to="`detail/${item.id}`">
                  <el-icon><View /></el-icon>
                </router-link>
              </div>
            </div>
            <div class="popular-tours__content">
              <div class="popular-tours__stars">
                <i class="fa fa-star"></i> {{ item.score }} 评分
              </div>
              <h3 class="popular-tours__title">
                <router-link :to="`detail/${item.id}`">{{
                  item.name
                }}</router-link>
              </h3>
              <p class="popular-tours__rate">
                <span>{{ item.ticket }}</span
                >元 / 每人
              </p>
              <ul class="popular-tours__meta list-unstyled">
                <li>
                  <el-scrollbar max-height="100px" class="scrollbar-content">
                    <span style="color: #303133"> 推荐理由：</span
                    >{{ item.season }}
                  </el-scrollbar>
                </li>
              </ul>
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
.el-pagination {
  display: flex;
  justify-content: center;
}
.scrollbar-content {
  padding-right: 15px;
  width: 250px;
  height: 110px;
  font-size: 14px;
}
</style>
