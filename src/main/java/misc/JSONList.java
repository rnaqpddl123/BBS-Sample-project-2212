package misc;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONList {

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<>();
		list1.add("cat.jpg");
		
		List<String> list2 = new ArrayList<>();
		list2.add("cat.jpg");
		list2.add("dog.jpg");
		list2.add("cow.jpg");
		
		String str1 = stringfy(list1);
		String str2 = stringfy(list2);
		
		System.out.println(str2);
		List<String> result = parse(str2);
		result.forEach(x -> System.out.println(x));
		
	}
	
	// json의 모양처럼 stringfy해줌
	public static String stringfy(List<String> list) {
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		return obj.toString();
	}
	
	// json을 내가 원하는 방식으로 읽는방법
	public static List<String> parse(String jsonStr){
		JSONParser parser = new JSONParser();
		List<String> list = null;
		try {
			JSONObject jsonList = (JSONObject) parser.parse(jsonStr);
//			System.out.println(jsonList);
			JSONArray jsonArr = (JSONArray) jsonList.get("list");
//			System.out.println(jsonArr);
			list = (List<String>) jsonArr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
}
