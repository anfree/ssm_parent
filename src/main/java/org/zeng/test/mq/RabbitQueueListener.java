//package org.zeng.test.mq;
//
//import com.oracle.jrockit.jfr.Producer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.stereotype.Component;
//import org.zeng.util.StringUtil;
//
//import javax.annotation.Resource;
//
///**
// * RabbitMQ监听队列
// *
// * @author 曾祥江
// * @since 2025/1/10
// */
//@Component
//public class RabbitQueueListener {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitQueueListener.class);
//
//    /**
//     * producer
//     */
//    @Resource
//    private Producer producer;
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
//            LOGGER.info("tianjinWaybillReceive begin businessId={},text={}", businessId, text);
//            Message message = new Message(JmqTypeEnum.JMQ_TIAN_JIN_PORT_WAYBILL_RECEIVE_JMQ.getName(), JSON.toJSONString(new CallbackDataInfo(JmqTypeEnum.JMQ_TIAN_JIN_PORT_WAYBILL_RECEIVE_JMQ.getChannelId(), text)), businessId);
//            producer.send(message);
//            LOGGER.info("tianjinWaybillReceive end businessId={}", businessId);
//        } catch (Exception e) {
//            LOGGER.error("tianjinWaybillReceive JMQException businessId={},text={}", businessId, text, e);
//        }
//    }
//
//    /**
//     * 天津订单报文回执监听
//     *
//     * @param text 报文体
//     */
////    @RabbitListener(queues = {RabbitMqConstants.RABBIT_TIAN_JIN_PORT_ORDER_RECEIVE}, containerFactory = "tianjinContainerFactory")
//    public void tianjinOrderReceive(String text) {
//        String businessId = System.currentTimeMillis() + "" + StringUtil.random(3);
//        try {
//            LOGGER.info("tianjinOrderReceive begin businessId={}, text={}", businessId, text);
//            Message message = new Message(JmqTypeEnum.JMQ_TIAN_JIN_PORT_ORDER_RECEIVE_JMQ.getName(), JSON.toJSONString(new CallbackDataInfo(JmqTypeEnum.JMQ_TIAN_JIN_PORT_ORDER_RECEIVE_JMQ.getChannelId(), text)), businessId);
//            producer.send(message);
//            LOGGER.info("tianjinOrderReceive end businessId={}", businessId);
//        } catch (Exception e) {
//            LOGGER.error("tianjinOrderReceive JMQException businessId={}, text={}", businessId, text, e);
//        }
//    }
//
//    /**
//     * 天津订单报文回执监听
//     * 广臻主体
//     *
//     * @param text 报文体
//     */
////    @RabbitListener(queues = {RabbitMqConstants.RABBIT_TIAN_JIN_PORT_ORDER_RECEIVE_GUANGZHEN}, containerFactory = "tianjinContainerFactory")
//    public void tianjinOrderReceiveGuangZhen(String text) {
//        String businessId = System.currentTimeMillis() + "" + StringUtil.random(3);
//        try {
//            LOGGER.info("tianjinOrderReceiveGuangZhen begin businessId={}, text={}", businessId, text);
//            Message message = new Message(JmqTypeEnum.JMQ_TIAN_JIN_PORT_ORDER_RECEIVE_JMQ.getName(), JSON.toJSONString(new CallbackDataInfo(JmqTypeEnum.JMQ_TIAN_JIN_PORT_ORDER_RECEIVE_JMQ.getChannelId(), text.trim(), BusinessConstant.YJY)), businessId);
//            producer.send(message);
//            LOGGER.info("tianjinOrderReceiveGuangZhen end businessId={}", businessId);
//        } catch (Exception e) {
//            LOGGER.error("tianjinOrderReceiveGuangZhen JMQException businessId={}, text={}", businessId, text, e);
//        }
//    }
//
//}
