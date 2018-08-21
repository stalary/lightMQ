# lightMQ
由java构建的轻量级消息队列

## 特点
### 构建简单，纯java编写
### 使用方便，使用客户端可以直接通过注解监听

## 使用步骤
1. 注册topic
2. 如采用发布订阅方式，需要注册group
3. 发送消息
4. 消费消息

## 提供接口
1. registerTopic 注册topic，传入topic
2. registerGroup 注册group，传入topic和group
3. produce 生产消息，传入topic，key，value，key可不传
4. consume 消费消息，传入group，topic，group可不传，默认消费master
5. getAllTopic 获取所有topic

## 客户端地址
https://github.com/stalary/lightMQ-client

