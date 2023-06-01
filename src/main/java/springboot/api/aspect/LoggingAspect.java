package springboot.api.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class LoggingAspect {


    @Around("@annotation(log)")
    public Object logMethod(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        long start = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        String methodName = joinPoint.getSignature().getName();
        Map<String, Object> parameters = obtainParameters(joinPoint);
        logger.info("{} started with parameters: {}", methodName, parameters);
        Object proceed;
        long executionTime = System.currentTimeMillis() - start;
        try {
            proceed = joinPoint.proceed();
            executionTime = System.currentTimeMillis() - start;
        } catch (Throwable e) {
            logger.info("{} failed with exception message: {}", methodName, e.getMessage());
            logger.info("{} executed in {} ms", methodName, executionTime);
            throw e;
        }
        logger.info("{} finished with return value: {}", methodName, proceed);
        logger.info("{} executed in {} ms", methodName, executionTime);
        return proceed;
    }

    private Map<String, Object> obtainParameters(ProceedingJoinPoint joinPoint) {
        Map<String, Object> parameters = new HashMap<>();
        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        for (int i=0; i < parameterNames.length && i < parameterValues.length; i++) {
            parameters.put(parameterNames[i], parameterValues[i]);
        }
        return parameters;
    }



}
