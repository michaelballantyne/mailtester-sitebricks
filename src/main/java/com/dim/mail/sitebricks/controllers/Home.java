package com.dim.mail.sitebricks.controllers;

import com.google.sitebricks.At;

@At("/")
public class Home {
	  private String message = "Hello";

	  public String getMessage() { return message; }
} 
