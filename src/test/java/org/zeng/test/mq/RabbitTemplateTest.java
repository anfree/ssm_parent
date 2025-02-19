package org.zeng.test.mq;

import org.junit.jupiter.api.Test;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/18
 */
public class RabbitTemplateTest {

    // Test Case 1: Successfully send message
    @Test
    void testSend() throws Exception {
        RabbitTemplate template = new RabbitTemplate();
        template.convertAndSend("testQueue", "Hello RabbitMQ");
        System.out.println("Message sent successfully!");
    }

}
