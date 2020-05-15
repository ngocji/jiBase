package com.jibase.anotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("ClassExplicitlyAnnotation")
public abstract class SimpleBindingInfo implements BindingInfo {
    @Override
    public Class<? extends Annotation> annotationType() {
        return BindingInfo.class;
    }
}
