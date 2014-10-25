package org.sidney.core.annotations;

import org.sidney.encoding.Encoding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Encode {
    Encoding encoding() default Encoding.PLAIN;
}