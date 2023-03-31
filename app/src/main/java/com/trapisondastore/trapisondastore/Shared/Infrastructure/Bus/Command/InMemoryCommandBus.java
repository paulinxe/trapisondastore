package com.trapisondastore.trapisondastore.Shared.Infrastructure.Bus.Command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.Command;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.CommandBus;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.CommandHandler;

public class InMemoryCommandBus implements CommandBus {

    @Override
    public void dispatch(Command command) {
        final String handlerName = command.getClass().getName() + "Handler";

        try {
            Class<?> handler = Class.forName(handlerName);
            Constructor<?> constructor = handler.getDeclaredConstructor();
            Object handlerObject = constructor.newInstance();
            ((CommandHandler) handlerObject).handle(command);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // @TODO: Define custom exception.
            e.printStackTrace();
        }
    }
}
