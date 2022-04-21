package com.my.timekeeping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConnectionPoolTest {

    @Mock
    ConnectionPool connectionPool;
    @Mock
    Connection mockConnection;

    @Test
    void getConnectionTest() throws SQLException {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(mockConnection);
            assertNotNull(connectionPool);
            assertEquals(connectionPool.getConnection(), mockConnection);
        }
        connectionPool = ConnectionPool.getInstance();
        assertNotNull(connectionPool);
        Connection connection = connectionPool.getConnection();
        assertNotEquals(connection, connectionPool.getConnection());
    }
}