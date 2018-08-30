package br.com.qrpoint.api.tests;

import br.com.qrpoint.api.exception.ApiKeyException;

public class ApiKey {
	public static void main(String[] args) {
		br.com.qrpoint.api.ApiKey a = null;
		try {
			a = new br.com.qrpoint.api.ApiKey("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		}catch(ApiKeyException e) {
			System.out.println(e);
		}
		System.out.println(a.isValid());
	}
}
