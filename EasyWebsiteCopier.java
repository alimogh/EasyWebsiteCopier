package test;

import java.io.IOException;
import java.util.Scanner;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class test {
	
	public static String getVersion(){
		Document d;
		try {
			d = Jsoup.connect("http://www.romponu.com/HTMLCopierVersion.html").get();
			return d.text();
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String fixURL(String URL){
		String newURL = "";
		if(!URL.contains("www.")){
			newURL = "www." + URL;
			if(!URL.contains("http://") || !URL.contains("https://")){
				newURL = "http://" + URL;
				if(newURL.charAt(URL.length()) == '/'){
					StringBuilder str = new StringBuilder(newURL);
					str.setCharAt(URL.length(), ' ');
				}
			}
		}else if(!URL.contains("http://") || !URL.contains("https://")){
			newURL = "http://" + URL;
		} else if(newURL.charAt(URL.length()) == '/'){
			StringBuilder str = new StringBuilder(URL);
			str.setCharAt(URL.length(), ' ');
		}else{
			return URL;
		}
		return newURL;
	}
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException{
		EasyLib2 e = new EasyLib2();
		Scanner sc = new Scanner(System.in);
		e.println("Hey! Welcome to EasySiteCopier " + getVersion() + "!");
		Thread.sleep(2000);
		e.println("");
		e.println("WARNING! This may appear as an attack on the server side of the website as it is automated!!");
		e.println("I am NOT responsible for your actions whilst using this program!");
		e.println("------------------------------------------------------------------------");
		if(!e.doesFileExist("serial.txt", "")){
			e.println("Lets get started, but first let me make a file to log some info for the future!");
			e.println("Creating serial.txt...");
			e.CreateLog("serial.txt", "", true);
			e.println("Done!");
		}else{
			e.println("Oh, It looks like you've been here before! Thats great! Welcome back! :)");
		}
		Thread.sleep(2000);
		e.println("------------------------------------------------------------------------");
		e.print("Please type OR copy and paste the URL(http(s)://URL.COM) here: ");
		String url = fixURL(sc.nextLine());
		e.println(url);
		Document d;
		try{
			d = Jsoup.connect(url).get();
			e.println("Great! It looks like '"+ url +"' is accessible!");
			String html = d.html().toString();
			String fileName = "";
			e.print("Do you want to have the file as a .html or .txt file?(html/txt) ");
			String fileType = sc.nextLine().toUpperCase();
			if(fileType.contains("HTML")){
				fileName = e.GetCurrentTime().toString() + "-SiteInfo.html";
			}else if(fileType.contains("TXT")){
				fileName = e.GetCurrentTime().toString() + "-SiteInfo.txt";
			}else{
				e.println("That isn't an option, setting to .txt!");
				fileName = e.GetCurrentTime().toString() + "-SiteInfo.txt";
			}
			if(!html.equals(null)){
				e.println("The website has successfully been copied!");
				e.println("Please check " + fileName  + " for the HTML!");
				e.CreateLog(fileName, "", false);
				e.WriteToLog(fileName, "", false, "<center><h1>This file was generated by EasySiteCopier!</h1></center>");
				e.WriteToLog(fileName, "", true, html);
				e.WriteToLog("serial.txt", "", true, "Logged '" + url + "' to " + fileName + "!");
			}else{
				e.println("The website hasn't been copied.");
				e.println("If this is the case, please check your internet connection! OR The website is down!");
			}
		}catch(HttpStatusException http){
			e.println("It looks like '"+ url +"' isn't accessible Status Code: " + http.getStatusCode());
			e.println("So... that didn't work, but thats okay! Restarting in 3 seconds!");
			e.println("");
			e.println("");
			e.println("");
			e.println("");
			e.println("");
			e.println("");
			e.println("");
			e.println("");
			e.println("");
			Thread.sleep(3000);
			main(null);
		}
		
		sc.close();
	}
}