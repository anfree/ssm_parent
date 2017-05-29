package org.zeng;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试spring基类
 * Created by 曾祥江 on 2017/5/29 15:48.
 * email: zengxiangjaing@aliyun.com
 */
// 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({ "classpath:spring/spring-dao.xml"})
public class BaseTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("setUp: run test start -------------");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown: run test end ------------");
    }
}
