package yakyang.dict.company;

import java.sql.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository("accountantDao")
public class AccountantDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("unchecked")
	public List<Accountant> findAccountant() {
		String sql = " select id,name,gender,age from accountant ";
		return jdbcTemplate.query(sql, new RowMapper() {
			public Accountant mapRow(ResultSet rs, int rowNum) throws SQLException {
				Accountant accountant = new Accountant();
				accountant.setId(rs.getInt(1));
				accountant.setName(rs.getString(2));
				accountant.setGender(rs.getString(3));
				accountant.setAge(rs.getInt(4));
				return accountant;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Accountant findAccountantById(Integer id) {
		String sql = " select id,name,gender,age from accountant where id=" + id;
		List<Accountant> list = jdbcTemplate.query(sql, new RowMapper() {
			public Accountant mapRow(ResultSet rs, int rowNum) throws SQLException {
				Accountant accountant = new Accountant();
				accountant.setId(rs.getInt(1));
				accountant.setName(rs.getString(2));
				accountant.setGender(rs.getString(3));
				accountant.setAge(rs.getInt(4));
				return accountant;
			}
		});
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
}
