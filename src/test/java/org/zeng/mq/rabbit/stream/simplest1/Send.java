package org.zeng.mq.rabbit.stream.simplest1;

import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.Producer;

import java.io.IOException;

/**
 * RabbitMQ producer(sender).
 * The producer will connect to RabbitMQ, send a single message, then exit.
 * P -> Stream Queue -> C
 *
 * @author 曾祥江
 * @since 2025/1/23
 */
public class Send {

    public static void main(String[] args) throws IOException {
        // 显式指定主机和端口
        Environment environment = Environment.builder()
                .host("localhost")
                .port(5552)
                .build();

        String stream = "hello-java-stream";

        // 尝试创建流（如果不存在）
        try {
            environment.streamCreator().stream(stream).maxLengthBytes(ByteCapacity.GB(5)).create();
            System.out.println("Stream created.");
        } catch (Exception e) {
            System.out.println("Stream already exists or creation failed: " + e.getMessage());
        }

        Producer producer = environment.producerBuilder().stream(stream).build();
        producer.send(producer.messageBuilder().addData("Hello, World!".getBytes()).build(), null);
        System.out.println(" [x] 'Hello, World!' message sent");

        System.out.println(" [x] Press Enter to close the producer...");
        System.in.read();
        producer.close();
        environment.close();
    }

}
