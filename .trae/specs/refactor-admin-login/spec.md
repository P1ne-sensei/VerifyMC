# 后台登录系统重构 Spec

## Why
当前后台登录系统使用固定的管理员密码（存储在 config.yml 中），存在安全风险且无法区分不同管理员。需要重构为基于玩家数据的登录系统，通过读取 `ops.json` 判断管理员身份，实现更安全、更灵活的身份认证。

## What Changes
- **BREAKING**: 移除 `config.yml` 中的 `admin.password` 配置项
- 新增玩家登录验证功能：支持用户名或邮箱 + 密码登录
- 新增 `ops.json` 文件读取功能：从服务器根目录读取管理员列表
- 新增密码验证逻辑：优先 AuthMe 密码，其次 VerifyMC 密码
- 重构 `AdminLoginHandler`：支持新的登录验证流程
- 更新前端登录表单：支持用户名/邮箱输入
- 更新国际化文本：适配新的登录流程

## Impact
- Affected specs: 后台管理认证系统
- Affected code:
  - `AdminLoginHandler.java` - 登录处理逻辑
  - `WebAuthHelper.java` - Token 管理（可能需要存储用户信息）
  - `ConfigManager.java` - 移除 admin.password 配置
  - `LoginForm.vue` - 前端登录表单
  - `api.ts` - API 接口定义
  - `zh.json` / `en.json` - 国际化文本
  - `messages_zh.properties` / `messages_en.properties` - 后端国际化

## ADDED Requirements

### Requirement: 玩家数据登录验证
系统 SHALL 支持玩家使用用户名或邮箱 + 密码登录后台管理系统。

#### Scenario: 使用用户名登录成功
- **WHEN** 玩家输入有效的用户名和正确的密码
- **AND** 该用户名在 ops.json 中存在
- **THEN** 系统返回登录成功并颁发 Token

#### Scenario: 使用邮箱登录成功
- **WHEN** 玩家输入有效的邮箱和正确的密码
- **AND** 该邮箱对应的用户名在 ops.json 中存在
- **THEN** 系统返回登录成功并颁发 Token

#### Scenario: 非管理员登录失败
- **WHEN** 玩家输入正确的用户名和密码
- **BUT** 该用户名不在 ops.json 中
- **THEN** 系统返回"无权限"错误

#### Scenario: 密码错误登录失败
- **WHEN** 玩家输入正确的用户名
- **BUT** 密码不正确
- **THEN** 系统返回"用户名或密码错误"

### Requirement: ops.json 管理员判断
系统 SHALL 从服务器根目录读取 `ops.json` 文件判断用户是否为管理员。

#### Scenario: ops.json 存在且包含用户
- **WHEN** 系统验证登录用户身份
- **AND** ops.json 文件存在且可读
- **AND** 用户名在 ops.json 的列表中
- **THEN** 该用户被识别为管理员

#### Scenario: ops.json 不存在或为空
- **WHEN** 系统验证登录用户身份
- **AND** ops.json 文件不存在或为空
- **THEN** 所有用户登录请求返回"系统配置错误"

### Requirement: 多数据源密码验证
系统 SHALL 支持从 AuthMe 和 VerifyMC 两个数据源验证密码。

#### Scenario: AuthMe 密码验证优先
- **WHEN** 用户启用了 AuthMe 集成
- **AND** 用户在 AuthMe 数据库中存在
- **THEN** 系统优先使用 AuthMe 密码进行验证

#### Scenario: VerifyMC 密码验证备用
- **WHEN** 用户未启用 AuthMe 集成
- **OR** 用户在 AuthMe 数据库中不存在
- **THEN** 系统使用 VerifyMC 用户数据库中的密码进行验证

### Requirement: 前端登录表单更新
系统 SHALL 更新前端登录表单以支持新的登录方式。

#### Scenario: 登录表单显示
- **WHEN** 用户访问登录页面
- **THEN** 显示用户名/邮箱输入框和密码输入框

#### Scenario: 登录请求发送
- **WHEN** 用户提交登录表单
- **THEN** 发送包含 username（用户名或邮箱）、password、language 的请求

## MODIFIED Requirements

### Requirement: 后台认证系统
系统 SHALL 使用基于玩家数据的认证方式替代原有的固定密码认证。

**原有行为**: 使用 config.yml 中的 admin.password 进行验证
**新行为**: 使用玩家用户名/邮箱 + 密码进行验证，并通过 ops.json 判断管理员权限

## REMOVED Requirements

### Requirement: 固定管理员密码
**Reason**: 安全风险，无法区分不同管理员
**Migration**: 使用 ops.json 中的玩家列表替代
