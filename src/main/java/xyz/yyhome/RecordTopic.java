package xyz.yyhome;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 给练习题添加题目类型注解
 * @author yy
 */

@Target(value = {ElementType.METHOD,ElementType.TYPE})
public @interface RecordTopic {
    String[] type() default {"LeetCode"};
}
