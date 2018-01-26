package come.gomeplus.mailCenter.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;

import come.gomeplus.mailCenter.Main;
import come.gomeplus.mailCenter.entity.DBInfo;
import come.gomeplus.mailCenter.entity.TaskInfo;
import come.gomeplus.mailCenter.tool.IOTools;
import come.gomeplus.mailCenter.tool.JDBCTools;
import come.gomeplus.mailCenter.tool.OptionUtil;

public class JdbcCommand extends Main{
	
	private TaskInfo task;
	
	private static Logger log = LogManager.getLogger(JdbcCommand.class);
	
	public JdbcCommand(TaskInfo task) {
		super();
		this.task = task;
	}

	public static void main(String[] args) {
		TaskInfo task = OptionUtil.buildTaskInfo(args, null);
		Main main = new JdbcCommand(task);
		main.setKey(task.getKey());
		main.doCommand();
	}

	@Override
	public void doCommand() {
		DBInfo info = task.getDbInfo();
		String[] sql = IOTools.readFile(task.getFile());
		log.info("read sql : " + sql[0]);
		log.info("parameters : " + task.getParams());
		
		JsonElement result = JDBCTools.query(sql[0], info, task.getParams());
		String json = result.toString();
		log.info("result json : " + json);
		IOTools.writeFile(json, getOutput());
		log.info("finished.");
	}

}
