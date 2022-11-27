package mak.logger.service;

import mak.logger.annotation.LogAround;
import mak.logger.model.Employee;
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

//    @Around(value = "@annotation(mak.annotation.LogAround)")
    @Around("@annotation(mak.logger.annotation.LogAround)")
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
        Object a = e.getClass().getMethod(getterMethod).invoke(e);
        String getterMethod2 = getterMethod(fieldsArgs[1]);
        String city = (String)a.getClass().getMethod(getterMethod2).invoke(a);
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
