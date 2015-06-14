package yakyang.dict.service;

import java.util.*;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import yakyang.dict.bean.*;
import yakyang.dict.bean.Dictionary;
import yakyang.dict.dao.*;

@Service("dictViewService")
public class DictViewServiceImpl implements DictViewService {

	@Autowired
	private DictDao dictDao;

	/**
	 * 忽略加入字典的类型代码集
	 */
	@Resource
	private Set<String> excludeToDictionaryClassCodes = new HashSet<String>();

	/**
	 * 字典拦截器列表
	 */
	@Autowired
	private List<DictViewInterceptor> interceptors;
	
	public Map<String, String> getCodeMap(String classCode) {
		return getCodeMap(classCode, null, false);
	}

	public Map<String, String> getCodeMap(String classCode, boolean includeQuickCode) {
		return getCodeMap(classCode, null, includeQuickCode);
	}

	public Map<String, String> getCodeMap(String classCode, String scopeId) {
		return getCodeMap(classCode, scopeId, false);
	}

	public Map<String, String> getCodeMap(String classCode, String scopeId, boolean includeQuickCode) {
		DictViewInterceptor interceptor = getDictViewInterceptor(classCode);
		if (interceptor != null) {
			return interceptor.getCodeMap(classCode, scopeId, includeQuickCode);
		}

		Dict dict = getDict(classCode);
		if (dict == null) {
			throw new RuntimeException("Not found dict, classCode=[" + classCode + "].");
		}

		Set<String> scopeCodes = null;
		if (scopeId != null) {
			DictScope dictScope = dict.getScopes().get(scopeId);
			if (dictScope != null) {
				scopeCodes = dictScope.getCodes();
			}
		}

		Map<String, String> codeMap = new LinkedHashMap<String, String>();
		for (Iterator<DictCodeMap> it = dict.getCodeMaps().iterator(); it.hasNext();) {
			DictCodeMap dictCodeMap = it.next();
			if (scopeId != null && dictCodeMap.getScopeId() != null) {
				if (scopeId.equals(dictCodeMap.getScopeId()) == false) {
					continue;
				}
			}

			if (scopeCodes != null && scopeCodes.contains(dictCodeMap.getCode()) == false) {
				continue;
			}

			StringBuffer name = new StringBuffer();
			if (includeQuickCode) {
				if (dictCodeMap.getQuickCode() != null) {
					name.append(dictCodeMap.getQuickCode());
				} else {
					name.append(dictCodeMap.getCode());
				}

				name.append("-");
				name.append(dictCodeMap.getName());
			} else {
				name.append(dictCodeMap.getName());
			}

			codeMap.put(dictCodeMap.getCode(), name.toString());
		}

		return codeMap;
	}

	public List<Dict> getDictsByExcludes(Set<String> excludeClassCodes) {
		List<Dict> dicts = getDictsByClassCodePattern(null);
		for (Iterator<Dict> it = dicts.iterator(); it.hasNext();) {
			if (excludeClassCodes.contains(it.next().getClassCode())) {
				it.remove();
			}
		}
		return dicts;
	}

	public List<Dict> getDictsByClassCodePattern(String classCodePattern) {
		List<Dict> retDicts = new ArrayList<Dict>();
		if (classCodePattern == null) {
			retDicts.addAll(dictDao.getAllDicts());
		} else {
			List<Dict> dicts = dictDao.getDictByClassCodePattern(classCodePattern);
			retDicts.addAll(dicts);
		}

		if (interceptors != null) {
			for (DictViewInterceptor interceptor : interceptors) {
				retDicts.addAll(interceptor.getDicts(classCodePattern));
			}
		}
		return retDicts;
	}

	public String getNameByCode(String classCode, String code) {
		return getNameByCode(classCode, code, false);
	}

