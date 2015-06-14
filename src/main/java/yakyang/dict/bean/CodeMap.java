package yakyang.dict.bean;

import java.io.Serializable;

public class CodeMap implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 代码
	 */
	private String code;
	
	/**
	 * 名称
	 */
	private String name;

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
	
}
