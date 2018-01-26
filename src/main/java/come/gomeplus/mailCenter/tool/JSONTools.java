package come.gomeplus.mailCenter.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import come.gomeplus.mailCenter.handler.DataHandler;

public class JSONTools {
	
	private static Logger logger = LogManager.getLogger(JSONTools.class);

	public static Map<String, Object> buildMap(String jsonPath) {
		Map<String, Object> beans = new HashMap<String, Object>();
		File root = new File(jsonPath);
		
		if (!root.exists() || !root.isDirectory()){
			throw new RuntimeException("path " + jsonPath + " is not a directory!!!");
		}
		
		for (File file : root.listFiles()){
			String fileName = file.getName();
			try(FileReader fr = new FileReader(file);
					BufferedReader reader = new BufferedReader(fr);){
				StringBuffer sb = new StringBuffer();
				String str = "";
				while((str=reader.readLine()) != null){
					sb.append(str);
				}
				JsonElement json = null;
				try{
					JsonParser parser = new JsonParser();
					json = parser.parse(sb.toString());
				} catch (ClassCastException e){
					json = JsonNull.INSTANCE;
				}
				beans.put(fileName, parse(json));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return DataHandler.doHandler(beans);
		//return beans; 
	}
	
	public static Object parse(JsonElement json) {
		if (json.isJsonArray()){
			return parseArray((JsonArray)json);
		} else if (json.isJsonObject()){
			return parseObject((JsonObject)json);
		} else if (json.isJsonPrimitive()){
			Object value = getPrimitiveValue((JsonPrimitive)json);
			//去掉value中的引号
			if (value instanceof String && ((String) value).startsWith("\"")
					&& ((String) value).endsWith("\"")){
				value = ((String)value).substring(1, ((String)value).length() - 1);
			}
			if (null == value || "null".equals(value)){
				value = "0";
			}
			return value;
		}
		return json;
	}

	/**
	 * 解析JsonObject
	 * 
	 * @param json
	 * @return
	 */
	private static Map<String, Object> parseObject(JsonObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map.Entry<String, JsonElement> entry : ((JsonObject)json).entrySet()){
			map.put(entry.getKey(), parse(entry.getValue()));
		}
		return map;
	}
	
	/**
	 * 解析JsonArray
	 * 
	 * @param array
	 * @return
	 */
	private static List<Object> parseArray(JsonArray array){
		List<Object> list = new ArrayList<Object>();
		Iterator<JsonElement> it = array.iterator();
		while (it.hasNext()){
			list.add(parse(it.next()));
		}
		return list;
	}
	
	/**
	 * 获取Primitive类型的参数内容
	 * 
	 * @param p
	 * @return value
	 */
	private static Object getPrimitiveValue(JsonPrimitive p){
		if (p.isNumber()) {
		      return p.getAsNumber();
		    } else if (p.isBoolean()) {
		      return p.getAsBoolean();
		    } else {
		      return p.getAsString();
		    }
	}

	/**
	 * 将字符串转换成Json对象
	 * 
	 * @param entity
	 * @return
	 */
	public static JsonElement toJson(String entity) {
		return new JsonParser().parse(entity);
	}

	/**
	 * 获取json的值
	 * 
	 * @param json
	 * @param key
	 */
	public static JsonElement get(JsonElement json, String key) {
		if (json.isJsonArray()){
			int index = 0;
			try{
				index = Integer.parseInt(key.split("\\[")[1].replaceAll("\\]", ""));
			} catch (NumberFormatException e){
				logger.warn("key " + key + "can't find index,the default index is 0");
			}
			return ((JsonArray)json).get(index);
		} else if (json.isJsonObject()){
			return ((JsonObject)json).get(key);
		}
		return json;
	}
}
