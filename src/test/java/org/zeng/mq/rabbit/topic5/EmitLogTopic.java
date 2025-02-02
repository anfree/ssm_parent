package org.zeng.mq.rabbit.topic5;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * RabbitMQ 生产者测试类
 * 5. Topic exchange 模式
 * Topic exchange is powerful and can behave like other exchanges.
 * When a queue is bound with # (hash) binding key - it will receive all the messages, regardless of the routing key - like in fanout exchange.
 * When special characters, * (star) and # (hash), aren't used in bindings, the topic exchange will behave just like a direct one.
 *
 * @author 曾祥江
 * @since 2025/1/27
 */
public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    /**
     * to emit a log with a routing key kern.critical type:
     * java -cp $CP EmitLogTopic "kern.critical" "A critical kernel error"
     *
     * @param argv 0: routing key, 1: message
     * @throws Exception 异常处理
     */
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String routingKey = getRouting(argv);
            String message = getMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }

    private static String getRouting(String[] strings) {
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length < startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
