package org.zeng.test;

import org.junit.Test;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/26
 */
public class SystemTest {

    @Test
    public void testSystemProperty() {
        System.out.println("Hello World");
        // 获取当前工作目录
        String currentWorkingDir = System.getProperty("user.dir");
        System.out.println("当前工作目录: " + currentWorkingDir);

        // 获取用户主目录
        String userHomeDir = System.getProperty("user.home");
        System.out.println("用户主目录: " + userHomeDir);
    }
}
