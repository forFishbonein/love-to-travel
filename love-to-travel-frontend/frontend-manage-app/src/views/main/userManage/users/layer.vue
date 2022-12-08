<template>
  <Layer :layer="layer" @confirm="submit">
    <el-form :model="ruleForm" :rules="rules" ref="form" label-width="120px" style="margin-right:30px;">
      <el-form-item label="邮箱：" prop="email">
        <el-input v-model="ruleForm.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="用户名：" prop="name">
        <el-input v-model="ruleForm.name" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="状态：" prop="status">
        <el-input v-model="ruleForm.status" placeholder="请输入状态"></el-input>
      </el-form-item>
      <el-form-item label="性别：" prop="gender">
        <el-input v-model="ruleForm.gender" placeholder="请输入性别"></el-input>
      </el-form-item>
      <el-form-item label="电话：" prop="tele">
        <el-input v-model="ruleForm.tele" placeholder="请输入电话"></el-input>
      </el-form-item>
      <el-form-item label="出生日期：" prop="birthday">
        <el-input v-model="ruleForm.birthday" placeholder="请输入出生日期"></el-input>
      </el-form-item>
      <el-form-item label="岗位：" prop="post">
        <el-input v-model="ruleForm.post" placeholder="请输入岗位"></el-input>
      </el-form-item>
      <el-form-item label="职业：" prop="profession">
        <el-input v-model="ruleForm.profession" placeholder="请输入职业"></el-input>
      </el-form-item>
      <el-form-item label="签名：" prop="signature">
        <el-input v-model="ruleForm.signature" placeholder="请输入签名"></el-input>
      </el-form-item>
      <el-form-item label="地址：" prop="address">
        <el-input v-model="ruleForm.address" placeholder="请输入地址"></el-input>
      </el-form-item>
    </el-form>
  </Layer>
</template>

<script lang="ts">
import { defineComponent, ref, reactive } from 'vue'
import Layer from '@/components/layer/index.vue'
import { add, update } from '@/api/table'
export default defineComponent({
  components: {
    Layer
  },
  props: {
    layer: {
      type: Object,
      default: () => {
        return {
          show: false,
          title: '',
          showButton: true
        }
      }
    }
  },
  setup(props, ctx) {
    let ruleForm = reactive({
      name: ''
    })
    const rules = {
      name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
      email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
    }
    return {
      ruleForm,
      rules,
    }
  },
  methods: {
    submit() {
      this.$refs['form'].validate((valid: boolean) => {
        if (valid) {
          let params = this.ruleForm
          if (this.layer.row) {
            this.updateForm(params)
          } else {
            this.addForm(params)
          }
        } else {
          return false;
        }
      });
    },
    // 新增提交事件
    addForm(params: object) {
      add(params).then(res => {
        this.$message({
          type: 'success',
          message: '新增成功'
        })
        this.layer.show = false
        this.$emit('getTableData', true)
      })
    },
    // 编辑提交事件
    updateForm(params: object) {
      update(params).then(res => {
        this.$message({
          type: 'success',
          message: '编辑成功'
        })
        this.$emit('getTableData', false)
      })
    }
  }
})
</script>

<style lang="scss" scoped>
  
</style>