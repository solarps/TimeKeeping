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
        public static final String GET_ALL_FOR_USER_WHERE_TEMPLATE = "select user_id, activity_id, name, title as category, state\n" +
                "from users_has_activity\n" +
                "         join activities a on a.id = users_has_activity.activity_id\n" +
                "         join activity_category ac on ac.id = a.category_id\n" +
                "         join activity_state \"as\" on users_has_activity.state_id = \"as\".id\n" +
                "where ";
        public static final String GET_ALL_ACTIVITIES = "select activities.id, name, title as category from activities " +
                "join activity_category ac on ac.id = activities.category_id";
        public static final String GET_ALL_ACTIVITIES_FOR_USER = "select activities.id, name, title as category, case when state_id is null then 'UNFOLLOWED' else state end\n" +
                "from activities\n" +
                "         join activity_category ac on ac.id = activities.category_id\n" +
                "         left join users_has_activity uha on activities.id = uha.activity_id\n" +
                "         left join activity_state \"as\" on \"as\".id = state_id";
        public static final String GET_ALL_CATEGORIES = "select title from activity_category";
        public static final String GET_CATEGORY_BY_NAME = "select * from activity_category where title = ?";
        public static final String GET_ACTIVITY_BY_PARAMETERS = "select name, title from activities " +
                "join activity_category ac on ac.id = activities.category_id " +
                "where name = ? and title = ?";
        public static final String ADD_NEW_ACTIVITY_CATEGORY = "insert into activity_category (title) values (?)";
        public static final String ADD_NEW_ACTIVITY = "insert into activities (name, category_id) values (?,?)";
        public static final String GET_CATEGORY_ID = "select id from activity_category where title = ?";
        public static final String GET_ACTIVITY_ID = "select activities.id from activities\n" +
                "join activity_category ac on ac.id = activities.category_id\n" +
                "where name = ? and ac.title = ?";
        public static final String DELETE_ACTIVITY = "delete from activities where id = ?";
        public static final String DELETE_FOR_USERS_ACTIVITY = "delete from users_has_activity where activity_id = ?";
        public static final String DELETE_CATEGORY = "delete from activity_category where title = ?";
        public static final String COUNT_CATEGORY = "select count(*) from activities\n" +
                "join activity_category ac on activities.category_id = ac.id\n" +
                "where title = ?";
    }

    static class FollowRequest {
        public static final String FOLLOW_ACTIVITY = "insert into users_has_activity values (?,?)";
        public static final String FOLLOW_REQUEST = "insert into users_has_activity values (?,?,?)";
        public static final String UNFOLLOW_ACTIVITY = "DELETE FROM users_has_activity WHERE user_id = ? AND activity_id = ?";
        public static final String CONFIRM_REQUEST = "UPDATE public.users_has_activity SET state_id = 1 WHERE user_id = ? AND activity_id = ?";
    }
}
