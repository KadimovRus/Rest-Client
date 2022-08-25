package ru.kadimov.rest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kadimov.rest.config.MyConfig;
import ru.kadimov.rest.entity.User;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )  {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        List<User> userList = communication.getAllUsers();
        System.out.println(userList);

        User user = new User(3L, "James", "Brown", (byte)56);
        communication.saveUser(user);

        User userUpd = new User(3L, "Thomas", "Shelby", (byte)45);
        communication.updateUser(userUpd);

        communication.deleteUser(3L);
    }
}
