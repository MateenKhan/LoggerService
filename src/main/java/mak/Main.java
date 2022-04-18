package mak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        SpringApplication.run(Main.class, args);
    }

}
