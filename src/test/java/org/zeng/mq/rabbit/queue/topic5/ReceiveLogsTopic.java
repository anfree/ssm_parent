package org.zeng.mq.rabbit.queue.topic5;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * RabbitMq接收消息测试类。
 *
 * @author 曾祥江
 * @since 2025/1/27
 */
public class ReceiveLogsTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    /**
     * To receive all the logs:
     * java -cp $CP ReceiveLogsTopic "#"
     * <p>
     * To receive all logs from the facility kern:
     * java -cp $CP ReceiveLogsTopic "kern.*"
     * <p>
     * Or if you want to hear only about critical logs:
     * java -cp $CP ReceiveLogsTopic "*.critical"
     * <p>
     * You can create multiple bindings:
     * java -cp $CP ReceiveLogsTopic "kern.*" "*.critical"
     *
     * @param argv the binding keys
     */
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

        if (argv.length < 1) {
            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
            System.exit(1);
        }

        for (String bindingKey : argv) {
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
