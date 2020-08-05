package WeatherMapAPI;

import java.util.HashMap;
import java.util.Map;

public class HeaderConfig {
	public Map<String, String> Header(){
		Map<String, String> header=new HashMap<String, String>();
		header.put("Content-Type", "application/json");
//		header.put("APPID", "e23c0d45c86237ad54fd7350070c5f7a");
		return header;

	}

	public Map<String, String> Params(){
		Map<String, String> param=new HashMap<String, String>();
		//		param.put("q", "London,uk");
		param.put("APPID", "0615e04dd91464b63901e9146cfc93e6");
		return param;

	}

	public Map<String,String> Station1(){

		Map<String,String> Station1=new HashMap<String,String>();
		Station1.put("external_id", "DEMO_TEST001");
		Station1.put("name", "Interview StationAbc");
		Station1.put("latitude", "33.33");
		Station1.put("longitude", "-111.43");
		Station1.put("altitude", "444");
		return Station1;

	}


	public Map<String,String> Station2(){

		Map<String,String> Station2=new HashMap<String,String>();
		Station2.put("external_id", "Interview1");
		Station2.put("name", "Interview Station 1212");
		Station2.put("latitude", "33.44");
		Station2.put("longitude", "-12.44");
		Station2.put("altitude", "444");
		return Station2;

	}	

}
