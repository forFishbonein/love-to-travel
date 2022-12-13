<script>
import { ElConfigProvider } from 'element-plus'
import zhCn from 'element-plus/lib/locale/lang/zh-cn'

export default {
  name: 'App',
  components: {
    ElConfigProvider
  },
  setup() {
    return {
      locale: zhCn
    }
  }
}
</script>

<template>
  <el-config-provider :locale="locale">
    <router-view></router-view>
  </el-config-provider>
</template>

<style scoped></style>
