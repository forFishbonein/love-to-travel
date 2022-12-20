<script setup lang="ts">
import { ref, reactive, toRefs } from "vue";
import { theCityScenerysInfoType } from "@/apis/interface/myInterface";
import { getPageScenerysInfoByKeyword } from "@/apis/travelService/search";
import {
  getAllSceneryList,
  getPageScenerysInfo,
  getRecommondSceneryByUserId,
} from "@apis/travelService/scenery";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import { mainStore } from "@/store/user";
const store = mainStore();
/* 全局根据简介查询 */
const props = defineProps<{
  keyword: string;
}>();
const keyword = props.keyword;
/* 分页获取数据 */
let scenerysPageInfo = ref([] as theCityScenerysInfoType[]);
const pageParams = reactive({
  total: 0,
  page: 1,
  limit: 12,
});
const { total, page, limit } = toRefs(pageParams);
const requestPageScenerysInfo = async () => {
  if (keyword) {
    await getPageScenerysInfoByKeyword(keyword, page.value, limit.value)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          // alert("获取成功");
          scenerysPageInfo.value = res.data.records;
          total.value = res.data.total;
          console.log(scenerysPageInfo);
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  } else {
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
  }
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
/* 获取推荐列表 */
let scenerysRecommendInfo = ref([] as theCityScenerysInfoType[]);
let userLoginFlag = ref(false);
const requertRecommendSceneryInfo = () => {
  if (store.userInfo.id) {
    // alert(1111);
    // getRecommendScenerys(store.userInfo.id)
    getRecommondSceneryByUserId(store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          // ElMessage({
          //   type: "error",
          //   message: res.msg,
          // });
          // userLoginFlag.value = false;
        } else {
          console.log(res);
          // alert("获取成功");
          if (res.data.length !== 0) {
            scenerysRecommendInfo.value = res.data;
            userLoginFlag.value = true;
          }
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  }
};
requertRecommendSceneryInfo();

const thisPageKeyword = ref("");
const searchTheCity = async () => {
  total.value = 0;
  page.value = 1;
  limit.value = 12;
  await getPageScenerysInfoByKeyword(
    thisPageKeyword.value,
    page.value,
    limit.value
  )
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        scenerysPageInfo.value = res.data.records;
        total.value = res.data.total;
        console.log(scenerysPageInfo);
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
</script>
<script lang="ts">
//@ts-ignore
(function ($) {
  $(document).ready(function () {
    $(".search-popup").css("transform", "translateY(-110%)");
  });
  //@ts-ignore
})(jQuery);
</script>
<template>
  <section class="popular-tours-two change-tours-two" v-if="userLoginFlag">
    <div class="container">
      <div class="row">
        <div class="section-title text-left">
          <span class="section-title__tagline">Guess you like</span>
          <h2 class="section-title__title ali-font-family">猜你喜欢</h2>
          <div>基于推荐算法，提供个性化推荐</div>
        </div>
        <div
          class="col-xl-4 col-lg-4 col-md-4 wow fadeInUp margindiv"
          data-wow-delay="100ms"
          v-for="(item, index) in scenerysRecommendInfo"
          :key="item.id"
        >
          <div class="popular-tours__single change-size-single">
            <div class="popular-tours__img change-size-img">
              <img :src="item.url" alt="" />
              <div class="popular-tours__icon">
                <router-link :to="`detail/${item.id}`">
                  <el-icon><View /></el-icon>
                </router-link>
              </div>
            </div>
            <div class="popular-tours__content change-size-content">
              <div class="popular-tours__stars">
                <i class="fa fa-star"></i> {{ item.score }} 评分
              </div>
              <h3 class="popular-tours__title change-size-title">
                <router-link :to="`detail/${item.id}`">{{
                  item.name
                }}</router-link>
              </h3>
              <p class="popular-tours__rate change-size-rate">
                <span>{{ item.ticket }}</span
                >元 / 每人
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <div class="search-input-container">
    <form
      autocomplete="off"
      @submit.prevent="searchTheCity()"
      class="sidebar__search-form"
    >
      <input
        type="search"
        class="search"
        placeholder="输入搜索内容"
        v-model="thisPageKeyword"
      />
      <button type="submit" class="search-page-button">
        <i class="icon-magnifying-glass"></i>
      </button>
    </form>
  </div>
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
              <!--              <ul class="popular-tours__meta list-unstyled" v-show="item.season">-->
              <!--                <li>-->
              <!--                  <el-scrollbar max-height="100px" class="scrollbar-content">-->
              <!--&lt;!&ndash;                    <span style="color: #303133"></span>&ndash;&gt;-->
              <!--                    {{ item.season }}-->
              <!--                  </el-scrollbar>-->
              <!--                </li>-->
              <!--              </ul>-->
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
.popular-tours__img {
  width: 370px;
  height: 280px;
  img {
    width: 100%;
    height: 100%;
  }
}
/* 搜索框部分需要的样式 */
.popular-tours-two {
  padding-top: 0px;
}
.search-input-container {
  width: 100%;
  padding: 50px 200px;
  padding-top: 25px;
}
.search-input-container .search {
  border: 2px solid #fca38f;
  transition: ease-in-out;
}
.search-input-container .search:hover,
.search-input-container .search:focus {
  border: 2px solid #ef7555;
  background-color: #f8f1e1;
}
.search-page-button {
  border-radius: 10px;
  transition: all 0.3s linear;
}
.search-page-button:hover {
  background-color: #e8604c;
}
.search-page-button:hover > i {
  color: #ffffff;
}
/* 推荐列表 */
.change-size-single {
  width: 250px;
  height: 300px;
}
.change-size-img {
  width: 250px;
  height: 180px;
}
.change-size-content {
  border: 0;
  width: 250px;
  height: 120px;
  padding: 15px;
  padding-left: 25px;
}
.margindiv {
  display: flex;
  justify-content: center;
}
.change-size-title {
  a {
    font-size: 18px;
  }
  height: 30px;
  width: 220px;
  // overflow: scroll;
  overflow: hidden;
}
.change-size-rate {
  span {
    font-size: 18px;
  }
  height: 25px;
}
.change-tours-two {
  padding-bottom: 0;
}
.section-title {
  margin-top: 50px;
}
</style>
