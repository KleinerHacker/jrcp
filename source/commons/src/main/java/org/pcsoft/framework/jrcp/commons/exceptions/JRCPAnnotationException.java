package org.pcsoft.framework.jrcp.commons.exceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class JRCPAnnotationException extends JRCPException{
    public JRCPAnnotationException(Class<?> errorClass, Class<? extends Annotation> annotationClass) {
        super("Missing required annotation " + annotationClass.getSimpleName() + " on class " + errorClass.getName());
    }

    public JRCPAnnotationException(Method method, Class<? extends Annotation> annotationClass) {
        super("Missing required annotation " + annotationClass.getSimpleName() + " on method " + method.toString());
    }
}
