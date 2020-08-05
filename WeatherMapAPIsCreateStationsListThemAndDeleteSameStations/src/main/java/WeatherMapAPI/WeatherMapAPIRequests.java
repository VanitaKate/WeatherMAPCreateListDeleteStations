package WeatherMapAPI;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class WeatherMapAPIRequests {
	public String id1,id2;

	// 1.	Validate that attempt to register a weather station without an API key will return the following in message body. 
	@Test(priority=1)
	public void getWeatherWithOutRegister() {
		RestAssured.baseURI="https://openweathermap.org";
		Response response=(Response) RestAssured.given()
				.contentType("application/json")
				.accept("application/json")
				.when().get("/data/3.0/stations")
				.then()
				.statusCode(401)
				.extract().response();
		String ResponseBody;
		String ExpMessage="Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.";
		System.out.println(ResponseBody=response.asString());
		JsonPath jsonPathEvaluator = response.jsonPath();
		System.out.println(jsonPathEvaluator.get("message"));
		Assert.assertTrue(ResponseBody.contains(ExpMessage));
		System.out.println("===============================================");

	}
	@Test(priority=1)
	// 2.	Successfully register two stations with the following details and verify that HTTP response code is 201. 
	public void postStation() throws IOException {
		HeaderConfig HeaderConfig=new HeaderConfig();
		String APIKey="e23c0d45c86237ad54fd7350070c5f7a";
		RestAssured.baseURI="http://api.openweathermap.org";
		Map<String,String> HeaderMap=new HashMap<String, String>();
		Map<String,String> ParamMap=new HashMap<String, String>();
		ParamMap=HeaderConfig.Params();
		HeaderMap=HeaderConfig.Header();
		//		Map<String,String> StationMap1=new HashMap<String, String>();
		//		StationMap1=HeaderConfig.Station1();
		//		System.out.println(StationMap1);
		System.out.println("===============================================");
		System.out.println(HeaderMap);
		System.out.println("================================================");
		StationPOJO Station1=new StationPOJO("DEMO_TEST001", "InterView Station 1015", 33.33, -111.43, 444 );
		StationPOJO Station2=new StationPOJO("Interview1", "InterView Station 1013", 33.44, -12.44, 444 );
		System.out.println("===============================================");

		Response response1=RestAssured.given()
				.headers(HeaderMap)
				.queryParams(ParamMap)
				.body(Station1)	
				.when().post("/data/3.0/stations")
				.then().extract().response();

		String ResponseBody1=response1.asString();
		System.out.println("\nResponse Body of the postStation1 requestis:= "+ResponseBody1);
		JsonPath jsonPathEvaluator1 = response1.jsonPath();
		id1=jsonPathEvaluator1.get("ID");
		System.out.println("\nID generated for 1st Station is: "+jsonPathEvaluator1.get("ID"));
		System.out.println("===============================================");

		Response response2=RestAssured.given()
				.headers(HeaderMap)
				.queryParams(ParamMap)
				.body(Station2)	
				.when().post("/data/3.0/stations")
				.then().extract().response();

		String ResponseBody2=response2.asString();
		System.out.println("Response Body of the postStation2 requestis:= "+ResponseBody2);			

		JsonPath jsonPathEvaluator2 = response2.jsonPath();
		id2=jsonPathEvaluator2.get("ID");
		System.out.println("\nID generated for 2nd Station is: "+jsonPathEvaluator2.get("ID"));
		List<String> list= new ArrayList<String>();
		// code to save IDs of stations created, to be used in deleteRequest
		String TestFile = "D:\\temp.txt";
		File FC = new File(TestFile);//Created object of java File class.
		if (FC.exists()) {
			FC.delete();
			System.out.println("An Existing temp file is deleted.");
		}
		FC.createNewFile();	
		System.out.println(TestFile);
		FileWriter FW = new FileWriter(TestFile);
		BufferedWriter BW = new BufferedWriter(FW);
		BW.write(id1); //Writing In To File.
		BW.newLine();//To write next string on new line.
		BW.write(id2); //Writing In To File.
		BW.close();
	}	


	//3.	Using “[GET] /stations” API verify that the stations were successfully stored in the DB and their values are the same as specified in the registration request. 		
	@Test(priority=2)		
	public void getStations() {
		HeaderConfig HeaderConfig=new HeaderConfig();
		String APIKey="e23c0d45c86237ad54fd7350070c5f7a";
		RestAssured.baseURI="http://api.openweathermap.org";
		Map<String,String> HeaderMap=new HashMap<String, String>();
		Map<String,String> ParamMap=new HashMap<String, String>();
		ParamMap=HeaderConfig.Params();
		HeaderMap=HeaderConfig.Header();
		Response response=RestAssured.given()
				.headers(HeaderMap)
				.queryParams(ParamMap)
				.when().get("/data/3.0/stations")
				.then().extract().response();

		String ResponseBody=response.asString();
		System.out.println("Response Body of getStations the requestis:= "+ResponseBody);
		//		JsonPath jsonPathEvaluator = response.jsonPath();
		//		System.out.println(jsonPathEvaluator.get("ID"));
		Assert.assertTrue(ResponseBody.contains("Interview1"));
		Assert.assertTrue(ResponseBody.contains("DEMO_TEST001"));
	}


	//4.	Delete both of the created stations and verify that returned HTTP response is 204. 	
	@Test(priority=3)		
	public void deleteStations() throws FileNotFoundException {
		//code to retrieve IDs of Stations to be deleted
		String TestFile = "D:\\temp.txt";
		File FC = new File(TestFile);		
		FileReader FR = new FileReader(TestFile);
		BufferedReader BR = new BufferedReader(FR);
		String Content = "";
		for (int lineNumber = 1; lineNumber < 3; lineNumber++) {
			if (lineNumber==1) {
				try {
					id1=BR.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(lineNumber==2)	{
				try {
					id2=BR.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}

		String id = null;
		HeaderConfig HeaderConfig=new HeaderConfig();
		String APIKey="e23c0d45c86237ad54fd7350070c5f7a";
		RestAssured.baseURI="http://api.openweathermap.org";
		Map<String,String> HeaderMap=new HashMap<String, String>();
		Map<String,String> ParamMap=new HashMap<String, String>();
		ParamMap=HeaderConfig.Params();
		HeaderMap=HeaderConfig.Header();
		for (int i=0;i<=1;i++) {
			if (i==0) {id=id1;}else id=id2;
			Response response=RestAssured.given()
					.headers(HeaderMap)
					.queryParams(ParamMap)
					.when().delete("/data/3.0/stations/"+id)
					.then().extract().response();

			String ResponseBody=response.asString();
			System.out.println("Response Body of the deleteStations requestis:= "+ResponseBody);

			Assert.assertFalse(ResponseBody.contains("Interview1"));
			Assert.assertFalse(ResponseBody.contains("DEMO_TEST001"));
			System.out.println("Deleted Station ID: " +id);
		}	

	}

	//5.	Repeat the previous step and verify that returned HTTP response is 404 and that message body contains “message”: “Station not found". 

	@Test(priority=4)
	public void TryRedeleteStations() throws FileNotFoundException {
		String id=null;
		String TestFile = "D:\\temp.txt";
		File FC = new File(TestFile);		
		FileReader FR = new FileReader(TestFile);
		BufferedReader BR = new BufferedReader(FR);
		String Content = "";
		for (int lineNumber = 1; lineNumber < 3; lineNumber++) {
			if (lineNumber==1) {
				try {
					id1=BR.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(lineNumber==2)	{
				try {
					id2=BR.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}
		HeaderConfig HeaderConfig=new HeaderConfig();
		String APIKey="e23c0d45c86237ad54fd7350070c5f7a";
		RestAssured.baseURI="http://api.openweathermap.org";
		Map<String,String> HeaderMap=new HashMap<String, String>();
		Map<String,String> ParamMap=new HashMap<String, String>();
		ParamMap=HeaderConfig.Params();
		HeaderMap=HeaderConfig.Header();
		for (int i=0;i<=1;i++) {
			if (i==0) {id=id1;}else id=id2;
			System.out.println("\nStation id to be redeleted: "+id);
		Response response=RestAssured.given()
				.headers(HeaderMap)
				.queryParams(ParamMap)
				.when().delete("/data/3.0/stations/5f23e967cca8ce0001ef4f0d")
				.then().statusCode(404).extract().response();
		String ResponseBody=response.asString();
		System.out.println("Response Body of the TryRedeleteStations requestis:= "+ResponseBody);
		String message="Station not found";
		Assert.assertTrue(ResponseBody.contains(message));
		Assert.assertFalse(ResponseBody.contains("Interview1"));
		Assert.assertFalse(ResponseBody.contains("DEMO_TEST001"));

		}
		FC.delete(); //Delete the text file with Station IDs
	}




	//Get list of Stations after deletion of above two stations (Not a part of coding assignment
	//	@Test		
	public void getStationsAfterDeletion() {
		HeaderConfig HeaderConfig=new HeaderConfig();
		String APIKey="e23c0d45c86237ad54fd7350070c5f7a";
		RestAssured.baseURI="http://api.openweathermap.org";
		Map<String,String> HeaderMap=new HashMap<String, String>();
		Map<String,String> ParamMap=new HashMap<String, String>();
		ParamMap=HeaderConfig.Params();
		HeaderMap=HeaderConfig.Header();
		Response response=RestAssured.given()
				.headers(HeaderMap)
				.queryParams(ParamMap)
				.when().get("/data/3.0/stations")
				.then().extract().response();

		String ResponseBody=response.asString();
		System.out.println("Response Body of the requestis:= "+ResponseBody);
		Assert.assertFalse(ResponseBody.contains("Interview1"));
		Assert.assertFalse(ResponseBody.contains("DEMO_TEST001"));
	}



	// API for received in an email along with API Key (Not a part of Coding Test)
//		@Test
	public void getWeatherWithAPIKey() {
		String APIKey="e23c0d45c86237ad54fd7350070c5f7a";
		RestAssured.baseURI="https://api.openweathermap.org";
		HeaderConfig HeaderConfig=new HeaderConfig();
		Map<String,String> HeaderMap=new HashMap<String, String>();
		HeaderMap=HeaderConfig.Header();
		Map<String,String> ParamMap=new HashMap<String, String>();
		ParamMap=HeaderConfig.Params();		
		Response response=(Response) RestAssured.given()
				.contentType("application/json")
				.accept("application/json")
				.params(ParamMap)
				.when().get("/data/2.5/weather")
				.then()
				.statusCode(200)
				.extract().response();
		String ResponseBody;
		//		String ExpMessage="Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.";

		System.out.println(ResponseBody=response.asString());
		//		Assert.assertTrue(ResponseBody.contains(ExpMessage));
	}

}
