package com.linln.component.actionLog.annotation;

import java.lang.annotation.*;

/**
 * 控制器实体参数注解

 * @date 2/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface EntityParam {
}
