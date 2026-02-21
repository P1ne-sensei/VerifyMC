# Tasks

- [x] Task 1: 创建 OpsManager 类用于读取和解析 ops.json 文件
  - [x] SubTask 1.1: 创建 OpsManager 类，实现 ops.json 文件读取功能
  - [x] SubTask 1.2: 实现 ops.json 解析逻辑（支持 UUID 和 name 字段）
  - [x] SubTask 1.3: 添加缓存机制，避免频繁文件读取
  - [x] SubTask 1.4: 在 PluginContext 中集成 OpsManager

- [x] Task 2: 重构 AdminLoginHandler 支持新的登录验证流程
  - [x] SubTask 2.1: 修改请求参数解析，支持 username（用户名或邮箱）和 password
  - [x] SubTask 2.2: 实现用户名/邮箱查找用户逻辑
  - [x] SubTask 2.3: 实现多数据源密码验证（AuthMe 优先，VerifyMC 备用）
  - [x] SubTask 2.4: 集成 OpsManager 判断管理员权限
  - [x] SubTask 2.5: 更新错误消息和响应逻辑

- [x] Task 3: 更新 WebAuthHelper 支持存储用户信息
  - [x] SubTask 3.1: 修改 Token 存储结构，关联用户名
  - [x] SubTask 3.2: 添加获取当前登录用户名的方法
  - [x] SubTask 3.3: 更新 AdminAuthUtil 支持获取用户信息

- [x] Task 4: 更新 ConfigManager 移除 admin.password 配置
  - [x] SubTask 4.1: 移除 getAdminPassword() 方法
  - [x] SubTask 4.2: 更新配置文件模板和帮助文档

- [x] Task 5: 更新前端登录组件
  - [x] SubTask 5.1: 修改 LoginForm.vue 支持用户名/邮箱输入
  - [x] SubTask 5.2: 更新 api.ts 接口定义
  - [x] SubTask 5.3: 更新国际化文本 zh.json 和 en.json

- [x] Task 6: 更新后端国际化文本
  - [x] SubTask 6.1: 更新 messages_zh.properties 添加新的错误消息
  - [x] SubTask 6.2: 更新 messages_en.properties 添加新的错误消息

- [x] Task 7: 测试和验证
  - [x] SubTask 7.1: 测试使用用户名登录
  - [x] SubTask 7.2: 测试使用邮箱登录
  - [x] SubTask 7.3: 测试非管理员登录被拒绝
  - [x] SubTask 7.4: 测试密码错误场景
  - [x] SubTask 7.5: 测试 ops.json 不存在场景

# Task Dependencies
- [Task 2] depends on [Task 1], [Task 3]
- [Task 5] depends on [Task 2]
- [Task 7] depends on [Task 1], [Task 2], [Task 3], [Task 4], [Task 5], [Task 6]
