<script setup lang="ts">
import { ref, reactive, toRefs } from "vue";
import { getPageProductsInfo } from "@apis/travelService/product";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import { productInfoType } from "@/apis/interface/myInterface";
import { subPlansFormat } from "@/utils/filters/subPlan";
/* 分页查询的实现 */
let productsInfo = ref([] as productInfoType[]);
const pageParams = reactive({
  total: 0,
  page: 1,
  limit: 6,
});
const { total, page, limit } = toRefs(pageParams);

//得到分页数据
const requestPageProductsInfo = async () => {
  await getPageProductsInfo(page.value, limit.value)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        productsInfo.value = res.data.records;
        total.value = res.data.total;
        console.log(productsInfo);
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
requestPageProductsInfo();
// 回调函数1：每页记录数改变时调用，size：回调参数，表示当前选中的“每页条数”
const changePageSize = (size: number) => {
  limit.value = size; //将页面大小改变
  requestPageProductsInfo(); //请求新的数据
};

// 回调函数2：改变页码时调用，page：回调参数，表示当前选中的“页码”
const changeCurrentPage = (p: number) => {
  page.value = p; //将当前页数改变
  requestPageProductsInfo(); //请求新的数据
};
</script>

<template>
  <section class="popular-tours-two">
    <div class="container">
      <div class="row">
        <div
          class="col-xl-4 col-lg-6 col-md-6 wow fadeInUp"
          data-wow-delay="100ms"
          v-for="(item, index) in productsInfo"
          :key="index"
        >
          <!--Popular Tours Two Single-->
          <div class="popular-tours__single">
            <div class="popular-tours__img">
              <img :src="item.url" alt="" />
            </div>
            <div class="popular-tours__content">
              <h3 class="popular-tours__title">
                <router-link :to="`/buyTravel/product/detail/${item.id}`">{{
                  item.name
                }}</router-link>
              </h3>
              <p class="popular-tours__rate">
                <span>¥{{ item.price }}</span>
              </p>
              <ul class="popular-tours__meta list-unstyled">
                <li>
                  <a href="javascript:;"
                    ><el-icon style="margin-right: 10px"
                      ><OfficeBuilding /></el-icon
                    >途径{{ item.plan.subPlans.length }}城市</a
                  >
                </li>
                <li>
                  <a href="javascript:;"
                    ><el-icon style="margin-right: 10px"><Clock /></el-icon
                    >历时{{ subPlansFormat(item.plan.subPlans) }}天</a
                  >
                </li>
                <li>
                  <a href="javascript:;"
                    ><el-icon style="margin-right: 10px"><Phone /></el-icon
                    >联系人电话:{{ item.tele }}</a
                  >
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
          :page-sizes="[6, 12, 18, 30, 48, 90]"
          style="padding: 30px 0; text-align: center"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @size-change="changePageSize"
          @current-change="changeCurrentPage"
          hide-on-single-page
          pager-count="10"
          prev-icon="Back"
          next-icon="Right"
          default-page-size="6"
        />
      </el-config-provider>
    </div>
  </section>
</template>

<style lang="scss" scoped>
.popular-tours-two {
  padding-top: 50px;
}
.popular-tours__img {
  width: 370px;
  height: 280px;
  img {
    width: 100%;
    height: 100%;
  }
}
.popular-tours__meta {
  flex-direction: column;
  align-items: flex-start;
}
.popular-tours__meta li + li {
  margin-left: 0;
}
/* 分页按钮居中 */
.el-pagination {
  display: flex;
  justify-content: center;
}
</style>
