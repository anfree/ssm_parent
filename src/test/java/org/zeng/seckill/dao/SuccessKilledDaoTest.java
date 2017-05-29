package org.zeng.seckill.dao;

import org.junit.Test;
import org.zeng.seckill.entity.SuccessKilled;

import javax.annotation.Resource;

/**
 * 秒杀成功Dao测试
 * Created by 曾祥江 on 2017/5/29 16:02.
 * email: zengxiangjaing@aliyun.com
 */
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
//      第一次:insertCount=1
//      第二次:insertCount=0
        long id = 1001;
        long phone = 13631231234L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount=" + insertCount);
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        long id = 1001;
        long phone = 13631231234L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}