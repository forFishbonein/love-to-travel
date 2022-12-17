<script setup lang="ts">
import { teamInfoParams } from "@/apis/travelService/tInterface";
import { ref, reactive, toRefs, nextTick } from "vue";
import { ElInput } from "element-plus";
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
import { mainStore } from "@/store/user";
import { getUserAllPlansInfoByUserId } from "@/apis/travelService/plan";
import { theGivenAllCityPlansInfoType } from "@/apis/interface/myInterface";
import { createOneTeam } from "@/apis/travelService/team";
import { useRouter } from "vue-router";
const router = useRouter();
const size = ref<"default" | "large" | "small">("default");
const store = mainStore();
/* el-tag需要的变量方法 */

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
/* 行程选项 */
interface userPlansOption {
  planId: string;
  budget: string;
  cityNames: string;
}
let userAllPlansInfo = [] as theGivenAllCityPlansInfoType[];
const userPlansOption = ref([] as userPlansOption[]);
const requestMyPlansInfo = async () => {
  await getUserAllPlansInfoByUserId(store.userInfo.id)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        userAllPlansInfo = res.data;

        // console.log(userAllPlansInfo.value);
        userAllPlansInfo.forEach((e) => {
          let planId = e.id;
          let budget = e.budget;
          let cityNames = "出发地";
          e.subPlans.forEach((e2) => {
            cityNames += "—>" + e2.city;
          });
          // @ts-ignore
          userPlansOption.value.push({
            planId: planId,
            budget: budget,
            cityNames: cityNames + "—>返回地",
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
requestMyPlansInfo();
/* 创建队伍 */
/* 注意：下面这个toRefs里面的内容会自动同步到reactive这个大对象里面 */
const createTeamInfo = reactive({
  planId: "",
  teamName: "",
  ownerId: store.userInfo.id,
  place: [],
  depart: "",
  day: "",
  budget: "",
  introduction: "",
  slogan: "",
  num: "",
} as teamInfoParams);
const {
  planId,
  teamName,
  place,
  depart,
  day,
  budget,
  num,
  slogan,
  introduction,
} = toRefs(createTeamInfo);

const createTheTeam = () => {
  createOneTeam(createTeamInfo)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        successDialogVisible.value = true;
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
const successDialogVisible = ref(false);
const goSeeMyNotes = () => {
  successDialogVisible.value = false;
  router.push("/personal/myteam/mycreate");
};
const continuePublish = () => {
  successDialogVisible.value = false;
  location.reload();
};
</script>

<template>
  <div class="page-header__bottom">
    <div class="container">
      <div class="page-header__bottom-inner">
        <ul class="thm-breadcrumb list-unstyled">
          <li><router-link to="/">首页</router-link></li>
          <li><span>.</span></li>
          <li><router-link to="/groupTravel/create">组旅游</router-link></li>
          <li><span>.</span></li>
          <li class="active">创建队伍</li>
        </ul>
      </div>
    </div>
  </div>
  <div class="header-border">
    <div class="header-left">
      <el-icon size="22px"><Stamp /></el-icon>创建队伍
    </div>
    <div class="header-right">
      <span>{{ store.userInfo.name }}</span
      ><router-link to="/personal/center"
        ><img :src="store.userInfo.url"
      /></router-link>
    </div>
  </div>
  <div class="create-team-container">
    <div class="create-team">
      <div class="base-row">
        <span class="span-style-front">队伍名</span>
        <el-input v-model="teamName" class="w-50 m-2" placeholder="请输入内容">
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
            :size="size"
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
        <el-input v-model="budget" class="w-50 m-2" placeholder="请输入内容">
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
        <el-input v-model="slogan" class="w-50 m-2" placeholder="请输入内容">
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
    </div>
  </div>
  <div class="select-plan-container">
    <div class="select-title">选择该游记对应的行程</div>
    <el-radio-group v-model="planId">
      <el-radio :label="item.planId" v-for="(item, index) in userPlansOption"
        ><el-popover
          placement="top-start"
          :title="`行程${index + 1}`"
          :width="150"
          trigger="hover"
          :content="`预算:${item.budget}   行程:${item.cityNames}`"
        >
          <template #reference>
            <el-button class="m-2">行程{{ index + 1 }}</el-button>
          </template>
        </el-popover></el-radio
      >
    </el-radio-group>
  </div>
  <div class="publish-border">
    <div class="publish-button" @click="createTheTeam">创建队伍</div>
  </div>
  <el-dialog
    v-model="successDialogVisible"
    title="成功提示"
    width="30%"
    draggable
    show-close="false"
  >
    <span>恭喜您，队伍创建成功！</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="goSeeMyNotes">
          查看我的队伍
        </el-button>
        <el-button type="success" @click="continuePublish">
          继续创建
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.header-border {
  width: 100%;
  height: 60px;
  display: fixed;
  top: 0;
  border-bottom: 2px #e8604c solid;
  line-height: 60px;
  padding-left: 50px;

  .header-left {
    font-weight: 700;
    color: #e8604c;
    font-size: 22px;
    float: left;
    display: flex;
    align-items: center;
  }
  .header-right {
    font-weight: 700;
    color: #303133;
    font-size: 16px;
    float: right;
    display: flex;
    align-items: center;
    // border: 1px #e8604c solid;
    margin-right: 20px;
    img {
      width: 50px;
      height: 50px;
      border-radius: 30px;
      margin-left: 10px;
      cursor: pointer;
      margin-bottom: 5px;
    }
  }
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

.select-plan-container {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  margin-bottom: 40px;
  .select-title {
    width: 100%;
    height: auto;
    padding: 10px 0;
    display: flex;
    justify-content: start;
    padding-left: 125px;
    color: #e74128;
    font-weight: 700;
  }
}
.publish-border {
  width: 100%;
  height: 60px;
  display: fixed;
  bottom: 0;
  border-top: 2px #e8604c solid;
  margin-top: 20px;
  // background-color: #e8604c;
  padding-top: 10px;
  padding-right: 130px;
  .publish-button {
    width: 150px;
    height: 40px;
    float: right;
    background-color: #e8604c;
    border-radius: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #ffffff;
    cursor: pointer;
    font-weight: 700;
  }
  .publish-button:hover {
    background-color: #e74128;
  }
}
</style>
