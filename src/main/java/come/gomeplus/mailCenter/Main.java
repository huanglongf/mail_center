package come.gomeplus.mailCenter;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public abstract class Main {

	private String target;
	private String key;
	private Map<String, String> env = new HashMap<String, String>();
	
	private void init(){
		String baseHome = System.getProperty("base.home");
		env.put("base.home", baseHome);
		String tmpPath = System.getProperty("tmp.execl.path", baseHome + "/source");
		env.put("tmp.execl.path", tmpPath);
		String outputPath = System.getProperty("json.output.path", baseHome + "/output");
		env.put("json.output.path", outputPath);
		String exportPath = System.getProperty("export.execl.path", baseHome + "/export");
		env.put("export.execl.path", exportPath);
		String exportName = System.getProperty("tmp.execl.name", "template.xlsx");
		env.put("tmp.execl.name", exportName);
	}
	
	public Main(){
		init();
	}
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String[] getTargets(){
		if (null == this.target || this.target.trim().length() == 0){
			return null;
		}
		return this.target.split("\\.");
	}
	
	public abstract void doCommand();
	
	/**
	 * json数据存储路径
	 * 
	 * @return
	 */
	protected String getOutput() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dirPath = env.get("json.output.path") + "/" + 
					df.format(Calendar.getInstance().getTime());
		File dir = new File(dirPath);
		if (!dir.exists() || !dir.isDirectory()){
			dir.mkdirs();
		}
		if (null == this.key){
			this.key = "";
		}
		return dirPath + "/" + this.key;
	}
	
	/**
	 * 模板文件路径
	 * 
	 * @return
	 */
	protected String getTmpPath(){
		return env.get("tmp.execl.path") + "/" + env.get("tmp.execl.name");
	}
	
	/**
	 * 导出的Excel存储路径
	 * 
	 * @return
	 */
	protected String getExportPath(){
		return env.get("export.execl.path");
	}
	
}
