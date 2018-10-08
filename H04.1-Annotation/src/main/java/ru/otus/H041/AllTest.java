package ru.otus.H041;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import ru.otus.H041.annotation.After;
import ru.otus.H041.annotation.Before;
import ru.otus.H041.annotation.Test;
import ru.otus.H041.RefHlp;

public class AllTest {

    private ArrayList<Method> AllMethods;

    public AllTest(Class cls)
    {
        if (null == cls)
            throw new RuntimeException("Class is NULL");

        this.AllMethods = new ArrayList<> (getAnnMethods(cls, Test.class));
    }

    public AllTest(String pckName) throws IOException, URISyntaxException, ClassNotFoundException {
        if ( pckName == null  || pckName.isEmpty())
            throw new RuntimeException("is empty package name");

        this.AllMethods = getAnnPackageMethods(pckName);
    }



    private ArrayList<Method> getAnnPackageMethods(String pckName) throws IOException, URISyntaxException, ClassNotFoundException {
        ArrayList<Class> clss = getPackageClss(pckName);
       return allTestMethods(clss);
    }

    private ArrayList<Class> getPackageClss(String pckName) throws IOException, URISyntaxException, ClassNotFoundException {
        ArrayList<Class> classes = RefHlp.getPackageClss (pckName);
        return classes;
    }

    private ArrayList<Method> allTestMethods(ArrayList<Class>classes) {
        ArrayList<Method> methods = new ArrayList<Method>();

        for (Class cls : classes) {
            List<Method> m = getAnnMethods(cls, Test.class);
            methods.addAll(m);
        }

       return methods;
    }


    private ArrayList<Method> getAnnMethods(Class cls, Class<? extends Annotation> annCls) {
        ArrayList<Method> res = new ArrayList<Method>();

        Method[] methods = cls.getDeclaredMethods();
        for (Method m : methods) {
            if (isAnnot(m, annCls)) {
                res.add(m);
            }
        }

        return res;
    }

    private Method getAnnMethod(Class cls, Class<? extends  Annotation> annClass) {
        ArrayList<Method> res = getAnnMethods(cls, annClass);
        if (0 == res.size()) {
            return null;
        } else if (1 == res.size()) {
            return res.get(0);
        }
        return null;
    }

    private boolean isAnnot(Method method, Class<? extends Annotation> annCls) {
        Annotation[] ann = method.getDeclaredAnnotations();
        if (ann.length > 0) {
            for (Annotation a : ann) {
                if (a.annotationType() == annCls) {
                    return true;
                }
            }
        }

        return false;
    }


    public void runTests() {
            for (Method m : this.AllMethods) {
            runTest(m);
      }

        return;
    }

    private void runTest(Method method) {

        Class cls = method.getDeclaringClass();
        Method m_before = getAnnMethod(cls, Before.class);
        Method m_after = getAnnMethod(cls, After.class);

        try {

            Object oInst = cls.getDeclaredConstructor().newInstance();
            if (null != m_before) {
                m_before.invoke(oInst);
            }

            method.invoke(oInst);

            if (null != m_after) {
                m_after.invoke(oInst);
            }
        }
        catch (Exception ex)
        {

        }

      return;
    }

}
