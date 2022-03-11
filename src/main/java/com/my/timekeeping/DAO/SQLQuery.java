package com.my.timekeeping.DAO;

public class SQLQuery {
    static class UserRequest {
        public static final String GET_USER_ID_BY_LOGIN = "SELECT id FROM users WHERE login = ?";
        //public static final String GET_USER_BY_LOGIN = "SELECT * FROM users JOIN user_role USING (role_id) WHERE login = ?";
        public static final String ADD_NEW_USER = "insert into users (role_id, name, login, password) values (?, ?, ?, ?)";
        public static final String GET_USER_BY_LOGIN = "select users.id,title as role,name, login, password " +
                "from users join user_role ur on users.role_id = ur.id WHERE login = ?";
        public static final String GET_ALL_USERS = "select users.id,title as role,name, login, password " +
                "from users join user_role ur on users.role_id = ur.id";
        public static final String GET_ALL_WHERE_TEMPLATE = "select users.id,title as role,name, login, password from" +
                " users join user_role ur on users.role_id = ur.id where ";
    }

    static class ActivityRequest {
        public static final String GET_ALL_FOR_USER_WHERE_TEMPLATE = "select user_id, activity_id, name, title as category from users_has_activity\n" +
                "join activities a on a.id = users_has_activity.activity_id\n" +
                "join activity_category ac on ac.id = a.category_id\n" +
                "where ";
        public static final String GET_ALL_ACTIVITIES = "select activities.id, name, title as category from activities " +
                "join activity_category ac on ac.id = activities.category_id";
        public static final String GET_ALL_CATEGORIES = "select title from activity_category";
        public static final String GET_CATEGORY_BY_NAME = "select * from activity_category where title = ?";
        public static final String GET_ACTIVITY_BY_PARAMETERS = "select name, title from activities " +
                "join activity_category ac on ac.id = activities.category_id " +
                "where name = ? and title = ?";
        public static final String ADD_NEW_ACTIVITY_CATEGORY = "insert into activity_category (title) values (?)";
        public static final String ADD_NEW_ACTIVITY = "insert into activities (name, category_id) values (?,?)";
        public static final String GET_CATEGORY_ID = "select id from activity_category where title = ?";
    }
}
