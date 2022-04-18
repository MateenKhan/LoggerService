package mak.service;

import mak.annotation.LogAround;
import mak.model.Address;
import mak.model.Employee;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAroundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAroundService.class);

    @Autowired
    Environment env;

    @Around(value = "@annotation(mak.annotation.LogAround)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("***************************************");
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LogAround logAround = method.getAnnotation(LogAround.class);
        String beforeLogQuery = env.getProperty(logAround.before());


        LOGGER.debug(beforeLogQuery, getBeforeArgs(joinPoint));

        String afterLogQuery = env.getProperty(logAround.after());

        Object result = joinPoint.proceed();

        LOGGER.debug(afterLogQuery, getBeforeArgs(joinPoint));
        System.out.println("***************************************");
        return result;
    }


    private Object[] getBeforeArgs(ProceedingJoinPoint joinPoint) throws Exception {
        Object[] methodArguments = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LogAround logAround = method.getAnnotation(LogAround.class);
        String[] argFields = logAround.argFields();
        Employee e = (Employee) methodArguments[0];
        String cityArg = argFields[0];
        String[] fieldsArgs = cityArg.split("\\.");
        String getterMethod = getterMethod(fieldsArgs[0]);
        Address a = (Address)e.getClass().getMethod(getterMethod).invoke(e);
        String city = a.getCity();
        return new String[]{city};
    }

    private Object[] getAfterArgs(ProceedingJoinPoint joinPoint) throws Exception {
        return null;
    }

    public static String getterMethod(String field){
        if(field!=null && field.length()!=0 &&field.trim().length()!=0){
            return "get"+(field.charAt(0)+"").toUpperCase()+field.substring(1);
        }
        return null;
    }
}
