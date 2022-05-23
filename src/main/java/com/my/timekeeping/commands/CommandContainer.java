package com.my.timekeeping.commands;

import com.my.timekeeping.commands.filter.*;
import com.my.timekeeping.commands.user.*;
import com.my.timekeeping.commands.activity.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This command conteiner for realization Command pattern
 *
 * @author Andrey
 * @version 1.0
 */
public class CommandContainer {

    private static final Map<String, Command> commands;

    static {
        commands = new HashMap<>();
        commands.put("register", new RegisterCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("getAllUsers", new GetAllUsersCommand());
        commands.put("getAllActivity", new GetAllActivityCommand());
        commands.put("filterByRole", new FilterByRoleCommand());
        commands.put("globalUsersFilter", new GlobalUsersFilterCommand());
        commands.put("globalActivityFilter", new GlobalActivityFilterCommand());
        commands.put("createActivity", new CreateActivityCommand());
        commands.put("deleteActivity", new DeleteActivityCommand());
        commands.put("unfollowActivity", new UnfollowActivityCommand());
        commands.put("followRequestActivity", new FollowRequestCommand());
        commands.put("refuseActivity", new RefuseActivityCommand());
        commands.put("confirmRequestActivity", new ConfirmRequestCommand());
        commands.put("setSpentTime", new SetSpentTimeCommand());
    }

    private CommandContainer() {
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }

}
