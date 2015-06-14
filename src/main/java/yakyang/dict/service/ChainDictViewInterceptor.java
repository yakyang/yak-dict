package yakyang.dict.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.*;
import yakyang.dict.bean.*;

/**
 * 链式数据字典拦截器实现类
 * 
 */
public class ChainDictViewInterceptor implements DictViewInterceptor {
	/**
	 * 数据字典服务
	 */
	@Resource
	private DictViewService dictViewService;

	/**
	 * 包含的类名称集
	 */
	private Set<String> includeClassCodes;

	/**
	 * 不包含的类名称集
	 */
	private Set<String> excludeClassCodes;

	private boolean isSupportClassCode(String classCode) {
		if (excludeClassCodes != null && excludeClassCodes.size() > 0 && excludeClassCodes.contains(classCode)) {
			return false;
		}

		if (includeClassCodes != null && includeClassCodes.size() > 0 && includeClassCodes.contains(classCode) == false) {
			return false;
		}

		return true;
	}

	public Map<String, String> getCodeMap(String classCode, String scopeId, boolean includeHelpCode) {
		if (isSupportClassCode(classCode) == false) {
			throw new RuntimeException("Not found the classCode=[" + classCode + "].");
		}

		return dictViewService.getCodeMap(classCode);
	}

	public List<Dict> getDicts(String classCodePattern) {
		List<Dict> dicts = dictViewService.getDictsByClassCodePattern(classCodePattern);
		for (Iterator<Dict> it = dicts.iterator(); it.hasNext();) {
			Dict dict = it.next();
			if (isSupportClassCode(dict.getClassCode()) == false) {
				it.remove();
			}
		}

		return dicts;
	}

	public String getNameByCode(String classCode, String code, boolean includeHelpCode) {
		if (isSupportClassCode(classCode) == false) {
			throw new RuntimeException("Not found the classCode=[" + classCode + "].");
		}
		return dictViewService.getNameByCode(classCode, code, includeHelpCode);
	}

	public Set<String> getScopeIds(String classCode) {
		if (isSupportClassCode(classCode) == false) {
			throw new RuntimeException("Not found the classCode=[" + classCode + "].");
		}
		return dictViewService.getScopeIds(classCode);
	}

	public boolean isIntercepted(String classCode) {
		if (isSupportClassCode(classCode) == false) {
			return false;
		}
		return dictViewService.isIntercepted(classCode);
	}

	public DictViewService getDictViewService() {
		return dictViewService;
	}

	public void setDictViewService(DictViewService dictViewService) {
		this.dictViewService = dictViewService;
	}

	public Set<String> getIncludeClassCodes() {
		return includeClassCodes;
	}

	public void setIncludeClassCodes(Set<String> includeClassCodes) {
		this.includeClassCodes = includeClassCodes;
	}

	public Set<String> getExcludeClassCodes() {
		return excludeClassCodes;
	}

	public void setExcludeClassCodes(Set<String> excludeClassCodes) {
		this.excludeClassCodes = excludeClassCodes;
	}

}
