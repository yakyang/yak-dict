package yakyang.dict.dao;

import java.sql.*;
import java.util.*;

import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import yakyang.dict.bean.*;

@Repository("dictDao")
public class DictDaoImpl implements DictDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Dict> getAllDicts() {
		return getDictByClassCodePattern(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dict getDictByClassCode(String classCode) {
		Dict dict = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT class_code,name ");
		sb.append(" FROM yak_dict  ");
		sb.append(" WHERE class_code='" + classCode + "' ");
		List<Dict> dicts = jdbcTemplate.query(sb.toString(), new RowMapper() {
			public Dict mapRow(ResultSet rs, int rowNum) throws SQLException {
				Dict dict = new Dict();
				dict.setClassCode(rs.getString(1));
				dict.setName(rs.getString(2));
				return dict;
			}
		});

		if (dicts != null && dicts.size() > 0) {
			dict = dicts.get(0);
		} else {
			return null;
		}

		dict.setCodeMaps(this.findDictCodeMap(classCode));
		List<DictScope> dictScopeList = this.findDictScope(classCode);
		Map<String, DictScope> scopeMap = new HashMap<String, DictScope>();
		for (DictScope dictScope : dictScopeList) {
			scopeMap.put(dictScope.getScopeId(), dictScope);
		}
		dict.setScopes(scopeMap);
		return dict;
	}

	@SuppressWarnings("unchecked")
	private List<DictCodeMap> findDictCodeMap(String classCode) {
		StringBuffer mapSQL = new StringBuffer();
		mapSQL.append(" SELECT code,name,quick_code  ");
		mapSQL.append(" FROM yak_dict_code_map ");
		mapSQL.append(" WHERE class_code='" + classCode + "' ");
		mapSQL.append(" ORDER BY order_num ");
		return jdbcTemplate.query(mapSQL.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				DictCodeMap dictCodeMap = new DictCodeMap();
				dictCodeMap.setCode(rs.getString(1));
				dictCodeMap.setName(rs.getString(2));
				dictCodeMap.setQuickCode(rs.getString(3));
				return dictCodeMap;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private List<DictScope> findDictScope(String classCode) {
		StringBuffer scopeSQL = new StringBuffer();
		scopeSQL.append(" SELECT dict_scope_id,scope_id  ");
		scopeSQL.append(" FROM yak_dict_scope ");
		scopeSQL.append(" WHERE class_code='" + classCode + "' ");
		List<DictScope> dictScopeList = jdbcTemplate.query(scopeSQL.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				DictScope dictScope = new DictScope();
				dictScope.setDictScopeId(rs.getLong(1));
				dictScope.setScopeId(rs.getString(2));
				return dictScope;
			}
		});

		for (DictScope dictScope : dictScopeList) {
			StringBuffer scopeCodeSQL = new StringBuffer();
			scopeCodeSQL.append(" SELECT code  ");
			scopeCodeSQL.append(" FROM yak_dict_scop_code ");
			scopeCodeSQL.append(" WHERE dict_scope_id='" + dictScope.getDictScopeId() + "' ");
			List<String> scopeCodeList = jdbcTemplate.query(scopeCodeSQL.toString(), new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString(1);
				}
			});
			dictScope.setCodes(new HashSet(scopeCodeList));
		}
		return dictScopeList;
	}

	@Override
	public void addDict(Dict dict) {
		String insertSql = "insert into test(name) values (?)";
		int count = jdbcTemplate.update(insertSql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setObject(1, "name4");
			}
		});
		Assert.assertEquals(1, count);
	}

	@Override
	public void deleteDict(Dict dict) {
		String deleteSql = "delete from test where name=?";
		int count = jdbcTemplate.update(deleteSql, new Object[] { "name4" });
		Assert.assertEquals(1, count);
	}

	@Override
	public void updateDict(Dict dict) {
		// TODO Auto-generated method stub

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Dict> getDictByClassCodePattern(String classCodePattern) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT class_code,name ");
		sb.append(" FROM yak_dict  ");
		if (classCodePattern != null && classCodePattern.length() > 0) {
			sb.append(" WHERE class_code like '%" + classCodePattern + "%' ");
		}

		List<Dict> dictList = jdbcTemplate.query(sb.toString(), new RowMapper() {
			public Dict mapRow(ResultSet rs, int rowNum) throws SQLException {
				Dict dict = new Dict();
				dict.setClassCode(rs.getString(1));
				dict.setName(rs.getString(2));
				return dict;
			}
		});

		for (Dict dict : dictList) {
			dict.setCodeMaps(this.findDictCodeMap(dict.getClassCode()));
			List<DictScope> dictScopeList = this.findDictScope(dict.getClassCode());
			Map<String, DictScope> scopeMap = new HashMap<String, DictScope>();
			for (DictScope dictScope : dictScopeList) {
				scopeMap.put(dictScope.getScopeId(), dictScope);
			}
			dict.setScopes(scopeMap);
		}
		return dictList;
	}

}
