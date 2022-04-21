package com.my.timekeeping.dao;

import com.my.timekeeping.ConnectionPool;
import com.my.timekeeping.dto.UserDTO;
import com.my.timekeeping.entity.Role;
import com.my.timekeeping.entity.User;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {


    @BeforeEach
    void createDB() throws SQLException, IOException {
        String scriptFilePath = "src/test/resources/db-create.sql";
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(scriptFilePath));
            sr.setLogWriter(null);
            sr.runScript(reader);
        }
    }

    @Test
    void getInstance() {
        UserDAO instance1 = UserDAO.getInstance();
        assertNotNull(instance1);
        UserDAO instance2 = UserDAO.getInstance();
        assertEquals(instance1, instance2);
    }

    @Test
    void getUserByLogin() throws DAOException, EncryptException {
        assertEquals(-1L, UserDAO.getInstance().getUserByLogin("Login").getId());
        UserDAO.getInstance().addUser(User.newBuilder()
                .setRole(Role.USER)
                .setLogin("Login")
                .setName("Name")
                .setPassword("password")
                .build());
        assertNotNull(UserDAO.getInstance().getUserByLogin("Login"));
    }


    @Test
    void getAllUsersByRole() throws DAOException, EncryptException {
        assertEquals(0, UserDAO.getInstance().getAllUsersByRole(new int[]{2}).size());

        UserDAO.getInstance().addUser(User.newBuilder()
                .setRole(Role.USER)
                .setLogin("Login")
                .setName("Name")
                .setPassword("password")
                .build());

        assertEquals(1, UserDAO.getInstance().getAllUsersByRole(new int[]{2}).size());
    }
}