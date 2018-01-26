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

import org.apache.poi.ss.usermodel.Workbook;

import come.gomeplus.mailCenter.Main;
import come.gomeplus.mailCenter.tool.JSONTools;
import net.sf.jxls.transformer.XLSTransformer;

public class ExportExcel extends Main{

	public static void main(String[] args) {
		//args[0] 目标文件路径
		//args[1] 模板文件路径
		//args[2] json数据存储路径
		new ExportExcel().doCommand();
		System.out.println("finish");
	}

	@Override
	public void doCommand() {
		Map<String, Object> beans = JSONTools.buildMap(getOutput());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try (OutputStream os = new FileOutputStream(getExportPath() + "/" 
					+ df.format(Calendar.getInstance().getTime()) + ".xlsx");
				FileInputStream fileOutStream = new FileInputStream(getTmpPath());
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
