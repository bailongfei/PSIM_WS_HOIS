package com.util;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @Package: com.xdh.info.entity
 * @ClassName: ConstructorBuilder
 * @Description: TODO
 * @Author: LaoShiRen
 * @CreateDate: 2019-07-08 10:42
 * @Version: 1.0
 */
public class ConstructorBuilder<T,U> {

//    On
    public T builderObject(T t, Map<String, Object > map) {
//        获取map 所有的key
        Set<String> keySet = map.keySet();

//        获取泛型的所有方法
        Method[] declaredMethods = t.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
//            获取方法名 setQueueNo
            String methodName = declaredMethod.getName();
            for (String key : keySet) {
//                setQueueNo -- setqueueuno
                String methodTargetName = StringUtils.lowerCase(declaredMethod.getName());
//                queue_No - setqueueno
                String targetKey = "set" + StringUtils.lowerCase(StringUtils.replace(key,"_",""));
                if (methodTargetName.equals(targetKey)) {
                    Method declaredMethod1 = null;
                    Object o = map.get(key);
                    try {
                        declaredMethod1 = t.getClass().getDeclaredMethod(methodName, String.class);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (declaredMethod1 != null) {
                        try {
                            declaredMethod1.invoke(t, "" + o.toString());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return t;
    }
}
