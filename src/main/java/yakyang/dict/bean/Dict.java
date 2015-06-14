package yakyang.dict.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Dict implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 字典分类代码
	 */
	private String classCode;

	/**
	 * 字典分类名称
	 */
	private String name;

	/**
	 * 代码映射
	 */
	private List<DictCodeMap> codeMaps;

	/**
	 * 范围集
	 */
	private Map<String, DictScope> scopes;

	/**
	 * 代码映射到DictCodeMap
	 */
	private transient Map<String, DictCodeMap> dictCodeMapByCode;
	
	public void optimize(boolean force) {
		if (force == false && dictCodeMapByCode != null) {
			return;
		}

		dictCodeMapByCode = new HashMap<String, DictCodeMap>();
		for (Iterator<DictCodeMap> it = codeMaps.iterator(); it.hasNext();) {
			DictCodeMap dictCodeMap = it.next();
			dictCodeMapByCode.put(dictCodeMap.getCode(), dictCodeMap);
		}
	}

	public void addCodeName(String code, String name) {
		DictCodeMap dictCodeMap = new DictCodeMap();
		dictCodeMap.setCode(code);
		dictCodeMap.setName(name);
		
		addDictCodeMap(dictCodeMap);
	}
	
	public void addDictCodeMap(DictCodeMap codeMap) {
		if (this.codeMaps == null) {
			this.codeMaps = new ArrayList<DictCodeMap>();
		}

		this.codeMaps.add(codeMap);
	}

	public DictCodeMap getDictCodeMap(String code) {
		return dictCodeMapByCode.get(code);
	}

	public void addDictScope(DictScope dictScope) {
		dictScope.setDict(this);
		scopes.put(dictScope.getScopeId(), dictScope);
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DictCodeMap> getCodeMaps() {
		return codeMaps;
	}

	public void setCodeMaps(List<DictCodeMap> codeMaps) {
		this.codeMaps = codeMaps;
	}

	public Map<String, DictScope> getScopes() {
		return scopes;
	}

	public void setScopes(Map<String, DictScope> scopes) {
		this.scopes = scopes;
	}

}
