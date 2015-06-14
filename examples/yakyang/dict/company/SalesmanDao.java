package yakyang.dict.company;

import java.sql.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository("salesmanDao")
public class SalesmanDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("unchecked")
	public List<Salesman> findSalesman() {
		String sql = " select id,name,gender,age from salesman ";
		return jdbcTemplate.query(sql, new RowMapper() {
			public Salesman mapRow(ResultSet rs, int rowNum) throws SQLException {
				Salesman salesman = new Salesman();
				salesman.setId(rs.getInt(1));
				salesman.setName(rs.getString(2));
				salesman.setGender(rs.getString(3));
				salesman.setAge(rs.getInt(4));
				return salesman;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Salesman findSalesmanById(Integer id) {
		String sql = " select id,name,gender,age from salesman where id=" + id;
		System.out.println(sql);
		List<Salesman> list = jdbcTemplate.query(sql, new RowMapper() {
			public Salesman mapRow(ResultSet rs, int rowNum) throws SQLException {
				Salesman salesman = new Salesman();
				salesman.setId(rs.getInt(1));
				salesman.setName(rs.getString(2));
				salesman.setGender(rs.getString(3));
				salesman.setAge(rs.getInt(4));
				return salesman;
			}
		});
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
}
