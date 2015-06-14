package yakyang.dict.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CodeMapTable implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String scopeId;
	
	private List<CodeMap> codeMaps = new ArrayList<CodeMap>();

	public List<CodeMap> getCodeMaps() {
		return codeMaps;
	}

	public void setCodeMaps(List<CodeMap> codeMaps) {
		this.codeMaps = codeMaps;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}

}
