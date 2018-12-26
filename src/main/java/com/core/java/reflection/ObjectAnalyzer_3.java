package com.core.java.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * @author
 * @date 2018.12.20
 */
public class ObjectAnalyzer_3 {

    private ArrayList<Object> visited = new ArrayList<>();

    /**
     * Converts an object to a string representation lists all fields.
     *
     * @param object an object
     * @return a string with the object's class name and all field names and values
     */
    public String toString(Object object) {
        if (object == null) return "null";
        if (visited.contains(object)) return "...";
        visited.add(object);
        Class<?> cl = object.getClass();
        if (cl == String.class) return (String) object;

        // if cl is array
        if (cl.isArray()) {
            // get array's component type
            String r = cl.getComponentType() + "[]{";
            for (int i = 0; i < Array.getLength(object); i++) {   // Array get the length of an array
                if (i > 0) r += ",";
                Object val = Array.get(object, i);
                if (cl.getComponentType().isPrimitive()) {    // component type is primitive
                    r += val;
                } else {
                    // recursion
                    r += toString(val);
                }
            }
            return r + "}";
        }

        String r = "";
        // inspect the fields of this class and all superclasses
        do {
            r += cl.getName();
            r += "[";
            Field[] fields = cl.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            //get the names and values of all fields
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (!r.endsWith("[")) {
                        r += ",";
                    }
                    r += field.getName() + "=";
                    try {
                        Class<?> type = field.getType();
                        Object val = field.get(object);
                        if (type.isPrimitive()) {
                            r += val;
                        } else {
                            r += toString(val);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            r += "],  ";
            cl = cl.getSuperclass();
        } while (cl != null);
        return r;
    }
}
