[English](https://github.com/KiteMC/VerifyMC/releases/tag/v1.5.0) | 简体中文 | [官方文档](https://kitemc.com/docs/verifymc/)

# VerifyMC v1.5.0 更新日志

从本版本起，VerifyMC 采用规范的语义化版本号（SemVer）。此前的 v1.2.5、v1.2.8、v1.2.10 均引入了大量新功能却只递增了修订号，不符合 SemVer 规范。由于历史 tag 对应的 jar 包内已硬编码版本号无法追溯修改，因此从本版本起纠正：v1.2.5 对应 v1.3.0、v1.2.8 对应 v1.4.0、当前对应 v1.5.0。

## 🔒 安全修复

- 管理员登录暴力破解防护（每 IP 5 分钟内最多 5 次尝试）
- WebSocket 连接现在需要管理员 Token 认证
- 用户密码改为 SHA-256 哈希存储，不再明文保存
- `/api/check-whitelist` 不再返回用户邮箱地址
- `X-Forwarded-For` 请求头默认不再信任（新增 `trust_proxy` 配置项）
- 验证码发送增加 IP 维度限流（每 IP 10 分钟内最多 10 次）
- 修复注册接口竞态条件，防止重复用户名
- 修复注册接口验证顺序（先校验格式再查数据库）

## 🔧 Bug 修复与改进

- plugin.yml 增加 `api-version`，修复 Legacy Material Support 警告
- 新增 Leaves 服务器核心识别
- 修复 Jakarta Mail 资源在 shaded jar 中缺失的问题
- 修复 Paper 热重载时资源初始化失败
- 修复无效正则表达式导致的验证崩溃（自动回退到默认值）
- 过期 Token/问卷/限流记录自动清理，清理线程改为守护线程
- 语言缓存改为线程安全的 ConcurrentHashMap
