# SpringBoot+Vue 的微人事系统

> 来源于 王松 的 《SpringBoot + Vue 全栈开发实战》 一书


## 技术需求

主要分以下两部分构成:

- Vue 前台
	- element 桌面端组件库（[官方文档](http://element-cn.eleme.io/#/zh-CN/component)）
	- nodejs
	- webpack
	

- SpringBoot 后台


## 项目构建

### 前端项目构建

- 安装 nodejs

- npm i -g vue-cli

- vue init webpack vuehr

	> <font color=red size=4 face=楷体>常见错误 1：</font>
	>
	> npm ERR! code EINTEGRITY npm ERR! sha1- ***
	>
	> <font color=red size=4 face=楷体>解决方法：</font>
	>
	> - 方法一： 修改 npm 镜像 
	>	
	>	npm config set registry http://registry.cnpmjs.org/
	> - 方法二： 清空 npm 缓存并降低 npm 版本为 4.x
	> 
	>	npm cache verify && npm i -g npm@4
	
- cd vuehr && npm run dev

	> 弹出以下提示即构建成功：
	>
	> your application is running here: http://localhost:8080

### 后端项目构建

- 新建 SpringBoot 项目
- 登录模块
  - 权限认证采用 Spring Security 实现
  - 数据库访问使用 mybatis
  - 信息缓存使用 redis