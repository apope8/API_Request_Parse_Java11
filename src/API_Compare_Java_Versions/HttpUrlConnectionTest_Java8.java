package API_Compare_Java_Versions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrlConnectionTest_Java8 {

	private static HttpURLConnection connection;

	public static void main(String[] args) {

		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			//Open connection to API
			URL url = new URL("https://jsonplaceholder.typicode.com/albums");
			connection = (HttpURLConnection) url.openConnection();

			// Request set-up
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			//Check connection status - returns 200 (OK)
			int status = connection.getResponseCode();
//			System.out.println(status);

			//if error
			if (status > 299){
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null){
					responseContent.append(line);
				}
				reader.close();
			}
			else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null){
					responseContent.append(line);
				}
				reader.close();
			}

//			System.out.println(responseContent.toString());

			parse(responseContent.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			// close connection to API
			connection.disconnect();
		}
	}

	public static String parse(String responseBody){
		JSONArray albums = new JSONArray(responseBody);
		for (int i = 0; i < albums.length(); i++) {
			JSONObject album = albums.getJSONObject(i);
			int id = album.getInt("id");
			int userId = album.getInt("userId");
			String title = album.getString("title");

			System.out.println(id + " " + title + " " + userId);
		}
		return null;
	}
}


