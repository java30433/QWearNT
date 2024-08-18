package bakuen.plugin.apkhook.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 替换原包中的方法
 * @throws NoSuchMethodError
 */
@SuppressWarnings("JavadocDeclaration")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Replace {
}
