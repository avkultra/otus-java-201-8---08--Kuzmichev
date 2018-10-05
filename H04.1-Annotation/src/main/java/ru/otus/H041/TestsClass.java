package ru.otus.H041;

import ru.otus.H041.annotation.After;
import ru.otus.H041.annotation.Before;
import ru.otus.H041.annotation.Test;
import  java.lang.Exception;

public class TestsClass {

    private int member = 0;

    @Before
    public void before()
    {
        this.member = 255;
        System.out.println("before method");
    }

    @Test
    public void test() throws Exception
    {
        if( 255 != this.member)
        {
            throw new Exception("expected legal value");
        }
    }

    @After
    public void after()
    {
        System.out.println(" after method");
    }
}