package come.gomeplus.mailCenter.command;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.poi.ss.usermodel.Workbook;

import come.gomeplus.mailCenter.Main;
import come.gomeplus.mailCenter.tool.JSONTools;
import net.sf.jxls.transformer.XLSTransformer;

public class ExportExcelCommand extends Main{
	
	private static String template_file_path;//模板文件路径+模板文件名
	
	private static String template_file_name;//模板文件名
	//private static String target_file_path;//excel成品文件路径
	//private static String json_file_path;//json输出目录

	public static void main(String[] args) {
		//args[0] 目标文件路径
		//args[1] 模板文件路径
		//args[2] json数据存储路径
		ExportExcelCommand instances = new ExportExcelCommand();
		instances.initParam(args);
		instances.doCommand();
		System.out.println("export excel finish");
	}
	
	private void initParam(String[] args){
		Options opts = new Options();
		opts.addOption("path", true, "template file path");
		opts.addOption("name", true, "template file name");
		//opts.addOption("j", true, "json file path");
				
		CommandLineParser parser = new DefaultParser();
		CommandLine cli = null;

		try {
			cli = parser.parse(opts, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}		

		if (cli.hasOption("path")) {
			template_file_path = cli.getOptionValue("path");			
		}
		if (cli.hasOption("name")) {
			template_file_name = cli.getOptionValue("name");
		}
//
//		if (cli.hasOption("j")) {
//			json_file_path = cli.getOptionValue("j");
//		}
	}	

	@Override
	public void doCommand() {
		Map<String, Object> beans = JSONTools.buildMap(getOutput());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try (OutputStream os = new FileOutputStream(getExportPath() + "/" + template_file_name);
				FileInputStream fileOutStream = new FileInputStream(template_file_path+"/"+template_file_name);
				InputStream is = new BufferedInputStream(fileOutStream);) {
			XLSTransformer transformer = new XLSTransformer();
			Workbook workbook = transformer.transformXLS(is, beans);
			workbook.setForceFormulaRecalculation(true);
			workbook.write(os);
			System.out.println("finish doCommand");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("导出excel错误!");
		}
	}

}
