package com.my.timekeeping.dao;

import com.my.timekeeping.ConnectionPool;
import com.my.timekeeping.dto.ActivityDTO;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.exceptions.DAOException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.timekeeping.dao.SQLQuery.ActivityRequest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityDAOTest {

    @Mock
    ActivityDAO activityDAO;
    @Mock
    ConnectionPool connectionPool;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    Statement statement;
    @Mock
    ResultSet rs;

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
        try (MockedStatic<ActivityDAO> mockStatic = Mockito.mockStatic(ActivityDAO.class)) {
            mockStatic.when(ActivityDAO::getInstance).thenReturn(activityDAO);
            assertNotNull(activityDAO);
            assertEquals(ActivityDAO.getInstance(), activityDAO);
        }
        ActivityDAO instance1 = ActivityDAO.getInstance();
        assertNotNull(instance1);
        ActivityDAO instance2 = ActivityDAO.getInstance();
        assertEquals(instance1, instance2);

    }

    @Test
    void getAllActivitiesTest() throws SQLException {
        List<ActivityDTO> result = new ArrayList<>();
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(GET_ALL_ACTIVITIES_FOR_USER)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(rs);

            assertNotNull(connection);
            assertNotNull(preparedStatement);
            assertNotNull(rs);

            assertEquals(ActivityDAO.getInstance().getAllActivities(), result);
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getAllCategories() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);
            when(connection.createStatement()).thenReturn(statement);
            when(statement.executeQuery(GET_ALL_CATEGORIES)).thenReturn(rs);

            assertNotNull(connection);
            assertNotNull(preparedStatement);
            assertNotNull(rs);

            assertEquals(ActivityDAO.getInstance().getAllCategories(), new ArrayList<>());
        } catch (DAOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isActivityExistFalseCase() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(GET_ACTIVITY_BY_PARAMETERS)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(rs);

            assertNotNull(connection);
            assertNotNull(preparedStatement);
            assertNotNull(rs);

            assertFalse(ActivityDAO.getInstance().isActivityExist("Study", "Study"));
        } catch (DAOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isActivityExistTrueCase() throws DAOException {
        DBManager.getInstance().addActivity(Activity.newBuilder().setName("Study").setCategory("Study").build());

        assertTrue(ActivityDAO.getInstance().isActivityExist("Study", "Study"));
        assertTrue(ActivityDAO.getInstance().isActivityCategoryExist("Study"));
    }

    @Test
    void isActivityCategoryExist() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(GET_CATEGORY_BY_NAME)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(rs);

            assertNotNull(connection);
            assertNotNull(preparedStatement);
            assertNotNull(rs);

            assertFalse(ActivityDAO.getInstance().isActivityCategoryExist("Study"));
        } catch (DAOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createActivityCategory() throws DAOException {
        ActivityDAO.getInstance().createActivityCategory("Study");

        assertTrue(ActivityDAO.getInstance().isActivityCategoryExist("Study"));
        assertFalse(ActivityDAO.getInstance().isActivityCategoryExist("Sport"));
    }

    @Test
    void deleteActivity() throws DAOException {
        Activity activity = Activity.newBuilder().setId(1L).setName("Study").setCategory("Study").build();
        DBManager.getInstance().addActivity(activity);
        assertTrue(ActivityDAO.getInstance().isActivityExist(activity.getName(), activity.getCategory()));
        ActivityDAO.getInstance().deleteActivity(new ActivityDTO(1L, "Study", "Study"));
        assertFalse(ActivityDAO.getInstance().isActivityExist(activity.getName(), activity.getCategory()));
    }

    @Test
    void getActivityId() throws DAOException {
        Activity activity = Activity.newBuilder().setId(1L).setName("Study").setCategory("Study").build();
        DBManager.getInstance().addActivity(activity);
        assertEquals(1L, ActivityDAO.getInstance().
                getActivityId(new ActivityDTO(activity.getId(), activity.getName(), activity.getCategory())));
    }

    @Test
    void countOfCategory() throws DAOException {
        assertEquals(0, ActivityDAO.getInstance().countOfCategory("Study"));
        DBManager.getInstance().addActivity(Activity.newBuilder().setName("Study").setCategory("Study").build());
        assertEquals(1, ActivityDAO.getInstance().countOfCategory("Study"));
    }

    @Test
    void deleteCategory() throws DAOException {
        ActivityDAO.getInstance().deleteCategory("Study");
    }
}