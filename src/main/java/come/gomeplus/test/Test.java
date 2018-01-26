package come.gomeplus.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;

import come.gomeplus.mailCenter.tool.JSONTools;
import net.sf.jxls.transformer.XLSTransformer;

public class Test {

	public static void main(String[] args) {
		
		/*Map<String, Object> beans = new HashMap<String, Object>();
		JsonElement json = null;
		try{
			JsonParser parser = new JsonParser();
			json = parser.parse("[{\"date\":\"2017-06-26\",\"unique_is_rebate_order_num\":\"797\",\"unique_expect_rebate_user_num\":\"657\",\"expect_rebate_amount\":\"1659.29\"},{\"date\":\"2017-06-27\",\"unique_is_rebate_order_num\":\"781\",\"unique_expect_rebate_user_num\":\"641\",\"expect_rebate_amount\":\"1225.17\"}]");
		} catch (ClassCastException e){
			json = JsonNull.INSTANCE;
		}
		beans.put("key", JSONTools.parse(json));
		System.out.println(beans);*/
		
		Map<String, Object> beans = new HashMap<String, Object>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 6; i++){
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("key1", "100" + i);
			list1.add(map1);
		}
		
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 6; i++){
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("key2", "10000" + i);
			list2.add(map1);
		}
		beans.put("list1", list1);
		beans.put("list2", list2);
		beans.put("value1", 1);
		beans.put("value2", 2);
		beans.put("value3", 3);
		beans.put("equeal", "=");
		try (OutputStream os = new FileOutputStream("/home/yang/test.xlsx");
				FileInputStream fileOutStream = new FileInputStream("/home/yang/tmp.xlsx");
				InputStream is = new BufferedInputStream(fileOutStream);) {
			XLSTransformer transformer = new XLSTransformer();
			Workbook workbook = transformer.transformXLS(is, beans);
			workbook.setForceFormulaRecalculation(true);
			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("导出excel错误!");
		}
	}
}
