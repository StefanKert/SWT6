package swt6.spring.worklog.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DateUtil;

public class EmployeeDaoJdbc extends JdbcDaoSupport implements EmployeeDao {

	private EmployeeInserter employeeInserter;
	private EmployeeUpdater employeeUpdater;

	@Override
	protected void initDao() throws Exception {
		this.employeeInserter = new EmployeeInserter(this.getDataSource());
		this.employeeUpdater = new EmployeeUpdater(this.getDataSource());
	}
	
	@Override
	public void save(Employee e) {
		final String sql = "insert into Employee (firstName, lastName, dateofbirth) " + "values (?, ?, ?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getDataSource().getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, e.getFirstName());
			stmt.setString(2, e.getLastName());
			stmt.setDate(3, DateUtil.toSqlDate(e.getDateOfBirth()));
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				System.err.println(ex);
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				System.err.println(ex);
			}
		}

	}

	@Override
	public Employee findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee merge(Employee entity) {
		// TODO Auto-generated method stub
		return null;
	}

	// Helper class that maps ResultSets to collections
	// Can be shared between different read queries.
	protected static class EmployeeRowMapper implements RowMapper<Employee> {
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee empl = new Employee();

			empl.setId(rs.getLong(1));
			empl.setFirstName(rs.getString(2));
			empl.setLastName(rs.getString(3));
			empl.setDateOfBirth(rs.getDate(4));

			return empl;
		}
	}

	// Inserter class for Employees. Statement is prepared in the constructor.
	// One instance one be used by different threads because class is
	// thread-safe. An instance is created in initDao().
	protected static class EmployeeInserter extends SqlUpdate {
		public EmployeeInserter(DataSource ds) {
			super(ds, "insert into Employee (firstName, lastName, dateofbirth) " + "values (?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));
			setReturnGeneratedKeys(true);
			compile(); // statement is prepared here.
		}

		public void insert(Employee e) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			update(new Object[] { e.getFirstName(), e.getLastName(), e.getDateOfBirth() }, keyHolder);
			e.setId(keyHolder.getKey().longValue());
		}
	}

	// Updater class for employees.
	protected static class EmployeeUpdater extends SqlUpdate {
		public EmployeeUpdater(DataSource ds) {
			super(ds, "update Employee set firstName=?, lastName=?, dateofbirth=?" + " where id=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.DATE));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		public int update(Employee e) {
			return update(e.getFirstName(), e.getLastName(), e.getDateOfBirth(), e.getId());
		}
	}

}
