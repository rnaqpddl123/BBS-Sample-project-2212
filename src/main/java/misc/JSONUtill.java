package misc;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtill {
	
	public String stringfy(List<String> list) {
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		return obj.toString();
	}
	
	public List<String> parse(String jsonStr){
		JSONParser parser = new JSONParser();
		List<String> list = null;
		try {
			JSONObject jsonList = (JSONObject) parser.parse(jsonStr);
			System.out.println(jsonList);
			JSONArray jsonArr = (JSONArray) jsonList.get("list");
			System.out.println(jsonArr);
			list = (List<String>) jsonArr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

}
