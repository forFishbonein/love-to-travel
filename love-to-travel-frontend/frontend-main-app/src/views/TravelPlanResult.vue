<script setup lang="ts">
import { ref, reactive } from "vue";
const dialogFormVisible = ref(false);
const formLabelWidth = "140px";
const city = ref("");
const citys = ref([
  { id: 1, name: "北京" },
  { id: 2, name: "上海" },
  { id: 3, name: "杭州" },
  { id: 4, name: "广州" },
  { id: 5, name: "重庆" },
]);
const openCityDialog = () => {
  dialogFormVisible.value = true;
};
const addACity = () => {
  citys.value.push({ id: 0, name: city.value });
  dialogFormVisible.value = false;
};
const addBefore = (index: any) => {
  console.log("----------");
  console.log(index);
  console.log("----------");
  citys.value.splice(index, 0, { id: 0, name: "天津" });
};
const addAfter = (index: any) => {
  console.log("----------");
  console.log(index);
  console.log("----------");
  citys.value.splice(index + 1, 0, { id: 0, name: "天津" });
};
const deleteCity = (index: any) => {
  citys.value.splice(index, 1);
};
const activeIndex = ref("/result/route/list");
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>

<template>
  <div class="base-header">header</div>
  <div class="base-container">
    <div class="body-left">
      <div class="left-header">武汉——>上海</div>
      <div class="left-body">
        <el-scrollbar max-height="400px">
          <div
            v-for="(item, index) in citys"
            :key="item.id"
            class="left-scrollbar-item"
          >
            <div class="item-left">
              <el-icon :size="35"><LocationInformation /></el-icon>
              <div class="item-left-text">
                <p>12月6日</p>
                <p>{{ item.name }}</p>
              </div>
            </div>
            <div class="item-right">
              <el-dropdown trigger="click">
                <el-button type="primary">
                  <el-icon size="15"><arrow-down /></el-icon>
                </el-button>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="addBefore(index)">
                      <el-icon><Plus /></el-icon> 之前添加一个城市
                    </el-dropdown-item>
                    <el-dropdown-item @click="addAfter(index)">
                      <el-icon><Plus /></el-icon> 之后添加一个城市
                    </el-dropdown-item>
                    <el-dropdown-item @click="deleteCity(index)">
                      <el-icon><DeleteFilled /></el-icon> 删除该城市
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
          <div class="add-city" @click="openCityDialog">+ 添加城市</div>
        </el-scrollbar>
      </div>
      <div class="left-footer">上海——>武汉</div>
    </div>
    <div class="body-middle">
      <div class="middle-header">
        <p>12月6日</p>
        <p>
          <span>上海</span>
          <span> 3000元预算 </span>
        </p>
      </div>
      <div class="middle-body">
        <el-empty description="该城市还未安排行程" :image-size="150" />
      </div>
    </div>
    <div class="body-right">
      <div class="right-header">
        <span>上海</span>
        <div class="input-box">
          <input type="text" class="search-input" />
          <span class="search-span">
            <el-icon><Search /></el-icon>
          </span>
        </div>
      </div>
      <div class="right-nav">
        <el-menu
          :default-active="activeIndex"
          class="el-menu"
          mode="horizontal"
          @select="handleSelect"
          router="true"
        >
          <!-- 前面加/就是覆盖路径，不加就是在后面添加路径 -->
          <el-menu-item index="/result/route/list">路线</el-menu-item>
          <el-menu-item index="/result/scenicSpot/list">游玩</el-menu-item>
        </el-menu>
      </div>
      <div class="right-body">
        <router-view></router-view>
      </div>
    </div>
  </div>
  <el-dialog v-model="dialogFormVisible" title="添加一个城市">
    <el-form :model="city">
      <el-form-item label="热门城市" :label-width="formLabelWidth">
        <el-select v-model="city" placeholder="选择一个城市">
          <el-option label="上海" value="上海" />
          <el-option label="北京" value="北京" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addACity"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.base-header {
  width: 100%;
  height: 10%;
  // border: 1px #e8604c solid;
  background-color: #ffffff;
  border-bottom: 2px #e8604c solid;
  // box-shadow: 0px 20px 10px rgba(0, 0, 0, 0.9);
}
.base-container {
  width: 100%;
  height: 90%;
  // border: 1px #e8604c solid;
  position: relative;
  background-color: #f2f3f5;
  .body-left {
    width: 250px;
    height: 450px;
    position: absolute;
    // border: 1px #e8604c solid;
    left: 20px;
    top: 30px;
    border-radius: 5px;
    background-color: #ffffff;
    box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
    .left-header {
      width: auto;
      height: 12%;
      border-bottom: 2px #e8604c solid;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .left-footer {
      width: auto;
      height: 12%;
      // border: 1px #e8604c solid;
      border-top: 2px #e8604c solid;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .left-body {
      width: auto;
      height: 76%;
      // border: 1px #e8604c solid;
      .left-scrollbar-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 80px;
        // border: 1px #e8604c solid;
        background: rgba(255, 255, 255);
        cursor: pointer;
        transition: all 0.3s linear;
        padding-right: 15px;
        padding-left: 10px;
        border-bottom: 1px #dcdfe6 solid;
        .item-left {
          width: 100px;
          height: 80px;
          display: flex;
          justify-content: space-around;
          align-items: center;
          i {
            color: #303133;
          }
          .item-left-text {
            p {
              font-size: 15px;
              margin: 0;
              line-height: 1.4em;
            }
          }
        }
        .item-right {
          button {
            width: 30px;
            height: 30px;
            span {
              padding: 0 2px;
            }
          }
        }
      }
      .left-scrollbar-item:hover {
        background: rgba(245, 245, 245);
      }
      .add-city {
        width: auto;
        height: 10%;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        background: rgba(255, 255, 255);
        transition: all 0.3s linear;
        background-color: #f2f6fc;
      }
      .add-city:hover {
        background: rgba(245, 245, 245);
      }
    }
  }
  .body-middle {
    width: 480px;
    height: 400px;
    position: absolute;
    // border: 1px #e8604c solid;
    left: 300px;
    top: 30px;
    border-radius: 5px;
    background-color: #ffffff;
    box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
    .middle-header {
      width: auto;
      height: 80px;
      padding-left: 20px;
      // border: 1px #e8604c solid;
      border-bottom: 1px #dcdfe6 solid;
      padding-top: 10px;
      p {
        margin: 0;
        line-height: 30px;
      }
      > p:first-child {
        font-size: 15px;
      }
      > p:nth-child(2) {
        > span:first-child {
          font-size: 20px;
          color: #303133;
          font-weight: 700;
          margin-right: 30px;
        }
        > span:nth-child(2) {
          font-size: 15px;
          color: #606266;
          font-weight: 700;
          margin-right: 30px;
        }
      }
    }
  }
  .body-right {
    width: 450px;
    height: 470px;
    position: absolute;
    // border: 1px #e8604c solid;
    box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
    left: 810px;
    top: 30px;
    background-color: #ffffff;
    border-radius: 5px;
    .right-header {
      width: auto;
      height: 50px;
      border-bottom: 1px #dcdfe6 solid;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 20px;
      > span:first-child {
        color: #303133;
        font-size: 16px;
      }
      .input-box {
        position: relative;
        display: inline-block;
        .search-input {
          padding: 0 40px 0 20px;
          width: 160px;
          height: 38px;
          font-size: 14px;
          border: 1px solid #eee;
          border-radius: 40px;
          background: #eee;
          transition: width 0.5s;
          transition-delay: 0.1s;
        }
        .search-input:focus {
          width: 350px;
          outline: none;
          box-shadow: none;
        }
        .search-input:focus + .search-span {
          background-color: #f48f80;
          color: #fff;
        }
        .search-span {
          position: absolute;
          top: 4px;
          right: 5px;
          width: 30px;
          height: 30px;
          line-height: 30px;
          padding: 0;
          color: #d4d7de;
          text-align: center;
          background: #e8604c;
          border-radius: 50%;
          font-size: 15px;
          cursor: pointer;
          i {
            height: 30px; /* 让图标居中的方案 */
          }
        }
      }
    }
    .right-nav {
      // border: 1px #e8604c solid;
      // padding-left: 150px;
      .el-menu {
        > li:first-child {
          margin-left: 90px;
        }
        li {
          padding: 0 50px;
        }
      }
    }
    .right-body {
      width: auto;
      height: 350px;
      // border: 1px #e8604c solid;
    }
  }
}
</style>
