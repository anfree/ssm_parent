package org.zeng.test.mq.producer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.zeng.module.customs.CustomsService;
import org.zeng.test.mq.RmqTemplate;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/7
 */
@RunWith(MockitoJUnitRunner.class)
public class ProducerTest {

    @Mock
    private CustomsService customsService;

    @InjectMocks
    private RmqTemplate tianjinRmqTemplate;

    @InjectMocks
    private Producer producer;

    @Before
    public void setUp() {
        // 使用 MockitoJUnitRunner 时，不需要显式调用 MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendMessage() {
        // 测试有效参数的情况
//        producer.sendMessage("hello", "123456", "test");
        // tianJin customs send queue
//        producer.sendMessage("DXPENT0000470415_TO_NODE", "123456", "test");
        // tianJin customs receive queue
        producer.sendMessage("NODE_TO_DXPENT0000470415", "123456", "test");
    }
}
