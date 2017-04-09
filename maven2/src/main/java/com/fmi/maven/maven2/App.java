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

       //връщане към master branch

       new MySimpleClass().mySimpleMethod("Дончи");


       new MySimpleClass().mySimpleMethod(null);

       new MySimpleClass().mySimpleMethod("Донч");


       	methodOne();


    }
    
    public void doncho(){

    }
    public static void methodOne(){
    	 new MySimpleClass().mySimpleMethod("Донч");

    }
}
