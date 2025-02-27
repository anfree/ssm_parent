package org.zeng.test.mq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Rabbit Mq connection pool
 *
 * @author 曾祥江
 * @since 2025/2/18
 */
public class RmqConnectionPool {

    private final GenericObjectPool<Connection> connectionPool;

    public RmqConnectionPool(ConnectionFactory connectionFactory, GenericObjectPoolConfig<Connection> poolConfig) {
        RmqConnectionFactory pooledFactory = new RmqConnectionFactory(connectionFactory);
        this.connectionPool = new GenericObjectPool<>(pooledFactory, poolConfig);
    }

    public RmqConnectionPool(ConnectionFactory connectionFactory) {
        RmqConnectionFactory pooledFactory = new RmqConnectionFactory(connectionFactory);
        // 配置连接池参数
        GenericObjectPoolConfig<Connection> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(10); // 最大连接数 10
        poolConfig.setMaxIdle(10);   // 最大空闲连接数 5
        poolConfig.setMinIdle(2);   // 最小空闲连接数 2
        poolConfig.setTestOnBorrow(true); // 借出时验证连接
        this.connectionPool = new GenericObjectPool<>(pooledFactory, poolConfig);
    }

    public Connection getConnection() throws Exception {
        return connectionPool.borrowObject();
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            connectionPool.returnObject(connection);
        }
    }

    public void close() {
        if (connectionPool != null) {
            connectionPool.close();
        }
    }
}
