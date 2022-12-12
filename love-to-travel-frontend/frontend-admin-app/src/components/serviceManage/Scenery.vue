<script setup lang="ts" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html"></script>

<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <div class="query">
          <el-input v-model="queryStr" placeholder="Please input" /> &nbsp;&nbsp;
          <el-button class="button" type="primary" round @click="queryInfo">查询</el-button>
        </div>
        <div>
          <el-button class="button" type="success" round @click="openAddDialog">添加</el-button>
          <el-button class="button" type="warning" round @click="multiDelete">删除</el-button>

    <el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />

      <el-table-column prop="id" label="景区id" width="100" />
      <el-table-column prop="name" label="景区名称" width="100" />
      <el-table-column prop="introduction" label="景区简介" width="200" />
      <el-table-column prop="score" label="景区评分" width="150" />
      <el-table-column prop="ticket" label="景区门票" width="150" />
      <el-table-column prop="opening" label="开放时间" width="150" />
      <el-table-column prop="lng" label="经度" width="150" />
      <el-table-column prop="lat" label="纬度" width="150" />
      <el-table-column prop="level" label="景区等级" width="150" />
      <el-table-column prop="score" label="景区评分" width="150" />
      <el-table-column prop="address" label="地址" width="150" />
      <el-table-column prop="score" label="景区评分" width="150" />
      <el-table-column fixed="right" label="Operations" width="120">
        <template #default>
          <el-button link type="primary" size="small" @click="singleDelete"
          >删除</el-button
          >
          <el-button link type="primary" size="small" @click="openUpdateDialog">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <!-- 对话框：添加修改功能 -->
  <el-dialog v-model="dialogFormVisible" :title="title">
    <el-form :model="form">
      <el-form-item label="商品编号" :label-width="formLabelWidth">
        <el-input v-model="form.isbn" autocomplete="off" />
      </el-form-item>
      <el-form-item label="商品名称" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="商品价格" :label-width="formLabelWidth">
        <el-input v-model="form.price" autocomplete="off" />
      </el-form-item>
      <el-form-item label="商品信息" :label-width="formLabelWidth">
        <el-input v-model="form.imformation" autocomplete="off" />
      </el-form-item>
      <el-form-item label="商品地址" :label-width="formLabelWidth">
        <el-input v-model="form.image" autocomplete="off" />
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="btnAddUpdate">
          {{btnName}}
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

  <script>
export default {
  name: "Scenery"
}
</script>

<style scoped>

</style>