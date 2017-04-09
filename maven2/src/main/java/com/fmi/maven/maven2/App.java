package com.fmi.maven.maven2;

import com.fmi.maven.maven1.MySimpleClass;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       new MySimpleClass().mySimpleMethod(null);

       //връщане към master branch

       new MySimpleClass().mySimpleMethod("Дончо");


    }
    public void methodOne(){

    }
    
    public void doncho(){

    }
}
