package com.emate.shop.common;

import java.util.Random;

public class RandomNumberBeanFactory {
	 public static RandomNumberBean getRandomBean() {
	        return new RandomNumberBean();
	 }

	 static public class RandomNumberBean {

	      	public RandomNumberBean() {
	            this.randomInteger = new Random().nextInt(1000000);
	            this.randomString = String.valueOf(this.randomInteger);
	        }

	        private String  randomString;

	        private Integer randomInteger;

	        public String getRandomString() {
	            return randomString;
	        }

	        public void setRandomString(String randomString) {
	            this.randomString = randomString;
	        }

	        public Integer getRandomInteger() {
	            return randomInteger;
	        }

	        public void setRandomInteger(Integer randomInteger) {
	            this.randomInteger = randomInteger;
	        }

	    }
}
