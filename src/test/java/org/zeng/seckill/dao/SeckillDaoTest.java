package org.zeng.seckill.dao;

import org.junit.Test;
import org.zeng.BaseTest;
import org.zeng.seckill.entity.Seckill;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 秒杀Dao测试
 * Created by 曾祥江 on 2017/5/29 15:44.
 * email: zengxiangjaing@aliyun.com
 */
public class SeckillDaoTest extends BaseTest {
    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception  {
        //Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
        // java没有保存形参的记录:queryAll(int offset, int limit) -> queryAll(arg1,arg2)
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }

    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
    }

    @Test
    public void killByProcedure() throws Exception {
    }

}