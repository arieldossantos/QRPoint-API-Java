package br.com.qrpoint.api.exception;

public class ApiKeyException extends Exception {
	public ApiKeyException() {
		System.out.println("Your API Key is invalid");
	}
}
