package org.zeng.test.mq;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zeng.module.customs.CustomsService;

import java.io.IOException;

import static org.zeng.frame.constant.DataConstant.CHARSET_UTF8;


/**
 * Rabbit Mq Template
 *
 * @author 曾祥江
 * @since 2025/2/18
 */
@Slf4j
@Component
public class RmqTemplate {

    private static final ConnectionFactory connectionFactory;
    private static final RmqConnectionPool rmqConnectionPool;

    private static final CustomsService customsService;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        // 天津海关 RabbitMQ
//        connectionFactory.setHost("60.28.236.164");
//        connectionFactory.setPort(5678);
//        connectionFactory.setUsername("aseadmin");
//        connectionFactory.setPassword("aseadmin");
//        connectionFactory.setVirtualHost("vhost");
        rmqConnectionPool = new RmqConnectionPool(connectionFactory);
        customsService = new CustomsService();
    }

    public void convertAndSend(String queue, byte[] body) throws Exception {
        log.info("RabbitMQ 发送消息，队列为：{}，消息为：{}", queue, new String(body, CHARSET_UTF8));
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

    public void receive(String queue) {
        try {
            // 创建连接
            Connection connection = rmqConnectionPool.getConnection();
            // 创建频道
            Channel channel = connection.createChannel();

            // 声明队列，确保队列存在且持久化
            channel.queueDeclare(queue, true, false, false, null);

            // 设置预取消息数量
            channel.basicQos(1);

            // 定义回调方法，用于处理接收到的消息
            DeliverCallback deliverCallback = new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery delivery) throws IOException {
                    // 获取消息体并转换为字符串
                    String message = new String(delivery.getBody(), CHARSET_UTF8);
                    // 打印接收到的消息
                    log.info("天津海关回执: {}", message);

                    //解析报文
                    try {
                        customsService.receiptHandle(message);
                        log.info("天津海关处理回执消息完成");
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    } catch (Exception e) {
                        log.error("处理天津海关消息失败", e);
                    }
                }
            };

            // 开始消费消息
            channel.basicConsume(queue, false, deliverCallback, new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {
                    // 取消消费时的回调，此处为空实现
                }
            });
        } catch (Exception e) {
            log.error("RabbitMQ 接收消息失败", e);
            // 根据实际情况决定是否重试或采取其他措施
        }
    }

}
