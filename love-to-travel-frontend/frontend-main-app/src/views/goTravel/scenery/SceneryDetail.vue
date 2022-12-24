<script setup lang="ts">
import { ref } from "vue";
import {
  getOneSceneryInfoById,
  getRecommondUsersByUserId,
  getRecommondNotesBySceneryId,
} from "@/apis/travelService/scenery";
import { theCityScenerysInfoType } from "@/apis/interface/myInterface";
import { keepTwoDecimal } from "@/utils/filters/number";
import { mainStore } from "@/store/user";
import { sceneryCommentParams } from "@/apis/travelService/tInterface";
import {
  sceneryCommentBody,
  sceneryCommentsInfoType,
} from "@/apis/interface/myInterface";
import { getNowTime } from "@/utils/getNowTime";
import {
  addASceneryComment,
  getCommentsBySceneryId,
} from "@/apis/travelService/comment";
import { UserInfo } from "@/apis/userService/uInterface";
import { theNotesInfoType } from "@/apis/interface/myInterface";
import { realtimeStore } from "@/store/realTime";
const tstore = realtimeStore();
const store = mainStore();
const props = defineProps<{
  sceneryId: string;
}>();
// alert(props.sceneryId);
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
        // alert("得到数据了！");
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

const sceneryCommentsInfo = ref({} as sceneryCommentsInfoType);
const requestSceneryCommentsInfo = async () => {
  await getCommentsBySceneryId(sceneryId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        sceneryCommentsInfo.value = res.data;
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
requestSceneryCommentsInfo();
const commentContent = ref("");
const sceneryScore = ref(4.5);
const addSceneryCommentAndScore = async () => {
  // alert(pId);
  if (store.userInfo.id) {
    const commentParams: sceneryCommentParams = {
      sceneryId: sceneryId,
      content: commentContent.value,
      userId: store.userInfo.id,
      score: sceneryScore.value,
      createTime: getNowTime(),
    };
    // console.log("============");
    // console.log(commentParams);
    // console.log("============");
    await addASceneryComment(commentParams)
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
            message: "评论成功",
          });
          setTimeout(
            () =>
              //更新评论
              getCommentsBySceneryId(sceneryId)
                .then((res: any) => {
                  if (res.code != 0) {
                    //@ts-ignore
                    ElMessage({
                      type: "error",
                      message: res.msg,
                    });
                  } else {
                    sceneryCommentsInfo.value = res.data;
                  }
                })
                .catch((error) => {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: error.message,
                  });
                }),
            1000
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
    //@ts-ignore
    ElMessage({
      type: "warning",
      message: "请先登录！",
    });
  }
};
const theRecommondUsers = ref([] as UserInfo[]);
let userLoginFlag = ref(false);
const getRecommondUsers = () => {
  if (store.userInfo.id) {
    getRecommondUsersByUserId(store.userInfo.id)
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
            theRecommondUsers.value = res.data;
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
getRecommondUsers();
let notesRelatedInfo = ref([] as theNotesInfoType[]);
const requestRelatedNotes = () => {
  getRecommondNotesBySceneryId(sceneryId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        // ElMessage({
        //   type: "error",
        //   message: res.msg,
        // });
        // userLoginFlag.value = false;
      } else {
        // console.log(res);
        // alert("获取成功");
        if (res.data.length !== 0) {
          notesRelatedInfo.value = res.data.slice(0, 3);
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
};
requestRelatedNotes();

const initBrowseList = () => {
  if (tstore.browseList.length < 3) {
    if (tstore.browseList.indexOf(sceneryId) === -1) {
      tstore.browseList.push(sceneryId);
    }
  } else {
    // let bowerInfo = "";
    // tstore.browseList.forEach((e) => {
    //   bowerInfo = bowerInfo + e + ",";
    // });
    tstore.getRealTimeFlag = true; //不管怎么样都要变成true，即使请求还没有成功返回，因为比较慢
    tstore
      .getRealTimeRecommendSceneryFromPy(
        tstore.browseList[0],
        tstore.browseList[1],
        tstore.browseList[2]
      ) // 获得推荐景区
      .then((res) => {
        // alert("得到了");
        console.log("实时推荐：");
        console.log(tstore.realTimeRecommendscenerys);
        tstore.getRealTimeFlag = true;
        // @ts-ignore
        ElMessage({
          type: "success",
          message: "爱宝儿，已为你实时推荐景区~",
        });
      })
      .catch(() => {
        tstore.getRealTimeFlag = true; //即使失败了也要改成true
        tstore.realTimeRecommendscenerys = [] as theCityScenerysInfoType[];
      });
  }
};
initBrowseList();
</script>
<script lang="ts">
(function ($) {
  $(document).ready(function () {
    if ($(".accrodion-grp").length) {
      var accrodionGrp = $(".accrodion-grp");
      accrodionGrp.each(function () {
        // @ts-ignore
        var accrodionName = $(this).data("grp-name");
        // @ts-ignore
        var Self = $(this);
        var accordion = Self.find(".accrodion");
        Self.addClass(accrodionName);
        Self.find(".accrodion .accrodion-content").hide();
        Self.find(".accrodion.active").find(".accrodion-content").show();
        accordion.each(function () {
          // @ts-ignore
          $(this)
            .find(".accrodion-title")
            .on("click", function () {
              // @ts-ignore
              if ($(this).parent().hasClass("active") === false) {
                $(".accrodion-grp." + accrodionName)
                  .find(".accrodion")
                  .removeClass("active");
                $(".accrodion-grp." + accrodionName)
                  .find(".accrodion")
                  .find(".accrodion-content")
                  .slideUp();
                // @ts-ignore
                $(this).parent().addClass("active");
                // @ts-ignore
                $(this).parent().find(".accrodion-content").slideDown();
              }
            });
        });
      });
    }
  });
  //@ts-ignore
})(jQuery);
</script>
<template>
  <section class="destinations-details">
    <div class="container">
      <div class="row">
        <div class="col-xl-8 col-lg-7">
          <div class="destinations-details__left">
            <div class="destinations-details__discover">
              <h3 class="destinations-details__title">
                {{ sceneryInfo.name }}
              </h3>
              <p class="destinations-details__discover-text-1">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
                  sceneryInfo.introduction
                }}
              </p>
            </div>
            <div class="destinations-details__overview">
              <h3 class="destinations-details__title">详情信息</h3>
              <ul class="list-unstyled destinations-details__overview-list">
                <li>
                  <div class="destinations-details__overview-left">
                    <p>景区评分</p>
                  </div>
                  <div class="destinations-details__overview-right">
                    <p>{{ sceneryInfo.score || "-" }}</p>
                  </div>
                </li>
                <li>
                  <div class="destinations-details__overview-left">
                    <p>景区票价</p>
                  </div>
                  <div class="destinations-details__overview-right">
                    <p>¥{{ sceneryInfo.ticket }}</p>
                  </div>
                </li>
                <li>
                  <div class="destinations-details__overview-left">
                    <p>景区地址</p>
                  </div>
                  <div class="destinations-details__overview-right">
                    <p>{{ sceneryInfo.address || "-" }}</p>
                  </div>
                </li>
                <li>
                  <div class="destinations-details__overview-left">
                    <p>景区电话</p>
                  </div>
                  <div class="destinations-details__overview-right">
                    <p>{{ sceneryInfo.tele || "-" }}</p>
                  </div>
                </li>
                <li>
                  <div class="destinations-details__overview-left">
                    <p>景区经纬度</p>
                  </div>
                  <div class="destinations-details__overview-right">
                    <p>
                      <el-icon><CaretRight /></el-icon>东经{{
                        keepTwoDecimal(sceneryInfo.lng)
                      }}<el-icon><CaretTop /></el-icon>北纬{{
                        keepTwoDecimal(sceneryInfo.lat)
                      }}
                    </p>
                  </div>
                </li>
              </ul>
            </div>
            <div class="destinations-details__faq">
              <div class="accrodion-grp" data-grp-name="faq-one-accrodion">
                <div class="accrodion active">
                  <div class="accrodion-title">
                    <h4>该景区什么时间开放？</h4>
                  </div>
                  <div class="accrodion-content">
                    <div class="inner">
                      <p>
                        {{ sceneryInfo.opening }}
                      </p>
                    </div>
                    <!-- /.inner -->
                  </div>
                </div>
                <div class="accrodion">
                  <div class="accrodion-title">
                    <h4>为什么推荐这个景区？</h4>
                  </div>
                  <div class="accrodion-content">
                    <div class="inner">
                      <p>
                        {{ sceneryInfo.season }}
                      </p>
                    </div>
                    <!-- /.inner -->
                  </div>
                </div>
                <div class="accrodion last-chiled">
                  <div class="accrodion-title">
                    <h4>该景区需要注意的内容有哪些？</h4>
                  </div>
                  <div class="accrodion-content">
                    <div class="inner">
                      <p>
                        {{ sceneryInfo.tips }}
                      </p>
                    </div>
                    <!-- /.inner -->
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="comment-form">
            <h3 class="comment-form__title">填写评分 & 发表评论</h3>
            <div class="rate-row">
              <span class="span-style-front">评分：</span>
              <el-rate
                v-model="sceneryScore"
                :texts="['1分', '2分', '3分', '4分', '5分']"
                show-text
                size="large"
              />
            </div>
            <div class="row">
              <div class="col-xl-12">
                <div class="comment-form__input-box">
                  <textarea
                    name="message"
                    placeholder="写下评论内容"
                    v-model="commentContent"
                    style="font-size: 16px"
                  ></textarea>
                  <button
                    type="submit"
                    class="thm-btn comment-form__btn"
                    @click="addSceneryCommentAndScore"
                  >
                    确认
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xl-4 col-lg-5">
          <div class="destinations-details__right">
            <div
              class="tour-details-two__last-minute tour-details-two__last-minute-2"
            >
              <h3 class="tour-details-two__sidebar-title">相关游记</h3>
              <ul class="tour-details-two__last-minute-list list-unstyled">
                <li v-for="item in notesRelatedInfo">
                  <div class="tour-details-two__last-minute-image">
                    <img :src="item.url" alt="" />
                  </div>
                  <div class="tour-details-two__last-minute-content">
                    <el-scrollbar max-height="74px">
                      <span class="one-span"
                        ><span class="span-style">点赞量:</span
                        >{{ item.like }}</span
                      >
                      <span class="two-span"
                        ><router-link
                          :to="`/readTravel/note/detail/${item.id}`"
                          >{{ item.title }}</router-link
                        ></span
                      >
                      <span class="three-span"
                        ><span class="span-style">作者:</span
                        ><router-link :to="`/user/${item.userId}`">{{
                          item.userName
                        }}</router-link>
                      </span>
                    </el-scrollbar>
                  </div>
                </li>
              </ul>
            </div>
            <div class="destinations-details__discount">
              <img :src="sceneryInfo.url" alt="" />
              <div class="destinations-details__discount-content">
                <h3 style="color: #e8604c; font-size: 22px">
                  <el-icon :color="`#e8604c`"><Star /></el-icon
                  >{{ sceneryInfo.score }}
                </h3>
                <h4 style="font-size: 18px">
                  {{ sceneryInfo.name }}<br />
                  ¥{{ sceneryInfo.ticket }}
                </h4>
              </div>
            </div>
            <div class="sidebar__single sidebar__category">
              <h3 class="sidebar__title">
                景区评分 & 评论
                <span class="span-style-front3"
                  >总评论数:&nbsp;{{ sceneryCommentsInfo.total }}</span
                >
              </h3>
              <ul class="sidebar__category-list list-unstyled">
                <li>
                  <el-scrollbar max-height="800px" style="padding-right: 10px">
                    <div
                      :name="index"
                      v-for="(
                        item, index
                      ) in sceneryCommentsInfo?.sceneryCommentList"
                      :key="index"
                    >
                      <div class="comment-container">
                        <div class="comment-header">
                          <p>
                            <router-link :to="`/user/${item.userId}`">{{
                              item.userName
                            }}</router-link>
                          </p>
                          <p>
                            {{ item.createTime }}
                          </p>
                          <p>
                            <span class="span-style-front2"
                              >评分:&nbsp;{{ item.score }}</span
                            >
                          </p>
                        </div>
                        <div class="comment-body">
                          <el-scrollbar max-height="40px">
                            <div class="content-main">
                              {{ item.content }}
                            </div>
                          </el-scrollbar>
                        </div>
                      </div>
                    </div>
                  </el-scrollbar>
                </li>
              </ul>
            </div>
            <div class="sidebar__single sidebar__category" v-if="userLoginFlag">
              <h3 class="sidebar__title">
                驴友圈
                <span class="span-style-front4">活跃用户</span>
              </h3>
              <ul class="sidebar__category-list list-unstyled">
                <li>
                  <el-scrollbar max-height="400px">
                    <div class="warp">
                      <div class="user-card" v-for="item in theRecommondUsers">
                        <div class="card-left">
                          <div class="img-con">
                            <img :src="item.url" />
                          </div>
                        </div>
                        <div class="card-right">
                          <p style="padding-left: 10px">
                            <router-link
                              :to="`/user/${item.id}`"
                              style="font-size: 16px"
                              >{{ item.name }}</router-link
                            >
                          </p>
                          <p style="padding: 0 10px; margin-top: 5px">
                            {{
                              item.signature || "这个人很懒，什么都没有留下~"
                            }}
                          </p>
                        </div>
                      </div>
                    </div>
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
.destinations-details {
  padding-top: 70px;
}
.comment-form {
  margin-top: 80px;
}
.rate-row {
  height: 100px;
  display: flex;
  align-items: center;
  // border: 1px #e8604c solid;
  margin-bottom: 20px;
  background-color: #faf5ee;
  padding-left: 20px;
  border-radius: 10px;
}
.span-style-front {
  display: inline-block;
  min-width: 50px;
  padding: 5px;
  padding-left: 10px;
  height: 30px;
  line-height: 20px;
  color: #ffffff;
  font-size: 14px;
  font-weight: 600;
  background-color: #e8604c;
  border-radius: 5px;
  margin-right: 10px;
}
.span-style-front2 {
  display: inline-block;
  min-width: 65px;
  padding-top: 2px;
  padding-left: 5px;
  height: 25px;
  line-height: 20px;
  color: #ffffff;
  font-size: 14px;
  font-weight: 600;
  background-color: #e8604c;
  border-radius: 5px;
  margin-right: 10px;
}
.sidebar__title {
  display: flex;
  align-items: center;
}
.span-style-front3 {
  display: inline-block;
  min-width: 95px;
  padding-top: 2px;
  padding-left: 5px;
  height: 25px;
  line-height: 20px;
  color: #ffffff;
  font-size: 14px;
  font-weight: 600;
  background-color: #e8604c;
  border-radius: 5px;
  margin-left: 15px;
}
.span-style-front4 {
  display: inline-block;
  min-width: 65px;
  padding-top: 2px;
  padding-left: 5px;
  height: 25px;
  line-height: 20px;
  color: #ffffff;
  font-size: 14px;
  font-weight: 600;
  background-color: #e8604c;
  border-radius: 5px;
  margin-left: 15px;
}
.comment-container {
  .comment-header {
    height: 80px;
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
    > p:nth-child(3) {
      font-size: 14px;
      color: #909399;
      margin: 5px 0;
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
      font-size: 16px;
    }
  }
}
.sidebar__single {
  margin-top: 30px;
}
.warp {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  // padding: 20px;
  padding-top: 0;
  padding-left: 10px;
  padding-right: 20px;
  // padding-bottom: 30px;
  .user-card {
    width: 230px;
    height: 100px;
    // border: 1px #e8604c solid;
    background-color: #ffffff;
    box-shadow: rgba(0, 0, 0, 0.2) 1px 1px 8px 1px;
    border-radius: 10px;
    padding-left: 10px;
    margin-top: 10px;
    // margin-right: 20px;
    margin-bottom: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    .card-left {
      width: 80px;
      height: 100px;
      //   border: 1px #e8604c solid;
      float: left;
      display: flex;
      justify-content: center;
      align-items: center;
      .img-con {
        width: 80px;
        height: 80px;
        border-radius: 80px;
        img {
          width: 100%;
          height: 100%;
          border-radius: 80px;
        }
      }
    }
    .card-right {
      width: 150px;
      height: 100px;
      //   border: 1px #e8604c solid;
      float: right;
      display: flex;
      flex-direction: column;
      justify-content: center;
      padding: 10px 0;
      p {
        margin: 0;
        line-height: 1.5em;
      }
      > p:first-child {
        font-size: 20px;
        font-weight: 700;
        color: #e8604c;
      }
      > p:nth-child(2) {
        width: 100%;
        max-height: 70px;
        overflow: hidden;
        font-size: 14px;
        margin-top: 10px;
      }
    }
  }
}
.tour-details-two__last-minute-image {
  width: 60px;
  height: 65px;
}
// .destinations-details__overview-list {
//   li {
//     width: 268px;
//     height: 100px;
//   }
// }
.tour-details-two__last-minute-content {
  width: 188px;
  height: 74px;
  // overflow: scroll;
  display: flex;
  flex-direction: column;
  justify-content: center;
  .one-span {
    width: 188px;
    height: 24px;
    display: inline-block;
    font-size: 14px;
    line-height: 1.5em;
    margin: 0;
    display: flex;
    align-items: center;
    margin-bottom: 5px;
  }
  .two-span {
    width: 188px;
    min-height: 25px;
    display: inline-block;
    font-size: 14px;
    line-height: 1.5em;
    margin: 0;
    display: flex;
    align-items: center;
    margin-bottom: 5px;
  }
  .three-span {
    width: 188px;
    height: 25px;
    display: flex;
    align-items: center;
    display: inline-block;
    font-size: 14px;
    line-height: 1.5em;
    margin: 0;
    display: flex;
    align-items: center;
    margin-bottom: 5px;
  }
}
.span-style {
  display: inline-block;
  width: auto;
  max-width: 100px;
  overflow: hidden;
  height: 25px;
  color: #ffffff;
  background-color: #e8604c;
  border-radius: 5px;
  padding: 0 5px;
  line-height: 2em;
  margin-right: 5px !important;
  line-height: 25px;
}
.list-unstyled {
  li {
    display: flex;
    align-items: center;
    // padding: 0 20px;
  }
}
</style>
