package com.acme.odp;

public class ODP {
	
	public static void main(String[] args) {
		
		if(args.length == 0) {
			System.out.println("Hello World!");
		} else {
			System.out.println("Hello " + args[0]);
		}
	}
}