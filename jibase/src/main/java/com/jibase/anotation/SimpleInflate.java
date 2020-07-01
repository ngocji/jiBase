package com.jibase.anotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("ClassExplicitlyAnnotation")
public abstract class SimpleInflate implements Inflate {
    @Override
    public Class<? extends Annotation> annotationType() {
        return Inflate.class;
    }
}
