package com.sunset.discjockey.util.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MessageHandler
{
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TagMessageHandler
    {
        String id() default "";
    }

//    static {
//        String packageName = Reference.MOD_PACKAGE;
//        try {
//            ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClassesRecursive(packageName).forEach(info -> {
//                try {
//                    Class<?> clazz = info.load();
//                    Method[] methods = clazz.getDeclaredMethods();
//                    for (Method method : methods) {
//                        if (method.isAnnotationPresent(TagMessageHandler.class)) {
//                            TagMessageHandler annotation = method.getAnnotation(TagMessageHandler.class);
//                            String id = annotation.id();
//                            BiConsumer<TagMessage, Supplier<NetworkEvent.Context>> handler = (message, contextSupplier) -> {
//                                try {
//                                    method.invoke(null, message, contextSupplier);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            };
//                            TagMessage.HANDLERS.put(id, handler);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
