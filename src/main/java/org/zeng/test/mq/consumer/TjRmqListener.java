package org.zeng.test.mq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.zeng.test.mq.RmqTemplate;

import javax.annotation.Resource;

/**
 * spring 3.2.4 listener 监听器
 *
 * @author 曾祥江
 * @since 2025/2/24
 */
//@ComponentScan(basePackages = "org.zeng.test.mq")
@Component
public class TjRmqListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(TjRmqListener.class);

    @Resource
    private RmqTemplate tianjinRmqTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 当Spring上下文被初始化或刷新时执行的逻辑
        log.info("RabbitMQ 接收消息 start");

        // tianJin customs send queue
        String queue = "DXPENT0000470415_TO_NODE";
        // tianJin customs receive queue
//        String queue = "NODE_TO_DXPENT0000470415";
        tianjinRmqTemplate.receive(queue);
        log.info("RabbitMQ 接收消息 end");

    }

}
