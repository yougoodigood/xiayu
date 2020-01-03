package com.xiayu.log;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Component
@ConditionalOnProperty(value = "ucex.features.audit", matchIfMissing = true)
public class AuditAdvisor implements PointcutAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(AuditAdvisor.class);

    @Override
    public Pointcut getPointcut() {
        return new Pointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return clazz -> clazz.isAnnotationPresent(Auditable.class);
            }

            @Override
            public MethodMatcher getMethodMatcher() {
                return new MethodMatcher() {
                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        return !method.getClass().equals(Object.class);
                    }

                    @Override
                    public boolean isRuntime() {
                        return false;
                    }

                    @Override
                    public boolean matches(Method method, Class<?> targetClass, Object... args) {
                        return !method.getClass().equals(Object.class);
                    }
                };
            }
        };
    }

    @Override
    public Advice getAdvice() {
        return new AuditAdvice();
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

    //dev note- must be public because afterThrowing is called reflectively by spring aop
    public class AuditAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {
        @Override
        public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
            logger.info("after return - authName {}, returnVal {}, method {}, args {}",
                    authName(),
                    returnValue,
                    method.toGenericString(),
                    Arrays.deepToString(args)
            );
        }

        public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
            logger.info("after throwing - authName {}, method {}, args {}",
                    authName(),
                    method.toGenericString(),
                    Arrays.deepToString(args),
                    ex
            );
        }

        @Override
        public void before(Method method, Object[] args, Object target) {
            authName();
            // todo check that it is an admin role?
            // dev note - we need to move our security auth token into ucex-commons first
        }

//        public String authName() throws AuthenticationCredentialsNotFoundException {
        public String authName() {
//            try {
//                return Objects.requireNonNull(
//                        Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication())
//                                .getName()
//                );
//            } catch (NullPointerException e) {
//                throw new AuthenticationCredentialsNotFoundException("auth credentials not found", e);
//            }
            return "";
        }
    }

}
