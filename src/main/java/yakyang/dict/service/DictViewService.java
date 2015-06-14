package yakyang.dict.service;

import java.util.*;

import yakyang.dict.bean.*;
import yakyang.dict.bean.Dictionary;

public interface DictViewService {
	/**
	 * 获得字典定义集
	 * @param classCodePattern 分类代码模式
	 * @return 字典基本定义集
	 */
	public List<Dict> getDictsByClassCodePattern(String classCodePattern);

	/**
	 * 获得字典定义集（可例外选择）
	 * @param excludeClassCodes
	 * @return
	 */
	public List<Dict> getDictsByExcludes(Set<String> excludeClassCodes);

	/**
	 * 获得分类代码下的字典代码映射
	 * @param classCode 分类代码
	 * @return 字典代码映射
	 */

	public Map<String, String> getCodeMap(String classCode);

	/**
	 * 获得分类代码下的字典代码映射
	 * @param classCode 分类代码
	 * @param includeQuickCode 是否包含助记代码
	 * @return 字典代码映射
	 */

	public Map<String, String> getCodeMap(String classCode, boolean includeQuickCode);

	/**
	 * 获得字典代码映射层次信息
	 * @param classCode
	 * @param includeQuickCode
	 * @return
	 */

	public Map<String, Map<String, String>> getCodeMapHierarchy(String classCode, boolean includeQuickCode);

	/**
	 * 获得字典代码映射层次信息
	 * @param classCode
	 * @return
	 */

	public Map<String, Map<String, String>> getCodeMapHierarchy(String classCode);

	/**
	 * 获得分类代码下的字典代码映射
	 * @param classCode 分类代码
	 * @param scopeId 分类范围
	 * @return 字典代码映射
	 */
	
	public Map<String, String> getCodeMap(String classCode, String scopeId);

	/**
	 * 获得分类代码下的字典代码映射
	 * @param classCode 分类代码
	 * @param scopeId 分类范围
	 * @param includeQuickCode 是否包含助记代码
	 * @return 字典代码映射
	 */
	
	public Map<String, String> getCodeMap(String classCode, String scopeId, boolean includeQuickCode);

	/**
	 * 通过字典代码获得对应的名称
	 * @param classCode 分类代码
	 * @param code 字典代码
	 * @return 字典代码对应的名称
	 */
	
	public String getNameByCode(String classCode, String code);

	/**
	 * 通过字典代码获得对应的名称
	 * @param classCode 分类代码
	 * @param code 字典代码
	 * @param includeQuickCode 是否包含助记代码
	 * @return 字典代码对应的名称
	 */
	
	public String getNameByCode(String classCode, String code, boolean includeQuickCode);

	/**
	 * 通过字典代码集获得对应的名称
	 * @param classCode 分类代码
	 * @param codes 字典代码
	 * @param includeQuickCode 是否包含助记代码
	 * @return 字典代码对应的名称
	 */
	
	public String getNameByCodes(String classCode, Collection<String> codes, boolean includeQuickCode);

	/**
	 * 通过字典代码集获得对应的名称
	 * @param classCode 分类代码
	 * @param codes 字典代码
	 * @param includeQuickCode 是否包含助记代码
	 * @return 字典代码对应的名称
	 */
	
	public String getNameByCodes(String classCode, Collection<String> codes);

	/**
	 * 增加一个字典代码映射
	 * @param dict 字典代码映射
	 */
	
	public void addDict(Dict dict);

	/**
	 * 删除一个字典代码映射
	 * @param classCode 分类代码
	 */
	
	public void deleteDict(String classCode);

	/**
	 * 修改一个字典代码映射
	 * @param dict 字典代码映射
	 */
	
	public void updateDict(Dict dict);

	/**
	 * 获得完整的字典
	 * @return 字典对象
	 */
	
	public Dictionary getDictionary();

	/**
	 * 获得完整的字典
	 * @return 字典对象
	 */
//	@WebMethod
	public Dictionary getDictionaryByExcludes(Set<String> excludeClassCodes);

	/**
	 * 获得字典
	 * @param classCodePattern
	 * @return
	 */
	
	public Dictionary getDictionaryByClassCode(String classCodePattern);

	/**
	 * 获得范围代码
	 * @param classCode
	 * @return
	 */
	public Set<String> getScopeIds(String classCode);

	/**
	 * 是否支持
	 * @param classCode
	 * @return
	 */
	public boolean isIntercepted(String classCode);
		
}
