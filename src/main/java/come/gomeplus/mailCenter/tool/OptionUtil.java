package come.gomeplus.mailCenter.tool;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.*;

import come.gomeplus.mailCenter.entity.DBInfo;
import come.gomeplus.mailCenter.entity.HiveDBInfo;
import come.gomeplus.mailCenter.entity.TaskInfo;

/**
 * Created by yang on 17-4-27.
 */
public class OptionUtil {

	public static TaskInfo buildTaskInfo(String[] args, String type) {
		Options opts = new Options();
		opts.addOption("file", true, "Sql file");
		opts.addOption("params", true, "A comma seperated list of parameter which would replace '${key}' in sql file.");
		opts.addOption("driver", true, "JDBC driver class");
		opts.addOption("url", true, "jdbc url");
		opts.addOption("user", true, "user who connect database");
		opts.addOption("pwd", true, "password");
		opts.addOption("output", true, "file to output");
		opts.addOption("k", "key", true, "the key in excel");
		opts.addOption("t", "target", true, "the target context from the json response");
		opts.addOption("kerberos", true, "是否启用kerberos认证");
		opts.addOption("kLoginUser", true, "kerberos认证用户");
		opts.addOption("kKeytabPath", true, "keytab文件路径");
		opts.addOption("engine", true, "执行引擎");

		opts.addOption("h", "help", false, "print help for the command.");

		String format = "db2json [-file][-params][-driver][-url][-user][-pwd][-output]";
		HelpFormatter formatter = new HelpFormatter();

		CommandLineParser parser = new DefaultParser();
		CommandLine cli = null;

		try {
			cli = parser.parse(opts, args);
		} catch (ParseException e) {
			formatter.printHelp(format, opts);
		}

		TaskInfo task = new TaskInfo();
		DBInfo info = createDBInfo(type);

		if (cli.hasOption("file")) {
			task.setFile(cli.getOptionValue("file").split(","));
		}
		if (cli.hasOption("driver")) {
			info.setDriver(cli.getOptionValue("driver"));
		}

		if (cli.hasOption("url")) {
			info.setUrl(cli.getOptionValue("url"));
		}

		if (cli.hasOption("user")) {
			info.setUsername(cli.getOptionValue("user"));
		}

		if (cli.hasOption("pwd")) {
			info.setPassowrd(cli.getOptionValue("pwd"));
		}
		
		if (info instanceof HiveDBInfo){
			if (cli.hasOption("kerberos")) {
				((HiveDBInfo) info).setUse_kerberos(Boolean.valueOf(cli.getOptionValue("kerberos")));
			}
			
			if (cli.hasOption("kLoginUser")) {
				((HiveDBInfo) info).setKerberos_login_user(cli.getOptionValue("kLoginUser"));
			}
			
			if (cli.hasOption("kKeytabPath")) {
				((HiveDBInfo) info).setKerberos_keytab_path(cli.getOptionValue("kKeytabPath"));
			}
			if (cli.hasOption("engine")) {
				((HiveDBInfo) info).setEngine(cli.getOptionValue("engine"));
			}
		}
		

		String[] params = null;
		if (cli.hasOption("params")) {
			params = cli.getOptionValues("params");
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		if (null != params){
			for (String entity : params){
				paramMap.put(entity.split("=")[0], entity.split("=")[1]);
			}
		}
		task.setParams(paramMap);
		task.setDbInfo(info);
		task.setKey(cli.getOptionValue("k"));
		System.out.println(task);
		return task;
	}

	private static DBInfo createDBInfo(String type) {
		return "hive".equals(type) ? new HiveDBInfo() : new DBInfo();
	}
}
