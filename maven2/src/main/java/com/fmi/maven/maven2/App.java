package com.fmi.maven.maven2;

import com.fmi.maven.maven1.MySimpleClass;

/**
 * Hello world!
 *
 */
public class App 
{
	public void ivan(){
		
	}
    public static void main( String[] args )
    {
       new MySimpleClass().mySimpleMethod(null);
       	methodOne();

    }
    public static void methodOne(){
    	 new MySimpleClass().mySimpleMethod("Донч");
    }
    
    public static void oneTestMethod(){
   	 new MySimpleClass().mySimpleMethod("TestMetod");
   }
}
