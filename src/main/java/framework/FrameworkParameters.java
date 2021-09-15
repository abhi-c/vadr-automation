package framework;

import java.io.File;


public class FrameworkParameters {

	private String relativePath;
	public final String fileSeparator = System.getProperty("file.separator");

	private Boolean stopExecution = Boolean.valueOf(false);
	private String runConfiguration;

	private static FrameworkParameters parameters;

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public Boolean getStopExecution() {
		return stopExecution;
	}

	public void setStopExecution(Boolean stopExecution) {
		this.stopExecution = stopExecution;
	}

	public String getRunConfiguration() {
		return runConfiguration;
	}

	public void setRunConfiguration(String runConfiguration) {
		this.runConfiguration = runConfiguration;
	}

	public static synchronized FrameworkParameters getInstance() {
		if (parameters == null) {
			parameters = new FrameworkParameters();
		}
		return parameters;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	static void setRelativePath() {
		String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		if (relativePath.contains("allocator")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}

		parameters.setRelativePath(relativePath);
	}

}
