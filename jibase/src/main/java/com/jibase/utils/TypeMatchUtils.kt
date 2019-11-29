package com.jibase.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * Find type match of class
 * @param type : type let than
 * @param targetType: type than
 */
fun isTypeMatch(type: Type, targetType: Type): Boolean {
    if (type is Class<*> && targetType is Class<*>) {
        if (type.isAssignableFrom(targetType)) {
            return true
        }
    } else if (type is ParameterizedType && targetType is ParameterizedType) {
        if (isTypeMatch(type.rawType, targetType.rawType)) {
            val types = type.actualTypeArguments
            val targetTypes = targetType.actualTypeArguments
            if (types.size != targetTypes.size) {
                return false
            }
            val len = types.size
            for (i in 0 until len) {
                if (!isTypeMatch(types[i], targetTypes[i])) {
                    return false
                }
            }
            return true
        }
    }
    return false
}
