<script lang="ts">
import E from "wangeditor";
import { UPLOADER } from "@/utils/uploadImg";
import { srcPattern } from "@/utils/filters/srcPattern";
import { mainStore } from "@/store/user";
import { getUserAllPlansInfoByUserId } from "@/apis/travelService/plan";
import { finalAllCityPlansInfoType } from "@apis/interface/iPlan";
// import { storeToRefs } from "pinia";
import { ref } from "vue";
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
    },
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
    const store = mainStore();
    const userInfo = ref(store.userInfo);
    // alert(userInfo.value.url);
    const userAllPlansInfo = ref([] as finalAllCityPlansInfoType[]);
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
            userAllPlansInfo.value = res.data;
            console.log(userAllPlansInfo.value);
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
    return {
      userInfo,
      userAllPlansInfo,
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
        // @ts-ignore
        this.src = srcPattern(html)[0].url;
        console.log(this.src);
        this.$emit("change", this.info_); // 将内容同步到父组件中
        console.log(this.info_);
      };

      // 创建富文本编辑器
      // @ts-ignore
      this.editor.create();
    },
  },
  mounted() {
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
          <li><a href="#">组旅游</a></li>
          <li><span>.</span></li>
          <li class="active">发布游记</li>
        </ul>
      </div>
    </div>
  </div>
  <div class="header-border">
    <div class="header-left">
      <el-icon size="22px"><EditPen /></el-icon>写游记
    </div>
    <div class="header-right">
      <span>{{ userInfo.name }}</span
      ><router-link to="/personal/center"
        ><img :src="userInfo.url"
      /></router-link>
    </div>
  </div>
  <div class="editor-container">
    <div class="editor">
      <div ref="toolbar" class="toolbar"></div>
      <div ref="editor" class="text"></div>
    </div>
  </div>
  <div class="publish-border">
    <div class="publish-button">发布游记</div>
  </div>
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
