package org.zeng.test.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Rabbit Mq Template
 *
 * @author 曾祥江
 * @since 2025/2/18
 */
@Component
public class RmqTemplate {

    private static final Logger log = LoggerFactory.getLogger(RmqTemplate.class);
    private static final ConnectionFactory connectionFactory;
    public static final RmqConnectionPool rmqConnectionPool;

    static {
        connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("localhost");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        connectionFactory.setVirtualHost("/");
        // 天津海关 RabbitMQ
        connectionFactory.setHost("60.28.236.164");
        connectionFactory.setPort(5678);
        connectionFactory.setUsername("aseadmin");
        connectionFactory.setPassword("aseadmin");
        connectionFactory.setVirtualHost("vhost");
        rmqConnectionPool = new RmqConnectionPool(connectionFactory);
    }

    public void convertAndSend(String queue, byte[] body) throws Exception {
        log.info("RabbitMQ 发送消息，队列为：{}，消息为：{}", queue, body.toString());
        Connection connection = rmqConnectionPool.getConnection();
        try (Channel channel = connection.createChannel()) {

            channel.queueDeclare(queue, true, false, false, null);

            channel.confirmSelect();
            channel.basicPublish("", queue, true, false, MessageProperties.PERSISTENT_TEXT_PLAIN, body);
            channel.waitForConfirmsOrDie();
            log.info("RabbitMQ 发送消息成功");

        } finally {
            rmqConnectionPool.releaseConnection(connection);
        }
    }

    public String receiveMessageFromQueue() throws Exception {
        // 创建连接
        Connection connection = rmqConnectionPool.getConnection();
        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列，确保队列存在且持久化
        String queue = "testQueue";
        channel.queueDeclare(queue, true, false, false, null);

        // 设置预取消息数量
        channel.basicQos(1);

        // 定义回调方法，用于处理接收到的消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // 获取消息体并转换为字符串
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            // 打印接收到的消息
            System.out.println(" [x] Received '" + message + "'");
        };

        // 开始消费消息
        channel.basicConsume(queue, false, deliverCallback, consumerTag -> { });

        // 返回接收到的消息
        return "";
    }

}
