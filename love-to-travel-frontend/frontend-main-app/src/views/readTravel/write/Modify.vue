<script lang="ts">
import E from "wangeditor";
import { UPLOADER } from "@/utils/uploadImg";
import { srcPattern } from "@/utils/filters/srcPattern";
import { mainStore } from "@/store/user";
import { getUserAllPlansInfoByUserId } from "@/apis/travelService/plan";
import { theGivenAllCityPlansInfoType } from "@/apis/interface/myInterface";
import { publishOneNote } from "@/apis/travelService/note";
import { noteInfoParams } from "@/apis/travelService/tInterface";
// import { storeToRefs } from "pinia";
import { nextTick, ref } from "vue";
import { useRouter } from "vue-router";
import { ElInput } from "element-plus";
export default {
  data() {
    return {
      editor: null,
      info_: null,
      UPLOADER,
      src: "",
    };
  },
  model: {
    prop: "value",
    event: "change",
  },
  props: {
    value: {
      type: String,
      default: "",
    },
    isClear: {
      type: Boolean,
      default: false,
    }
  },
  watch: {
    isClear(val) {
      // 触发清除文本域内容
      if (val) {
        // @ts-ignore
        this.editor.txt.clear();
        this.info_ = null;
      }
    },
    value(val) {
      // 使用 v-model 时,设置初始值
      // @ts-ignore
      this.editor.txt.html(val);
    },
  },
  setup() {
    /* tag对应的代码 */

    const inputValue = ref("");
    const noteTips = ref([] as Array<string>);
    const inputVisible = ref(false);
    const InputRef = ref<InstanceType<typeof ElInput>>();

    const handleClose = (tag: string) => {
      noteTips.value.splice(noteTips.value.indexOf(tag), 1);
    };

    const showInput = () => {
      inputVisible.value = true;
      nextTick(() => {
        InputRef.value!.input!.focus();
      });
    };

    const handleInputConfirm = () => {
      if (inputValue.value) {
        noteTips.value.push(inputValue.value);
      }
      inputVisible.value = false;
      inputValue.value = "";
    };
    /* 结束 */
    /* 复选框标签 */
    const defaultTags = ref([
      {
        flag: false,
        value: "骑行",
      },
      {
        flag: false,
        value: "滑雪",
      },
      {
        flag: false,
        value: "国庆",
      },
      {
        flag: false,
        value: "春节",
      },
      {
        flag: false,
        value: "中秋节",
      },
      {
        flag: false,
        value: "穷游",
      },
      {
        flag: false,
        value: "环游",
      },
      {
        flag: false,
        value: "赏秋",
      },
      {
        flag: false,
        value: "万圣节",
      },
      {
        flag: false,
        value: "清明",
      },
      {
        flag: false,
        value: "摄影",
      },
      {
        flag: false,
        value: "深度游",
      },
      {
        flag: false,
        value: "寒假",
      },
      {
        flag: false,
        value: "潜水",
      },
      {
        flag: false,
        value: "购物",
      },
      {
        flag: false,
        value: "端午",
      },
      {
        flag: false,
        value: "漫游",
      },
      {
        flag: false,
        value: "踏春",
      },
      {
        flag: false,
        value: "蜜月",
      },
      {
        flag: false,
        value: "人文",
      },
      {
        flag: false,
        value: "婚纱",
      },
      {
        flag: false,
        value: "毕业游",
      },
      {
        flag: false,
        value: "赏樱",
      },
      {
        flag: false,
        value: "暑假",
      },
      {
        flag: false,
        value: "海滨海岛",
      },
      {
        flag: false,
        value: "短途周末",
      },
      {
        flag: false,
        value: "徒步",
      },
      {
        flag: false,
        value: "探险",
      },
      {
        flag: false,
        value: "赏枫",
      },
      {
        flag: false,
        value: "自驾",
      },
      {
        flag: false,
        value: "冬季",
      },
      {
        flag: false,
        value: "美食",
      },
      {
        flag: false,
        value: "登山",
      },
      {
        flag: false,
        value: "温泉",
      },
      {
        flag: false,
        value: "五一",
      },
      {
        flag: false,
        value: "跨年",
      },
      {
        flag: false,
        value: "圣诞",
      },
      {
        flag: false,
        value: "游轮",
      },
      {
        flag: false,
        value: "夏季",
      },
    ]);
    const store = mainStore();
    const router = useRouter();
    const userInfo = ref(store.userInfo);
    // alert(userInfo.value.url);
    let userAllPlansInfo = [] as theGivenAllCityPlansInfoType[];

    interface userPlansOption {
      planId: string;
      budget: string;
      cityNames: string;
    }
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
    const planId = ref("");
    const noteTitle = ref("");
    const noteCity = ref("");
    let noteInfo = {} as noteInfoParams;
    const publishTheNote = async (src: string, info_: any) => {
      if (store.userInfo.id) {
        if (info_ !== null && info_ !== "" && noteTitle.value !== "") {
          defaultTags.value.forEach((e) => {
            if (e.flag === true) {
              noteTips.value.push(e.value);
            }
          });
          // alert("11111");
          noteInfo.content = info_;
          noteInfo.url = src;
          noteInfo.title = noteTitle.value;
          noteInfo.planId = planId.value;
          noteInfo.userId = store.userInfo.id;
          noteInfo.city = noteCity.value;
          noteInfo.trip = noteTips.value;
          console.log(noteInfo);
          await publishOneNote(noteInfo)
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
        } else {
          //@ts-ignore
          ElMessage({
            type: "warn",
            message: "爱宝儿，标题和内容必须输入哦~",
          });
        }
      } else {
        //@ts-ignore
        ElMessage({
          type: "warning",
          message: "爱宝儿，登录后才能发布游记哦~",
        });
      }
    };
    const successDialogVisible = ref(false);
    const goSeeMyNotes = () => {
      successDialogVisible.value = false;
      router.push("/personal/mynote");
    };
    const continuePublish = () => {
      successDialogVisible.value = false;
      location.reload();
    };
    return {
      userInfo,
      userAllPlansInfo,
      planId,
      userPlansOption,
      noteTitle,
      publishTheNote,
      successDialogVisible,
      goSeeMyNotes,
      continuePublish,
      noteCity,
      noteTips,
      handleClose,
      showInput,
      handleInputConfirm,
      inputValue,
      inputVisible,
      defaultTags,
    };
  },
  methods: {
    seteditor() {
      this.editor = new E(this.$refs.toolbar, this.$refs.editor);
      // 配置菜单
      // @ts-ignore
      this.editor.customConfig.menus = [
        "head", // 标题
        "bold", // 粗体
        "fontSize", // 字号
        "fontName", // 字体
        "italic", // 斜体
        "underline", // 下划线
        "strikeThrough", // 删除线
        "foreColor", // 文字颜色
        "backColor", // 背景颜色
        "link", // 插入链接
        "list", // 列表
        "justify", // 对齐方式
        "quote", // 引用
        "emoticon", // 表情
        "image", // 插入图片
        "table", // 表格
        // 'video', // 插入视频
        "code", // 插入代码
        "undo", // 撤销
        "redo", // 重复
      ];
      // @ts-ignore
      this.editor.customConfig.onchange = (html) => {
        this.info_ = html; // 绑定当前逐渐地值
        // console.log(srcPattern(html)[0]);
        // console.log(srcPattern(html));
        // @ts-ignore
        if (srcPattern(html).length !== 0) {
          // @ts-ignore
          this.src = srcPattern(html)[0].url;
        }
        // console.log(this.src);
        this.$emit("change", this.info_); // 将内容同步到父组件中
        // console.log(this.info_);
      };

      // 创建富文本编辑器
      // @ts-ignore
      this.editor.create();
    },
  },
  mounted() {
    /* 初始化富文本编辑器 */
    this.seteditor();
    // @ts-ignore
    this.editor.txt.html(
      "<p>开始游记创作吧！</p><p>注意：上传的第一张图片将作为游记封面</p>"
    );
    // @ts-ignore
    this.editor.config.UploadInit = this.UPLOADER(this.editor).init();
  },
};
</script>
<template>
  <div class="page-header__bottom">
    <div class="container">
      <div class="page-header__bottom-inner">
        <ul class="thm-breadcrumb list-unstyled">
          <li><router-link to="/">首页</router-link></li>
          <li><span>.</span></li>
          <li><router-link to="/groupTravel/write">阅旅游</router-link></li>
          <li><span>.</span></li>
          <li class="active">编辑游记</li>
        </ul>
      </div>
    </div>
  </div>
  <div class="header-border">
    <div class="header-left">
      <el-icon size="22px"><EditPen /></el-icon>修改
    </div>
    <div class="header-right">
      <span>{{ userInfo.name }}</span
      ><router-link to="/personal/center"
        ><img :src="userInfo.url"
      /></router-link>
    </div>
  </div>
  <div class="header-title">
    <div class="title-left">游记标题</div>
    <input
      type="text"
      placeholder="请输入标题"
      class="title-right"
      v-model="noteTitle"
    />
  </div>
  <div class="editor-container">
    <div class="editor">
      <div ref="toolbar" class="toolbar"></div>
      <div ref="editor" class="text"></div>
    </div>
  </div>
  <div class="select-plan-container">
    <div class="select-title">选择该游记对应的您的行程</div>
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
  <div class="select-plan-container">
    <div class="select-title">填写该游记对应的城市</div>
    <el-input
      v-model="noteCity"
      class="w-50 m-2"
      placeholder="请输入内容(将会展示为城市标签)"
    >
      <template #suffix>
        <el-icon class="el-input__icon"><OfficeBuilding /></el-icon>
      </template>
    </el-input>
  </div>
  <div class="select-plan-container2">
    <div class="select-title2">选择系统推荐的标签</div>
    <div class="tag-container-el2">
      <el-checkbox
        v-model="item.flag"
        :label="item.value"
        border
        v-for="item in defaultTags"
      />
    </div>
    <div class="select-title2">添加自定义的标签</div>
    <div class="tag-container-el">
      <el-tag
        v-for="tag in noteTips"
        :key="tag"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)"
      >
        {{ tag }}
      </el-tag>
      <el-input
        v-if="inputVisible"
        ref="InputRef"
        v-model="inputValue"
        class="ml-1 w-20"
        @keyup.enter="handleInputConfirm"
        @blur="handleInputConfirm"
      />
      <el-button v-else class="button-new-tag ml-1" @click="showInput">
        + 添加标签
      </el-button>
    </div>
  </div>
  <div class="publish-border">
    <div class="publish-button" @click="publishTheNote(src, info_)">
      发布游记
    </div>
  </div>
  <el-dialog
    v-model="successDialogVisible"
    title="成功提示"
    width="30%"
    draggable
    show-close="false"
  >
    <span>恭喜爱宝儿，游记发布成功！</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="goSeeMyNotes">
          查看我的游记
        </el-button>
        <el-button type="success" @click="continuePublish">
          继续发布
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
input::-webkit-input-placeholder {
  /* WebKit browsers 适配谷歌 */
  color: #cdd0d6;
  font-weight: 0;
}
input:-moz-placeholder {
  /* Mozilla Firefox 4 to 18 适配火狐 */
  color: #cdd0d6;
  font-weight: 0;
}
input::-moz-placeholder {
  /* Mozilla Firefox 19+ 适配火狐 */
  color: #cdd0d6;
  font-weight: 0;
}
input:-ms-input-placeholder {
  /* Internet Explorer 10+  适配ie*/
  color: #cdd0d6;
  font-weight: 0;
}

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
.header-title {
  width: 100%;
  height: 60px;
  padding-left: 150px;
  display: flex;
  margin-top: 20px;
  align-items: center;
  .title-left {
    width: 120px;
    height: 40px;
    background-color: #e8604c;
    border-radius: 20px;
    color: #ffffff;
    font-weight: 700;
    font-size: 24px;
    padding: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .title-right {
    margin-left: 30px;
    padding-left: 20px;
    width: 800px;
    height: 50px;
    border-radius: 20px;
    background-color: #faf5ee;
    color: #303133;
    font-size: 20px;
    font-weight: 600;
    outline: none;
    border: 0;
    transition: all 0.2s linear;
  }
  .title-right:focus {
    border: 2px #e8604c solid;
  }
}
.editor-container {
  width: 100%;
  height: auto;
  display: flex;
  margin-top: 20px;
  .editor {
    width: 80%;
    margin: 0 auto;
  }
  .toolbar {
    border: 1px solid #ccc;
  }
  .text {
    border: 1px solid #ccc;
    height: 500px;
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
.select-plan-container2 {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  margin-bottom: 40px;
  .select-title2 {
    width: 100%;
    height: auto;
    padding: 10px 0;
    display: flex;
    justify-content: start;
    padding-left: 125px;
    color: #e74128;
    font-weight: 700;
  }
  .tag-container-el {
    width: 100%;
    height: auto;
    display: flex;
    justify-content: start;
    padding-left: 270px;
    .el-tag {
      width: 98px;
      height: 32px;
      margin-right: 20px;
    }
    .el-input {
      width: 98px;
    }
  }
}
.tag-container-el2 {
  width: 100%;
  height: auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: start;
  padding-left: 270px;
  .el-checkbox {
    width: 103px;
    margin-bottom: 10px;
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
