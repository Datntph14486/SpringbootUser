package demo.test.repository;

import java.util.List;

import demo.test.model.User;



public interface UserRepository {
	int save(User user);
	int update(User user);
	User findbyId(Long id);
	int deleteById(Long id);
	List<User> findAll();
	List<User> findByUsername(String name);
	List<User> findByRole(Boolean role);
	int deleteAll();
	
	

}
