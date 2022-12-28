package Tesqurelexam.app.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import Tesqurelexam.app.Entity.User;
import Tesqurelexam.app.Exception.ResourceNotFoundException;

public interface UserService {
	
	public User addUser(@RequestBody User user);
	
	public ResponseEntity<User> getoneUser(@PathVariable long userId) throws ResourceNotFoundException;
	
	public List<User> getAllUser(@RequestBody User  user);
	
	public User updateUser(@PathVariable long userId ,@RequestBody User user);
	
	
	
	public Map<String, Boolean> deleteUser(@PathVariable Long userId)throws ResourceNotFoundException;
	

	public void ReadExcel() ;
	

	

}
