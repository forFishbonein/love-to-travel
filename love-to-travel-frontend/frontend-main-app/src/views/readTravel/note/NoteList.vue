<script setup lang="ts">
import { ref, reactive, toRefs } from "vue";
import { theNotesInfoType } from "@/apis/interface/myInterface";
import { getAllNoteList, getPageNotesInfo } from "@apis/travelService/note";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
import { getPageNotesInfoByKeyword } from "@/apis/travelService/search";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
/* 全局根据简介查询 */
const props = defineProps<{
  keyword: string;
}>();
const keyword = props.keyword;
/* 分页获取数据 */
let notesPageInfo = ref([] as theNotesInfoType[]);
const pageParams = reactive({
  total: 0,
  page: 1,
  limit: 12,
});
const { total, page, limit } = toRefs(pageParams);
const requestPageNotesInfo = async () => {
  if (keyword) {
    await getPageNotesInfoByKeyword(keyword, page.value, limit.value)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          // alert("获取成功");
          notesPageInfo.value = res.data.records;
          total.value = res.data.total;
          console.log(notesPageInfo);
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
    await getPageNotesInfo(page.value, limit.value)
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
          notesPageInfo.value = res.data.records;
          total.value = res.data.total;
          console.log(notesPageInfo);
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
requestPageNotesInfo();
// 回调函数1：每页记录数改变时调用，size：回调参数，表示当前选中的“每页条数”
const changePageSize = (size: number) => {
  limit.value = size; //将页面大小改变
  requestPageNotesInfo(); //请求新的数据
};

// 回调函数2：改变页码时调用，page：回调参数，表示当前选中的“页码”
const changeCurrentPage = (p: number) => {
  page.value = p; //将当前页数改变
  requestPageNotesInfo(); //请求新的数据
};

// let notesInfo = ref([] as theNotesInfoType[]);
// const requestNotesInfo = async () => {
//   await getAllNoteList()
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
//         notesInfo.value = res.data;
//         console.log(notesInfo);
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
// requestNotesInfo();
const thisPageKeyword = ref("");
const searchTheCity = async () => {
  total.value = 0;
  page.value = 1;
  limit.value = 12;
  await getPageNotesInfoByKeyword(
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
        notesPageInfo.value = res.data.records;
        total.value = res.data.total;
        console.log(notesPageInfo);
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
  <section class="news-one">
    <div class="container">
      <div class="row">
        <div
          class="col-xl-4 col-lg-6 col-md-6 fadeInUp"
          data-wow-delay="100ms"
          v-for="(item, index) in notesPageInfo"
          :key="item.id"
        >
          <!--News One Single-->
          <div class="news-one__single">
            <div class="news-one__img">
              <img :src="item.url" alt="" class="notes-img" />
              <router-link :to="`detail/${item.id}`">
                <span class="news-one__plus"></span>
              </router-link>
              <div class="news-one__date">
                <p>
                  <span>{{ timeFormat(item.createTime) }}</span>
                </p>
              </div>
            </div>
            <div class="news-one__content">
              <ul class="list-unstyled news-one__meta">
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><View /></el-icon>浏览:{{
                      numberFormat(item.view)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><Pointer /></el-icon>点赞:{{
                      numberFormat(item.like)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><Star /></el-icon>收藏:{{
                      numberFormat(item.star)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;">
                    <el-icon size="20px"><Document /></el-icon>评论:{{
                      numberFormat(item.comment)
                    }}
                  </a>
                </li>
                <li>
                  <router-link :to="`/user/${item.userId}`"
                    ><i class="far fa-user-circle"></i
                    ><span class="span-style">{{
                      item.userName
                    }}</span></router-link
                  >
                </li>
                <li>
                  <a href="javascript:;">
                    <el-icon size="20px"><OfficeBuilding /></el-icon>相关城市:{{
                      item.city
                    }}
                  </a>
                </li>
              </ul>
              <h3 class="news-one__title">
                <router-link :to="`detail/${item.id}`">{{
                  item.title
                }}</router-link>
              </h3>
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
.list-unstyled {
  display: flex;
  flex-wrap: wrap;
  > li {
    margin-left: 5px;
    display: flex;
    justify-content: center;
    > a {
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}
.span-style {
  display: inline-block;
  width: auto;
  max-width: 100px;
  overflow: hidden;
  height: 2em;
  color: #ffffff;
  background-color: #e8604c;
  border-radius: 5px;
  padding: 0 5px;
  line-height: 2em;
  margin-right: 5px;
}
.el-pagination {
  display: flex;
  justify-content: center;
}
/* 修改图片那个地方的样式！ */
.notes-img {
  width: 100%;
  height: 100%;
}
.news-one__img {
  width: 350px;
  height: 300px;
}
/* 搜索框部分需要的样式 */
.news-one {
  padding-top: 0px;
}
.search-input-container {
  width: 100%;
  padding: 50px 200px;
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
</style>
