package com.my.timekeeping.DAO;

import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.PasswordUtil;
import com.my.timekeeping.entity.Role;
import com.my.timekeeping.entity.User;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import com.my.timekeeping.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.timekeeping.DAO.SQLQuery.UserRequest.*;

/**
 * This class for interaction in the database with user (dao layer)
 *
 * @author Andrey
 * @version 1.0
 */
public class UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class);
    private static UserDAO instance;

    private UserDAO() {
    }

    ///////////////
    ///singleton///
    ///////////////

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * This method that checks if the user exists in the database
     *
     * @param login user login
     * @return result of check
     */
    public boolean isUserExist(String login) {
        boolean result = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement getUserStatement =
                     connection.prepareStatement(GET_USER_ID_BY_LOGIN)) {
            getUserStatement.setString(1, login);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * This method that adds a new user to the database
     *
     * @param user new user to be added
     */
    public Long addUser(User user) throws DAOException, EncryptException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement addUserStatement =
                     connection.prepareStatement(ADD_NEW_USER)) {

            int k = 1;

            addUserStatement.setInt(k++, user.getRole().getId());
            addUserStatement.setString(k++, user.getName());
            addUserStatement.setString(k++, user.getLogin());
            addUserStatement.setString(k, PasswordUtil.getSaltedHash(user.getPassword()));

            ResultSet rs = addUserStatement.executeQuery();
            long res = 0L;
            if (rs.next()) {
                res = rs.getLong(1);
            }
            logger.trace("Returned id:{}", res);
            return res;
        } catch (SQLException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            throw new DAOException("cannot add new user");
        }
    }

    /**
     * This method that gets user from database by his login
     *
     * @param login user login
     * @return new user from database
     */
    public UserDTO getUserByLogin(String login) throws DAOException {
        logger.trace("get user by login started");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(-1L);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(GET_USER_BY_LOGIN)) {

            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userDTO = mapUser(resultSet);
            }
        } catch (SQLException e) {
            logger.error("error while getting users by login. Caused by {}", e.getMessage());
            throw new DAOException("Cannot get user");
        }
        return userDTO;
    }

    /**
     * This supporting method that maps result set from database to user entity
     *
     * @param rs result set from database response
     * @return new user
     */
    private UserDTO mapUser(ResultSet rs) throws SQLException {
        logger.trace("user mapping started");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(-1L);
        userDTO.setId(rs.getLong("id"));
        userDTO.setRole(Role.valueOf(rs.getString("role")));
        userDTO.setName(rs.getString("name"));
        userDTO.setLogin(rs.getString("login"));
        userDTO.setPassword(rs.getString("password"));
        logger.debug("mapped user: {}", userDTO);
        return userDTO;
    }

    /**
     * This method that gets all users from database
     *
     * @return list of users
     */
    public List<UserDTO> getAllUsers() throws DAOException {
        logger.trace("get all users started");
        List<UserDTO> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)) {

            while (resultSet.next()) {
                result.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            logger.error("error while getting users. Caused by {}", e.getMessage());
            throw new DAOException("Cannot get users");
        }
        return result;
    }

    /**
     * This method that gets all users by role
     *
     * @param rolesId array of roles id
     * @return list of users
     */
    public List<UserDTO> getAllUsersByRole(int[] rolesId) throws DAOException {
        logger.trace("get all users by role started");
        StringBuilder query = new StringBuilder(GET_ALL_WHERE_TEMPLATE);

        query.append("role_id = ").append(rolesId[0]);

        for (int i = 1; i < rolesId.length; i++) {
            query.append(" or ").append(" role_id = ").append(rolesId[i]);
        }
        logger.debug("request {}", query);

        List<UserDTO> result = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.toString())) {

            while (resultSet.next()) {
                result.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            logger.error("error while getting users. Caused by {}", e.getMessage());
            throw new DAOException("Cannot get users");
        }
        return result;
    }
}
