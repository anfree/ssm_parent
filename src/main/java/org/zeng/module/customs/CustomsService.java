package org.zeng.module.customs;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/27
 */
@Slf4j
@Service
public class CustomsService {
    public void receiptHandle(String message) {
        log.info("接收到的消息为：{}", message);
    }
}
