<template>
  <div class="article-edit">
    <el-card>
      <template #header>
        <span>{{ isEdit ? '编辑文章' : '发布文章' }}</span>
        <el-button link @click="$router.push('/article')">返回列表</el-button>
      </template>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" v-loading="loading">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="1~10个字符" maxlength="10" show-word-limit />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 200px">
            <el-option v-for="c in categories" :key="c.id" :label="c.categoryName" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="封面" prop="coverImg">
          <div class="cover-upload">
            <img v-if="form.coverImg" :src="form.coverImg" class="cover-preview" />
            <el-upload
              v-else
              :show-file-list="false"
              :before-upload="beforeCoverUpload"
              :http-request="uploadCover"
              accept="image/*"
            >
              <el-button type="primary" size="small">上传封面</el-button>
            </el-upload>
            <el-button v-if="form.coverImg" type="danger" link size="small" @click="form.coverImg = ''">删除</el-button>
          </div>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-radio-group v-model="form.state">
            <el-radio value="已发布">已发布</el-radio>
            <el-radio value="草稿">草稿</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="正文" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="10" placeholder="文章正文" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="submitting" @click="submit">提交</el-button>
          <el-button @click="$router.push('/article')">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getArticleDetail, addArticle, updateArticle } from '../../api/article'
import { getCategoryList } from '../../api/category'
import { uploadFile } from '../../api/upload'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const formRef = ref(null)
const loading = ref(false)
const submitting = ref(false)
const categories = ref([])

const id = computed(() => route.params.id)
const isEdit = computed(() => !!id.value)

const form = reactive({
  id: null,
  title: '',
  categoryId: undefined,
  coverImg: '',
  state: '草稿',
  content: ''
})

const rules = {
  title: [
    { required: true, message: '请输入标题', trigger: 'blur' },
    { min: 1, max: 10, message: '1~10个字符', trigger: 'blur' }
  ],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
  coverImg: [{ required: true, message: '请上传封面', trigger: 'change' }],
  state: [{ required: true, message: '请选择状态', trigger: 'change' }],
  content: [{ required: true, message: '请输入正文', trigger: 'blur' }]
}

const loadCategories = async () => {
  const res = await getCategoryList()
  categories.value = res.data || []
}

const loadDetail = async () => {
  if (!id.value) return
  loading.value = true
  try {
    const res = await getArticleDetail(id.value)
    Object.assign(form, res.data)
  } finally {
    loading.value = false
  }
}

const beforeCoverUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  if (!isImage) ElMessage.error('只能上传图片')
  return isImage
}

const uploadCover = async ({ file }) => {
  const res = await uploadFile(file)
  form.coverImg = res.data
}

const submit = async () => {
  await formRef.value?.validate()
  submitting.value = true
  try {
    if (isEdit.value) {
      await updateArticle(form)
      ElMessage.success('修改成功')
    } else {
      await addArticle(form)
      ElMessage.success('发布成功')
    }
    router.push('/article')
  } finally {
    submitting.value = false
  }
}

onMounted(async () => {
  await loadCategories()
  await loadDetail()
})
</script>

<style scoped>
.cover-upload { display: flex; align-items: center; gap: 12px; }
.cover-preview { max-width: 200px; max-height: 120px; object-fit: cover; border-radius: 4px; }
</style>
