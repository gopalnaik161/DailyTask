package Tesqurelexam.app.Service;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Tesqurelexam.app.Entity.User;
import Tesqurelexam.app.Exception.ResourceNotFoundException;
import Tesqurelexam.app.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public User addUser(User user) {
		user.setIsActive("A");

		// code for date pattren change

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String format = sdf.format(new Date());

		try {
			user.setDate(sdf.parse(format));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUser(User user) {

		return userRepo.findByIsActive("A");
	}

	@Override
	public User updateUser(long userId, User user) {
//		User existingUser = userRepo.findById(userId).orElse(null);

		User existingUser = userRepo.findByUserIdAndIsActive(userId, "A").orElse(null);

		if (existingUser != null) {
			existingUser.setUserId(user.getUserId());
			existingUser.setUserName(user.getUserName());
			existingUser.setAddress(user.getAddress());
			existingUser.setContactNumber(user.getContactNumber());
			existingUser.setAdminId(user.getAdminId());
			existingUser.setCreatedBy(existingUser.getCreatedBy());
			existingUser.setDate(existingUser.getDate());
		}

		return userRepo.save(existingUser);
	}

	@Override
	public Map<String, Boolean> deleteUser(Long userId) throws ResourceNotFoundException {
		User user2 = userRepo.findByUserIdAndIsActive(userId, "A")
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		if (user2 != null) {
//			User user = findById.get();
			user2.setIsActive("D");
//			userRepo.delete(user);
			userRepo.save(user2);

		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@Override
	public ResponseEntity<User> getoneUser(long userId) throws ResourceNotFoundException {
		User user = userRepo.findByUserIdAndIsActive(userId, "A")
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		return ResponseEntity.ok().body(user);
	}

	@Override
	public void ReadExcel() {

		try {
			FileInputStream file = new FileInputStream(new File("C:\\Users\\gopal naik\\Downloads\\users (1).xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> itr = sheet.iterator();
			while (itr.hasNext()) {
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				ArrayList<Object> object = new ArrayList<>();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					CellType cellType = cell.getCellType();
					switch (cellType) {
					case NUMERIC:
						double value = cell.getNumericCellValue();
						object.add(value);

						break;
					case STRING:
						String value2 = cell.getStringCellValue();
						object.add(value2);
						break;

					default:
					}
				}
				System.out.println(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//		entityManager.merge(User);
//
//		
//		 Workbook  workbook =new XSSFWorkbook();
//			
//			Sheet Sheet = workbook.createSheet("UserData");
//			
//			int rowNum = 0;
//			
//		
//			     Row row =Sheet.createRow(rowNum ++);
//				 row.createCell(0).setCellValue(User.getUserId());
//			     row.createCell(1).setCellValue(User.getUserName());
//				 row.createCell(2).setCellValue(User.getAddress());
//			     row.createCell(3).setCellValue(User.getContactNumber());
//			     row.createCell(4).setCellValue(User.getAdminId());
//	   		     row.createCell(5).setCellValue(User.getCreatedBy());
//				 row.createCell(6).setCellValue(new Date());
//				 row.createCell(7).setCellValue(User.getIsActive());
//	 
//			  FileOutputStream fileOut = new FileOutputStream("C:\\Users\\gopal naik\\OneDrive\\Documents\\UserData.xlsx");
//		        workbook.write(fileOut);
//		    
//		        fileOut.close();
//		        
//		        workbook.close();	

}

//
//	@Override
//	public void exportToExcel(HttpServletResponse response) throws IOException {
//		
//		
//		response.setContentType("application/octet-stream");
//		DateFormat dateformate=new SimpleDateFormat();
//		String CurrentDateandTime = dateformate.format(new Date());
//		String header = "content-Disposition";
//		
//		String headerValue = "attachment; filename = UserData2022" +CurrentDateandTime+".XLSX";
//		response.setHeader(header, headerValue);
//		
//		
//		 List<User> listUsers = userRepo.findAll();
//		
//		 
//		 ExelExporter excelexporter = new ExelExporter(listUsers);
//		 
//		 excelexporter.exportData(response);
//	}