	public String getNameByCode(String classCode, String code, boolean includeQuickCode) {
		if (code == null) {
			return null;
		}

		DictViewInterceptor interceptor = getDictViewInterceptor(classCode);
		if (interceptor != null) {
			return interceptor.getNameByCode(classCode, code, includeQuickCode);
		}

		Dict dict = getDict(classCode);
		if (dict == null) {
			return code;
		}

		DictCodeMap dictCodeMap = dict.getDictCodeMap(code);

		if (dictCodeMap == null) {
			return code;
		}

		StringBuffer name = new StringBuffer();
		if (includeQuickCode) {
			if (dictCodeMap.getQuickCode() != null) {
				name.append(dictCodeMap.getQuickCode());
			} else {
				name.append(dictCodeMap.getCode());
			}

			name.append("-");
			name.append(dictCodeMap.getName());
		} else {
			name.append(dictCodeMap.getName());
		}

		return name.toString();
	}

	public String getNameByCodes(String classCode, Collection<String> codes, boolean includeQuickCode) {
		StringBuffer name = new StringBuffer();
		for (String code : codes) {
			if (name.length() > 0) {
				name.append(",");
			}

			name.append(getNameByCode(classCode, code, includeQuickCode));
		}

		return name.toString();
	}

	public String getNameByCodes(String classCode, Collection<String> codes) {
		return getNameByCodes(classCode, codes, false);
	}

	public Map<String, Map<String, String>> getCodeMapHierarchy(String classCode, boolean includeQuickCode) {
		Set<String> scopeIds;

		DictViewInterceptor interceptor = getDictViewInterceptor(classCode);
		if (interceptor != null) {
			scopeIds = interceptor.getScopeIds(classCode);
			if (scopeIds.isEmpty()) {
				scopeIds.add("");
			}
		} else {
			scopeIds = new TreeSet<String>();
			Dict dict = getDict(classCode);
			if (dict == null) {
				throw new RuntimeException("Not found the dict, classCode=[" + classCode + "].");
			}

			if (dict.getScopes().isEmpty()) {
				scopeIds.add("");
			} else {
				scopeIds.addAll(dict.getScopes().keySet());
			}
		}

		Map<String, Map<String, String>> cmh = new TreeMap<String, Map<String, String>>();

		for (String scopeId : scopeIds) {
			cmh.put(scopeId, this.getCodeMap(classCode, scopeId == "" ? null : scopeId, includeQuickCode));
		}

		return cmh;
	}

	public Map<String, Map<String, String>> getCodeMapHierarchy(String classCode) {
		return getCodeMapHierarchy(classCode, false);
	}

	private void addCodeToCodeMapByScopeId(CodeMap cm, String scopeId, Map<String, List<CodeMap>> codeMapByScopeId) {
		List<CodeMap> cms = codeMapByScopeId.get(scopeId);
		if (cms == null) {
			cms = new ArrayList<CodeMap>();
			codeMapByScopeId.put(scopeId, cms);
		}

		if (cm != null) {
			cms.add(cm);
		}
	}

