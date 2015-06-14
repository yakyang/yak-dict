package yakyang.dict.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubDictionary implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String classCode;
	
	private List<CodeMapTable> codeMapTables = new ArrayList<CodeMapTable>();

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public List<CodeMapTable> getCodeMapTables() {
		return codeMapTables;
	}

	public void setCodeMapTables(List<CodeMapTable> codeMapTables) {
		this.codeMapTables = codeMapTables;
	}

}
