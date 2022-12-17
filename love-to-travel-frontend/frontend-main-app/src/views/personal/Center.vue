<script setup lang="ts">
import { ref } from "vue";
import { getNotesInfoByUserId } from "@/apis/travelService/note";
import { mainStore } from "@/store/user";
import { theNotesInfoType } from "@/apis/interface/myInterface";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
import emitter from "@/mitt/event";
const toAddFoot = () => {
  emitter.emit("addFoot");
};
const store = mainStore();
const userNotesInfo = ref([] as theNotesInfoType[]);
const getNotesInfo = async () => {
  await getNotesInfoByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        userNotesInfo.value = res.data;
        console.log(userNotesInfo.value);
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
getNotesInfo();
</script>

<template>
  <ul class="main-button">
    <li class="tour-types__single wow fadeInUp" data-wow-delay="100ms">
      <div class="tour-types__content">
        <div class="tour-types__icon">
          <el-icon><EditPen /></el-icon>
        </div>
        <h4 class="tour-types__title">写游记</h4>
      </div>
    </li>
    <li class="tour-types__single wow fadeInUp" data-wow-delay="200ms">
      <router-link to="/"
        ><div class="tour-types__content">
          <div class="tour-types__icon">
            <el-icon><ChatDotRound /></el-icon>
          </div>
          <h4 class="tour-types__title">问答</h4>
        </div>
      </router-link>
    </li>
    <li
      class="tour-types__single wow fadeInUp"
      data-wow-delay="300ms"
      @click="toAddFoot"
    >
      <div class="tour-types__content">
        <div class="tour-types__icon">
          <el-icon><MapLocation /></el-icon>
        </div>
        <h4 class="tour-types__title">添加足迹</h4>
      </div>
    </li>
    <li class="tour-types__single wow fadeInUp" data-wow-delay="400ms">
      <div class="tour-types__content">
        <div class="tour-types__icon">
          <el-icon><User /></el-icon>
        </div>
        <h4 class="tour-types__title">找队友</h4>
      </div>
    </li>
  </ul>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>我发布的游记</span>
        <el-button class="button" text>发游记</el-button>
      </div>
    </template>
    <el-scrollbar max-height="420px">
      <div
        class="card-item-container"
        v-for="(item, index) in userNotesInfo"
        :key="index"
      >
        <div class="item-img">
          <img :src="item.url" />
        </div>
        <div class="item-content">
          <div class="content-left">
            <div>
              <router-link :to="`/readTravel/note/detail/${item.id}`">{{
                item.title
              }}</router-link>
            </div>
            <div><span class="span-style">相关城市:</span>{{ item.city }}</div>
            <div>
              <span class="span-style">发表时间:</span
              >{{ timeFormat(item.createTime) }}
            </div>
          </div>
          <div class="content-right">
            <div>
              <el-icon size="16px"><View /></el-icon>浏览:{{
                numberFormat(item.view)
              }}
              &nbsp;&nbsp; <el-icon size="16px"><Pointer /></el-icon>点赞:{{
                numberFormat(item.like)
              }}&nbsp;&nbsp; <el-icon size="16px"><Star /></el-icon>收藏:{{
                numberFormat(item.star)
              }}&nbsp;&nbsp; <el-icon size="16px"><Document /></el-icon>评论:{{
                numberFormat(item.comment)
              }}
            </div>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </el-card>
</template>

<style lang="scss" scoped>
.main-button {
  width: 100%;
  margin: 0 auto;
  // border: 1px #e8604c solid;
  height: 210px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: 20px;
  .tour-types__single {
    margin-bottom: 0;
    cursor: pointer;
  }
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.box-card {
  width: 800px;
  margin-left: 25px;
  margin-top: 30px;
  .card-item-container {
    width: 700px;
    height: auto;
    // border: 1px #e8604c solid;
    border: 1px #dcdfe6 solid;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    margin-bottom: 25px;
    .item-img {
      height: 300px;
      width: 300px;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .item-content {
      width: 400px;
      height: 300px;
      display: flex;
      justify-content: space-around;
      flex-direction: column;
      .content-left {
        width: 400px;
        height: 170px;
        // border: 1px #e8604c solid;
        padding-top: 10px;
        .span-style {
          color: #ffffff;
          background-color: #e8604c;
          border-radius: 5px;
          padding: 5px;
          margin-right: 5px;
        }
        > div {
          height: 50px;
          width: 400px;
          padding-left: 30px;
        }
        > div:first-child {
          font-size: 22px;
          font-weight: 700;
          height: auto;
        }
        > div:nth-child(2) {
          margin-top: 15px;
          font-size: 16px;
        }
        > div:nth-child(3) {
          font-size: 16px;
        }
      }
      .content-right {
        width: 400px;
        height: 100px;
        // border: 1px #e8604c solid;
        display: flex;
        flex-direction: column;
        justify-content: end;
        div {
          text-align: right;
          // border: 1px #e8604c solid;
          font-size: 16px;
          display: flex;
          align-items: center;
          justify-content: end;
          padding-right: 10px;
        }
      }
    }
  }
}
</style>
