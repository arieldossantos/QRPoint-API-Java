package br.com.qrpoint.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.qrpoint.api.configs.Constants;

public class QRPointAPI {
	ApiKey apiKey;
	
	/**
	 * Criação do objeto api, objeto responsável por realizar requests a api QRPoint
	 * 
	 * @param apiKey objeto api key válido
	 */
	public QRPointAPI(ApiKey apiKey) {
		this.apiKey = apiKey;
	}
	
	
	/**
	 * Obtém as empresas disponíveis para a sua chave
	 * 
	 * @return Objeto JSON
	 */
	public JSONObject obterEmpresas() {
		if(this.apiKey.isValid()) {
			try {
				return new JSONObject(this.doRequest(Constants.OBTER_EMPRESAS, new JSONObject()));
			} catch (JSONException e) {
				return new JSONObject();
			}
		}else {
			return new JSONObject();
		}
	}

	/**
	 * Obtém empregadores de uma determinada empresa
	 * 
	 * @param codEmpresa código da empresa
	 * @return Objeto JSON
	 */
	public JSONObject obterEmpregadores(String codEmpresa) {
		if(this.apiKey.isValid()) {
			try {
				JSONObject json = new JSONObject("{\"empresa\": \"" + codEmpresa + "\"}");
				return new JSONObject(this.doRequest(Constants.OBTER_EMPREGADORES, json));
			} catch (JSONException e) {
				return new JSONObject();
			}
		}else {
			return new JSONObject();
		}
	}

	/**
	 * Obtém os colaboradores de um determinado empregador
	 * 
	 * @param codEmpregador Código do empregador obtido pelo metodo obterEmpregadores
	 * @return
	 */
	public JSONObject obterColaboradores(String codEmpregador) {
		if(this.apiKey.isValid()) {
			try {
				JSONObject json = new JSONObject("{\"empresa\": \"" + codEmpregador + "\"}");
				return new JSONObject(this.doRequest(Constants.OBTER_COLABORADORES, json));
			} catch (JSONException e) {
				return new JSONObject();
			}
		}else {
			return new JSONObject();
		}
	}

	/**
	 * Obtém os registros de ponto de um determinado colaborador
	 * 
	 * @param empregador Código do empregador obtido pelo metodo obterEmpregadores
	 * @param colaborador código do colaborador
	 * @param dataInicio Data de inicio em String, formato dd/MM/yyyy
	 * @param dataFim Data de fim em String, formato dd/MM/yyyy
	 * @return
	 */
	public JSONObject obterRegistrosDePonto(String empregador, int colaborador, String dataInicio, String dataFim) {
		if(this.apiKey.isValid()) {
			try {
				JSONObject json = new JSONObject();
				json.put("empregador", empregador);
				json.put("colaborador", colaborador);
				json.put("datainicio", dataInicio);
				json.put("datafim", dataFim);
				return new JSONObject(this.doRequest(Constants.OBTER_REGISTROS_DE_PONTO, json));
			} catch (JSONException e) {
				return new JSONObject();
			}
		}else {
			return new JSONObject();
		}
	}
	
	
	private String doRequest(String method, JSONObject bodyJson) {
		String body = bodyJson.toString();
		StringBuilder sb = new StringBuilder();
		String request = Constants.SERVER_URL + method;
		URL url;
		try {
			url = new URL( request );
		} catch (MalformedURLException e1) {
			return "{}";
		}
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			return "{}";
		}           
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		try {
			conn.setRequestMethod("POST");
		} catch (ProtocolException e1) {
			return "{}";
		}
		conn.setRequestProperty("Content-Type", "application/json"); 
		conn.setRequestProperty("Content-Length", Integer.toString(body.getBytes().length));
		conn.setRequestProperty("Authorization", this.apiKey.getKey());
		conn.setUseCaches(false);
		try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
		   wr.write(body.getBytes());
		}catch(Exception e) {
			return "{}";
		}
		try {
			conn.connect();
		} catch (IOException e1) {
			return "{}";
		}
		BufferedReader br;
		try {
			if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
			    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
			    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
		} catch (IOException e) {
			return "{}";
		}
		String line;
		try {
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			return "{}";
		}
		JSONObject json = null;
		try {
			json = new JSONObject(sb.toString());
		} catch (JSONException e) {
			return "{}";
		}
		
		return json.toString();		
	}
}
