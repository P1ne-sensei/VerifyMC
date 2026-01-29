[English](https://github.com/KiteMC/VerifyMC/releases/tag/v1.2.6) | 简体中文 | [官方文档](https://kitemc.com/docs/verifymc/)

# VerifyMC v1.2.6 更新日志

## 🐛 Bug 修复

### Velocity-CTD 兼容性修复 (Issue #14)
- 修复了与 Velocity 分支版本（如 Velocity-CTD 3.5.0）的 Guice 依赖注入错误
- 将 `javax.inject.Inject` 更改为 `com.google.inject.Inject` 以提升兼容性

### 版本号显示修复 (Issue #15)
- 修复了插件中版本号显示错误的问题
- 统一了所有配置文件中的版本号管理

## ✨ 新功能

### Minecraft 1.21.11 支持 (Issue #16)
- 添加对 Minecraft 1.21.11 的官方支持
- 更新 Velocity API 至 3.4.0-SNAPSHOT
- 更新 Waterfall API 至 1.21-R0.1-SNAPSHOT
