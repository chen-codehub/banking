# 银行交易系统

## 项目简介
一个银行交易系统，功能包括创建交易、修改交易、删除交易、列出所有交易，其中数据保存在内存，不需要使用持久化存储

## 环境及依赖
### 运行环境
* JDK 21
* SpringBoot 3.3.12

### 其他依赖
* spring-boot-starter-validation：接口参数验证
* lombok：自动生成getter/setter方法
* slf4j-api：日志打印

## 项目结构
```
src/
├── main/
│   └── java/
│       └── com/
│           └── example/
│               └── bank/
│                   ├── controller/  # 控制器类
│                   ├── entity/      # 实体类
│                   ├── enums/       # 枚举类型定义
│                   ├── exception/   # 异常定义及捕获
│                   └── service/     # 服务实现类
```

## 接口说明
1、创建交易

POST `/api/v1/bank/transactions`

2、修改交易

PUT `/api/v1/bank/transactions`

3、删除交易

DELETE `/api/v1/bank/transactions`

4、列出所有交易

GET `/api/v1/bank/transactions`

## 快速开始
```bash
# 编译
mvn clean package -s settings.xml

# 运行
java -jar target/bank-0.0.1-SNAPSHOT.jar
```

## 测试
### 单元测试
```bash
# 运行单元测试
mvn test
```

### 压力测试
使用本地笔记本电脑（6U12C）进行压力测试，压测工具为Jmeter，参数配置为500个线程、每个线程循环运行500次


| 接口   | 平均响应时间 | 最大响应时间 | 错误率   | QPS  |
|------|--------|--------|-------|------|
| 创建交易 | 30ms   | 541ms  | 0.00% | 3906 |
| 修改交易 | 29ms   | 612ms  | 0.01% | 3906 |
| 查询交易 | 30ms   | 554ms  | 0.00% | 3907 |
| 删除交易 | 29ms   | 554ms  | 0.00% | 3907 |
