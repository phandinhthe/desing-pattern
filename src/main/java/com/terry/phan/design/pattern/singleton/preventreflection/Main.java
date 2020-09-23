package com.terry.phan.design.pattern.singleton.preventreflection;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getSecurityManager());
        Singleton singleton = Singleton.getInstance();
        Singleton singleton2 = null;
        try
        {
            Constructor constructor =
                    Arrays.stream(Singleton.class.getDeclaredConstructors()).findFirst().get();
            constructor.setAccessible(true);
            singleton2 = (Singleton) constructor.newInstance();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(singleton);
        System.out.println(singleton2);
        System.out.println(singleton == singleton2);
    }
}

class Singleton {
    private static Singleton instance;

    private Singleton() {
        if (null != instance) {
            throw new RuntimeException("Cannot create 2 singleton instance");
        }
        synchronized (Singleton.class) {
            if (instance != null) {
                throw new RuntimeException("Cannot create 2 singleton instance");
            }
            instance = this;
        }
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}
