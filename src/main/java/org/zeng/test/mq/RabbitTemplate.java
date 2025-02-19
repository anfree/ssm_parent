package org.zeng.test.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.zeng.util.RabbitUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/18
 */
public class RabbitTemplate {

    public void convertAndSend(String queue, String message) throws IOException, TimeoutException, InterruptedException {

        try (Connection rmqConnection = RabbitUtil.createConnection();
             Channel channel = rmqConnection.createChannel()) {
            channel.queueDeclare(queue, true, false, false, null);

            channel.confirmSelect();
            channel.basicPublish("", queue, true, false, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            channel.waitForConfirmsOrDie();
        }

    }

}
