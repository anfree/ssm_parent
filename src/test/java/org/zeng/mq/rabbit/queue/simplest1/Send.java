package org.zeng.mq.rabbit.queue.simplest1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ producer(sender).
 * The producer will connect to RabbitMQ, send a single message, then exit.
 * P -> Queue -> C
 *
 * @author 曾祥江
 * @since 2025/1/23
 */
public class Send {

    private final static String QUEUE_NAME = "DXPENT0000470415_TO_NODE";

    public static void main(String[] argv) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ConnectionFactory factory = new ConnectionFactory();
//        factory.useSslProtocol();
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(5000);
        factory.setTopologyRecoveryEnabled(true);
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(300000);
        factory.setHandshakeTimeout(30000);
        // 连接本地 RabbitMQ server
//        factory.setHost("localhost");
//        factory.setPort(5672);
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        factory.setVirtualHost("/");
        // 天津海关 RabbitMQ
        factory.setHost("60.28.236.164");
        factory.setPort(5678);
        factory.setUsername("aseadmin");
        factory.setPassword("aseadmin");
        factory.setVirtualHost("vhost");
        // 郑州海关 RabbitMQ
//        factory.setHost("www.haeport.com");
//        factory.setPort(5672);
//        factory.setUsername("aseadmin");
//        factory.setPassword("aseadmin");
//        factory.setVirtualHost("ase");
        try {
            Connection connection = factory.newConnection(executorService);
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Done");

        }

    }

}
