package com.my.timekeeping.DAO;

import com.my.timekeeping.ConnectionPool;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.my.timekeeping.DAO.SQLQuery.FollowRequest.*;

/**
 * This support class for interacting with several entities in the database at once(dao layer)
 *
 * @author Andrey
 * @version 1.0
 */

public class DBManager {
    private static final Logger logger = LogManager.getLogger(DBManager.class);
    private static DBManager instance;
    private static final UserDAO userDAOInstance = UserDAO.getInstance();
    private static final ActivityDAO activityDAOInstance = ActivityDAO.getInstance();

    private static final Integer WAIT_ID = 2;

    private DBManager() {
    }

    ///////////////
    ///singleton///
    ///////////////

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }


    /**
     * This method that gets all users with their activities
     *
     * @return list of users
     */
    public List<UserDTO> getAllUsersWithActivities() throws DAOException {
        List<UserDTO> users = userDAOInstance.getAllUsers();
        activityDAOInstance.mapAllActivityForeachUser(users);
        return users;
    }

    /**
     * This method that add activity to the database. If activity category isn't exist,
     * category will be added to the database.
     *
     * @param activity new activity
     */
    public void addActivity(Activity activity) throws DAOException {
        if (!ActivityDAO.getInstance().isActivityCategoryExist(activity.getCategory())) {
            ActivityDAO.getInstance().createActivityCategory(activity.getCategory());
        }

        ActivityDAO.getInstance().addActivity(activity);
    }

    /**
     * This method confirm the connection between the user and the activity, change state to followed in database
     *
     * @param userId     user id
     * @param activityId activity id
     */
    public void followActivity(Long userId, Long activityId) throws DAOException {
        logger.trace("Follow activity started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement followStatement = connection.prepareStatement(FOLLOW_ACTIVITY)) {
            followStatement.setLong(1, userId);
            followStatement.setLong(2, activityId);
            int res = followStatement.executeUpdate();
            traceResult(res);
        } catch (SQLException exception) {
            logger.error("Error while trying follow activity cause:{}", exception.getMessage());
            throw new DAOException("Error while trying follow activity");
        }
    }

    /**
     * This method delete the connection between the user and the activity
     *
     * @param userId     user id
     * @param activityId activity id
     */
    public void unfollowActivity(Long userId, Long activityId) throws DAOException {
        logger.trace("Unfollow activity started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement unfollowStatement = connection.prepareStatement(UNFOLLOW_ACTIVITY)) {
            unfollowStatement.setLong(1, userId);
            unfollowStatement.setLong(2, activityId);
            int res = unfollowStatement.executeUpdate();
            traceResult(res);
        } catch (SQLException exception) {
            logger.error("Error while trying unfollow activity cause:{}", exception.getMessage());
            throw new DAOException("Error while trying unfollow activity");
        }
    }

    /**
     * This method create the connection between the user and the activity, change state to waited in database
     *
     * @param userId     user id
     * @param activityId activity id
     */
    public void followRequest(Long userId, Long activityId) throws DAOException {
        logger.trace("saving request started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement requestStatement = connection.prepareStatement(FOLLOW_REQUEST)) {
            requestStatement.setLong(1, userId);
            requestStatement.setLong(2, activityId);
            requestStatement.setInt(3, WAIT_ID);
            int res = requestStatement.executeUpdate();
            traceResult(res);
        } catch (SQLException exception) {
            logger.error("Error while saving follow request cause:{}", exception.getMessage());
            throw new DAOException("Error while saving follow request");
        }
    }

    /**
     * This method confirm the connection between the user and the activity, change state to followed in database
     *
     * @param userId     user id
     * @param activityId activity id
     */
    public void confirmActivity(Long userId, Long activityId) throws DAOException {
        logger.trace("confirm activity started");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement confirmStatement = connection.prepareStatement(CONFIRM_REQUEST)) {
            confirmStatement.setLong(1, userId);
            confirmStatement.setLong(2, activityId);
            int res = confirmStatement.executeUpdate();
            traceResult(res);
        } catch (SQLException exception) {
            logger.error("Error when confirming request cause:{}", exception.getMessage());
            throw new DAOException("Error when confirming request");
        }
    }

    /**
     * This method that trace result in log4j
     *
     * @param res result of database query
     */
    private void traceResult(int res) {
        logger.trace("Result :{}", res);
    }
}
