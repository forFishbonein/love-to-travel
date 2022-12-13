<script setup lang="ts">
import { ref } from "vue";
import {
  getOneNoteInfoById,
  getNotesInfoByUserId,
} from "@/apis/travelService/note";
import { getOneUserPlansInfoById } from "@/apis/travelService/plan";
import { getFootsByUserId } from "@/apis/travelService/foot";
import { theNotesInfoType } from "@apis/interface/iPlan";
import { finalAllCityPlansInfoType } from "@apis/interface/iPlan";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
import { strFormat } from "@/utils/filters/string";
const props = defineProps<{
  noteId: string;
}>();
const noteId = props.noteId;
const noteInfo = ref({} as theNotesInfoType);
const oneUserPlansInfo = ref({} as finalAllCityPlansInfoType);
let planId = "";
let userId = "";
const userBeensInfo = ref([]);
const otherNotesInfo = ref([] as theNotesInfoType[]);
const requestOneNoteInfoAndOthers = async () => {
  await getOneNoteInfoById(noteId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("得到数据了2！");
        noteInfo.value = res.data;
        planId = res.data.planId;
        userId = res.data.userId;
        console.log(noteInfo.value);
        // console.log(planId);
        // alert(planId);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  await getOneUserPlansInfoById(planId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        oneUserPlansInfo.value = res.data;
        console.log(oneUserPlansInfo.value);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  await getFootsByUserId(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        userBeensInfo.value = res.data.beens;
        console.log(userBeensInfo.value);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  await getNotesInfoByUserId(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        otherNotesInfo.value = res.data.slice(0, 3);
        console.log(otherNotesInfo.value);
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
requestOneNoteInfoAndOthers();
</script>
<template>
  <section class="news-details">
    <div class="container">
      <div class="row">
        <div class="col-xl-8 col-lg-7">
          <div class="news-details__left">
            <div class="news-details__img">
              <img :src="noteInfo.url" alt="" />
              <div class="news-one__date">
                <p>
                  <span>{{ timeFormat(noteInfo.createTime) }}</span>
                </p>
              </div>
            </div>
            <div class="news-details__content">
              <ul class="list-unstyled news-one__meta other-style">
                <li>
                  <a href="javascript:;"
                    ><i class="far fa-user-circle"></i
                    ><span class="span-style"
                      >你好啊大苏打实打实大{{ noteInfo.userName }}</span
                    ></a
                  >
                </li>
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><View /></el-icon>浏览:{{
                      numberFormat(noteInfo.view)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><Pointer /></el-icon>点赞:{{
                      numberFormat(noteInfo.like)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><Star /></el-icon>收藏:{{
                      numberFormat(noteInfo.star)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;">
                    <el-icon size="20px"><Document /></el-icon>评论:{{
                      numberFormat(noteInfo.comment)
                    }}
                  </a>
                </li>
              </ul>
              <h3 class="news-details__title">
                {{ noteInfo.title }}
              </h3>
              <p class="news-details__text-1">
                {{ noteInfo.content }}
              </p>
            </div>
            <div class="news-details__bottom">
              <p class="news-details__tags">
                <span>Tags:</span>
                <a href="javascript:;" v-for="item in noteInfo.trip">{{
                  item
                }}</a>
              </p>
              <div class="news-details__social-list">
                <a href="javascript:;"><i class="fab fa-twitter"></i></a>
                <a href="javascript:;"><i class="fab fa-facebook"></i></a>
                <a href="javascript:;"><i class="fab fa-instagram"></i></a>
                <a href="javascript:;"><i class="fab fa-dribbble"></i></a>
              </div>
            </div>
            <div class="author-one">
              <div class="author-one__image">
                <img src="/images/blog/author-1-1.jpg" alt="" />
              </div>
              <div class="author-one__content">
                <h3>Christine Eve</h3>
                <p>
                  It has survived not only five centuries, but also the leap
                  into electronic typesetting, remaining unchanged. It was
                  popularised in the sheets containing.
                </p>
              </div>
            </div>
            <div class="comment-one">
              <h3 class="comment-one__title">2 Comments</h3>
            </div>
            <div class="comment-form">
              <h3 class="comment-form__title">Leave a Comment</h3>
              <form action="inc/sendemail.php" class="comment-one__form">
                <div class="row">
                  <div class="col-xl-6">
                    <div class="comment-form__input-box">
                      <input type="text" placeholder="Your name" name="name" />
                    </div>
                  </div>
                  <div class="col-xl-6">
                    <div class="comment-form__input-box">
                      <input
                        type="email"
                        placeholder="Email address"
                        name="email"
                      />
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-xl-12">
                    <div class="comment-form__input-box">
                      <textarea
                        name="message"
                        placeholder="Write Comment"
                      ></textarea>
                    </div>
                    <button type="submit" class="thm-btn comment-form__btn">
                      Submit Comment
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div class="col-xl-4 col-lg-5">
          <div class="sidebar">
            <div class="sidebar__single sidebar__tags">
              <h3 class="sidebar__title">文章标签</h3>
              <div class="sidebar__tags-list">
                <a href="javascript:;" v-for="item in noteInfo.trip">{{
                  item
                }}</a>
              </div>
            </div>
            <div class="sidebar__single sidebar__category">
              <h3 class="sidebar__title">Ta去过的地方</h3>
              <ul class="sidebar__category-list list-unstyled">
                <li>
                  <!-- <a
                    href="javascript:;"
                    v-for="item in userBeensInfo"
                    >{{item.cityName}}</a
                  > -->
                </li>
                <li><a href="javascript:;">Traveling</a></li>
                <li><a href="javascript:;">Adventures</a></li>
                <li><a href="javascript:;">National Parks</a></li>
                <li><a href="javascript:;">Beaches and Sea</a></li>
              </ul>
            </div>
            <div class="sidebar__single sidebar__post">
              <h3 class="sidebar__title">Ta的全部游记</h3>
              <ul
                class="sidebar__post-list list-unstyled"
                v-for="item in otherNotesInfo"
              >
                <li>
                  <div class="sidebar__post-image">
                    <img :src="item.url" alt="" />
                  </div>
                  <div class="sidebar__post-content">
                    <h3>
                      <a href="#" class="sidebar__post-content_meta"
                        ><el-icon size="20px"><Pointer /></el-icon>点赞:{{
                          numberFormat(item.like)
                        }}</a
                      >
                      <router-link :to="`${item.id}`">{{
                        strFormat(item.title, 30)
                      }}</router-link>
                    </h3>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style lang="scss" scoped>
.other-style {
  display: flex;
  flex-wrap: wrap;
  margin: 20px 0;
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
</style>
