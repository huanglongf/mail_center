package come.gomeplus.mailCenter.tool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.hadoop.security.UserGroupInformation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.hadoop.conf.Configuration;

import come.gomeplus.mailCenter.entity.HiveDBInfo;

public class HiveJdbcTools {

	public static JsonElement query(String sql, HiveDBInfo info, Map<String, String> params) {
		JsonArray array = new JsonArray();
		if (info.isUse_kerberos()){
			Configuration conf = new Configuration();
			conf.set("hadoop.security.authentication", "Kerberos");
			UserGroupInformation.setConfiguration(conf);
			try {
				UserGroupInformation.loginUserFromKeytab(info.getKerberos_login_user(),
						info.getKerberos_keytab_path());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			Class.forName(info.getDriver());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection con = DriverManager.getConnection(info.getUrl(), info.getUsername(), info.getPassowrd());
				Statement stmt = con.createStatement();) {
			for (String key : params.keySet()) {
				sql = sql.replaceAll("\\$\\{" + key + "\\}", params.get(key));
			}
			if(info.getEngine()!= null && !"".equals(info.getEngine())){
				boolean resHivePropertyTest = false;
				if("mr".equals(info.getEngine())){
				   resHivePropertyTest = stmt.execute("set hive.execution.engine=mr");
				}else if("tez".equals(info.getEngine())){
				   resHivePropertyTest = stmt.execute("set hive.execution.engine=tez");
				}
			    System.out.println("切换hive执行引擎:"+resHivePropertyTest);
			}else{
				//默认tez
				stmt.execute("set hive.execution.engine=tez");
			}
			
			try (ResultSet rs = stmt.executeQuery(sql);) {
				ResultSetMetaData meta = rs.getMetaData();
				while (rs.next()) {
					JsonObject row = new JsonObject();
					for (int i = 1; i <= meta.getColumnCount(); i++) {
						row.addProperty(meta.getColumnLabel(i).toLowerCase(), rs.getString(i));
					}
					array.add(row);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array.size() == 1 ? array.get(0) : array;

	}
}
