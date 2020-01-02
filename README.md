# MeetHere

## Overview

这个项目由Springboot和Vue搭建而成，采用前后端分离框架，所有内容为本学期开发。

测试包含单元测试，接口测试，系统测试和性能测试，其中单元测试分为前端单元测试与后端单元测试。

本项目共有四人开发，开发人员与任务分配详见最后，开发日期为2019.10.1至2020.1.3。

## Environment

#### Backend

SpringBoot 2.1.9

#### Frontend

Vue 2.6.10 + Axois + vuex + vue-router + element-ui

Created  by @vue/cli 4.1.1

#### Database

**Local** MySQL 8

**Server** MariaDB 10.1.43


#### Server

Tecent Cloud, Centos 7.6 64位

1 Core，2G Memory，1Mbps Brandwidth

## Run this project

#### Backend

It is located at 'backend' folder.

Use IDEA to run this project, it will configure it automatically.

#### Frontend

It is located at 'frontend' folder.

Use npm:

```bash
npm install
npm run server --fix
```

Use yarn:

```bash
yarn
yarn server --fix
```

## Run tests

#### Unit Test

For backend, use IDEA to run all tests. It is located at 'backend/src/test' folder.

For fronend, it is located at 'fronend2.0/tests/unit' folder.

 enter:
```bash
npm run test:unit
```
or
```bash
yarn test:unit
```

#### Interface Test

It is written in the backend project. It is located at 'backend/src/test' folder.
Use IDEA to run test.

#### System Test

For manager system test, it uses selenium + maven + allure + junit5.
It is located at 'ManagerSystemTest' folder.

To view report:

``` bash
$./allure serve allure-results
```

For user page system test, it uses nightwatch. It is located at 'fronend2.0/tests/e2e' folder.

To test it:

``` bash
npm run test:e2e --fix
```
or
``` bash
yarn test:e2e --fix
```

#### Performance Test

We use locus. It is located at 'performance test' folder.

``` bash
locust -f FILE_NAME.py --host=http://localhost:8081
```



## Developer

朱嘉忆：产品经理+前台开发+前台单元测试+系统测试+服务器管理

唐巍：前台开发+前台单元测试+系统测试

雷申：后台开发+后台单元测试+接口测试+性能测试

陶仪青：后台开发+后台单元测试+接口测试+性能测试
