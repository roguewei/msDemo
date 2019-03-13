package com.rogue.gbf.gbfdemo.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogue.gbf.gbfdemo.exception.GlobalException;
import com.rogue.gbf.gbfdemo.result.CodeMsg;
import com.rogue.gbf.gbfdemo.validator.annotation.NeedLogin;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author weigaosheng
 * @description
 * @CalssName NeedLoginAspect
 * @date 2019/3/12
 * @params
 * @return
 */
@Aspect
@Component
public class NeedLoginAspect {

    /*切点表达式中，..两个点表明多个，*代表一个，  上面表达式代表切入com.xhx.springboot.controller包下的所有类的所有方法，
    方法参数不限，返回类型不限。  其中访问修饰符可以不写，不能用*，，第一个*代表返回类型不限，第二个*表示所有类，
    第三个*表示所有方法，..两个点表示方法里的参数不限。  然后用@Pointcut切点注解，想在一个空方法上面，一会儿在Advice通知中，
    直接调用这个空方法就行了，也可以把切点表达式卸载Advice通知中的，单独定义出来主要是为了好管理*/

    private final String POINT_CUT = "execution(public * com.rogue.gbf.gbfdemo.controller.*.*(..))";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //使用org.slf4j.Logger,这是spring实现日志的方法
    private final static Logger logger = LoggerFactory.getLogger(NeedLoginAspect.class);

    @Pointcut(POINT_CUT)
    public void pointCut(){}

    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint){
        logger.info("@Before通知执行");
//        //获取目标方法参数信息
//        Object[] args = joinPoint.getArgs();
//        Arrays.stream(args).forEach(arg->{  // 大大
//            try {
//                logger.info(OBJECT_MAPPER.writeValueAsString(arg));
//            } catch (JsonProcessingException e) {
//                logger.info(arg.toString());
//            }
//        });
//
//
//        //aop代理对象
//        Object aThis = joinPoint.getThis();
//        logger.info(aThis.toString()); //com.xhx.springboot.controller.HelloController@69fbbcdd
//
//        //被代理对象
//        Object target = joinPoint.getTarget();
//        logger.info(target.toString()); //com.xhx.springboot.controller.HelloController@69fbbcdd
//
//        //获取连接点的方法签名对象
//        Signature signature = joinPoint.getSignature();
//        logger.info(signature.toLongString()); //public java.lang.String com.xhx.springboot.controller.HelloController.getName(java.lang.String)
//        logger.info(signature.toShortString()); //HelloController.getName(..)
//        logger.info(signature.toString()); //String com.xhx.springboot.controller.HelloController.getName(String)
//        //获取方法名
//        logger.info(signature.getName()); //getName
//        //获取声明类型名
//        logger.info(signature.getDeclaringTypeName()); //com.xhx.springboot.controller.HelloController
//        //获取声明类型  方法所在类的class对象
//        logger.info(signature.getDeclaringType().toString()); //class com.xhx.springboot.controller.HelloController
//        //和getDeclaringTypeName()一样
//        logger.info(signature.getDeclaringType().getName());//com.xhx.springboot.controller.HelloController
//
//        //连接点类型
//        String kind = joinPoint.getKind();
//        logger.info(kind);//method-execution
//
//        //返回连接点方法所在类文件中的位置  打印报异常
//        SourceLocation sourceLocation = joinPoint.getSourceLocation();
//        logger.info(sourceLocation.toString());
//        //logger.info(sourceLocation.getFileName());
//        //logger.info(sourceLocation.getLine()+"");
//        //logger.info(sourceLocation.getWithinType().toString()); //class com.xhx.springboot.controller.HelloController
//
//        ///返回连接点静态部分
//        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
//        logger.info(staticPart.toLongString());  //execution(public java.lang.String com.xhx.springboot.controller.HelloController.getName(java.lang.String))
//
//
//        //attributes可以获取request信息 session信息等
//        ServletRequestAttributes attributes =
//                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        logger.info(request.getRequestURL().toString()); //http://127.0.0.1:8080/hello/getName
//        logger.info(request.getRemoteAddr()); //127.0.0.1
//        logger.info(request.getMethod()); //GET

        logger.info("before通知执行结束");
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 检查参数是否为空
     * @Date 14:52 2019/3/1
     * @Param
     **/
    @Around(value = "pointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        MethodSignature signature = ((MethodSignature) proceedingJoinPoint.getSignature());
        //得到拦截的方法
        Method method = signature.getMethod();
        //获取方法参数注解，返回二维数组是因为某些参数可能存在多个注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return proceedingJoinPoint.proceed();
        }
        //获取方法参数名
        String[] paramNames = signature.getParameterNames();
        //获取参数值
        Object[] paranValues = proceedingJoinPoint.getArgs();
        //获取方法参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                //如果该参数前面的注解是NeedLogin的实例,则进行非空校验
                if (parameterAnnotations[i][j] != null && parameterAnnotations[i][j] instanceof NeedLogin) {
                    System.out.println(paramNames[i]+"-------"+paranValues[i]);
                    paramIsNull(paramNames[i], paranValues[i], parameterTypes[i] == null ? null : parameterTypes[i].getName());
                    break;
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

    /**
     * 参数非空校验，如果参数为空，则抛出ParamIsNullException异常
     * @param paramName
     * @param value
     * @param parameterType
     */
    private void paramIsNull(String paramName, Object value, String parameterType) {
        if (value == null || "".equals(value.toString().trim())) {
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
    }

}
