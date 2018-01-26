package come.gomeplus.mailCenter.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import come.gomeplus.mailCenter.entity.DBInfo;

public class JDBCTools {
	
	private static final Logger logger = LogManager.getLogger(JDBCTools.class);
	
	public static JsonElement query(String sql, String driver, String url, 
			String user, String pwd, Map<String, String> params){
		JsonArray array = new JsonArray();
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		try (Connection con = DriverManager.getConnection(url,user,pwd);
				Statement st = con.createStatement();){
			for (String key : params.keySet()){
				sql = sql.replaceAll("\\$\\{" + key + "\\}", params.get(key));
			}
			try(ResultSet rs = st.executeQuery(sql);){
				ResultSetMetaData meta = rs.getMetaData();
				while(rs.next()){
					JsonObject row = new JsonObject();
					for(int i = 1; i <= meta.getColumnCount(); i++){
						row.addProperty(meta.getColumnLabel(i).toLowerCase(), rs.getString(i));
					}
					array.add(row);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		return array.size() == 1 ? array.get(0) : array;
	}

	public static JsonElement query(String sql, DBInfo info, Map<String, String> params) {
		return query(sql, info.getDriver(), info.getUrl(), info.getUsername(), info.getPassowrd(), params);
	}
	
}
