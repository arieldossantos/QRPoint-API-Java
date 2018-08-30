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
import br.com.qrpoint.api.exception.ApiKeyException;

public class ApiKey {
	private String apiKey;
	
	/**
	 * Chave da API obtida no site.
	 * 
	 * @param api
	 * @throws ApiKeyException
	 */
	public ApiKey(String api) throws ApiKeyException {
		if(api.length() >= 32) {
			this.apiKey = api;	
		}else {
			throw new ApiKeyException();
		}
	}
	
	protected String getKey() {
		return apiKey;
	}
	
	public boolean isValid() {
		if(this.apiKey != null) {
			StringBuilder sb = new StringBuilder();
			String urlParam = "key=" + this.apiKey;
			String request = Constants.SERVER_URL + "panel/graph";
			URL url;
			try {
				url = new URL( request );
			} catch (MalformedURLException e1) {
				return false;
			}
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) url.openConnection();
			} catch (IOException e1) {
				return false;
			}           
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			try {
				conn.setRequestMethod("POST");
			} catch (ProtocolException e1) {
				return false;
			}
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty("Content-Length", Integer.toString(urlParam.getBytes().length));
			conn.setUseCaches(false);
			try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			   wr.write(urlParam.getBytes());
			}catch(Exception e) {
				return false;
			}
			try {
				conn.connect();
			} catch (IOException e1) {
				return false;
			}
			BufferedReader br;
			try {
				if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
				    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
				    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
			} catch (IOException e) {
				return false;
			}
			String line;
			try {
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				return false;
			}
			JSONObject json = null;
			try {
				json = new JSONObject(sb.toString());
			} catch (JSONException e) {
				return false;
			}
			
			try {
				if(json.getInt("total") >= json.getInt("max")) {
					return false;
				}else {
					return true;
				}
			} catch (JSONException e) {
				return false;
			}			
		}else {
			return false;
		}
	}
}
