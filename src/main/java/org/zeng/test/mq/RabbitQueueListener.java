package org.zeng.test.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.zeng.util.StringUtil;

/**
 * RabbitMQ监听队列
 *
 * @author 曾祥江
 * @since 2025/1/10
 */
@Component
public class RabbitQueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitQueueListener.class);

    /**
     * 天津运单报文回执监听
     *
     * @param text 报文体
     */
//    @RabbitListener(queues = {RabbitMqConstants.RABBIT_TIAN_JIN_PORT_WAYBILL_RECEIVE}, containerFactory = "tianjinContainerFactory")
    public void tianjinWaybillReceive(String text) {
        String businessId = System.currentTimeMillis() + "" + StringUtil.random(3);
        try {
            LOGGER.info("tianjinWaybillReceive begin businessId={},text={}", businessId, text);
        } catch (Exception e) {
            LOGGER.error("tianjinWaybillReceive JMQException businessId={},text={}", businessId, text, e);
        }
    }

}
