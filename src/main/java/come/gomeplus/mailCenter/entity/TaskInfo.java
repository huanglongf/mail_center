package come.gomeplus.mailCenter.entity;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by yang on 17-4-27.
 */
public class TaskInfo {
    private String[] file;
    private DBInfo dbInfo;
    private Map<String, String> params;
    private String key;

    public String[] getFile() {
        return file;
    }

    public void setFile(String[] file) {
        this.file = file;
    }

    public DBInfo getDbInfo() {
        return dbInfo;
    }

    public void setDbInfo(DBInfo dbInfo) {
        this.dbInfo = dbInfo;
    }

    public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}



	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "TaskInfo [file=" + Arrays.toString(file) + ", dbInfo=" + dbInfo + ", params=" + params + ", key=" + key
				+ "]";
	}
	
}
