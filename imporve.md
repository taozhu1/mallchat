## websocket集群
这套是单机通过 Netty 的 Channel 来做 IM 的，如果转为分布式集群需要做什么改动呢？

1、引入 MQ + Redis 记录 channelId
![img.png](img.png)
基于 MQ 的发布/订阅模式，在消费者判断 channelId 是否在当前机器，如果否就不处理
link：https://huaweicloud.csdn.net/638768e1dacf622b8df8b747.html#devmenu11
