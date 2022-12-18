<script setup lang="ts">
import { ref } from "vue";
import { getOneSceneryInfoById } from "@/apis/travelService/scenery";
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
    console.log("============");
    console.log(commentParams);
    console.log("============");
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
                allow-half
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
                <li>
                  <div class="tour-details-two__last-minute-image">
                    <img src="/images/resources/td-img-1.jpg" alt="" />
                  </div>
                  <div class="tour-details-two__last-minute-content">
                    <h6>$380</h6>
                    <h5>Africa 2 Days Tour</h5>
                    <p>Los Angeles</p>
                  </div>
                </li>
                <li>
                  <div class="tour-details-two__last-minute-image">
                    <img src="/images/resources/td-img-2.jpg" alt="" />
                  </div>
                  <div class="tour-details-two__last-minute-content">
                    <h6>$380</h6>
                    <h5>Africa 2 Days Tour</h5>
                    <p>Los Angeles</p>
                  </div>
                </li>
                <li>
                  <div class="tour-details-two__last-minute-image">
                    <img src="/images/resources/td-img-3.jpg" alt="" />
                  </div>
                  <div class="tour-details-two__last-minute-content">
                    <h6>$380</h6>
                    <h5>Africa 2 Days Tour</h5>
                    <p>Los Angeles</p>
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
              <h3 class="sidebar__title">景区评分 & 评论</h3>
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
                          <p>评分:{{ item.score }}</p>
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
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style lang="scss" scoped>
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
</style>
