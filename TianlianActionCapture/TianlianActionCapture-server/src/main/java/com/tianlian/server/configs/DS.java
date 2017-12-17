package com.tianlian.server.configs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.METHOD
})
public @interface DS {

  DatabaseType value() default DatabaseType.mysqlDb;

}
