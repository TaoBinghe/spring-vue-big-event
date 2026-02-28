# 清除历史提交中的阿里云密钥，以便通过 GitHub 推送
# 在项目根目录 e:\java_study\big-event 用 PowerShell 执行

# 步骤 0：确认当前 AliOssUtil.java 已是安全版本（无硬编码密钥）
Write-Host "步骤 1: 记下当前提交号（安全版本）"
$safeRef = git rev-parse HEAD
Write-Host "请复制这个提交号: $safeRef"
Write-Host ""

Write-Host "步骤 2: 执行交互式变基（会打开编辑器）"
Write-Host "  git rebase -i fef7f38^"
Write-Host "  在编辑器里把第一行 pick 改成 edit，保存并关闭。"
Write-Host ""

Write-Host "步骤 3: 变基停住后，在项目根目录执行下面这一整行（把 YOUR_REF 换成步骤1的提交号）："
Write-Host "  git show YOUR_REF:backend/src/main/java/com/binghetao/utils/AliOssUtil.java | Set-Content -Path backend/src/main/java/com/binghetao/utils/AliOssUtil.java -Encoding utf8"
Write-Host "  或直接执行（已用当前 ref）："
Write-Host "  git show ${safeRef}:backend/src/main/java/com/binghetao/utils/AliOssUtil.java | Set-Content -Path backend/src/main/java/com/binghetao/utils/AliOssUtil.java -Encoding utf8"
Write-Host ""

Write-Host "步骤 4: 把修改加入该提交并继续变基"
Write-Host "  git add backend/src/main/java/com/binghetao/utils/AliOssUtil.java"
Write-Host "  git commit --amend --no-edit"
Write-Host "  git rebase --continue"
Write-Host ""

Write-Host "步骤 5: 强制推送"
Write-Host "  git push --force origin master"
