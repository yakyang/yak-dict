package yakyang.dict.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import yakyang.dict.bean.*;

public interface DictViewInterceptor {
	/**
	 * 是否拦截分类代码
	 * @param classCode 分类代码
	 * @return 拦截标志
	 */
	public boolean isIntercepted(String classCode);
	
	/**
	 * 获得分类代码下的字典代码映射
	 * @param classCode 分类代码
	 * @param scopeId 分类范围
	 * @param includeHelpCode 是否包含助记代码
	 * @return 字典代码映射
	 */
	public Map<String, String> getCodeMap(String classCode, String scopeId, boolean includeHelpCode);
	
	/**
	 * 通过字典代码获得对应的名称
	 * @param classCode 分类代码
	 * @param code 字典代码
	 * @param includeHelpCode 是否包含助记代码
	 * @return 字典代码对应的名称
	 */
	public String getNameByCode(String classCode, String code, boolean includeHelpCode);
	
	/**
	 * 获得代码范围集
	 * @param classCode
	 * @return
	 */
	public Set<String> getScopeIds(String classCode);
	
	/**
	 * 获得字典定义集
	 * @param classCodePattern 分类代码模式
	 * @return 字典基本定义集
	 */
	public List<Dict> getDicts(String classCodePattern);
	
}
