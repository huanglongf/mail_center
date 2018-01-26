package come.gomeplus.mailCenter.entity;

public class HiveDBInfo extends DBInfo{

	private boolean use_kerberos = true;
	private String kerberos_login_user;
	private String kerberos_keytab_path;
	private String engine;
	
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public boolean isUse_kerberos() {
		return use_kerberos;
	}
	public void setUse_kerberos(boolean use_kerberos) {
		this.use_kerberos = use_kerberos;
	}
	public String getKerberos_login_user() {
		return kerberos_login_user;
	}
	public void setKerberos_login_user(String kerberos_login_user) {
		this.kerberos_login_user = kerberos_login_user;
	}
	public String getKerberos_keytab_path() {
		return kerberos_keytab_path;
	}
	public void setKerberos_keytab_path(String kerberos_keytab_path) {
		this.kerberos_keytab_path = kerberos_keytab_path;
	}
	@Override
	public String toString() {
		super.toString();
		return "HiveDBInfo [use_kerberos=" + use_kerberos + ", kerberos_login_user=" + kerberos_login_user
				+ ", kerberos_keytab_path=" + kerberos_keytab_path + "]";
	}
	
	
}
