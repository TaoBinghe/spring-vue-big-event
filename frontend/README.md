# 大事件前端

Vue 3 + Vite + Element Plus + Pinia + Vue Router。

## 开发

```bash
npm install
npm run dev
```

默认访问 http://localhost:5173。接口通过 Vite 代理转发到后端 `http://localhost:8080`，请确保后端已启动。

## 构建

```bash
npm run build
```

输出在 `dist/` 目录。

## 功能

- 登录 / 注册（x-www-form-urlencoded）
- 文章管理：列表（分页、按分类/状态筛选）、发布、编辑、删除
- 分类管理：列表、新增、编辑、删除
- 用户中心：基本信息、头像上传、修改密码
- 请求头自动携带 JWT（localStorage 的 token），401 时跳转登录页
