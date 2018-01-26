package come.gomeplus.mailCenter.command;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.gson.JsonElement;

import come.gomeplus.mailCenter.Main;
import come.gomeplus.mailCenter.tool.HttpClient;
import come.gomeplus.mailCenter.tool.IOTools;
import come.gomeplus.mailCenter.tool.JSONTools;

public class HttpCommand extends Main{
	
	private String url;
	private String[] params;

	public HttpCommand(String url, String[] params) {
		super();
		this.url = url;
		this.params = params;
	}

	public static void main(String[] args) {
		Options opts = new Options();
		opts.addOption("u", "url", true, "the request url");
		opts.addOption("p", "parameter", true, "the request parameter");
		opts.addOption("k", "key", true, "the key in excel");
		opts.addOption("t", "target", true, "the target context from the json response");
		CommandLineParser parser = new DefaultParser();  
      CommandLine cmd = null;
		try {
			cmd = parser.parse(opts, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		Main main = new HttpCommand(cmd.getOptionValue("u"), cmd.getOptionValues("p"));
		main.setKey(cmd.getOptionValue("k"));
		main.setTarget(cmd.getOptionValue("t"));
		main.doCommand();
	}
	
	public void doCommand(){
		String paramStr = "";
		for (String param : params){
			paramStr += param + "&";
		}
		url = url.endsWith("?") ? url+paramStr : url + "&" + paramStr;
		JsonElement json = HttpClient.get(url);
		for (String key : getTargets()){
			json = JSONTools.get(json, key);
		}
		IOTools.writeFile(json.toString(), getOutput());
	}
}
