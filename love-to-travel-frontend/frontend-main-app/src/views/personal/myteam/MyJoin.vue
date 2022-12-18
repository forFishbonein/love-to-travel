<script setup lang="ts">
import { ref } from "vue";
import { theTeamParams } from "@/apis/travelService/tInterface";
import { theTeamInfoType } from "@/apis/interface/myInterface";
import {
  searchJoinTeamByUserId,
  leaveOneTeam,
  postInviteEmail,
} from "@/apis/travelService/team";
import { mainStore } from "@/store/user";
import { getOneUserPlansInfoById } from "@/apis/travelService/plan";
import { finalAllCityPlansInfoType } from "@/apis/interface/myInterface";
const store = mainStore();
const myJoinTeamsInfo = ref([] as theTeamInfoType[]);
const requestMyJoinTeamsInfo = async () => {
  await searchJoinTeamByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        myJoinTeamsInfo.value = res.data;
        console.log(myJoinTeamsInfo);
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
requestMyJoinTeamsInfo();

const leaveDialogVisible = ref(false);
let leaveTeamId = "";
const openLeaveTheTeamDialog = (teamId: string) => {
  leaveTeamId = teamId;
  leaveDialogVisible.value = true;
};
const confirmLeaveTheTeam = () => {
  leaveTheTeam(leaveTeamId);
  leaveDialogVisible.value = false;
};
const leaveTheTeam = async (teamId: string) => {
  if (store.userInfo.id) {
    await leaveOneTeam({
      teamId: teamId,
      userId: store.userInfo.id,
    } as theTeamParams)
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
          requestMyJoinTeamsInfo();
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
const openSeeThePlanDialog = async (planId: string) => {
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
        planDialogVisible.value = true;
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
/* 发送邮件 */
let postTeamId = "";
let postTeamName = "";
const openEmailDialog = (teamId: string, teamName: string) => {
  postTeamId = teamId;
  postTeamName = teamName;
  emailDialogVisible.value = true;
};
const postEmail = ref("");
const emailDialogVisible = ref(false);
const postEmailToOnePeople = () => {
  postInviteEmail({
    teamId: postTeamId,
    teamName: postTeamName,
    inviterId: store.userInfo.id,
    // inviterId: store.userInfo.id,
    userEmail: postEmail.value,
  })
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        emailDialogVisible.value = false;
        // @ts-ignore
        ElMessage({
          type: "success",
          message: "邮件发送成功",
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
</script>

<template>
  <div class="container">
    <el-tabs type="border-card">
      <el-tab-pane
        :label="`队伍${index + 1}`"
        v-for="(item, index) in myJoinTeamsInfo"
        :key="item.id"
      >
        <el-descriptions
          class="margin-top"
          :title="item.teamName || '暂无队伍名'"
          :column="1"
          size="medium"
          border
        >
          <template #extra>
            <el-button type="success" @click="openSeeThePlanDialog(item.planId)"
              >查看对应行程方案</el-button
            >
            <el-button type="primary" @click="openLeaveTheTeamDialog(item.id)"
              >退出队伍</el-button
            >
          </template>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon><Calendar /></el-icon>
                出行日期
              </div>
            </template>
            {{ item.depart || "-" }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon><Sunrise /></el-icon>
                预计天数
              </div>
            </template>
            {{ item.day || "-" }}天
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon><User /></el-icon>
                预期人数
              </div>
            </template>
            {{ item.num || "-" }}人
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon><Money /></el-icon>
                预算/人
              </div>
            </template>
            {{ item.budget || "-" }}元
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon><OfficeBuilding /></el-icon>
                途径地点
              </div>
            </template>
            <el-tag
              size="small"
              style="margin-right: 10px"
              v-for="i in item.place"
              >{{ i || "-" }}</el-tag
            >
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon><Opportunity /></el-icon>
                口号
              </div>
            </template>
            {{ item.slogan }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon><Tickets /></el-icon>
                简介
              </div>
            </template>
            {{ item.introduction }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon><UserFilled /></el-icon>
                已加入成员
              </div>
            </template>
            <el-popover
              placement="bottom"
              title="更多信息"
              :width="200"
              trigger="click"
              :content="`电话:${j.tele || 0}；邮箱:${j.email || 0}`"
              v-for="j in item.members"
              :key="j.userId"
            >
              <template #reference>
                <el-button class="m-2">{{ j.userName || "-" }}</el-button>
              </template>
            </el-popover>
          </el-descriptions-item>
        </el-descriptions>
        <div class="bottom-button">
          <el-button
            type="warning"
            @click="openEmailDialog(item.id, item.teamName)"
          >
            邀请队友<el-icon class="el-icon--right"><Message /></el-icon>
          </el-button>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
  <el-dialog v-model="leaveDialogVisible" :show-close="false">
    <template #header="{ close, titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass">请确认您的选择</h4>
        <el-button type="danger" @click="close">
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
        <li style="font-size: 16px">总预算:{{ oneUserPlansInfo?.budget }}元</li>
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
        <el-button type="primary" @click="planDialogVisible = false">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog
    v-model="emailDialogVisible"
    title="填写邮箱"
    width="30%"
    draggable
  >
    <span>请填写邮箱地址，我们将会发送一封邀请邮件给ta~</span>
    <el-input v-model="postEmail" class="w-60" placeholder="邮箱地址">
      <template #prefix>
        <el-icon class="el-input__icon"><Message /></el-icon>
      </template>
    </el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="emailDialogVisible = false">取消发送</el-button>
        <el-button type="primary" @click="postEmailToOnePeople">
          确认发送
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: center;
  margin-top: 30px;
  .el-tabs--border-card {
    width: 820px;
  }
}
.el-descriptions {
  margin-top: 20px;
}
.cell-item {
  display: flex;
  align-items: center;
}
.margin-top {
  margin-top: 20px;
}
.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.bottom-button {
  width: 100%;
  height: 50px;
  // border: 1px #e8604c solid;
  display: flex;
  align-items: end;
  justify-content: end;
}
</style>
