# MeetHere

<p>
  <a href="https://github.com/vuejs/vue">
    <img src="https://img.shields.io/badge/vue-2.6.10-brightgreen.svg" alt="vue">
  </a>
  <a href="https://github.com/ElemeFE/element">
    <img src="https://img.shields.io/badge/element--ui-2.13.0-brightgreen.svg" alt="element-ui">
  </a>
</p>

## Overview

这个项目由`Springboot`和`Vue`搭建而成，采用前后端分离框架，所有内容为本学期开发。

测试包含单元测试，接口测试，系统测试和性能测试，其中单元测试分为前端单元测试与后端单元测试。

本项目共有四人开发，开发日期为2019.10.1至2020.1.3。

## Environment

- Backend

   - `SpringBoot 2.1.9`

- Frontend

   - `Vue 2.6.10` + `Axois` + `Vuex` + `Vue-router` + `element-ui`

   - Created  by `@vue/cli 4.1.1`

- Database

   - **Local:** `MySQL 8`

   - **Server:** `MariaDB 10.1.43`


- Server

   - Tecent Cloud, `Centos 7.6 64bit`

   - 1 Core，2G Memory，1Mbps Brandwidth

## Run this project

-  Backend

   - Location: '/backend'
   - Use IDEA to run this project, it will configure it automatically.

-  Frontend

   - Location: '/frontend2.0'
   - Use **npm** / **yarn** :
      1.  ```npm install``` or ```yarn```
      2.  ```npm run server --fix ``` or ```yarn server --fix```

## Run tests

- Unit Test

   - Backend

      - Location: '/backend/src/test'
      - Use IDEA to run all tests.

   - Frontend

      - Location: '/fronend2.0/tests/unit'
      - Run: ```npm run test:unit``` or ```yarn test:unit```

- Interface Test

   - Location: '/backend/src/test'
   - Use IDEA to run test.

- System Test

   - Manager System Test

      - Location: '/ManagerSystemTest'
      - Tools: `Selenium` + `Maven` + `Allure` + `Junit5`
      - Report: ```$./allure serve allure-results```

   - User System Test

      - Location: '/fronend2.0/tests/e2e'
      - Tools: `nightwatch`
      - Run: ```npm run test:e2e --fix``` or ```yarn test:e2e --fix```

- Performance Test

   - Location: '/performance test'
   - Tools: `locus`
   - Run: ```locust -f FILE_NAME.py --host=http://localhost:8081```
