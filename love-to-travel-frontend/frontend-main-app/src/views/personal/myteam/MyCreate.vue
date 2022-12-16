<script setup lang="ts">
import { ref, reactive, toRefs, nextTick } from "vue";
import { theTeamParams } from "@/apis/travelService/tInterface";
import {
  theTeamInfoType,
  teamModifyParams,
  teamMemberInfoType,
} from "@/apis/interface/iPlan";
import { searchCreateTeamByUserId } from "@/apis/travelService/team";
import { mainStore } from "@/store/user";
import { ElInput } from "element-plus";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import { modifyTheTeamInfo, kickOneMember } from "@/apis/travelService/team";
import { getOneUserPlansInfoById } from "@/apis/travelService/plan";
import { finalAllCityPlansInfoType } from "@apis/interface/iPlan";
const store = mainStore();
const myCreateTeamsInfo = ref([] as theTeamInfoType[]);
const requestMyCreateTeamsInfo = async () => {
  await searchCreateTeamByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        myCreateTeamsInfo.value = res.data;
        console.log(myCreateTeamsInfo);
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
requestMyCreateTeamsInfo();

const theModifyFormInfo = reactive({
  id: "",
  planId: "",
  teamName: "",
  ownerId: "",
  place: [],
  depart: "",
  day: "",
  budget: "",
  introduction: "",
  slogan: "",
  num: "",
  members: [] as teamMemberInfoType[],
} as teamModifyParams);
const {
  id,
  planId,
  teamName,
  ownerId,
  place,
  depart,
  day,
  budget,
  introduction,
  slogan,
  num,
  members,
} = toRefs(theModifyFormInfo);
const modifyDialogVisible = ref(false);
const innerVisible = ref(false);
const innerVisible2 = ref(false);
const openModifyTheTeamDialog = (teamData: theTeamInfoType) => {
  modifyDialogVisible.value = true;
  id.value = teamData.id;
  planId.value = teamData.planId;
  teamName.value = teamData.teamName;
  ownerId.value = teamData.ownerId;
  place.value = teamData.place;
  depart.value = teamData.depart;
  day.value = teamData.day;
  budget.value = teamData.budget;
  introduction.value = teamData.introduction;
  slogan.value = teamData.slogan;
  num.value = teamData.num;
  members.value = teamData.members;
};
/* el-tag需要的变量方法 */
const size = ref<"default" | "large" | "small">("default");
const inputValue = ref("");
// const dynamicTags = ref([] as Array<string>);
const inputVisible = ref(false);
const InputRef = ref<InstanceType<typeof ElInput>>();

const handleClose = (tag: string) => {
  place.value.splice(place.value.indexOf(tag), 1);
};

const showInput = () => {
  inputVisible.value = true;
  nextTick(() => {
    InputRef.value!.input!.focus();
  });
};

const handleInputConfirm = () => {
  if (inputValue.value) {
    place.value.push(inputValue.value);
  }
  inputVisible.value = false;
  inputValue.value = "";
};

/* 数字输入框的变量方法 */
// const num = ref(1);
const handleChange = (value: number) => {
  console.log(value);
};

/* 移除一个成员的方法 */
let theMemberId = "";
let theMemberIndex = 0;
const openTheRemoveDialog = (userId: string, index: number) => {
  innerVisible2.value = true;
  theMemberId = userId;
  theMemberIndex = index;
};
const removeOneMember = () => {
  kickOneMember({
    userId: theMemberId,
    teamId: id.value,
  } as theTeamParams)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        members.value.splice(theMemberIndex, 1);
        //@ts-ignore
        ElMessage({
          type: "success",
          message: "删除成功",
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
  innerVisible2.value = false;
};

/* 保存修改的信息 */
/* 取消保存 */
const cancelSaveTheInfo = () => {
  modifyDialogVisible.value = false;
  id.value = "";
  planId.value = "";
  teamName.value = "";
  ownerId.value = "";
  place.value = [];
  depart.value = "";
  day.value = "";
  budget.value = "";
  introduction.value = "";
  slogan.value = "";
  num.value = "";
  members.value = [] as teamMemberInfoType[];
};
/* 确认保存 */
const saveTheModifiedInfo = async () => {
  await modifyTheTeamInfo(theModifyFormInfo)
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
          message: "修改成功",
        });
        innerVisible.value = false;
        modifyDialogVisible.value = false;
        requestMyCreateTeamsInfo();
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
</script>

<template>
  <div class="container">
    <el-tabs type="border-card">
      <el-tab-pane
        :label="`队伍${index + 1}`"
        v-for="(item, index) in myCreateTeamsInfo"
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
            <el-button type="primary" @click="openModifyTheTeamDialog(item)"
              >修改信息</el-button
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
      </el-tab-pane>
    </el-tabs>
  </div>
  <el-dialog v-model="modifyDialogVisible" :show-close="false">
    <template #default>
      <div class="create-team-container">
        <div class="create-team">
          <div class="base-row">
            <span class="span-style-front">队伍名</span>
            <el-input
              v-model="teamName"
              class="w-50 m-2"
              placeholder="请输入内容"
            >
              <template #suffix>
                <el-icon><Flag /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">途经城市</span>
            <el-tag
              v-for="tag in place"
              :key="tag"
              class="mx-1"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)"
            >
              {{ tag }}
            </el-tag>
            <!-- <el-col :span="4"> -->
            <el-input
              v-if="inputVisible"
              ref="InputRef"
              v-model="inputValue"
              class="w-10"
              size="small"
              @keyup.enter="handleInputConfirm"
              @blur="handleInputConfirm"
            />
            <el-button
              v-else
              class="button-new-tag ml-1"
              size="small"
              @click="showInput"
            >
              + 添加
            </el-button>
            <!-- </el-col> -->
          </div>
          <div class="base-row">
            <span class="span-style-front">出发时间</span>
            <el-config-provider :locale="zhCn">
              <el-date-picker
                type="date"
                placeholder="选择日期"
                size="small"
                v-model="depart"
              />
            </el-config-provider>
          </div>
          <div class="base-row">
            <span class="span-style-front">预计天数</span>
            <el-input-number
              v-model="day"
              :min="1"
              :max="10"
              @change="handleChange"
            />
          </div>
          <div class="base-row">
            <span class="span-style-front">预算/人</span>
            <el-input
              v-model="budget"
              class="w-50 m-2"
              placeholder="请输入内容"
            >
              <template #suffix>
                <el-icon><Money /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">预期人数</span>
            <el-input-number
              v-model="num"
              :min="1"
              :max="10"
              @change="handleChange"
            />
          </div>
          <div class="base-row">
            <span class="span-style-front">口号</span>
            <el-input
              v-model="slogan"
              class="w-50 m-2"
              placeholder="请输入内容"
            >
              <template #suffix>
                <el-icon><Opportunity /></el-icon>
              </template>
            </el-input>
          </div>
          <div class="base-row">
            <span class="span-style-front">简介</span>
            <el-input
              :autosize="{ minRows: 2, maxRows: 4 }"
              type="textarea"
              placeholder="请输入内容"
              v-model="introduction"
            />
          </div>
          <div class="base-row">
            <span class="span-style-front">已加入的成员</span>
            <el-tag
              v-for="(m, index) in members"
              :key="m.userId"
              class="mx-1"
              closable
              type="warning"
              @close="openTheRemoveDialog(m.userId, index)"
            >
              {{ m.userName }}
            </el-tag>
          </div>
          <div class="base-row">
            <span class="span-style-front">对应的行程id</span>
            <el-input class="w-50 m-2" v-model="planId" disabled />
          </div>
        </div>
      </div>
      <el-dialog
        v-model="innerVisible2"
        width="30%"
        title="确认框"
        append-to-body
      >
        确定要移除该成员吗？<span style="color: red">(该操作不可逆)</span>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="innerVisible2 = false">取消</el-button>
            <el-button type="primary" @click="removeOneMember">
              确认
            </el-button>
          </span>
        </template>
      </el-dialog>
      <el-dialog
        v-model="innerVisible"
        width="30%"
        title="确认框"
        append-to-body
      >
        此操作将会覆盖您先前创建的队伍信息，确定继续吗？
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="innerVisible = false">取消</el-button>
            <el-button type="primary" @click="saveTheModifiedInfo">
              确认
            </el-button>
          </span>
        </template>
      </el-dialog>
    </template>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelSaveTheInfo">取消</el-button>
        <el-button type="primary" @click="innerVisible = true">
          修改
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
.dialog-footer button:first-child {
  margin-right: 10px;
}
.create-team-container {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: center;
  .create-team {
    min-width: 600px;
    height: auto;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    .base-row {
      width: 100%;
      height: 60px;
      // border: 1px #e8604c solid;
      display: flex;
      justify-content: start;
      align-items: center;
      .el-input {
        width: 60px;
      }
      .span-style-front {
        display: inline-block;
        min-width: 50px;
        padding: 5px;
        height: 30px;
        line-height: 20px;
        color: #e8604c;
        font-size: 14px;
        font-weight: 600;
        background-color: #f7f2ea;
        border-radius: 5px;
        margin-right: 10px;
      }
    }
  }
}
.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>
