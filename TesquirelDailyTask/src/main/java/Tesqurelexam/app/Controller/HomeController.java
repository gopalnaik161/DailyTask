package Tesqurelexam.app.Controller;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Tesqurelexam.app.Entity.User;
import Tesqurelexam.app.Exception.ResourceNotFoundException;
import Tesqurelexam.app.Repository.UserRepository;
import Tesqurelexam.app.Service.ExcelGenerator;
import Tesqurelexam.app.Service.UserService;

@RestController
public class HomeController {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private UserRepository userRepo;
	

	
	@PostMapping("/createUser")
	public User addUser(@RequestBody User user) {
		
	return this.userservice.addUser(user);
			
	}
	
	@GetMapping("/getuserbyId/{userId}")
	public ResponseEntity<User> getoneUser(@PathVariable long userId) throws ResourceNotFoundException {
		
		return userservice.getoneUser(userId);
	
	}

	@GetMapping("/getAllUser")
	public List<User> getAllUser(@RequestBody User  user){
		return userservice.getAllUser(user);	
	}
	
	@PutMapping("/updateUser/{userId}")
	public User updateUser(@PathVariable long userId ,@RequestBody User user) {
		 return userservice.updateUser(userId, user);
	}
	

	@DeleteMapping("/deleteUser/{userId}")
	public Map<String, Boolean> deleteUser(@PathVariable Long userId)throws ResourceNotFoundException {
		
         return userservice.deleteUser(userId);
	}
	
	
	
	@PostMapping("/WriteToExcel")
	public void ReadExcel() {
		userservice.ReadExcel();
		
		
	}
	
	
	@GetMapping("/export")
	public ResponseEntity<InputStreamResource> exportUsersToExcel() throws IOException {

	    List<User> users = userRepo.findAll();

	    ByteArrayInputStream in = ExcelGenerator.usersToExcel(users);

	    // Set the content type and attachment header.
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attachment; filename=users.xlsx");

	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .body(new InputStreamResource(in));
	
	}
	

}
	
		
	
	
	
	
	

		 

	
	            
	        

