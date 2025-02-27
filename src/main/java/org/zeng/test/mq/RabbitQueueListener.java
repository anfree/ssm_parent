//package org.zeng.test.mq;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageListener;
//import org.zeng.util.StringUtil;
//
///**
// * RabbitMQ监听队列
// *
// * @author 曾祥江
// * @since 2025/1/10
// */
//public class RabbitQueueListener implements MessageListener {
//
//    private static final Logger log = LoggerFactory.getLogger(RabbitQueueListener.class);
//
//    /**
//     * 天津运单报文回执监听
//     *
//     * @param text 报文体
//     */
////    @RabbitListener(queues = {RabbitMqConstants.RABBIT_TIAN_JIN_PORT_WAYBILL_RECEIVE}, containerFactory = "tianjinContainerFactory")
//    public void tianjinWaybillReceive(String text) {
//        String businessId = System.currentTimeMillis() + "" + StringUtil.random(3);
//        try {
//            log.info("tianjinWaybillReceive begin businessId={},text={}", businessId, text);
//        } catch (Exception e) {
//            log.error("tianjinWaybillReceive JMQException businessId={},text={}", businessId, text, e);
//        }
//    }
//
//    @Override
//    public void onMessage(Message message) {
//        log.info("onMessage message={}", message.getBody());
//    }
//}
