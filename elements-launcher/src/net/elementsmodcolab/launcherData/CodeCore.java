package net.elementsmodcolab.launcherData;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class CodeCore 
{
	public static String OS = null;
	
	public static String getWorkingDir()
	{
		if (isWin())
		{
			return System.getProperty("user.home") + "/AppData/Roaming/.minecraft";
		}
		
		if (isLinux())
		{
			return System.getProperty("user.home") + "/.minecraft";
		}
		
		return null;
	}
	
	public static boolean isWin()
	{
		return getOsName().startsWith("Windows");
	}
	
	public static boolean isLinux()
	{
		return getOsName().startsWith("Linux");
	}
	
	public static String getOsName()
	{
	   if(OS == null) { OS = System.getProperty("os.name"); }
	   return OS;
	}
	
	public static String excutePost(String targetURL, String urlParameters)
	{
	    HttpsURLConnection connection = null;
	    try
	    {
	      URL url = new URL(targetURL);
	      connection = (HttpsURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	      connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");

	      connection.setUseCaches(false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      connection.connect();

	      DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	      wr.writeBytes(urlParameters);
	      wr.flush();
	      wr.close();

	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));

	      StringBuffer response = new StringBuffer();
	      String line;
	      while ((line = rd.readLine()) != null)
	      {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();

	      String str1 = response.toString();
	      return str1;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      return null;
	    }
	    finally
	    {
	      if (connection != null)
	        connection.disconnect();
	    }
	}

	public static void settingsDialogue() 
	{
		
	}
	
	public static void download() throws Exception
	{
		File mcjar = new File(CodeCore.getWorkingDir() + "/bin/minecraft.jar");
		File lwjgljar = new File(CodeCore.getWorkingDir() + "/bin/lwjgl.jar");
		File jinputjar = new File(CodeCore.getWorkingDir() + "/bin/jinput.jar");
		File utiljar = new File(CodeCore.getWorkingDir() + "/bin/lwjgl_util.jar");
		File elementzip = new File(CodeCore.getWorkingDir() + "/bin/eois.zip");
		URL minecraft = new URL("http://s3.amazonaws.com/MinecraftDownload/minecraft.jar");
		URL lwjgl = new URL("http://s3.amazonaws.com/MinecraftDownload/lwjgl.jar");
		URL jinput = new URL("http://s3.amazonaws.com/MinecraftDownload/jinput.jar");
		URL lwjgl_util = new URL("http://s3.amazonaws.com/MinecraftDownload/lwjgl_util.jar");
		URL elements = new URL("http://dl.jphweb.com/emc/latest.zip");
		org.apache.commons.io.FileUtils.copyURLToFile(minecraft, mcjar, 5000, 10000);
		org.apache.commons.io.FileUtils.copyURLToFile(lwjgl, lwjgljar, 5000, 10000);
		org.apache.commons.io.FileUtils.copyURLToFile(jinput, jinputjar, 5000, 10000);
		org.apache.commons.io.FileUtils.copyURLToFile(lwjgl_util, utiljar, 5000, 10000);
		org.apache.commons.io.FileUtils.copyURLToFile(elements, elementzip, 5000, 10000);
	}
}
