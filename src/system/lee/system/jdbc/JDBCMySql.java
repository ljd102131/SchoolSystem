package lee.system.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;



public class JDBCMySql {
	private static ApplicationContext ctx = null;
	public static NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	/*private static String jdbcUser = "";
	private static String jdbcPassword = "";
	private static String jdbcUrl = "";
	private static String driverClass = "";
	private static int initialPoolSize = 5;
	private static int maxPoolSize = 20;*/
	{
		try {
			if (ctx == null) {
				ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			}
			namedParameterJdbcTemplate = (NamedParameterJdbcTemplate)ctx.getBean(NamedParameterJdbcTemplate.class);
			
			/*ComboPooledDataSource pool= (ComboPooledDataSource) ctx.getBean("dataSource");
			jdbcUser = pool.getUser();
			jdbcPassword = pool.getPassword();
			jdbcUrl = pool.getJdbcUrl();
			driverClass = pool.getDriverClass();
			initialPoolSize = pool.getInitialPoolSize();
			maxPoolSize = pool.getMaxPoolSize();*/
		} catch (Exception e) {
			System.out.println("错误：" +e.getMessage()+ e.getStackTrace());
		}
	}
	
	public <T> T queryForObject2(String sql,Object parameters,BeanPropertyRowMapper<T> rowMapper) {
		T t = null;
		try {
			SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(parameters);
			t = namedParameterJdbcTemplate.queryForObject(sql,parameterSource,rowMapper);
		} catch (DataAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        return t;
	}
	
	
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	/*public static Connection getConnection() {
		//声明连接对象
		Connection conn = null;
		try {
			//注册(加载)驱动程序
			Class.forName(driverClass);
			// 获取数据库连接
			conn = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPassword);
		} catch (Exception  e) {
			e.printStackTrace();
		} 
		return conn;
	}*/
	
	/**
	 * 释放数据库连接
	 * @param conn
	 */
	 /*public static void releaseConnection(Connection conn) {
		 if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 
	 }*/
	 
	 /*public int insert(String sql) {
		 Connection conn = getConnection();
		 int count = 0;
		 try {
			Statement statement = conn.createStatement();// 或者用PreparedStatement方法
			count = statement.executeUpdate(sql);//执行sql语句
			if (statement != null) {
				statement.close();
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 return count;
	 }*/
}
