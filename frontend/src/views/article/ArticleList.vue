<template>
  <div class="article-list">
    <el-card>
      <template #header>
        <span>文章管理</span>
        <el-button type="primary" style="float: right" @click="$router.push('/article/edit')">发布文章</el-button>
      </template>
      <div class="filter">
        <el-select v-model="query.categoryId" placeholder="分类" clearable style="width: 120px" @change="loadList">
          <el-option v-for="c in categories" :key="c.id" :label="c.categoryName" :value="c.id" />
        </el-select>
        <el-select v-model="query.state" placeholder="状态" clearable style="width: 120px; margin-left: 10px" @change="loadList">
          <el-option label="已发布" value="已发布" />
          <el-option label="草稿" value="草稿" />
        </el-select>
      </div>
      <el-table :data="list" v-loading="loading" stripe>
        <el-table-column prop="title" label="标题" min-width="120" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column prop="state" label="状态" width="80" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="$router.push(`/article/detail/${row.id}`)">查看</el-button>
            <el-button type="primary" link @click="$router.push(`/article/edit/${row.id}`)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="query.pageNum"
        v-model:page-size="query.pageSize"
        :total="total"
        :page-sizes="[5, 10, 20]"
        layout="total, sizes, prev, pager, next"
        style="margin-top: 16px"
        @current-change="loadList"
        @size-change="loadList"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getArticleList, deleteArticle } from '../../api/article'
import { getCategoryList } from '../../api/category'
import { ElMessage, ElMessageBox } from 'element-plus'

const list = ref([])
const categories = ref([])
const total = ref(0)
const loading = ref(false)

const query = reactive({
  pageNum: 1,
  pageSize: 10,
  categoryId: undefined,
  state: undefined
})

const loadCategories = async () => {
  const res = await getCategoryList()
  categories.value = res.data || []
}

const loadList = async () => {
  loading.value = true
  try {
    const params = { pageNum: query.pageNum, pageSize: query.pageSize }
    if (query.categoryId != null) params.categoryId = query.categoryId
    if (query.state) params.state = query.state
    const res = await getArticleList(params)
    const items = res.data?.items || []
    const categoryMap = Object.fromEntries((categories.value || []).map(c => [c.id, c.categoryName]))
    list.value = items.map(a => ({ ...a, categoryName: categoryMap[a.categoryId] || '-' }))
    total.value = res.data?.total ?? 0
  } finally {
    loading.value = false
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除该文章吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteArticle(row.id)
    ElMessage.success('删除成功')
    loadList()
  }).catch(() => {})
}

onMounted(() => {
  loadCategories()
  loadList()
})
</script>

<style scoped>
.filter { margin-bottom: 16px; }
</style>
