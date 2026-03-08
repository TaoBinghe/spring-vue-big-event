<template>
  <div class="category-page">
    <el-card>
      <template #header>
        <span>分类管理</span>
        <el-button type="primary" style="float: right" @click="openDialog()">新增分类</el-button>
      </template>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="categoryName" label="分类名称" />
        <el-table-column prop="categoryAlias" label="分类别名" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button type="primary" link @click="openDialog(row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑分类' : '新增分类'" width="400px" @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="分类名称" />
        </el-form-item>
        <el-form-item label="分类别名" prop="categoryAlias">
          <el-input v-model="form.categoryAlias" placeholder="分类别名" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="submit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getCategoryList, getCategoryDetail, addCategory, updateCategory, deleteCategory } from '../../api/category'
import { ElMessage, ElMessageBox } from 'element-plus'

const list = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const formRef = ref(null)
const submitting = ref(false)

const form = reactive({ id: null, categoryName: '', categoryAlias: '' })
const rules = {
  categoryName: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
  categoryAlias: [{ required: true, message: '请输入分类别名', trigger: 'blur' }]
}

const loadList = async () => {
  loading.value = true
  try {
    const res = await getCategoryList()
    list.value = res.data || []
  } finally {
    loading.value = false
  }
}

const openDialog = async (row) => {
  if (row?.id) {
    const res = await getCategoryDetail(row.id)
    Object.assign(form, res.data)
  } else {
    form.id = null
    form.categoryName = ''
    form.categoryAlias = ''
  }
  dialogVisible.value = true
}

const resetForm = () => {
  formRef.value?.resetFields()
}

const submit = async () => {
  await formRef.value?.validate()
  submitting.value = true
  try {
    if (form.id) {
      await updateCategory({ id: form.id, categoryName: form.categoryName, categoryAlias: form.categoryAlias })
      ElMessage.success('修改成功')
    } else {
      await addCategory({ categoryName: form.categoryName, categoryAlias: form.categoryAlias })
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadList()
  } finally {
    submitting.value = false
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该分类吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteCategory(row.id)
    ElMessage.success('删除成功')
    loadList()
  }).catch(() => {})
}

onMounted(loadList)
</script>

<style scoped>
.category-page { }
</style>
