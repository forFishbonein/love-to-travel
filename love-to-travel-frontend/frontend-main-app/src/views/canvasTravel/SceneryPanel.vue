<template>
  <el-card class="box-card detail-panel" shadow="hover">
    <div slot="header" class="clearfix card-header">
      <span style="font-size: 14px; font-weight: 700">节点信息</span>
      <!--      <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
    </div>
    <div class="text item">
      <table>
        <tbody>
          <tr>
            <td class="detail-info span-style-front">
              景区:{{ currentNode[0] || "-" }}
            </td>
          </tr>
          <tr>
            <td class="detail-info">级别:{{ currentNode[1] || "-" }}</td>
          </tr>
          <tr>
            <td class="detail-info">评分:{{ currentNode[2] || "-" }}</td>
          </tr>
          <tr>
            <td class="detail-info">票价:￥{{ currentNode[3] || "-" }}</td>
          </tr>
          <tr>
            <td class="detail-info">开放时间:{{ currentNode[4] || "-" }}</td>
          </tr>
        </tbody>
      </table>
      <el-form ref="form" label-width="0px">
        <el-form-item>
          <el-select
            v-model="currentType"
            placeholder="请选择关系"
            class="type-select"
          >
            <el-option
              v-for="(type, i) in relationshipTypes"
              :label="type"
              :value="urls[i]"
              :key="type"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit" class="submit-btn"
            >查询关系</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </el-card>
</template>

<script>
import { getCanvasOneSceneryInfoByNameAndRelation } from "@/apis/travelService/canvas";
import { canvasStore } from "@/store/canvas";
const cstore = canvasStore();
export default {
  name: "SceneryPanel",
  data() {
    return {
      currentNode: {},
      currentType: "", //最终选择的
      relationshipTypes: ["景区特色", "所在地区", "推荐美食", "相关历史"],
      urls: ["is_detail", "in_area", "recommand_food", "is_related"],
    };
  },
  methods: {
    onSubmit() {
      var _this = this;

      if (this.currentType === "") {
        ElMessage({
          type: "error",
          message: "未选择任何类型",
        });
        // this.$message.error("未选择任何类型");
        return;
      } else {
        getCanvasOneSceneryInfoByNameAndRelation(
          _this.currentType,
          _this.currentNode[0]
        )
          .then((res) => {
            if (res.code != 0) {
              //@ts-ignore
              ElMessage({
                type: "error",
                message: res.msg,
              });
            } else {
              // alert("成功");
              console.log("成功");
              console.log(res.data);
              console.log("成功");
              _this.$emit(
                "queryRelation",
                res.data,
                _this.currentNode[0],
                _this.currentType
              );
            }
          })
          .catch((error) => {
            ElMessage({
              type: "error",
              message: error.message,
            });
          });
      }
      console.log(this.currentType);
    },
  },
};
</script>

<style scoped>
.card-header {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  height: 40px;
  padding: 0;
  padding-top: 5px;
}

.text {
  font-size: 14px;
}
::v-deep .el-input__inner {
  font-size: 12px;
}
.box-card {
  width: 200px;
  /* display: flex;
  flex-direction: column;
  justify-content: space-between; */
  background-color: rgba(255, 255, 255, 0.6);
}
.detail-panel {
  position: fixed;
  right: 3px;
  top: 150px;
  width: 200px;
  height: 400px;
  cursor: pointer;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
}
.detail-info {
  width: 50%;
  text-align: left;
  font-size: 12px;
  font-weight: bolder;
}
.type-select {
  margin-top: 10px;
  height: 20px;
}
.submit-btn {
  width: 100%;
  height: 30px;
  line-height: 0px;
  margin-top: 0px;
}
.span-style-front {
  display: flex;
  justify-content: center;
  align-items: center;
  min-width: 160px;
  padding: 5px;
  min-height: 20px;
  line-height: 20px;
  color: #ffffff;
  font-size: 12px;
  font-weight: 600;
  background-color: #e8604c;
  border-radius: 5px;
  margin-top: 10px;
}

.switch-container {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: center;
}
</style>
