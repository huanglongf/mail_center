package come.gomeplus.mailCenter.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class IOTools {

	public static String readFile(String file){
		StringBuilder builder = new StringBuilder();
		try (FileReader reader = new FileReader(file);){
			char[] b = new char[1];
			while(reader.read(b) > -1){
				builder.append(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static void writeFile(String content, String output){
		File file = new File(output);
		try (FileOutputStream outStream = new FileOutputStream(file);
				Writer writer = new OutputStreamWriter(outStream, "UTF-8");){
			writer.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static String[] readFile(String[] files) {
		String[] sqls = new String[files.length];
		for (int i = 0; i < files.length; i++){
			sqls[i] = readFile(files[i]);
		}
		return sqls;
	}
}
