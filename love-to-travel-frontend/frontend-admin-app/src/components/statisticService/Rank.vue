<template>
  <div>123</div>
</template>
<script lang="ts" setup></script>

<script>
import {deleteSceneryInfo, getSceneryInfo, postSceneryInfo, updateSceneryInfo} from '@/apis/serviceManage/scenery.js'

export default {
  data() {
    return {
      dialogFormVisible: false, //对话框是否显示
      queryStr: "",  //查询条件
      multipleSelection: [], //多选删除
      tableData: [], //省份信息数据
      queryData: [],//保存查询数据
      form: {},   //对话框表单数据
      formLabelWidth: "140px",  //对话框label宽度
      title: "",  //对话框标题
      btnName: ""  //对话框按钮文字
    }
  },
  mounted() {
  },
  methods: {}

}
</script>



<style scoped>

</style>