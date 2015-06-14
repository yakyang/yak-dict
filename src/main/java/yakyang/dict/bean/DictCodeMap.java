package yakyang.dict.bean;

import java.io.Serializable;

public class DictCodeMap implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 代码
	 */
	private String code;
	
	/**
	 * 代码对应名称
	 */
	private String name;
	
	/**
	 * 助记代码
	 */
	private String quickCode;

	/**
	 * 层次类型字典下的ScopeId
	 */
	private String scopeId;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuickCode() {
		return quickCode;
	}

	public void setQuickCode(String helpCode) {
		this.quickCode = helpCode;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}

}
