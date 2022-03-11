package com.my.timekeeping.DAO;

import com.my.timekeeping.DTO.ActivityDTO;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.entity.User;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DBManager {
    private static final Logger logger = LogManager.getLogger(DBManager.class);
    private static DBManager instance;
    private static final UserDAO userDAOInstance = UserDAO.getInstance();
    private static final ActivityDAO activityDAOInstance = ActivityDAO.getInstance();

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
        activityDAOInstance.getAllActivityForeachUser(users);
        return users;
    }

    public List<ActivityDTO> getAllActivities() throws DAOException {
        return activityDAOInstance.getAllActivity();
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
}
