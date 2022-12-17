<script setup lang="ts">
import { ref, reactive, toRefs,computed } from "vue";
import {
  teamInfoParams,
  joinTeamParams,
  theTeamParams,
} from "@/apis/travelService/tInterface";
import {theTeamInfoType} from "@/apis/interface/myInterface"
import { getPageTeamsInfo,isJoinTheTeam,joinOneTeam,leaveOneTeam } from "@/apis/travelService/team";
import { timeFormat } from "@/utils/filters/time";
import { mainStore } from "@/store/user";
import { getOneUserPlansInfoById } from "@/apis/travelService/plan";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import { finalAllCityPlansInfoType } from "@/apis/interface/myInterface";
const store = mainStore();
/* 分页获取数据 */
let teamsPageInfo = ref([] as theTeamInfoType[]);
const pageParams = reactive({
  total: 0,
  page: 1,
  limit: 5,
});
const { total, page, limit } = toRefs(pageParams);
const requestPageTeamsInfo = async () => {
  await getPageTeamsInfo(page.value, limit.value)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        // citysInfo.value = res.data.slice(0, 5);
        teamsPageInfo.value = res.data.records;
        total.value = res.data.total;
        console.log(teamsPageInfo);
        teamsPageInfo.value.forEach((e) => {
          // alert("加入");
          // @ts-ignore
          teamsJoinFlagList.value.push({
            id: e.id,
            joinFlag: false,
          });
        });
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
requestPageTeamsInfo();
// 回调函数1：每页记录数改变时调用，size：回调参数，表示当前选中的“每页条数”
const changePageSize = (size: number) => {
  limit.value = size; //将页面大小改变
  requestPageTeamsInfo(); //请求新的数据
};

// 回调函数2：改变页码时调用，page：回调参数，表示当前选中的“页码”
const changeCurrentPage = (p: number) => {
  page.value = p; //将当前页数改变
  requestPageTeamsInfo(); //请求新的数据
};

/* 加入，退出队伍逻辑 */
//存储每一个队伍的加入退出标志变量
const teamsJoinFlagList = ref([]);
/* 是否加入了的标志 */
const returnTheIsJoinFlag = computed(() => {
  return function (teamId: string, index: number) {
    teamisJoin(teamId);
    // @ts-ignore
    // alert(commentsLikeFlagList.value[index].likeFlag + "....");
    // @ts-ignore
    return teamsJoinFlagList.value[index]?.joinFlag;
  };
});
/* axios同步请求 */
const teamisJoin = (teamId: string) => {
  // alert("请求")
  if (store.userInfo.id) {
    // alert(22222)
    isJoinTheTeam({teamId:teamId, userId:store.userInfo.id} as theTeamParams)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          // alert("请求完成");
          console.log(res.data)
          teamsJoinFlagList.value.forEach((e) => {
            // @ts-ignore
            if (teamId === e.id) {
              // @ts-ignore
              e.joinFlag = res.data;
            }
          });
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
const joinDialogVisible = ref(false);
let joinTeamId = "";
const openJoinTheTeamDialog = (teamId: string)=>{
  joinTeamId = teamId;
  joinDialogVisible.value = true
}
const confirmJoinTheTeam = ()=>{
  joinTheTeam(joinTeamId);
  joinDialogVisible.value = false
}
const joinTheTeam = async (teamId: string) => {
  if (store.userInfo.id) {
    await joinOneTeam({
      teamId:teamId,
      userId:store.userInfo.id,
      userName:store.userInfo.name,
      email:store.userInfo.email,
      tele:store.userInfo.tele
    } as joinTeamParams)
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
            message: "加入队伍成功",
          });
          setTimeout(
            () =>{
              teamsJoinFlagList.value = []
              requestPageTeamsInfo()
            }
              ,1000
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
const leaveDialogVisible = ref(false);
let leaveTeamId = "";
const openLeaveTheTeamDialog = (teamId: string)=>{
  leaveTeamId = teamId;
  leaveDialogVisible.value = true
}
const confirmLeaveTheTeam = ()=>{
  leaveTheTeam(leaveTeamId);
  leaveDialogVisible.value = false
}
const leaveTheTeam = async (teamId: string) => {
  if (store.userInfo.id) {
    await leaveOneTeam({teamId:teamId, userId:store.userInfo.id} as theTeamParams)
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
            message: "退出队伍成功",
          });
          setTimeout(
            () =>{
              teamsJoinFlagList.value = []
              requestPageTeamsInfo()
            },
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
  }
};
const planDialogVisible = ref(false);
const oneUserPlansInfo = ref({} as finalAllCityPlansInfoType);
const openSeeThePlanDialog = async(planId: string)=>{
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
        planDialogVisible.value = true
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
</script>

<template>
  <section class="news-one">
    <div class="container">
      <div class="row">
        <div
          class="tours-list__single"
          v-for="(item, index) in teamsPageInfo"
          :key="index"
        >
          <div class="tours-list__content">
            <div class="tours-list__stars">
              <el-icon><Calendar /></el-icon> 出发时间：{{
                timeFormat(item.depart||"暂无")
              }}
            </div>
            <h3 class="tours-list__title">
              <a href="tour-details.html"
                ><span style="color: #e8604c; margin-right: 10px">队名:</span
                >{{ item.teamName||"暂无" }}</a
              >
            </h3>
            <p class="tours-list__rate">
              预算：<span>{{ item.budget||"暂无" }}</span> / 每人
            </p>
            <p class="tours-list__text">
              <span class="span-style">简介</span> {{ item.introduction||"暂无" }}
            </p>
            <p class="tours-list__text">
              <span class="span-style">途径地点</span>
              <el-tag
                v-for="(i, index2) in item.place"
                :key="index2"
                type="info"
                class="mx-1"
                effect="dark"
                round
              >
                {{ i||"暂无" }}
              </el-tag>
              <span></span>
            </p>
            <p class="tours-list__text" style="width:844px;height:54px">
              <span class="span-style">已加入成员</span>
              <span v-for="i in item.members" style="margin-right:20px"><router-link :to="`/user/${i.userId}`">{{i.userName||"暂无"}}</router-link></span>
            </p>
            <ul class="tours-list__meta list-unstyled">
              <li>
                <a href="javascript:;"
                  ><i class="far fa-calendar"></i>预计{{ item.day||"-" }}天</a
                >
              </li>
              <li>
                <a href="javascript:;"
                  ><i class="far fa-user-circle"></i>预期{{ item.num||"-" }}人</a
                >
              </li>
              <li>
                <a href="javascript:;"
                  ><i class="far fa-user-circle"></i>已加入{{ item.members?.length ||0 }}人</a
                >
              </li>
              <li>
                <a href="javascript:;">
                  <i class="far fa-map"></i>口号:{{ item.slogan||"暂无" }}
                </a>
              </li>
            </ul>
            <p class="tours-list__text tours-list__text-button">
            <div class="seeplan-team-button" @click="openSeeThePlanDialog(item.planId)"
              >查看对应行程方案</div>
             <div class="out-team-button"
              v-if="returnTheIsJoinFlag(item.id, index)"
              @click="openLeaveTheTeamDialog(item.id)"
              >退出队伍</div>
              <div class="join-team-button" @click="openJoinTheTeamDialog(item.id)" v-else
              >加入队伍</div>
            </p>
          </div>
        </div>
      </div>
      <el-config-provider :locale="zhCn">
        <el-pagination
          :current-page="page"
          :total="total"
          :page-size="limit"
          :page-sizes="[5, 10, 15, 20, 50, 100]"
          style="padding: 30px 0; text-align: center"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @size-change="changePageSize"
          @current-change="changeCurrentPage"
          pager-count="10"
          prev-icon="Back"
          next-icon="Right"
          default-page-size="5"
          hide-on-single-page
        />
      </el-config-provider>
    </div>
  </section>
  <el-dialog v-model="joinDialogVisible" :show-close="false">
    <template #header="{ close, titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass">请确认您的选择</h4>
        <el-button type="text" @click="close">
          <el-icon class="el-icon--left"><CircleCloseFilled /></el-icon>
          关闭
        </el-button>
      </div>
    </template>
    加入该队伍将会自动获取您的邮箱和电话号，确定继续吗？
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="confirmJoinTheTeam">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="leaveDialogVisible" :show-close="false">
    <template #header="{ close, titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass">请确认您的选择</h4>
        <el-button type="text" @click="close">
          <el-icon class="el-icon--left"><CircleCloseFilled /></el-icon>
          关闭
        </el-button>
      </div>
    </template>
    您将会退出该队伍，是否继续？
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="confirmLeaveTheTeam">
          继续
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="planDialogVisible" :show-close="false">
    <template #header="{ close, titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass">行程方案</h4>
        <el-button type="text" @click="close">
          <el-icon class="el-icon--left"><CircleCloseFilled /></el-icon>
          关闭
        </el-button>
      </div>
    </template>
      <div class="sidebar__single sidebar__category">
    <h3 class="sidebar__title">对应行程表</h3>
    <ul class="sidebar__category-list list-unstyled">
      <li style="font-size: 16px">
        总预算:{{ oneUserPlansInfo?.budget }}元
      </li>
      <li>
        <span class="span-style text-amber">行程信息</span>
        <el-scrollbar max-height="300px">
          <el-collapse accordion>
            <el-collapse-item
              :name="index"
              v-for="(item, index) in oneUserPlansInfo?.subPlans"
              :key="index"
            >
              <template #title>
                <div class="plan-note-header">
                  {{ item?.city
                  }}<el-icon class="header-icon" size="16px">
                    <Place />
                  </el-icon>
                  <span style="margin-left: 10px">预算:</span
                  >{{ item?.budget }}元
                </div>
              </template>
              <ul>
                <li v-for="(i, index2) in item?.days" :key="index2">
                  第{{ index2 + 1 }}天
                  <span v-for="(k, index3) in i.route" :key="index3">
                    {{ k?.originName }}({{ k?.departTime }}小时)
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
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="planDialogVisible=false">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.row {
  display: flex;
  justify-content: center;
}
.tours-list__single {
  margin: 0 auto;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  .tours-list__content {
    border-left: 1px solid #ece8e0;
    border-radius: 8px;
    padding-bottom: 20px;
    .tours-list__text {
      padding: 10px 0;
      display: flex;
      align-items: center;
      overflow: hidden;
    }
  }
}
.tours-list__text-button{
  display: flex !important;
  justify-content: end !important;
}
.tours-list__stars {
  font-size: 15px;
  margin-bottom: 10px;
}
.span-style {
  display: inline-block;
  width: auto;
  max-width: 100px;
  overflow: hidden;
  height: 1.5em;
  color: #ffffff;
  background-color: #e8604c;
  border-radius: 5px;
  padding: 0 5px;
  line-height: 2em;
  margin-right: 5px;
  font-size: 16px;
  line-height: 1.5em;
  margin-right: 10px;
}
.seeplan-team-button{
  width: 150px;
  height: 40px;
  float: right;
  // border: 1px #e8604c solid;
  cursor: pointer;
  border-radius: 20px;
  background-color: #E6A23C;
  font-weight: 700;
  color: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s linear;
  margin-right: 20px;
}
.seeplan-team-button:hover {
  background-color:  #b88230;
}
.out-team-button{
  width: 150px;
  height: 40px;
  float: right;
  // border: 1px #e8604c solid;
  cursor: pointer;
  border-radius: 20px;
  background-color: #909399;
  font-weight: 700;
  color: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s linear;
}
.out-team-button:hover {
  background-color: #606266;
}
.join-team-button {
  width: 150px;
  height: 40px;
  float: right;
  // border: 1px #e8604c solid;
  cursor: pointer;
  border-radius: 20px;
  background-color: #e8604c;
  font-weight: 700;
  color: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s linear;
}
.join-team-button:hover {
  background-color: #e74128;
}
.el-pagination {
  display: flex;
  justify-content: center;
}
.list-unstyled{
  margin: 10px 0;
}
.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>
