package com.example.dbutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.termproject.Account;
import com.example.termproject.Event;

import android.os.AsyncTask;
import android.util.Log;

public class OnlineDBUtil {

	public static class GetRecords extends AsyncTask<String, String, ArrayList<Account>> {

		@Override
		protected ArrayList<Account> doInBackground(String... arg0) {
			String query = arg0[0];
			ArrayList<Account> account = new ArrayList<Account>();
			JSONObject oauthLoginResponse;
			try {
				oauthLoginResponse = oAuthSessionProvider();

				JSONArray queryResponse = getRecords(oauthLoginResponse, query)
						.getJSONArray("records");

				for (int i = 0; i < queryResponse.length(); i++) {
					Account person = new Account(queryResponse.getJSONObject(i));
					account.add(person);
				}
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return account;
		}
	}
	
	public static class GetEvents extends AsyncTask<String, String, ArrayList<Event>> {

		@Override
		protected ArrayList<Event> doInBackground(String... arg0) {
			String query = arg0[0];
			ArrayList<Event> events = new ArrayList<Event>();
			JSONObject oauthLoginResponse;
			try {
				oauthLoginResponse = oAuthSessionProvider();

				JSONArray queryResponse = getRecords(oauthLoginResponse, query)
						.getJSONArray("records");

				for (int i = 0; i < queryResponse.length(); i++) {
					Event event = new Event(queryResponse.getJSONObject(i));
					events.add(event);
				}
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return events;
		}
	}
	
	public static class insertRecords extends AsyncTask<JSONObject, String, Void> {

		@Override
		protected Void doInBackground(JSONObject... params) {
			JSONObject object = params[0];
			JSONObject oauthLoginResponse;
			try {
				oauthLoginResponse = oAuthSessionProvider();

				insertRecords(oauthLoginResponse, object);

			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

	}
	
	private static JSONObject oAuthSessionProvider() throws HttpException,
			IOException, JSONException {
		String loginHost = "https://login.database.com";
		String username = "z547597@hotmail.com";
		String password = "password1230enaYxp5xmGD2MJTkOEaHQct";
		String clientId = "3MVG9A2kN3Bn17hscIyVDAIdqTCwGmSOZeknfWCCiYleEc4xqTBh92miMkBpjn9DAlf_33e_E.WqUDJ8B.YYc";
		String secret = "8393277502219606078";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		params.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 30000);

		String baseUrl = loginHost + "/services/oauth2/token";
		HttpPost oauthPost = new HttpPost(baseUrl);
		List<BasicNameValuePair> parametersBody = new ArrayList<BasicNameValuePair>();
		parametersBody.add(new BasicNameValuePair("grant_type", "password"));
		parametersBody.add(new BasicNameValuePair("username", username));
		parametersBody.add(new BasicNameValuePair("password", password));
		parametersBody.add(new BasicNameValuePair("client_id", clientId));
		parametersBody.add(new BasicNameValuePair("client_secret", secret));
		oauthPost
				.setEntity(new UrlEncodedFormEntity(parametersBody, HTTP.UTF_8));

		HttpResponse response = client.execute(oauthPost);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		Log.d("debug", sb.toString());

		JSONObject oauthLoginResponse = new JSONObject(sb.toString());
		return oauthLoginResponse;
	}

	private static JSONObject getRecords(JSONObject oauthLoginResponse, String query)
			throws JSONException, IllegalStateException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		String accessToken = oauthLoginResponse.get("access_token").toString();
		String instanceURL = oauthLoginResponse.getString("instance_url");
		Header oauthHeader = new BasicHeader("Authorization", "OAuth "
				+ accessToken);
		HttpGet httpGet = new HttpGet(
				instanceURL
						+ "/services/data/v27.0/" + query);
		httpGet.addHeader(oauthHeader);
		Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
		httpGet.addHeader(prettyPrintHeader);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		Log.d("debug", sb.toString());
		JSONObject queryResponse = new JSONObject(sb.toString());
		return queryResponse;
	}

	private static JSONObject insertRecords(JSONObject oauthLoginResponse, JSONObject body)
			throws JSONException, IllegalStateException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		String accessToken = oauthLoginResponse.get("access_token").toString();
		String instanceURL = oauthLoginResponse.getString("instance_url");
		Header oauthHeader = new BasicHeader("Authorization", "OAuth "
				+ accessToken);
		HttpPost httpPost = new HttpPost(
				instanceURL
						+ "/services/data/v27.0/sobjects/Account__c");
		httpPost.addHeader(oauthHeader);
		
		StringEntity se = new StringEntity(body.toString());
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		httpPost.setEntity(se);
		
		HttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		Log.d("debug", sb.toString());
		JSONObject queryResponse = new JSONObject(sb.toString());
		return queryResponse;
	}
}
