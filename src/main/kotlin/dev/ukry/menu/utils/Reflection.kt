/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.utils

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

object Reflection {

    @JvmStatic
    var DEBUG = false

    @JvmStatic
    fun wrapperToPrimitive(clazz: Class<*>): Class<*> {
        return when (clazz) {
            Boolean::class.java -> return Boolean::class.javaPrimitiveType as Class<*>
            Int::class.java -> return Int::class.javaPrimitiveType as Class<*>
            Double::class.java -> return Double::class.javaPrimitiveType as Class<*>
            Float::class.java -> return Float::class.javaPrimitiveType as Class<*>
            Long::class.java -> return Long::class.javaPrimitiveType as Class<*>
            Short::class.java -> return Short::class.javaPrimitiveType as Class<*>
            Char::class.java -> Char::class.javaPrimitiveType
            Byte::class.java -> return Byte::class.javaPrimitiveType as Class<*>
            Void::class.java -> return Void.TYPE
            else -> clazz
        } as Class<*>
    }

    @JvmStatic
    fun toParamTypes(vararg params: Any?): Array<Class<*>?> {
        val classes = arrayOfNulls<Class<*>>(params.size)
        for (i in params.indices) {
            if (params[i] == null) {
                classes[i] = Any::class.java
            } else {
                classes[i] = wrapperToPrimitive(params[i]!!.javaClass)
            }
        }
        return classes
    }

    @JvmStatic
    fun getClassSuppressed(name: String): Class<*>? {
        return try {
            Class.forName(name)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun getMethodSuppressed(clazz: Class<*>, methodName: String, vararg params: Class<*>): Method? {
        return try {
            val method = clazz.getMethod(methodName, *params)
            method.isAccessible = true
            method
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun getDeclaredMethod(clazz: Class<*>, methodName: String, vararg params: Class<*>): Method? {
        return try {
            val declaredMethod = clazz.getDeclaredMethod(methodName, *params)
            declaredMethod.isAccessible = true
            declaredMethod
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun callMethod(instance: Any, methodName: String, vararg params: Any): Any? {
        return try {
            val method = instance.javaClass.getMethod(methodName, *toParamTypes(*params))
            method.isAccessible = true
            method.invoke(instance, *params)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }

    }

    @JvmStatic
    fun callDeclaredMethod(instance: Any, methodName: String, vararg params: Any): Any? {
        return try {
            val declaredMethod = instance.javaClass.getDeclaredMethod(methodName, *toParamTypes(*params))
            declaredMethod.isAccessible = true
            declaredMethod.invoke(instance, *params)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }

    }

    @JvmStatic
    fun callConstructor(clazz: Class<*>, vararg params: Any?): Any? {
        return try {
            val constructor = clazz.getConstructor(*toParamTypes(*params))
            constructor.isAccessible = true
            constructor.newInstance(*params)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun callDeclaredConstructor(clazz: Class<*>, vararg params: Any?): Any? {
        return try {
            val declaredConstructor = clazz.getDeclaredConstructor(*toParamTypes(*params))
            declaredConstructor.isAccessible = true
            declaredConstructor.newInstance(*params)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun getConstructor(clazz: Class<*>, vararg params: Class<*>): Constructor<*>? {
        return try {
            val constructor = clazz.getConstructor(*params)
            constructor.isAccessible = true
            constructor
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()

                for (constructor in clazz.constructors) {
                    println("<init>(${constructor.parameterTypes.joinToString { it.simpleName }})")
                }
            }

            null
        }
    }

    @JvmStatic
    fun getDeclaredConstructor(clazz: Class<*>, vararg params: Class<*>): Constructor<*>? {
        return try {
            val declaredConstructor = clazz.getDeclaredConstructor(*params)
            declaredConstructor.isAccessible = true
            declaredConstructor
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()

                for (constructor in clazz.constructors) {
                    println("<init>(${constructor.parameterTypes.joinToString { it.simpleName }})")
                }
            }

            null
        }
    }

    @JvmStatic
    fun getField(clazz: Class<*>, fieldName: String): Field? {
        return try {
            val field = clazz.getField(fieldName)
            field.isAccessible = true
            field
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun getDeclaredField(clazz: Class<*>, fieldName: String): Field? {
        return try {
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            field
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun getFieldValue(instance: Any, fieldName: String): Any? {
        return try {
            val field = instance.javaClass.getField(fieldName)
            field.isAccessible = true
            field.get(instance)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun getFieldValue(clazz: Class<*>, instance: Any, fieldName: String): Any? {
        return try {
            val declaredField = clazz.getField(fieldName)
            declaredField.isAccessible = true
            declaredField.get(instance)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun setFieldValue(instance: Any, fieldName: String, value: Any) {
        try {
            val field = instance.javaClass.getField(fieldName)
            field.isAccessible = true
            field.set(instance, value)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun setFieldValue(clazz: Class<*>, instance: Any, fieldName: String, value: Any) {
        try {
            val declaredField = clazz.getField(fieldName)
            declaredField.isAccessible = true
            declaredField.set(instance, value)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun getDeclaredFieldValue(clazz: Class<*>, instance: Any, fieldName: String): Any? {
        return try {
            val declaredField = clazz.getDeclaredField(fieldName)
            declaredField.isAccessible = true
            declaredField.get(instance)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun getDeclaredFieldValue(instance: Any, fieldName: String): Any? {
        return try {
            val declaredField = instance.javaClass.getDeclaredField(fieldName)
            declaredField.isAccessible = true
            declaredField.get(instance)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }

            null
        }
    }

    @JvmStatic
    fun setDeclaredFieldValue(clazz: Class<*>, instance: Any, fieldName: String, value: Any) {
        try {
            val declaredField = clazz.getDeclaredField(fieldName)
            declaredField.isAccessible = true
            declaredField.set(instance, value)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun setDeclaredFieldValue(instance: Any, fieldName: String, value: Any) {
        try {
            val declaredField = instance.javaClass.getDeclaredField(fieldName)
            declaredField.isAccessible = true
            declaredField.set(instance, value)
        } catch (e: Exception) {
            if (DEBUG) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun getEnum(type: Class<*>, name: String): Any? {
        return try {
            return getDeclaredField(type, name)?.get(null)
        } catch (e: ReflectiveOperationException) {
            e.printStackTrace()
            null
        }
    }

}