package demo.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.test.model.User;
import demo.test.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class Usercontroller {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/user")
	ResponseEntity<List<User>> getAllUsser(@RequestParam(required = false) String name) {
		try {
			List<User> list = new ArrayList<User>();
			if (name == null)
				userRepository.findAll().forEach(list::add);
			else
				userRepository.findByUsername(name).forEach(list::add);
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		User user = userRepository.findbyId(id);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/user")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		try {
			userRepository.save(new User(user.getId(), user.getUsername(), user.getPassword(), user.getRole()));

			return new ResponseEntity<>("User was created successfully.", HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<String> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		User _user = userRepository.findbyId(id);
		if (_user != null) {
			_user.setId(id);
		
			_user.setUsername(user.getUsername());
			_user.setPassword(user.getPassword());
			userRepository.update(_user);
			return new ResponseEntity<>("User was updated successfully.", HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Can not find User with id=" + id, HttpStatus.NOT_FOUND);

		}
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
		try {
			int result = userRepository.deleteById(id);
			if (result == 0) {
				return new ResponseEntity<>("Cannot find User with id=" + id, HttpStatus.OK);

			}
			return new ResponseEntity<>("User was deleted successfully.", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("Cannot delete User.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/user")
	public ResponseEntity<String> deleteAllUser() {
		try {
			int numRows = userRepository.deleteAll();
			return new ResponseEntity<>("Delete" + numRows + "User sucessfully.", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("cannot delete user.", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/user/role")
	public ResponseEntity<List<User>> findByRole() {
		try {
			List<User> users = userRepository.findByRole(true);
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	

}
