package com.my.timekeeping.dao;

import com.my.timekeeping.ConnectionPool;
import com.my.timekeeping.dto.ActivityDTO;
import com.my.timekeeping.dto.UserDTO;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.entity.Role;
import com.my.timekeeping.entity.State;
import com.my.timekeeping.entity.User;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.ibatis.jdbc.ScriptRunner;
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

class DBManagerTest {

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
    void getInstanceTest() {
        DBManager instance1 = DBManager.getInstance();
        assertNotNull(instance1);
        DBManager instance2 = DBManager.getInstance();
        assertEquals(instance1, instance2);
    }

    @Test
    void getAllUsersWithActivitiesTest() throws DAOException, EncryptException {
        List<UserDTO> users = new ArrayList<>();
        assertEquals(users, DBManager.getInstance().getAllUsersWithActivities());

        DBManager.getInstance().addActivity(Activity.newBuilder().setId(1L).setCategory("Study").setName("Study").build());
        UserDAO.getInstance().addUser(User.newBuilder()
                .setId(1L)
                .setRole(Role.USER)
                .setLogin("User")
                .setName("User")
                .setPassword("password")
                .build());
        DBManager.getInstance().followActivity(1L, 1L);

        assertEquals(1, DBManager.getInstance().getAllUsersWithActivities().size());

        DBManager.getInstance().unfollowActivity(1L, 1L);
        assertEquals(0, DBManager.getInstance().getAllUsersWithActivities().get(0).getActivities().size());

    }

    @Test
    void addActivityTest() throws DAOException {
        assertEquals(0, ActivityDAO.getInstance().getAllActivities(1L).size());
        DBManager.getInstance().addActivity(Activity.newBuilder().setName("Study").setCategory("Study").build());
        assertEquals(1, ActivityDAO.getInstance().getAllActivities(1L).size());
    }

    @Test
    void followRequestTest() throws DAOException, EncryptException {
        assertEquals(0, DBManager.getInstance().getAllUsersWithActivities().size());
        DBManager.getInstance().addActivity(Activity.newBuilder().setId(1L).setCategory("Study").setName("Study").build());
        UserDAO.getInstance().addUser(User.newBuilder()
                .setId(1L)
                .setRole(Role.USER)
                .setLogin("User")
                .setName("User")
                .setPassword("password")
                .build());
        DBManager.getInstance().followRequest(1L, 1L);
        assertEquals(State.WAITING, DBManager.getInstance().getAllUsersWithActivities().get(0).getActivities().get(0).getState());

        DBManager.getInstance().confirmFollowRequest(1L, 1L);
        assertEquals(State.FOLLOWED, DBManager.getInstance().getAllUsersWithActivities().get(0).getActivities().get(0).getState());
    }

    @Test
    void setSpentTimeTets() throws DAOException, EncryptException {
        assertEquals(0, DBManager.getInstance().getAllUsersWithActivities().size());
        DBManager.getInstance().addActivity(Activity.newBuilder().setId(1L).setCategory("Study").setName("Study").build());
        UserDAO.getInstance().addUser(User.newBuilder()
                .setId(1L)
                .setRole(Role.USER)
                .setLogin("User")
                .setName("User")
                .setPassword("password")
                .build());
        DBManager.getInstance().followActivity(1L, 1L);
        assertNull(ActivityDAO.getInstance().getAllActivities(1L).get(0).getSpentTime().getTime());
        DBManager.getInstance().setSpentTime(1L, 1L, "111:12:12");
        List<ActivityDTO> activityDTOS = ActivityDAO.getInstance().getAllActivities(1L);
        assertEquals("111:12:12", activityDTOS.get(0).getSpentTime().getTime());
    }
}