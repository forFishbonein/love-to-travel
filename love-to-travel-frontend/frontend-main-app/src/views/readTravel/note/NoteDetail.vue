<script setup lang="ts">
import { ref } from "vue";
import {
  getOneNoteInfoById,
  getNotesInfoByUserId,
} from "@/apis/travelService/note";
import { getUserInfoById } from "@/apis/userService/user";
import { getOneUserPlansInfoById } from "@/apis/travelService/plan";
import {
  getCommentsByNoteId,
  likeTheComment,
  islikeTheComment,
} from "@/apis/travelService/comment";
import { getFootsByUserId } from "@/apis/travelService/foot";
import {
  theNotesInfoType,
  theNoteComment,
  tranformComments,
  secondComment,
} from "@apis/interface/iPlan";
import { UserInfo } from "@/apis/userService/uinterface";
import { finalAllCityPlansInfoType } from "@apis/interface/iPlan";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
import { strFormat } from "@/utils/filters/string";
import { useRouter } from "vue-router";
import { mainStore } from "@/store/user";
const router = useRouter();
const store = mainStore();
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
const authorInfo = ref({} as UserInfo);
const noteCommentsInfo = ref([] as theNoteComment[]);
const finalCommentsArray = ref([] as tranformComments[]);

const commentsFormat = (data: theNoteComment[]) => {
  let oneArray = [] as theNoteComment[];
  data.forEach((e) => {
    if (e.parentId === "0" || e.parentId === "" || e.parentId === null) {
      // @ts-ignore
      finalCommentsArray.value.push({
        id: e.id,
        userId: e.userId,
        userName: e.userName,
        content: e.content,
        like: e.like,
        createTime: e.createTime,
        reply: e.reply,
        son: [],
      });
    } else {
      // @ts-ignore
      oneArray.push(e);
    }
  });
  oneArray.forEach((e) => {
    // @ts-ignore
    finalCommentsArray.value.forEach((e2: tranformComments) => {
      // @ts-ignore
      if (e.parentId === e2.id) {
        e2.son.push({
          id: e.id,
          userId: e.userId,
          userName: e.userName,
          content: e.content,
          like: e.like,
          createTime: e.createTime,
          reply: e.reply,
        });
      }
    });
  });
};

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
        otherNotesInfo.value = res.data.slice(0, 3); //长度不够不会报错
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
  await getUserInfoById(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        authorInfo.value = res.data;
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
  await getCommentsByNoteId(noteId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        noteCommentsInfo.value = res.data;
        console.log(noteCommentsInfo.value);
        commentsFormat(noteCommentsInfo.value);
        console.log("-----------");
        console.log(finalCommentsArray.value);
        console.log("-----------");
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
// const cLikeFlag = ref(false);
const commentLike = async (commentId: string) => {
  if (store.userInfo.id) {
    // alert("点赞");
    await likeTheComment(commentId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "点赞成功",
          });
          setTimeout(
            () =>
              //更新评论
              getCommentsByNoteId(noteId)
                .then((res: any) => {
                  if (res.code != 0) {
                    //@ts-ignore
                    ElMessage({
                      type: "error",
                      message: res.msg,
                    });
                  } else {
                    noteCommentsInfo.value = res.data;
                    console.log(noteCommentsInfo.value);
                    commentsFormat(noteCommentsInfo.value);
                    console.log("-----------");
                    console.log(finalCommentsArray.value);
                    console.log("-----------");
                  }
                })
                .catch((error) => {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: error.message,
                  });
                }),
            2 * 1000
          );
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
    // alert("不保存");
    // router.push("/login");
    //@ts-ignore
    ElMessage({
      type: "warning",
      message: "请先登录！",
    });
  }
};
const commentisLike = (commentId: string) => {
  let theFlag = false;
  if (store.userInfo.id) {
    // alert("点赞");
    islikeTheComment(commentId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          theFlag = res.data;
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
  return theFlag;
};
const cancelCommentLike = (commentId: string) => {
  alert("取消点赞");
};
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
              <el-scrollbar max-height="500px">
                <p class="news-details__text-1">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
                    noteInfo.content
                  }}
                </p>
              </el-scrollbar>
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
                <!-- <img :src="authorInfo.url" alt="" /> -->
                <img src="/images/blog/author-1-1.jpg" alt="" />
              </div>
              <div class="author-one__content">
                <h3>
                  <router-link to="/">{{ authorInfo.name }}</router-link>
                </h3>
                <p>个性签名:{{ authorInfo.signature }}</p>
              </div>
            </div>
            <div class="comment-form">
              <h3 class="comment-form__title">发表评论</h3>
              <form action="inc/sendemail.php" class="comment-one__form">
                <div class="row">
                  <div class="col-xl-12">
                    <div class="comment-form__input-box">
                      <textarea
                        name="message"
                        placeholder="写下评论内容"
                      ></textarea>
                      <button type="submit" class="thm-btn comment-form__btn">
                        Submit Comment
                      </button>
                    </div>
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
                <li v-for="item in userBeensInfo">
                  <router-link
                    :to="`/goTravel/city/detail/${
                      // @ts-ignore
                      item.cityId
                    }`"
                    >{{
                      // @ts-ignore
                      item.cityName
                    }}</router-link
                  >
                </li>
              </ul>
            </div>
            <div class="sidebar__single sidebar__post">
              <h3 class="sidebar__title">Ta的部分游记</h3>
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
            <div class="sidebar__single sidebar__category">
              <h3 class="sidebar__title">对应行程表</h3>
              <ul class="sidebar__category-list list-unstyled">
                <li style="font-size: 16px">
                  总预算:{{ oneUserPlansInfo.budget }}元
                </li>
                <li>
                  <span class="span-style text-amber">行程信息</span>
                  <el-scrollbar max-height="300px">
                    <el-collapse accordion>
                      <el-collapse-item
                        :name="index"
                        v-for="(item, index) in oneUserPlansInfo.subPlans"
                        :key="index"
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
                          <li v-for="(i, index2) in item.days" :key="index2">
                            第{{ index2 + 1 }}天
                            <span v-for="(k, index3) in i.route" :key="index3">
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
            <div class="make-plan-button">
              <router-link to="/plan" class="about-one__btn thm-btn"
                >制定专属行程</router-link
              >
            </div>
            <div class="sidebar__single sidebar__category">
              <h3 class="sidebar__title">游记评论</h3>
              <ul class="sidebar__category-list list-unstyled">
                <li>
                  <el-scrollbar max-height="800px">
                    <el-collapse accordion>
                      <el-collapse-item
                        :name="index"
                        v-for="(item, index) in finalCommentsArray"
                        :key="index"
                      >
                        <template #title>
                          <div class="comment-container">
                            <div class="comment-header">
                              <p>
                                <router-link to="">{{
                                  item.userName
                                }}</router-link>
                              </p>
                              <p>
                                {{ item.createTime }}
                              </p>
                            </div>
                            <div class="comment-body">
                              <el-scrollbar max-height="60px">
                                <div class="content-main">
                                  {{ item.content }}
                                  你的骄傲的哈飒飒大大哇定位的大无大多所大无多无大大无多无多哇大武当哇大无大无多哇大无大大无大无大无大无多
                                </div>
                              </el-scrollbar>
                            </div>
                            <div class="function-button-container">
                              <div>
                                <svg
                                  class="icon"
                                  aria-hidden="true"
                                  @click="cancelCommentLike(item.id)"
                                  v-if="commentisLike(item.id)"
                                >
                                  <use xlink:href="#icon-dianzan1"></use>
                                </svg>
                                <svg
                                  class="icon"
                                  aria-hidden="true"
                                  @click="commentLike(item.id)"
                                  v-else
                                >
                                  <use xlink:href="#icon-dianzan"></use>
                                </svg>
                                {{ item.like }}
                              </div>
                              <div>
                                <el-icon
                                  size="22px"
                                  :color="`#e8604c`"
                                  style="padding-right: 5px"
                                  ><ChatSquare
                                /></el-icon>
                                {{ item.reply }}
                              </div>
                            </div>
                          </div>
                        </template>
                        <el-scrollbar max-height="100px">
                          <ul>
                            <li v-for="(i, index2) in item.son" :key="index2">
                              {{ i.userName }}：{{ i.content }}
                              {{ i.createTime }}
                            </li>
                          </ul>
                        </el-scrollbar>
                      </el-collapse-item>
                    </el-collapse>
                  </el-scrollbar>
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
/* 指定行程按钮样式 */
.make-plan-button {
  width: 100%;
  height: auto;
  display: flex;
  margin: 20px 0;
  justify-content: center;
}
.text-amber {
  font-size: 16px;
}
.comment-form__title {
  margin-bottom: 20px;
}
.comment-one {
  margin-top: 40px;
}
//样式穿透
::v-deep .el-collapse-item__header {
  display: flex;
  // flex-direction: column;
  justify-content: space-around;
  // flex-wrap: wrap;
  // height: 200px;
  // line-height: 200px;
  height: auto;
  padding: 10px 0;
}
.comment-container {
  .comment-header {
    height: 50px;
    width: 230px;
    // width: 100%;
    // border: 1px #e8604c solid;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    border-bottom: 1px #dcdfe6 solid;
    margin-bottom: 10px;
    > p {
      margin: 0;
      line-height: 1.5em;
    }
    > p:first-child {
      a {
        color: #e8604c;
        font-weight: 700;
      }
    }
    > p:nth-child(2) {
      font-size: 12px;
      color: #909399;
    }
  }
  .comment-body {
    // border: 1px #e8604c solid;
    height: 60px;
    width: 230px;
    // display: flex;
    // justify-content: start;
    // align-items: center;
    .content-main {
      line-height: 1.5em;
      color: #606266;
    }
  }
  .function-button-container {
    margin-top: 10px;
    height: 30px;
    width: 230px;
    // border: 1px #e8604c solid;
    display: flex;
    align-items: center;
    > div {
      width: 80px;
      height: 30px;
      // border: 1px #e8604c solid;
      display: flex;
      align-items: center;
      // justify-content: center;
      color: #606266;
      font-size: 16px;
    }
    > div:first-child {
      // width: auto;
      // height: 1.2em;
      // border: 1px #e8604c solid;
    }
  }
}
.plan-note-header {
  width: 100%;
  height: auto;
  // border: 1px #e8604c solid;
  display: flex;
  align-items: center;
}
</style>
