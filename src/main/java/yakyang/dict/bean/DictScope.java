package yakyang.dict.bean;

import java.io.Serializable;
import java.util.Set;

public class DictScope implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 内部编号
	 */
	private Long dictScopeId;

	/**
	 * 所属的字典对象
	 */
	private Dict dict;

	/**
	 * 范围编号
	 */
	private String scopeId;

	/**
	 * 代码集
	 */
	private Set<String> codes;

	public Set<String> getCodes() {
		return codes;
	}

	public void setCodes(Set<String> codes) {
		this.codes = codes;
	}

	public Dict getDict() {
		return dict;
	}

	public void setDict(Dict dict) {
		this.dict = dict;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}

	public Long getDictScopeId() {
		return dictScopeId;
	}

	public void setDictScopeId(Long dictScopeId) {
		this.dictScopeId = dictScopeId;
	}

}
