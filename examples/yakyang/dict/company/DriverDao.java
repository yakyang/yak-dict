package yakyang.dict.company;

import java.sql.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Repository("driverDao")
public class DriverDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("unchecked")
	public List<Driver> findDriver() {
		String sql = " select id,name,gender,age from driver ";
		return jdbcTemplate.query(sql, new RowMapper() {
			public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
				Driver driver = new Driver();
				driver.setId(rs.getInt(1));
				driver.setName(rs.getString(2));
				driver.setGender(rs.getString(3));
				driver.setAge(rs.getInt(4));
				return driver;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Driver findDriverById(Integer id) {
		String sql = " select id,name,gender,age from customer where id=" + id;
		List<Driver> list = jdbcTemplate.query(sql, new RowMapper() {
			public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
				Driver driver = new Driver();
				driver.setId(rs.getInt(1));
				driver.setName(rs.getString(2));
				driver.setGender(rs.getString(3));
				driver.setAge(rs.getInt(4));
				return driver;
			}
		});
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
