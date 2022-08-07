package demo.test.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import demo.test.model.User;

@Repository
public class jdbcUserRepository implements UserRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public int save(User user) {
		return jdbcTemplate.update("INSERT INTO USER (id,userName,password,role) VALUES(?,?,?,?)",
				new Object[] { user.getId(), user.getUsername(), user.getPassword(), user.getRole() });
	}

	@Override
	public int update(User user) {

		return jdbcTemplate.update("UPDATE USER SET userName=?,password=? WHERE ID=?",
				new Object[] { user.getUsername(), user.getPassword(), user.getId() });
	}

	@Override
	public User findbyId(Long id) {
		User user = jdbcTemplate.queryForObject("SELECT * FROM USER WHERE ID =?",
				BeanPropertyRowMapper.newInstance(User.class), id);

		return user;
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update("DELETE FROM USER WHERE id=?", id);
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM USER", BeanPropertyRowMapper.newInstance(User.class));
	}

	@Override
	public int deleteAll() {
		return jdbcTemplate.update("DELETE FROM USER");
	}

	@Override
	public List<User> findByUsername(String username) {
		String q = "SELECT * FROM USER WHERE USERNAME LIKE '%" + username + "%'";
		
		return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(User.class));
	}

	@Override
	public List<User> findByRole(Boolean role) {
		return jdbcTemplate.query("SELECT *  FROM USER WHERE ROLE=?", BeanPropertyRowMapper.newInstance(User.class),
				role);
	}

}
