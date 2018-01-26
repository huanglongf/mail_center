package come.gomeplus.mailCenter.handler;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * 业务数据处理
 *  
 */
public class DataHandler {
	
	private static Logger logger = LogManager.getLogger(DataHandler.class);
	
	//商城PC 28天趋势数据
	private static String trend28_PC = "GomeAndgomeplus28trendKey_PC";
	//PLUS PC 28天趋势数据
	private static String trend28_PLUSPC = "GomeAndgomeplus28trendKey_PPC";
	//在线、plus分站点销售数据文件前缀
	private static String prifix = "PlatAndFlowAndTransAndSale";
	//本周
	private static String NOW_WEEK = "NowWeek";
	//上周
	private static String LAST_WEEK = "LastWeek";
	//在线3端
	private static String ZAIXIAN3 = "ZAIXIAN3";
	//PLUS3端
	private static String PLUS3 = "PLUS3";
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> doHandler(Map<String, Object> beans){
		/***
		 * 处理28天趋势 商城PC毛销售额减去PLUS-PC的毛销售额，才是最终国美在线PC毛销售额
		 */
		logger.error("处理之前的"+beans);		
		
		List<Object> pc28s = (List<Object>) beans.get(trend28_PC);
				
		List<Object> pluspc28s = (List<Object>) beans.get(trend28_PLUSPC);
		
		if(pc28s!= null && pc28s.size()>0 && pluspc28s!=null && pluspc28s.size()>0){
		
			for (Object object : pc28s) {
				
				Map<String,String> pc = (Map<String, String>) object;
				String pcdate = pc.get("date");
				String pcOrderAmount = pc.get("order_amount");
				
				for(Object oo :pluspc28s){
					Map<String,String> ppc = (Map<String,String>) oo;
					String ppcdate = ppc.get("date");
					String ppcOrderAmount = null;
					try{
						ppcOrderAmount = ppc.get("order_amount");						
					}catch(Exception e){
						ppcOrderAmount = "0";
					}
					
					if(pcdate.equals(ppcdate)){
						Double d_pc_order_amount = Double.parseDouble(pcOrderAmount);
						Double d_ppc_order_amount = Double.parseDouble(ppcOrderAmount);
						Double d_final_order_amount = d_pc_order_amount - d_ppc_order_amount;
						pc.put("order_amount", String.valueOf(d_final_order_amount));					
						break;
					}
				}
			}
		}
		
		/***
		 * 国美在线、Plus本周分站点流量、转化及销售情况（按下单时间）报表中，PC站点的毛订单、毛销售额减去PLUS-PC的对应数据，才是真正PC的毛销售数据
		 */
		List<Map<String,String>> nowweek_zaixian3 = (List<Map<String, String>>) beans.get(prifix+"_"+NOW_WEEK+"_"+ZAIXIAN3);
		List<Map<String,String>> nowweek_plus3 = (List<Map<String, String>>) beans.get(prifix+"_"+NOW_WEEK+"_"+PLUS3);
		List<Map<String,String>> lastweek_zaixian3 = (List<Map<String, String>>) beans.get(prifix+"_"+LAST_WEEK+"_"+ZAIXIAN3);
		List<Map<String,String>> lastweek_plus3 = (List<Map<String, String>>) beans.get(prifix+"_"+LAST_WEEK+"_"+PLUS3);
		
		if(nowweek_zaixian3!= null && nowweek_zaixian3.size()>0 &&
				nowweek_plus3!= null && nowweek_plus3.size()>0 &&
						lastweek_zaixian3!= null && lastweek_zaixian3.size()>0 &&
								lastweek_plus3!= null && lastweek_plus3.size()>0
				
				){
		
		for (Map<String,String> z : nowweek_zaixian3) {
			String wd_pc = z.get("wd");
			if(wd_pc.equals("PC")){
				for (Map<String,String> p : nowweek_plus3) {
					String wd_plus = p.get("wd");
					if(wd_plus.equals("PLUS-PC")){
						int z_order_cnt = Integer.parseInt(z.get("order_cnt"));
						int p_order_cnt = Integer.parseInt(p.get("order_cnt"));
						int final_order_cnt = z_order_cnt - p_order_cnt;
						z.put("order_cnt", String.valueOf(final_order_cnt));
						
						Double z_order_amount = Double.parseDouble(z.get("order_amount"));
						Double p_order_amount = Double.parseDouble(p.get("order_amount"));
						Double final_order_amount = z_order_amount - p_order_amount;
						z.put("order_amount", String.valueOf(final_order_amount));						
						break;
					}					
				}
			}
		}
		
		for (Map<String,String> z : lastweek_zaixian3) {
			String wd_pc = z.get("wd");
			if(wd_pc.equals("PC")){
				for (Map<String,String> p : lastweek_plus3) {
					String wd_plus = p.get("wd");
					if(wd_plus.equals("PLUS-PC")){
						int z_order_cnt = Integer.parseInt(z.get("order_cnt"));
						int p_order_cnt = Integer.parseInt(p.get("order_cnt"));
						int final_order_cnt = z_order_cnt - p_order_cnt;
						z.put("order_cnt", String.valueOf(final_order_cnt));
						
						Double z_order_amount = Double.parseDouble(z.get("order_amount"));
						Double p_order_amount = Double.parseDouble(p.get("order_amount"));
						Double final_order_amount = z_order_amount - p_order_amount;
						z.put("order_amount", String.valueOf(final_order_amount));	
						break;
					}					
				}
			}
		}
		
		}
		
		logger.error("之后的"+beans);
		return beans;
	}
	
}
