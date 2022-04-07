package com.my.timekeeping.DAO;

import com.my.timekeeping.ConnectionPool;
import com.my.timekeeping.DTO.ActivityDTO;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.entity.User;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.my.timekeeping.DAO.SQLQuery.FollowRequest.*;

public class DBManager {
    private static final Logger logger = LogManager.getLogger(DBManager.class);
    private static DBManager instance;
    private static final UserDAO userDAOInstance = UserDAO.getInstance();
    private static final ActivityDAO activityDAOInstance = ActivityDAO.getInstance();

    private static final Integer wait_id = 2;
    private static final Integer followed_id = 1;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public List<UserDTO> getAllUsersWithActivities() throws DAOException {
        List<UserDTO> users = userDAOInstance.getAllUsers();
        activityDAOInstance.mapAllActivityForeachUser(users);
        return users;
    }

    public List<ActivityDTO> getAllActivities(UserDTO user) throws DAOException {
        return activityDAOInstance.getAllActivity(user);
    }

    public List<String> getAllCategories() throws DAOException {
        return activityDAOInstance.getAllCategories();
    }

    public boolean isUserExist(String login) {
        return UserDAO.getInstance().isUserExist(login);
    }

    public boolean isActivityExist(String name, String category) throws DAOException {
        return ActivityDAO.getInstance().isActivityExist(name, category);
    }

    public void addUser(User user) throws DAOException, EncryptException {
        UserDAO.getInstance().addUser(user);
    }

    public void addActivity(Activity activity) throws DAOException {
        if (!ActivityDAO.getInstance().isActivityCategoryExist(activity.getCategory())) {
            ActivityDAO.getInstance().createActivityCategory(activity.getCategory());
        }

        ActivityDAO.getInstance().addActivity(activity);
    }

    public boolean isActivityCategoryExist(String category) throws DAOException {
        return ActivityDAO.getInstance().isActivityCategoryExist(category);
    }

    public void deleteActivity(ActivityDTO activityDTO) throws DAOException {
        ActivityDAO.getInstance().deleteActivity(activityDTO);
    }

    public Long getActivityID(ActivityDTO activityDTO) throws DAOException {
        return ActivityDAO.getInstance().getActivityId(activityDTO);
    }

    public int countOfCategory(String category) throws DAOException {
        return ActivityDAO.getInstance().countOfCategory(category);
    }

    public void deleteCategory(String category) throws DAOException {
        ActivityDAO.getInstance().deleteCategory(category);
    }

    public void followActivity(Long user_id, Long activity_id) throws DAOException {
        logger.trace("Follow activity started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement followStatement = connection.prepareStatement(FOLLOW_ACTIVITY)) {
            followStatement.setLong(1, user_id);
            followStatement.setLong(2, activity_id);
            int res = followStatement.executeUpdate();
            System.out.println(res);
        } catch (SQLException exception) {
            logger.error("Error while trying follow activity cause:{}", exception.getMessage());
            throw new DAOException("Error while trying follow activity");
        }

    }

    public UserDTO getUserByLogin(String login) throws DAOException {
        return UserDAO.getInstance().getUserByLogin(login);
    }

    public void unfollowActivity(Long user_id, Long activity_id) throws DAOException {
        logger.trace("Unfollow activity started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement unfollowStatement = connection.prepareStatement(UNFOLLOW_ACTIVITY)) {
            unfollowStatement.setLong(1, user_id);
            unfollowStatement.setLong(2, activity_id);
            int res = unfollowStatement.executeUpdate();
            System.out.println(res);
        } catch (SQLException exception) {
            logger.error("Error while trying unfollow activity cause:{}", exception.getMessage());
            throw new DAOException("Error while trying unfollow activity");
        }
    }

    public void followRequest(Long user_id, Long activity_id) throws DAOException {
        logger.trace("saving request started");
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement requestStatement = connection.prepareStatement(FOLLOW_REQUEST);
            requestStatement.setLong(1, user_id);
            requestStatement.setLong(2, activity_id);
            requestStatement.setInt(3, wait_id);
            int res = requestStatement.executeUpdate();
            System.out.println(res);
        } catch (SQLException exception) {
            logger.error("Error while saving follow request cause:{}", exception.getMessage());
            throw new DAOException("Error while saving follow request");
        }
    }

    public void confirmActivity(Long user_id, Long activity_id) throws DAOException {
        logger.trace("confirm activity started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement confirmStatement = connection.prepareStatement(CONFIRM_REQUEST)) {
            confirmStatement.setLong(1, user_id);
            confirmStatement.setLong(2, activity_id);
            int res = confirmStatement.executeUpdate();
            System.out.println(res);
        } catch (SQLException exception) {
            logger.error("Error when confirming request cause:{}", exception.getMessage());
            throw new DAOException("Error when confirming request");
        }
    }
}
