# MES 制造执行系统

## 项目简介

MES（Manufacturing Execution System）制造执行系统是一套面向制造业车间的信息化管理平台，基于 Java Spring Boot 构建，提供从工单下达到产品完成的全程生产数据管理能力。系统涵盖工单管理、生产排程、质量检验、设备监控和数据统计等核心功能模块，帮助制造企业实现生产过程的透明化、数字化和可追溯管理。

## 适用场景

- 中小型制造企业的车间生产管理
- 机械加工、零部件制造等离散制造行业
- 需要对工单进度、质量数据和设备状态进行统一监控的场景
- 生产排程与质量追溯需求
- 企业数字化转型初期的 MES 系统搭建参考

## 核心功能

### 1. 工单管理
- 工单的创建、编辑、查看和删除
- 支持按状态（待处理/进行中/已完成/已取消）和优先级（低/中/高/紧急）筛选
- 记录计划时间和实际时间，支持进度追踪
- 工单分配到具体负责人

### 2. 生产排程
- 基于工单的工序排产管理
- 时间线视图展示排程安排
- 排程状态实时更新（待处理/进行中/已完成/延迟）
- 工序与工单关联

### 3. 质量检验
- 质检记录的创建与查询
- 支持合格/不合格/有条件合格三种检验结果
- 记录缺陷数量和缺陷描述
- 自动计算质检合格率

### 4. 设备监控
- 设备状态实时监控（运行中/空闲/维护中/故障/离线）
- 设备维护计划管理
- 设备运行日志记录
- 设备状态变更追踪

### 5. 数据统计
- 生产仪表盘，展示关键指标
- 工单状态分布统计
- 设备状态分布统计
- 质检合格率趋势

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 开发语言 |
| Spring Boot | 3.2.5 | 应用框架 |
| Spring Data JPA | - | 数据持久化 |
| Thymeleaf | - | 模板引擎 |
| H2 Database | - | 嵌入式数据库（文件模式） |
| Maven | 3.9 | 构建工具 |
| Bootstrap CSS | 自定义 | UI 样式 |

## 目录结构

```
pub-manufacture-mes/
├── Dockerfile                    # Docker 构建文件
├── publish_private_github.sh     # GitHub 发布脚本
├── .gitignore
├── .github.env.example
├── ssh_plugin/                   # SSH 插件
│   ├── entrypoint.sh
│   └── install_ssh.sh
└── repo/                         # 项目源码
    ├── pom.xml
    ├── patch/
    └── src/main/
        ├── java/com/mes/
        │   ├── MesApplication.java
        │   ├── config/
        │   │   └── DataInitializer.java
        │   ├── controller/
        │   │   ├── HomeController.java
        │   │   ├── WorkOrderController.java
        │   │   ├── ScheduleController.java
        │   │   ├── QualityController.java
        │   │   └── EquipmentController.java
        │   ├── model/
        │   │   ├── User.java
        │   │   ├── WorkOrder.java
        │   │   ├── ProductionSchedule.java
        │   │   ├── QualityInspection.java
        │   │   ├── Equipment.java
        │   │   └── EquipmentLog.java
        │   ├── repository/
        │   │   ├── UserRepository.java
        │   │   ├── WorkOrderRepository.java
        │   │   ├── ProductionScheduleRepository.java
        │   │   ├── QualityInspectionRepository.java
        │   │   ├── EquipmentRepository.java
        │   │   └── EquipmentLogRepository.java
        │   └── service/
        │       ├── WorkOrderService.java
        │       ├── ScheduleService.java
        │       ├── QualityService.java
        │       └── EquipmentService.java
        └── resources/
            ├── application.properties
            ├── templates/
            │   ├── layout.html
            │   ├── index.html
            │   ├── workorders/
            │   │   ├── list.html
            │   │   ├── detail.html
            │   │   ├── create.html
            │   │   └── edit.html
            │   ├── schedule/
            │   │   ├── list.html
            │   │   └── detail.html
            │   ├── quality/
            │   │   ├── list.html
            │   │   └── create.html
            │   ├── equipment/
            │   │   ├── list.html
            │   │   └── detail.html
            │   └── dashboard/
            │       └── overview.html
            └── static/
                ├── css/style.css
                └── js/main.js
```

## Docker 启动方式

```bash
# 构建镜像
docker build -t mes-system .

# 启动容器
docker run -d -p 8080:8080 -p 2222:22 mes-system

# 访问系统
# 浏览器打开 http://localhost:8080
# SSH 连接: ssh root@localhost -p 2222 (默认密码: password)
```

## 本地启动方式

```bash
# 进入项目目录
cd repo

# 使用 Maven 构建
mvn clean package -DskipTests

# 运行
java -jar target/pub-manufacture-mes-1.0.0.jar

# 或者使用 Maven 直接运行
mvn spring-boot:run
```

启动后访问 http://localhost:8080

H2 控制台: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:file:./data/mes_db, 用户名: sa, 密码为空)

## 默认账号

- 用户名: admin
- 密码: admin123

## 可扩展方向

1. **用户认证与权限管理** - 集成 Spring Security，实现基于角色的访问控制
2. **报表导出** - 支持生产数据导出为 Excel/PDF 格式
3. **实时通知** - 集成 WebSocket，实现设备故障和工单状态变更的实时推送
4. **数据可视化增强** - 集成 ECharts 或 Chart.js，实现更丰富的图表展示
5. **API 接口** - 提供 RESTful API，支持与 ERP、WMS 等系统集成
6. **多数据库支持** - 支持 MySQL、PostgreSQL 等生产级数据库
7. **移动端适配** - 响应式设计优化，支持移动端操作
8. **生产追溯** - 完善产品全生命周期追溯链路
9. **工艺路线管理** - 支持多工序工艺路线配置和流转
10. **设备预测性维护** - 基于设备运行数据的智能维护提醒
