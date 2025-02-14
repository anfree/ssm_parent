package org.zeng.test.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.zeng.util.StringUtil;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/7
 */
@Slf4j
@Service
public class Producer {

    @Resource
    private RabbitTemplate tianjinRabbitTemplate;

    public void sendMessage(String queue, String orderId, String message) {
        // 参数非空校验
        if (StringUtil.isEmpty(queue, orderId, message)) {
            log.error("sendMessage2Tianjin param empty orderId={},queue={},message={}",orderId,queue,message);
            return;
        }
        log.info("sendMessage2Tianjin rabbitMQ orderId={}, queue={}, message={}",orderId, queue, message);
        try {
            // 发送数据
            tianjinRabbitTemplate.convertAndSend(queue, message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("sendMessage2Tianjin rabbitMQ Exception orderId={}, queue={}, message={}", orderId, queue, message, e);
        }
    }
}
