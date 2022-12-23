<script setup lang="ts">
import { ref } from "vue";
import { getStarNotesInfoByUserId } from "@/apis/travelService/note";
import { mainStore } from "@/store/user";
import { theNotesInfoType } from "@/apis/interface/myInterface";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
const store = mainStore();
const starNotesInfo = ref([] as theNotesInfoType[]);
const getStarNotesInfo = async () => {
  await getStarNotesInfoByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        starNotesInfo.value = res.data;
        console.log(starNotesInfo.value);
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
getStarNotesInfo();
</script>
<script lang="ts">
window.onbeforeunload = function () {
  var scrollPos;
  if (typeof window.pageYOffset != "undefined") {
    scrollPos = window.pageYOffset;
  } else if (
    typeof document.compatMode != "undefined" &&
    document.compatMode != "BackCompat"
  ) {
    scrollPos = document.documentElement.scrollTop;
  } else if (typeof document.body != "undefined") {
    scrollPos = document.body.scrollTop;
  }
  document.cookie = "scrollTop=" + scrollPos; //存储滚动条位置到cookies中
};

window.onload = function () {
  if (document.cookie.match(/scrollTop=([^;]+)(;|$)/) != null) {
    var arr = document.cookie.match(/scrollTop=([^;]+)(;|$)/); //cookies中不为空，则读取滚动条位置
    // @ts-ignore
    document.documentElement.scrollTop = parseInt(arr[1]);
    // @ts-ignore
    document.body.scrollTop = parseInt(arr[1]);
  }
};
</script>
<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>我收藏的游记</span>
        <el-button class="button" text>发游记</el-button>
      </div>
    </template>
    <el-scrollbar max-height="600px">
      <div
        class="card-item-container"
        v-for="(item, index) in starNotesInfo"
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
