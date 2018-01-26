package come.gomeplus.mailCenter.tool;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

public class HttpClient {
	
	private static final Logger logger = LogManager.getLogger(HttpClient.class);

	public static JsonElement get(String url) {

		try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			logger.info("executing request " + httpget.getURI());
			RequestConfig config = RequestConfig.custom().setConnectTimeout(4000)
					.setConnectionRequestTimeout(1000)
					.setSocketTimeout(60000).build();
			httpget.setConfig(config);
			// 执行get请求.
			try (CloseableHttpResponse response = httpclient.execute(httpget);){
				if (response.getStatusLine().getStatusCode() == 200){
					// 获取响应实体
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String result = EntityUtils.toString(entity);
						logger.info("response " + result);
						return JSONTools.toJson(result);
					}
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return JsonNull.INSTANCE;
	}
	
	public static void main(String[] args) {
		System.out.println(HttpClient.get("http://192.168.1.1/api/v1shop/gp?mshopIds=21429&date=2017-06-27"));
	}
	
}
