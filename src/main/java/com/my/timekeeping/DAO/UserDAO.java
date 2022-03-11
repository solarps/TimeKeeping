package com.my.timekeeping.DAO;

import com.my.timekeeping.DTO.ActivityDTO;
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

public class UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class);
    private static UserDAO instance;

    private UserDAO() {
    }

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public boolean isUserExist(String login) {
        boolean result = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(GET_USER_ID_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addUser(User user) throws DAOException, EncryptException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(ADD_NEW_USER)) {

            int k = 1;
            //preparedStatement.setInt(k++, user.getRoleId());
            preparedStatement.setInt(k++, 2);
            preparedStatement.setString(k++, user.getName());
            preparedStatement.setString(k++, user.getLogin());
            //preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setString(k, PasswordUtil.getSaltedHash(user.getPassword()));
            int res = preparedStatement.executeUpdate();
            System.out.println(res);
        } catch (SQLException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            throw new DAOException("cannot add new user");
        }
    }

    public UserDTO getUserByLogin(String login) throws DAOException {
        logger.trace("get user by login started");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(-1);

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

    private UserDTO mapUser(ResultSet rs) throws SQLException {
        logger.trace("user mapping started");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(-1);
        userDTO.setId(rs.getInt("id"));
        userDTO.setRole(Role.valueOf(rs.getString("role")));
        userDTO.setName(rs.getString("name"));
        userDTO.setLogin(rs.getString("login"));
        userDTO.setPassword(rs.getString("password"));
        logger.debug("mapped user: {}", userDTO);
        return userDTO;
    }

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

    public List<UserDTO> getAllUsersByRole(int[] roles_id) throws DAOException {
        logger.trace("get all users by role started");
        StringBuilder query = new StringBuilder(GET_ALL_WHERE_TEMPLATE);

        query.append("role_id = ").append(roles_id[0]);

        for (int i = 1; i < roles_id.length; i++) {
            query.append(" or ").append(" role_id = ").append(roles_id[i]);
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
