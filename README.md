# lightMQ
由java构建的轻量级消息队列

## 使用步骤
1. 注册topic
2. 如采用发布订阅方式，需要注册group
3. 发送消息
4. 消费消息

## 消费模式
1. 非阻塞消费，无消息直接返回null
2. 阻塞消费，无消息进入阻塞

## 生产模式
1. 顺序生产，采用同步策略，速度较慢
2. 非顺序生产，采用异步策略，速度较快

## 提供接口
1. registerTopic 注册topic，传入topic
2. registerGroup 注册group，传入topic和group
3. produce 生产消息，传入topic，key，value，key可不传
4. consume 消费消息，传入group，topic，group可不传，默认消费master
5. getAllTopic 获取所有topic

## 客户端地址
https://github.com/stalary/lightMQ-client

