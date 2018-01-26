package come.gomeplus.mailCenter.entity;

/**
 * Created by yang on 17-4-27.
 */
public class DBInfo {
	private String driver;
	private String url;
	private String username;
	private String passowrd;
	private String host;
	private String port;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassowrd() {
		return passowrd;
	}

	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return "DBInfo [driver=" + driver + ", url=" + url + ", username=" + username + ", passowrd=" + passowrd
				+ ", host=" + host + ", port=" + port + "]";
	}

}
