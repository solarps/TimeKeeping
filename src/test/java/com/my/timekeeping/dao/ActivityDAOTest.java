package com.my.timekeeping.dao;

import com.my.timekeeping.ConnectionPool;
import com.my.timekeeping.dto.ActivityDTO;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.exceptions.DAOException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ActivityDAOTest {


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
        ActivityDAO instance1 = ActivityDAO.getInstance();
        assertNotNull(instance1);
        ActivityDAO instance2 = ActivityDAO.getInstance();
        assertEquals(instance1, instance2);

    }

    @Test
    void getAllActivitiesTest() throws DAOException {
        assertEquals(0, ActivityDAO.getInstance().getAllActivities(1L).size());

        DBManager.getInstance().addActivity(Activity.newBuilder().setName("Study").setCategory("Study").build());
        List<ActivityDTO> result = ActivityDAO.getInstance().getAllActivities(1L);

        assertEquals(1, result.size());
        assertEquals("Study", result.get(0).getName());

    }

    @Test
    void getAllCategoriesTest() throws DAOException {
        assertEquals(0, ActivityDAO.getInstance().getAllCategories().size());

        DBManager.getInstance().addActivity(Activity.newBuilder().setName("Study").setCategory("Study").build());
        List<String> result = ActivityDAO.getInstance().getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Study", result.get(0));
    }

    @Test
    void isActivityExistTest() throws DAOException {
        assertFalse(ActivityDAO.getInstance().isActivityExist("Study", "Study"));
        assertFalse(ActivityDAO.getInstance().isActivityCategoryExist("Study"));

        DBManager.getInstance().addActivity(Activity.newBuilder().setName("Study").setCategory("Study").build());

        assertTrue(ActivityDAO.getInstance().isActivityExist("Study", "Study"));
        assertTrue(ActivityDAO.getInstance().isActivityCategoryExist("Study"));

    }


    @Test
    void isActivityCategoryExistTest() throws DAOException {
        ActivityDAO.getInstance().createActivityCategory("Study");
        assertTrue(ActivityDAO.getInstance().isActivityCategoryExist("Study"));
        ActivityDAO.getInstance().deleteCategory("Study");
        assertFalse(ActivityDAO.getInstance().isActivityCategoryExist("Study"));
    }

    @Test
    void createActivityCategoryTest() throws DAOException {
        ActivityDAO.getInstance().createActivityCategory("Study");

        assertTrue(ActivityDAO.getInstance().isActivityCategoryExist("Study"));
        assertFalse(ActivityDAO.getInstance().isActivityCategoryExist("Sport"));
    }

    @Test
    void deleteActivityTest() throws DAOException {
        Activity activity = Activity.newBuilder().setId(1L).setName("Study").setCategory("Study").build();
        DBManager.getInstance().addActivity(activity);
        assertTrue(ActivityDAO.getInstance().isActivityExist(activity.getName(), activity.getCategory()));
        ActivityDAO.getInstance().deleteActivity(new ActivityDTO(1L, "Study", "Study"));
        assertFalse(ActivityDAO.getInstance().isActivityExist(activity.getName(), activity.getCategory()));
    }

    @Test
    void getActivityIdTest() throws DAOException {
        Activity activity = Activity.newBuilder().setId(1L).setName("Study").setCategory("Study").build();
        DBManager.getInstance().addActivity(activity);
        assertEquals(1L, ActivityDAO.getInstance().
                getActivityId(new ActivityDTO(activity.getId(), activity.getName(), activity.getCategory())));
    }

    @Test
    void countOfCategoryTest() throws DAOException {
        assertEquals(0, ActivityDAO.getInstance().countOfCategory("Study"));
        DBManager.getInstance().addActivity(Activity.newBuilder().setName("Study").setCategory("Study").build());
        assertEquals(1, ActivityDAO.getInstance().countOfCategory("Study"));
    }

    @Test
    void deleteCategoryTest() throws DAOException {
        ActivityDAO.getInstance().deleteCategory("Study");
    }
}