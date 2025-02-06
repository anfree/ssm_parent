package org.zeng.mq.rabbit.stream.simplest1;

import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;

import java.io.IOException;

/**
 * RabbitMq consumer(receiver).
 * The consumer will consume and print it to standard output.
 *
 * @author 曾祥江
 * @since 2025/1/23
 */
public class Receive {

    public static void main(String[] args) throws IOException {
        Environment environment = Environment.builder().build();
        String stream = "hello-java-stream";
        environment.streamCreator().stream(stream).maxLengthBytes(ByteCapacity.GB(5)).create();

        Consumer consumer = environment.consumerBuilder()
            .stream(stream)
            .offset(OffsetSpecification.first())
            .messageHandler((unused, message) -> {
                System.out.println("Received message: " + new String(message.getBodyAsBinary()));
            }).build();

        System.out.println(" [x]  Press Enter to close the consumer...");
        System.in.read();
        consumer.close();
        environment.close();
    }
}
