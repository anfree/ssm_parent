package org.zeng.test.mq.producer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/7
 */
@RunWith(MockitoJUnitRunner.class)
public class ProducerTest {

    @Mock
    private RabbitTemplate tianjinRabbitTemplate;

    @InjectMocks
    private Producer producer;

    @Before
    public void setUp() {
        // 使用 MockitoJUnitRunner 时，不需要显式调用 MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendMessage_AllParametersEmpty_LogsError() {
        // 测试所有参数为空的情况
        producer.sendMessage("", "", "");
        verify(tianjinRabbitTemplate, never()).convertAndSend(anyString(), any(byte[].class));
    }

    @Test
    public void sendMessage_ValidParameters_MessageSent() {
        // 测试有效参数的情况
        producer.sendMessage("hello", "123456", "test");
//        producer.sendMessage("00069_TO_NODE", "123456", "test");
        verify(tianjinRabbitTemplate, times(1)).convertAndSend(eq("hello"), any(byte[].class));
    }

    @Test
    public void sendMessage_SendMessageThrowsException_LogsError() {
        // 测试发送消息时抛出异常的情况
        doThrow(new RuntimeException("Test exception")).when(tianjinRabbitTemplate).convertAndSend(anyString(), any(byte[].class));
        producer.sendMessage("hello", "orderId", "message");
        verify(tianjinRabbitTemplate, times(1)).convertAndSend(eq("hello"), any(byte[].class));
    }
}
