package com.my.timekeeping.DAO;

import com.my.timekeeping.ConnectionPool;
import com.my.timekeeping.DTO.ActivityDTO;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.entity.State;
import com.my.timekeeping.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.timekeeping.DAO.SQLQuery.ActivityRequest.*;

public class ActivityDAO {
    private static final Logger logger = LogManager.getLogger(ActivityDAO.class);
    private static ActivityDAO instance;

    private ActivityDAO() {
    }

    public static synchronized ActivityDAO getInstance() {
        if (instance == null) instance = new ActivityDAO();
        return instance;
    }

    public void mapAllActivityForeachUser(List<UserDTO> users) throws DAOException {

        logger.trace("get all activities for users started");
        StringBuilder query = new StringBuilder(GET_ALL_FOR_USER_WHERE_TEMPLATE);

        query.append("user_id = ").append(users.get(0).getId());

        for (UserDTO userDTO : users) {
            query.append(" or ").append("user_id = ").append(userDTO.getId());
        }

        logger.debug("request {}", query);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.toString())) {

            while (resultSet.next()) {
                mapActivityToUser(resultSet, users);
            }

        } catch (SQLException exception) {
            logger.warn("error while getting activities . Caused by {}", exception.getMessage());
            throw new DAOException("Cannot get activities");
        }

        //return users;
    }

    private ActivityDTO mapActivity(ResultSet rs, int k) throws SQLException {
        logger.trace("activity mapping started");
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setId(rs.getLong(k++));
        activityDTO.setName(rs.getString(k++));
        activityDTO.setCategory(rs.getString(k++));
        activityDTO.setState(State.valueOf(rs.getString(k++)));
        logger.debug("mapped activity: {}", activityDTO);
        return activityDTO;
    }


    private void mapActivityToUser(ResultSet rs, List<UserDTO> userDTOList) throws SQLException {
        for (UserDTO userDTO : userDTOList) {
            if (userDTO.getId() == rs.getInt(1)) {
                userDTO.getActivities().add(mapActivity(rs, 2));
            }
        }
    }

    public List<ActivityDTO> getAllActivity(UserDTO user) throws DAOException {
        logger.trace("get all activities started");
        List<ActivityDTO> results = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ACTIVITIES_FOR_USER)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(mapActivity(resultSet, 1));
            }

        } catch (SQLException exception) {
            logger.warn("error while getting all activities . Caused by {}", exception.getMessage());
            throw new DAOException("failed get all activities");
        }
        return results;
    }

    public List<String> getAllCategories() throws DAOException {
        logger.trace("get all categories started");
        List<String> results = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_CATEGORIES)) {

            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }

        } catch (SQLException exception) {
            throw new DAOException("failed get all categories");
        }
        return results;
    }

    public boolean isActivityExist(String name, String category) throws DAOException {
        logger.trace("check is activity exists started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement getActivityStatement = connection.prepareStatement(GET_ACTIVITY_BY_PARAMETERS)) {
            getActivityStatement.setString(1, name);
            getActivityStatement.setString(2, category);
            ResultSet resultSet = getActivityStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException exception) {
            logger.warn("error while checking is activity exists . Caused by {}", exception.getMessage());
            throw new DAOException("error while checking");
        }
        return false;
    }

    public boolean isActivityCategoryExist(String category) throws DAOException {
        logger.trace("check is category exists started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement getCategoryStatement = connection.prepareStatement(GET_CATEGORY_BY_NAME)) {
            getCategoryStatement.setString(1, category);
            ResultSet resultSet = getCategoryStatement.executeQuery();
            if (resultSet.next()) return true;

        } catch (SQLException exception) {
            logger.error("error while checking is category exists cause:{}", exception.getMessage());
            throw new DAOException("error while checking is category exists");
        }
        return false;
    }

    public void createActivityCategory(String category) throws DAOException {
        logger.trace("create activity category started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement addCategoryStatement = connection.prepareStatement(ADD_NEW_ACTIVITY_CATEGORY)) {
            addCategoryStatement.setString(1, category);
            int res = addCategoryStatement.executeUpdate();
            System.out.println(res);
        } catch (SQLException exception) {
            logger.error("error while creating new category cause :{}", exception.getMessage());
            throw new DAOException("error while creating new category");
        }
    }


    public void addActivity(Activity activity) throws DAOException {
        logger.trace("add activity started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement getIDStatement = connection.prepareStatement(GET_CATEGORY_ID)) {
            getIDStatement.setString(1, activity.getCategory());
            ResultSet resultSet = getIDStatement.executeQuery();
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            PreparedStatement addActivityStatement = connection.prepareStatement(ADD_NEW_ACTIVITY);

            addActivityStatement.setString(1, activity.getName());
            addActivityStatement.setInt(2, id);

            int res = addActivityStatement.executeUpdate();
            System.out.println(res);
        } catch (SQLException exception) {
            logger.error("Error while trying add activity cause :{}", exception.getMessage());
            throw new DAOException("Error while trying add activity");
        }
    }

    public void deleteActivity(ActivityDTO activityDTO) throws DAOException {
        logger.trace("delete activity started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(DELETE_ACTIVITY)) {
            deleteStatement.setLong(1, activityDTO.getId());
            int res1 = deleteStatement.executeUpdate();
            System.out.println(res1);

            PreparedStatement deleteForUsersStatement = connection.prepareStatement(DELETE_FOR_USERS_ACTIVITY);
            deleteForUsersStatement.setLong(1, activityDTO.getId());
            int res2 = deleteForUsersStatement.executeUpdate();
            System.out.println(res2);
        } catch (SQLException exception) {
            logger.error("Error while trying delete activity cause:{}", exception.getMessage());
            throw new DAOException("Error while trying delete activity");
        }
    }

    public Long getActivityId(ActivityDTO activityDTO) throws DAOException {
        logger.trace("get activity id started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ACTIVITY_ID)) {
            preparedStatement.setString(1, activityDTO.getName());
            preparedStatement.setString(2, activityDTO.getCategory());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException exception) {
            logger.error("Error while trying get activity id cause :{}", exception.getMessage());
            throw new DAOException("Error while trying get activity id");
        }
    }

    public int countOfCategory(String category) throws DAOException {
        logger.trace("get count of categories started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement countOfCategoryStatement = connection.prepareStatement(COUNT_CATEGORY)) {
            countOfCategoryStatement.setString(1, category);
            ResultSet rs = countOfCategoryStatement.executeQuery();
            rs.next();
            int res = rs.getInt(1);
            System.out.println(res);
            return res;
        } catch (SQLException exception) {
            logger.error("Error while count category iteration cause:{}", exception.getMessage());
            throw new DAOException("Error while count category iteration");
        }

    }

    public void deleteCategory(String category) throws DAOException {
        logger.trace("delete category started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement deleteCategoryStatement = connection.prepareStatement(DELETE_CATEGORY)) {
            deleteCategoryStatement.setString(1, category);
            int res = deleteCategoryStatement.executeUpdate();
            System.out.println(res);
        } catch (SQLException exception) {
            logger.error("Error while deleting category cause:{}", exception.getMessage());
            throw new DAOException("Error while deleting category");
        }
    }
}