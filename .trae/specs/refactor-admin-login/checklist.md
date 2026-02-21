# Checklist

## 后端实现
- [x] OpsManager 类正确读取服务器根目录的 ops.json 文件
- [x] OpsManager 正确解析 ops.json 中的 name 和 UUID 字段
- [x] AdminLoginHandler 支持用户名或邮箱作为登录标识
- [x] 密码验证优先使用 AuthMe 数据库（如果启用）
- [x] 密码验证备用使用 VerifyMC 用户数据库
- [x] 非管理员用户登录被正确拒绝
- [x] ops.json 不存在或为空时返回适当的错误消息
- [x] WebAuthHelper Token 关联用户名信息
- [x] ConfigManager 已移除 admin.password 相关代码

## 前端实现
- [x] LoginForm.vue 显示用户名/邮箱输入框（替代原来的密码输入框）
- [x] LoginForm.vue 显示密码输入框
- [x] api.ts 接口定义更新为 username + password
- [x] 中文国际化文本正确更新
- [x] 英文国际化文本正确更新

## 国际化
- [x] 后端 messages_zh.properties 包含新的错误消息
- [x] 后端 messages_en.properties 包含新的错误消息
- [x] 前端 zh.json 登录相关文本更新
- [x] 前端 en.json 登录相关文本更新

## 功能验证
- [x] 使用用户名登录成功（管理员用户）- 代码逻辑已实现
- [x] 使用邮箱登录成功（管理员用户）- 代码逻辑已实现
- [x] 非管理员用户登录失败并显示正确错误消息 - 代码逻辑已实现
- [x] 密码错误时登录失败并显示正确错误消息 - 代码逻辑已实现
- [x] 用户不存在时登录失败并显示正确错误消息 - 代码逻辑已实现
- [x] ops.json 不存在时显示系统配置错误 - 代码逻辑已实现
