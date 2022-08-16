package com.nic.edetection.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nic.edetection.dto.UserStatusDto;
import com.nic.edetection.iservice.IFileUploadService;
import com.nic.edetection.model.UserStatus;
import com.nic.edetection.model.VehicleTransactionHistory;
import com.nic.edetection.repo.UserLoginRepo;
import com.nic.edetection.repo.VehicleTransactionHistoryRepository;

@Service
public class FileUploadService implements IFileUploadService {

	@Autowired
	VehicleTransactionHistoryRepository vehicleTransactionHistoryRepo;

	@Autowired
	UserLoginRepo userLoginRepo;
	
	@Autowired
	UserStatusService userStatuService;

	@Value("${basePath}")
	private String basePath;
	private Path root = null;

	@Override
	public void init() {
		this.root = Paths.get(this.basePath);
		// System.out.println("Base Path is"+System.getProperty("basePath")+"
		// "+basePath);
		try {
			Files.createDirectory(this.root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}
	public static String removeExtn(final String fileNameWithExtn) {
		final int extensionIndex = fileNameWithExtn.indexOf(".");
		if (extensionIndex == -1) {
			return fileNameWithExtn;
		}
		return fileNameWithExtn.substring(0, extensionIndex);
	}
	@Override
	public String save(MultipartFile file, Long userId) {
//		Optional<UserLogin> u = userLoginRepo.findById(userId);
//		UserLogin user = u.get();
		InputStream inputStream = null;
		InputStream copyStream = null;
		try {
			this.root = Paths.get(this.basePath);
			Date today = new Date();
			DateFormat formatter2 = new SimpleDateFormat("ddMMyyyyHHmm");
			String fileExtDate = formatter2.format(today);
			String fileName= removeExtn(file.getOriginalFilename());
			if(fileName.contains(" ") || fileName.contains(".")) {
				return "please change the filename as \"tollname_ddMMyy\" format!";
			}
			fileName = fileName+ fileExtDate+".csv";
			inputStream = file.getInputStream();
			 copyStream = file.getInputStream();
			Files.copy(copyStream, this.root.resolve(fileName));
			

			
			@SuppressWarnings("resource")
			Scanner csvReader = new Scanner(inputStream, "UTF-8");
			String row;
			int i = 0;
			VehicleTransactionHistory vehicleTransactionHistory;

			while ((row = csvReader.nextLine()) != null) {
				String[] data; 
				data = row.split(","); 
				// System.out.println(data.length +" "+ i);
				if (data.length == 0 && i < 2) {
					return "There is no data";
				}
				if (data.length == 0 && i > 1) {
					return "File Inserted Successfully!";
				}
				if(data[1].contains("MAV(3 axle)") || data[1].contains("TRUCK 2 AXLE")) {
					return "File sequence should be (TransactionDate, VehicleNo, Vehicle Class) !!";
				}
				i = i + 1;
				if (i > 1 && data.length != 0) {
					vehicleTransactionHistory = new VehicleTransactionHistory();
					
				   // Date date1;
				    String[] patterns = {"dd/MM/yyyy HH:mm","dd-MM-yyyy HH:mm","dd-MM-yyyy HH.mm","dd/MM/yyyy HH.mm"};
				    //System.out.println(data[0]);
				    if(!data[0].equals(null) && !data[0].equals("") && !data[0].equals("N/A")) {
				    	String date = data[0];
				    Date date1 = DateUtils.parseDate(date,patterns);
					vehicleTransactionHistory.setTransactionDate(date1);				
					vehicleTransactionHistory.setVehicleNo(data[1]); 
					vehicleTransactionHistory.setVtClass(data[2]);
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					String strDate = formatter.format(date1);
					vehicleTransactionHistory.setUniqueId(data[1] + "/" + strDate);
					vehicleTransactionHistory.setCreatedBy(userId); 
					vehicleTransactionHistory.setCreatedDate(new Date());
					UserStatusDto u = new UserStatusDto();
					u.setCreatedBy(userId);
					u.setCreatedDate(new Date());
					u.setUniqueId(data[1]+"/"+strDate);
					u.setUploadStatus("y");
					u.setUploadStatusDate(new Date());
					u.setVehicleNo(data[1]);
				    
					try {
						vehicleTransactionHistoryRepo.save(vehicleTransactionHistory);
						userStatuService.createUserStatus(u);
					} catch (Exception e) {
						continue;
					}
					// System.out.println(row + " " + data[0] + " " + data[1] + " " + data[2] + " "
					// + data[3]);

				}
				    else {
						return "Please check for NA fields and blank fields in the excel sheet";
					}
				    }
				

			}
			return "Uploaded Successfully";

		} catch (NoSuchElementException e) {
			
			return "File Inserted Successfully";
		}catch (ParseException e) {
			return "Unparsable Date format. Please Use Transaction date format as \"dd/MM/yyyy HH:mm\" or \"dd-MM-yyyy HH:mm\"";
		}
		catch(FileAlreadyExistsException e) {
			return "File Already Exist";
		}
		catch (Exception e) {
//			System.out.println(e);
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException io) {
                	throw new RuntimeException("inputStream Not Closed"+ io.getMessage());
                }
            }
            if(copyStream != null) {
            	try {
            		copyStream.close();
            	}
            	catch (IOException e) {
            		throw new RuntimeException("inputStream Not Closed"+ e.getMessage());
            	}
            }
		}

	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			this.root = Paths.get(this.basePath);
			File dir = new File(this.basePath);
			File[] files = dir.listFiles();
			Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
			List<Path> paths = new ArrayList<>();
			for (File file : files) { 
				if (!file.isHidden()) {
					if (file.isDirectory()) {
						// System.out.println("DIR \t" + file.getName());
					} else {
						if(file.getName().contains(".csv")) {
							paths.add(file.toPath());
						}
						else {
						
						// System.out.println("FILE\t" + file.getName());
						}
					}
				}
			}

//			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
			return paths.stream();
		} catch (Exception e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

}
