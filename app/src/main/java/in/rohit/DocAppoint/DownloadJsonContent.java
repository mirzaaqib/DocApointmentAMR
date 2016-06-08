package in.rohit.DocAppoint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


public class DownloadJsonContent {

   public static String downloadContent(String url) throws IOException {
	  URL website = new URL(url);
	  URLConnection connection = website.openConnection();
	  BufferedReader in = new BufferedReader(
			  new InputStreamReader(
					  connection.getInputStream()));

	  StringBuilder response = new StringBuilder();
	  String inputLine;

	  while ((inputLine = in.readLine()) != null)
		 response.append(inputLine);

	  in.close();

	  return response.toString();
   }


   public static String downloadContentUsingPostMethod(String urlString, String parameters) throws IOException {
	  URL url = new URL(urlString);
	  URLConnection conn = url.openConnection();
	  conn.setDoOutput(true);
	  OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
	  writer.write(parameters);
	  writer.flush();
	  String line;
	  BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	  StringBuilder response = new StringBuilder();
	  while ((line = reader.readLine()) != null) {
		 response.append(line);
	  }
	  writer.close();
	  reader.close();

	  return response.toString();
   }

   public static boolean isNetwork(Context context){
	  ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context
			  .CONNECTIVITY_SERVICE);
	  NetworkInfo info=cm.getActiveNetworkInfo();
	  if (info==null){
		 return false;
	  }
	  NetworkInfo.State network = info.getState();
	  return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
   }

   public class BitmapDownloader extends AsyncTask<String, Void, Bitmap> {

	  @Override
	  protected Bitmap doInBackground(String... urls) {
		 try {
			URL url = new URL(urls[0]);
			URLConnection conn = url.openConnection();
			conn.connect();
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			Bitmap bm = BitmapFactory.decodeStream(bis);
			bis.close();
			return bm;
		 } catch (IOException e) {
		 }
		 return null;
	  }
   }

}
