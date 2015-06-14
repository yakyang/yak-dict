package yakyang.dict.dao;

import java.util.List;
import yakyang.dict.bean.*;

public interface DictDao {
	
	/**
	 * 增加字典
	 * @param dict 字典对象
	 */
	public void addDict(Dict dict);
	
	/**
	 * 修改字典
	 * @param dict 字典对象
	 */
	public void updateDict(Dict dict);
	
	/**
	 * 删除字典
	 * @param dict 字典对象
	 */
	public void deleteDict(Dict dict);

	/**
	 * 获得所有的字典列表
	 * @return 字典列表
	 */
	public List<Dict> getAllDicts();
	
	/**
	 * 获得字典对象
	 * @param classCode 分类代码
	 * @return 字典对象
	 */
	public Dict getDictByClassCode(String classCode);
	
	/**
	 * 获得字典基础对象列表
	 * @param classCodePattern 分类代码模式
	 * @return 字典基础列表
	 */
	public List<Dict> getDictByClassCodePattern(String classCodePattern);
}
