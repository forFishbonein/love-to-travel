<script setup lang="ts">
import { ref } from "vue";
import type { TabsPaneContext } from "element-plus";
import { mainStore } from "@/store/user";
import { UserInfo } from "@/apis/userService/uinterface";
import {
  getUserFollowersInfo,
  getUserFolloweesInfo,
} from "@/apis/userService/follow";
const store = mainStore();
const followeesUserInfo = ref([] as UserInfo[]); //关注
const followersUserInfo = ref([] as UserInfo[]); //粉丝
/* 关注和粉丝信息 */
const getFollowInfo = () => {
  getUserFolloweesInfo(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        followeesUserInfo.value = res.data;
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  getUserFollowersInfo(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        followersUserInfo.value = res.data;
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
getFollowInfo();
const activeName = ref("first");

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event);
};
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
  <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
    <el-tab-pane label="我的关注" name="first">
      <div class="warp">
        <div class="user-card" v-for="item in followeesUserInfo">
          <div class="card-left">
            <div class="img-con">
              <img :src="item.url" />
            </div>
          </div>
          <div class="card-right">
            <p>
              <router-link :to="`/user/${item.id}`">{{
                item.name
              }}</router-link>
            </p>
            <p>{{ item.signature || "这个人很懒，什么都没有留下~" }}</p>
          </div>
        </div>
      </div>
    </el-tab-pane>
    <el-tab-pane label="我的粉丝" name="second">
      <div class="warp">
        <div class="user-card" v-for="item in followersUserInfo">
          <div class="card-left">
            <div class="img-con">
              <img :src="item.url" />
            </div>
          </div>
          <div class="card-right">
            <p>
              <router-link :to="`/user/${item.id}`">{{
                item.name
              }}</router-link>
            </p>
            <p>{{ item.signature || "这个人很懒，什么都没有留下~" }}</p>
          </div>
        </div>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<style lang="scss" scoped>
.el-tabs--top {
  padding-left: 10px;
  padding-right: 10px;
}
.warp {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: start;
  flex-wrap: wrap;
  padding: 20px;
  padding-top: 0;
  padding-bottom: 30px;
  .user-card {
    width: 250px;
    height: 100px;
    // border: 1px #e8604c solid;
    background-color: #ffffff;
    box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
    border-radius: 10px;
    padding-left: 10px;
    margin-top: 20px;
    margin-right: 20px;
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
</style>
