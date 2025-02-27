package org.zeng.test.mq;

import org.junit.jupiter.api.Test;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/18
 */
public class RmqTemplateTest {

    // Test Case 1: Successfully send message
    @Test
    void testSend() throws Exception {
        RmqTemplate template = new RmqTemplate();
        template.convertAndSend("DXPENT0000470415_TO_NODE", "Hello RabbitMQ".getBytes());
        System.out.println("Message sent successfully!");
    }

}
