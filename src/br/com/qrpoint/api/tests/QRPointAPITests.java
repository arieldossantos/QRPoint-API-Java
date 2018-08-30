package br.com.qrpoint.api.tests;

import br.com.qrpoint.api.ApiKey;
import br.com.qrpoint.api.QRPointAPI;
import br.com.qrpoint.api.exception.ApiKeyException;

public class QRPointAPITests {
	public static void main(String[] args) throws ApiKeyException {
		QRPointAPI api = new QRPointAPI(new ApiKey("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
		System.out.println(api.obterEmpresas());
		System.out.println(api.obterEmpregadores("24626495000127"));
		System.out.println(api.obterColaboradores("24626495000127"));
		System.out.println(api.obterRegistrosDePonto("24626495000127", 1596, "01/08/2018", "30/08/2018"));
	}
}
