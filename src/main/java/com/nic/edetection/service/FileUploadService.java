package com.nic.edetection.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nic.edetection.iservice.IFileUploadService;
import com.nic.edetection.model.VehicleTransactionHistory;
import com.nic.edetection.repo.VehicleTransactionHistoryRepository;

@Service
public class FileUploadService implements IFileUploadService {

	@Autowired
	VehicleTransactionHistoryRepository vehicleTransactionHistoryRepo;
	
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

	@Override
	public Boolean save(MultipartFile file) {
		try {
			this.root = Paths.get(this.basePath);
			Date today = new Date();
			String fileName = today.getTime() + "-" + file.getOriginalFilename();
			Files.copy(file.getInputStream(), this.root.resolve(fileName));

			InputStream inputStream = file.getInputStream();
			Scanner csvReader = new Scanner(inputStream, "UTF-8");
			String row;
			int i = 0;
			VehicleTransactionHistory vehicleTransactionHistory;

			while ((row = csvReader.nextLine()) != null) {
				String[] data = row.split(",");
				if (i > 0 && data[0] != null) { 
					vehicleTransactionHistory = new VehicleTransactionHistory();
					Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(data[0]);
					vehicleTransactionHistory.setTransactionDate(date1);
					vehicleTransactionHistory.setVehicleNo(data[1]);
					vehicleTransactionHistory.setVtClass(data[2]);
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
				    String strDate= formatter.format(date1);  
					vehicleTransactionHistory.setUniqueId(data[1]+"/"+strDate);
					try {
					vehicleTransactionHistoryRepo.save(vehicleTransactionHistory);
					}
					catch(Exception e) {
						continue;
					}
					//System.out.println(row + "  " + data[0] + " " + data[1] + " " + data[2] + " " + data[3]);
				}
				i += 1;

			}
			csvReader.close();
			return true; 

		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
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
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

}
