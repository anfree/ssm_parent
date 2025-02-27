package org.zeng.test.mq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Rabbit Mq Factory
 *
 * @author 曾祥江
 * @since 2025/2/18
 */
public class RmqConnectionFactory implements PooledObjectFactory<Connection> {

    private final ConnectionFactory connectionFactory;

    public RmqConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        Connection connection = connectionFactory.newConnection();
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void destroyObject(PooledObject<Connection> pooledObject) throws Exception {
        Connection connection = pooledObject.getObject();
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }

    @Override
    public boolean validateObject(PooledObject<Connection> pooledObject) {
        Connection connection = pooledObject.getObject();
        return connection != null && connection.isOpen();
    }

    @Override
    public void activateObject(PooledObject<Connection> pooledObject) throws Exception {
        // No-op
    }

    @Override
    public void passivateObject(PooledObject<Connection> pooledObject) throws Exception {
        // No-op
    }
}