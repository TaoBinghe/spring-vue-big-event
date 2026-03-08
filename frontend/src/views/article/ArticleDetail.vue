<template>
  <div class="article-detail">
    <el-card v-loading="loading">
      <template #header>
        <div class="header-inner">
          <span>文章详情</span>
          <span v-if="article">
            <el-button link @click="$router.push('/article')">返回列表</el-button>
            <el-button type="primary" link @click="$router.push(`/article/edit/${article.id}`)">编辑</el-button>
          </span>
        </div>
      </template>
      <div v-if="article">
        <div class="detail-meta">
          <h1 class="title">{{ article.title }}</h1>
          <div class="meta">
            <el-tag size="small">{{ article.state }}</el-tag>
            <span class="meta-item">分类：{{ categoryName }}</span>
            <span class="meta-item">创建：{{ article.createTime }}</span>
            <span class="meta-item">更新：{{ article.updateTime }}</span>
          </div>
        </div>
        <div class="cover" v-if="article.coverImg">
          <img :src="article.coverImg" alt="封面" />
        </div>
        <div class="content">{{ article.content }}</div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getArticleDetail } from '../../api/article'
import { getCategoryList } from '../../api/category'

const route = useRoute()
const article = ref(null)
const categories = ref([])
const loading = ref(false)

const categoryName = computed(() => {
  if (!article.value?.categoryId) return '-'
  const c = categories.value.find(c => c.id === article.value.categoryId)
  return c?.categoryName ?? '-'
})

const loadDetail = async () => {
  const id = route.params.id
  if (!id) return
  loading.value = true
  try {
    const [detailRes, categoryRes] = await Promise.all([
      getArticleDetail(id),
      getCategoryList()
    ])
    article.value = detailRes.data
    categories.value = categoryRes.data || []
  } finally {
    loading.value = false
  }
}

onMounted(loadDetail)
</script>

<style scoped>
.header-inner { display: flex; align-items: center; justify-content: space-between; }
.detail-meta { margin-bottom: 16px; }
.title { font-size: 22px; margin-bottom: 12px; }
.meta { color: #666; font-size: 14px; }
.meta-item { margin-right: 16px; }
.cover { margin: 16px 0; }
.cover img { max-width: 100%; max-height: 300px; object-fit: contain; border-radius: 4px; }
.content { white-space: pre-wrap; line-height: 1.6; }
</style>
