package org.zeng.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.zeng.seckill.entity.SuccessKilled;

/**
 * Created by 曾祥江 on 2017/5/29 14:06.
 * email: zengxiangjaing@aliyun.com
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细，可以过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


    /**
     * 根据ID查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
