package com.my.timekeeping.Commands;

import com.my.timekeeping.Commands.filter.FilterByRoleCommand;
import com.my.timekeeping.Commands.filter.GlobalActivityFilterCommand;
import com.my.timekeeping.Commands.filter.GlobalUsersFilterCommand;

import java.util.HashMap;
import java.util.Map;

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
        commands.put("createActivity",new CreateActivityCommand());
        commands.put("deleteActivity", new DeleteActivityCommand());
        commands.put("followActivity", new FollowActivityCommand());
        commands.put("unfollowActivity", new UnfollowActivityCommand());
        commands.put("followRequestActivity", new FollowRequestCommand());
        commands.put("confirmActivity", new ConfirmActivityCommand());
        commands.put("refuseActivity", new RefuseActivityCommand());
    }

    private CommandContainer(){}

    public static Command getCommand(String commandName){
        return commands.get(commandName);
    }

}
