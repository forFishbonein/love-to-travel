<template>
  <el-container class="layout-container-demo">
    <el-aside class="aside-container">
      <el-scrollbar>
        <div class="aside-title">lOVE TO TRAVEL<br/>Admin</div>
        <el-menu
            :default-openeds="['1', '3']"
            class="el-menu-vertical-demo"
            default-active="2">
          <el-menu-item index="1">
            <el-icon>
              <House/>
            </el-icon>
            <template #title>
              <router-link to="/home">首页</router-link>
            </template>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title>
              <el-icon>
                <ElementPlus/>
              </el-icon>
              <span>业务管理板块</span>
            </template>
            <el-menu-item-group>
              <template #title><span>旅游业务</span></template>
              <el-menu-item index="2-1"
              >
                <router-link to="/province">省份</router-link>
              </el-menu-item
              >
              <el-menu-item index="2-2"
              >
                <router-link to="/city">城市</router-link>
              </el-menu-item
              >
              <el-menu-item index="2-3">
                <router-link to="/scenery">景区</router-link>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="游记业务">
              <el-menu-item index="2-4">
                <router-link to="/note">游记</router-link>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="产品业务">
              <el-menu-item index="2-5">
                <router-link to="/product">产品</router-link>
              </el-menu-item>
              <el-menu-item index="2-6">
                <router-link to="/order">订单</router-link>
              </el-menu-item>
            </el-menu-item-group>
            <!-- <el-sub-menu index="2-5">
              <template #title><span>item four</span></template>
              <el-menu-item index="2-5-1">item one</el-menu-item>
            </el-sub-menu> -->
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
              <el-icon>
                <UserFilled/>
              </el-icon>
              <span>用户管理板块</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="3-1"
              >
                <router-link to="/user">用户</router-link>
              </el-menu-item
              >
              <el-menu-item index="3-2"
              >
                <router-link to="/log">登录日志</router-link>
              </el-menu-item>
              <el-menu-item index="3-3">
                <router-link to="/admin">管理员</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="4">
            <template #title>
              <el-icon>
                <Operation/>
              </el-icon>
              <span>统计分析板块</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="4-1">
                <router-link to="/rank">游记排行</router-link>
              </el-menu-item>
              <el-menu-item index="4-2">
                <router-link to="/sales">销售统计</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px"
            >
              <setting
              />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>View</el-dropdown-item>
                <el-dropdown-item>Add</el-dropdown-item>
                <el-dropdown-item>Delete</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span>Tom</span>
        </div>
      </el-header>

      <el-main>
        <el-scrollbar>
          <div class="router-container">
            <router-view></router-view>
          </div>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>
<script lang="ts" setup>
import {ref} from "vue";

const isCollapse = ref(true);
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
// export default {
//   data() {
//     return {};
//   },
//   methods: {
//     name() {},
//   },
//   components: {
//     NavSearch,
//   },
// };
</script>
<style lang="scss" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 100vh;
}

/* 右上header */
.layout-container-demo .el-header {
  position: relative;
  background-color: #ffffff;
  //   background-color: #c6e2ff;
  color: var(--el-text-color-primary);
  border-bottom: #c6e2ff 1px solid;
}

/* 左边aside */
.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: #ffffff;
  border-right: #c6e2ff 1px solid;
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.aside-title {
  padding: 0 10px;
  //   float: left;
  //   display: flex;
  text-align: center;
  width: auto;
  //   width: 200px;
  height: 59px;
  //   border: 1px #e8604c solid;
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  color: #337ecc;
  font-weight: 600;
  //   background-color: #c6e2ff;
  background-color: #ffffff;
  border-bottom: #c6e2ff 1px solid;
  position: relative;
}

.aside-button {
  margin: 0 5px;
}

.aside-container {
  //   display: flex !important;
  //   min-width: 200px !important;
  float: left;
  width: auto;
  clear: both;
}

.router-container {
  //   border-radius: 10px;
  font-family: '方正字汇-明涵黑 简';
  margin: 10px;
  width: auto;
  min-height: 100vh;
  background: rgb(240, 240, 240);
  background: linear-gradient(90deg, rgba(240, 240, 240, 1) 0%, rgba(241, 241, 241, 1) 48%, rgba(255, 255, 255, 1) 100%);

  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
}
</style>
