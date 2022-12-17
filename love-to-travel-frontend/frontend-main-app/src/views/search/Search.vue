<script setup lang="ts">
import { ref } from "vue";
import { getSomeInfoByKeyword } from "@/apis/travelService/search";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
import {
  citysInfoType,
  theNotesInfoType,
  theCityScenerysInfoType,
} from "@apis/interface/iPlan";
const props = defineProps<{
  keyword: string;
}>();
const keyword = props.keyword;
let citysInfo = ref([] as citysInfoType[]);
let scenerysInfo = ref([] as theCityScenerysInfoType[]);
let notesInfo = ref([] as theNotesInfoType[]);

const requestSearchAllInfo = async () => {
  if (keyword) {
    alert(keyword);
    await getSomeInfoByKeyword(keyword)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          alert("获取成功");
          citysInfo.value = res.data.cityList;
          scenerysInfo.value = res.data.sceneryList;
          notesInfo.value = res.data.noteList;
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
requestSearchAllInfo();

const thisPageKeyword = ref("");
const searchTheAll = async () => {
  await getSomeInfoByKeyword(thisPageKeyword.value)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        citysInfo.value = res.data.cityList;
        scenerysInfo.value = res.data.sceneryList;
        notesInfo.value = res.data.noteList;
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

<template>
  <div class="page-header__bottom">
    <div class="container">
      <div class="page-header__bottom-inner">
        <ul class="thm-breadcrumb list-unstyled">
          <li><router-link to="/">首页</router-link></li>
          <li><span>.</span></li>
          <li class="active">搜索结果</li>
        </ul>
      </div>
    </div>
  </div>
  <div class="search-input-container">
    <form
      autocomplete="off"
      @submit.prevent="searchTheAll()"
      class="sidebar__search-form"
    >
      <input
        type="search"
        placeholder="输入搜索内容"
        v-model="thisPageKeyword"
      />
      <button type="submit" class="search-page-button">
        <i class="icon-magnifying-glass"></i>
      </button>
    </form>
  </div>
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
    </div>
  </section>
  <section class="popular-tours-two">
    <div class="container">
      <div class="row">
        <div
          class="col-xl-4 col-lg-6 col-md-6 wow fadeInUp margindiv"
          data-wow-delay="100ms"
          v-for="(item, index) in scenerysInfo"
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
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <section class="news-one">
    <div class="container">
      <div class="row">
        <div
          class="col-xl-4 col-lg-6 col-md-6 fadeInUp"
          data-wow-delay="100ms"
          v-for="(item, index) in notesInfo"
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
                  <a
                    href="javascript:;"
                    style="width: 370px; justify-content: left"
                    ><i class="far fa-user-circle"></i
                    ><span class="span-style">{{ item.userName }}</span></a
                  >
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
    </div>
  </section>
</template>

<style lang="scss" scoped>
/* 搜索框部分需要的样式 */
.destinations-one {
  padding-top: 0px;
}
.news-one {
  padding-top: 0px;
}
.popular-tours-two {
  padding-top: 0px;
}
.search-input-container {
  width: 100%;
  padding: 50px 200px;
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

/* note */
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
/* 修改图片那个地方的样式！ */
.notes-img {
  width: 100%;
  height: 100%;
}
.news-one__img {
  width: 350px;
  height: 300px;
}

/* scenery */
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
</style>
