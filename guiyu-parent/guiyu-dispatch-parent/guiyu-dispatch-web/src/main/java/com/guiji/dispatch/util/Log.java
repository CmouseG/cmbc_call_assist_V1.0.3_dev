package com.guiji.dispatch.util;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作内容
     * @return 操作内容
     */
    String info();

    /**
     * 操作菜单id
     * @return 操作菜单id
     */
    int menuCode() default 0;
}

