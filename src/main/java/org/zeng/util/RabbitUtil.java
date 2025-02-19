package org.zeng.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/18
 */
public class RabbitUtil {

    public static Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        cf.setPort(5672);
        cf.setUsername("guest");
        cf.setPassword("guest");
        cf.setVirtualHost("/");
        return cf.newConnection();
    }

}
