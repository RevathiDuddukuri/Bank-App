package org.cap.bankapptest;

import java.security.Provider.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import junit.framework.Assert;


@RunWith(Parameterized.class)
public class ParameterizedTestCase {
	
	private int input1;
	private int input2;
	private int output;
	 public ParameterizedTestCase(int input1, int input2, int output){
		 super();
		 this.input1=input1;
		 this.input2=input2;
		 this.output=output;
	 }
	 
	 @Parameters
	 public static Object[][] getAllParameter(){
		 return new Object[][]{
			 {10,20,30},
			 {1,2,3},
			 {11,12,13}
	
		 };
	 }
	 
	
	 }