	public Dictionary getDictionaryByClassCode(String classCode) {
		List<Dict> dicts = getDictsByClassCodePattern(classCode);
		Dictionary dictionary = new Dictionary();
		for (Dict dict : dicts) {
			if (excludeToDictionaryClassCodes.contains(dict.getClassCode())) {
				// 忽略的类型代码，用于减少Dictionary的内容大小
				continue;
			}

			SubDictionary subDict = new SubDictionary();
			subDict.setClassCode(dict.getClassCode());
			dictionary.getSubDictionaries().add(subDict);

			Map<String, Set<String>> codeToScopeId = new TreeMap<String, Set<String>>();
			if (dict.getScopes() != null) {
				for (DictScope ds : dict.getScopes().values()) {
					for (String code : ds.getCodes()) {
						Set<String> scopeIds = codeToScopeId.get(code);
						if (scopeIds == null) {
							scopeIds = new HashSet<String>();
							codeToScopeId.put(code, scopeIds);
						}

						scopeIds.add(ds.getScopeId());
					}
				}
			}

			Map<String, List<CodeMap>> codeMapByScopeId = new TreeMap<String, List<CodeMap>>();
			addCodeToCodeMapByScopeId(null, "", codeMapByScopeId);
			for (DictCodeMap dcm : dict.getCodeMaps()) {
				CodeMap cm = new CodeMap();
				cm.setCode(dcm.getCode());
				cm.setName(dcm.getName());

				addCodeToCodeMapByScopeId(cm, "", codeMapByScopeId);

				if (dcm.getScopeId() != null) {
					addCodeToCodeMapByScopeId(cm, dcm.getScopeId(), codeMapByScopeId);
				}

				Set<String> scopeIds = codeToScopeId.get(cm.getCode());
				if (scopeIds == null) {
					continue;
				}

				for (String scopeId : scopeIds) {
					addCodeToCodeMapByScopeId(cm, scopeId, codeMapByScopeId);
				}
			}

			for (Map.Entry<String, List<CodeMap>> entry : codeMapByScopeId.entrySet()) {
				CodeMapTable cmt = new CodeMapTable();
				cmt.setScopeId(entry.getKey());
				cmt.setCodeMaps(entry.getValue());

				subDict.getCodeMapTables().add(cmt);
			}
		}

		return dictionary;
	}

	public Set<String> getScopeIds(String classCode) {
		DictViewInterceptor interceptor = getDictViewInterceptor(classCode);
		if (interceptor != null) {
			return interceptor.getScopeIds(classCode);
		}

		Dict dict = getDict(classCode);
		if (dict == null) {
			throw new RuntimeException("Not found the dict, classCode=[" + classCode + "].");
		}

		return dict.getScopes().keySet();
	}

	public boolean isIntercepted(String classCode) {
		DictViewInterceptor interceptor = getDictViewInterceptor(classCode);
		if (interceptor != null) {
			return true;
		}

		Dict dict = dictDao.getDictByClassCode(classCode);
		if (dict == null) {
			return false;
		}

		return true;
	}

	public Dictionary getDictionary() {
		return getDictionaryByClassCode(null);
	}

	public Dictionary getDictionaryByExcludes(Set<String> excludeClassCodes) {
		Dictionary dicts = getDictionary();
		for (Iterator<SubDictionary> it = dicts.getSubDictionaries().iterator(); it.hasNext();) {
			if (excludeClassCodes.contains(it.next().getClassCode())) {
				it.remove();
			}
		}
		return dicts;
	}
	

	
	private Dict getDict(String classCode) {
		return dictDao.getDictByClassCode(classCode);
	}

	private DictViewInterceptor getDictViewInterceptor(String classCode) {
		if (interceptors == null) {
			return null;
		}

		for (Iterator<DictViewInterceptor> it = interceptors.iterator(); it.hasNext();) {
			DictViewInterceptor interceptor = it.next();

			if (interceptor.isIntercepted(classCode)) {
				return interceptor;
			}
		}

		return null;
	}

	public void addDict(Dict dict) {
		dictDao.addDict(dict);
	}

	public void deleteDict(String classCode) {
		Dict dict = dictDao.getDictByClassCode(classCode);
		dictDao.deleteDict(dict);
	}

	public void updateDict(Dict dict) {
		dictDao.updateDict(dict);
	}

	public DictDao getDictDao() {
		return dictDao;
	}

	public void setDictDao(DictDao dictDao) {
		this.dictDao = dictDao;
	}

	public List<DictViewInterceptor> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<DictViewInterceptor> interceptors) {
		this.interceptors = interceptors;
	}

	public Set<String> getExcludeToDictionaryClassCodes() {
		return excludeToDictionaryClassCodes;
	}

	public void setExcludeToDictionaryClassCodes(Set<String> excludeToDictionaryClassCodes) {
		this.excludeToDictionaryClassCodes = excludeToDictionaryClassCodes;
	}

}
