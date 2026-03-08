<template>
  <div class="profile">
    <el-card>
      <template #header><span>基本信息</span></template>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" style="max-width: 400px">
        <el-form-item label="用户名">
          <el-input :model-value="userStore.userInfo?.username" disabled />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="1~10位" maxlength="10" show-word-limit />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="邮箱" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="saving" @click="saveBasic">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header><span>头像</span></template>
      <div class="avatar-wrap">
        <el-avatar :size="80" :src="userStore.userInfo?.userPic" v-if="userStore.userInfo?.userPic" />
        <el-avatar :size="80" v-else>{{ (userStore.userInfo?.nickname || userStore.userInfo?.username || '')?.[0] }}</el-avatar>
        <el-upload
          class="avatar-upload"
          :show-file-list="false"
          :before-upload="beforeAvatarUpload"
          :http-request="uploadAvatar"
          accept="image/*"
        >
          <el-button size="small" type="primary">更换头像</el-button>
        </el-upload>
      </div>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header><span>修改密码</span></template>
      <el-form :model="pwdForm" :rules="pwdRules" ref="pwdFormRef" label-width="100px" style="max-width: 400px">
        <el-form-item label="原密码" prop="old_pwd">
          <el-input v-model="pwdForm.old_pwd" type="password" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="new_pwd">
          <el-input v-model="pwdForm.new_pwd" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认新密码" prop="re_pwd">
          <el-input v-model="pwdForm.re_pwd" type="password" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="pwdLoading" @click="savePwd">修改密码</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { useUserStore } from '../../stores/user'
import { updateUser, updateAvatar, updatePwd } from '../../api/user'
import { uploadFile } from '../../api/upload'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const formRef = ref(null)
const pwdFormRef = ref(null)
const saving = ref(false)
const pwdLoading = ref(false)

const form = reactive({ nickname: '', email: '' })
const rules = {
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 1, max: 10, message: '1~10位', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ]
}

watch(() => userStore.userInfo, (info) => {
  if (info) {
    form.nickname = info.nickname || ''
    form.email = info.email || ''
  }
}, { immediate: true })

const saveBasic = async () => {
  await formRef.value?.validate()
  saving.value = true
  try {
    await updateUser({
      id: userStore.userInfo.id,
      username: userStore.userInfo.username,
      nickname: form.nickname,
      email: form.email
    })
    await userStore.fetchUserInfo()
    ElMessage.success('保存成功')
  } finally {
    saving.value = false
  }
}

const beforeAvatarUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  if (!isImage) ElMessage.error('只能上传图片')
  return isImage
}

const uploadAvatar = async ({ file }) => {
  const res = await uploadFile(file)
  const url = res.data
  await updateAvatar(url)
  await userStore.fetchUserInfo()
  ElMessage.success('头像已更新')
}

const pwdForm = reactive({ old_pwd: '', new_pwd: '', re_pwd: '' })
const validateRePwd = (_rule, value, callback) => {
  if (value !== pwdForm.new_pwd) callback(new Error('两次新密码不一致'))
  else callback()
}
const pwdRules = {
  old_pwd: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  new_pwd: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  re_pwd: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    { validator: validateRePwd, trigger: 'blur' }
  ]
}

const savePwd = async () => {
  await pwdFormRef.value?.validate()
  pwdLoading.value = true
  try {
    await updatePwd(pwdForm)
    ElMessage.success('密码已修改')
    pwdForm.old_pwd = ''
    pwdForm.new_pwd = ''
    pwdForm.re_pwd = ''
    pwdFormRef.value?.resetFields()
  } finally {
    pwdLoading.value = false
  }
}
</script>

<style scoped>
.profile { max-width: 600px; }
.avatar-wrap { display: flex; align-items: center; gap: 20px; }
.avatar-upload { margin-left: 10px; }
</style>
