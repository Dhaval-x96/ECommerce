package com.ecommerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.ecommerce.entity.FileDetails;
import com.ecommerce.exceptions.DataNotFoundException;
import com.ecommerce.json.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.entity.Electronics;
import com.ecommerce.services.ElectronicsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "electronics")
public class ElectronicsController {

	@Autowired
	private ElectronicsService electronicsService;

	@PostMapping()
	public ResponseEntity<ApiResponse<Electronics>> save(@Valid @RequestParam("electronics") Electronics electronics, @RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
		if(files.length ==0){
			throw new DataNotFoundException("400", "Please update pictures.");
		}

		List<FileDetails> fileDetailsList = new ArrayList<>();

		for(MultipartFile file: files){
			FileDetails fileDetails = new FileDetails();
			fileDetails.setImages(file.getBytes());
			fileDetails.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
			fileDetails.setSize(file.getSize());
			fileDetails.setOriginalFileName(file.getOriginalFilename());
			//electronics.setFileDetails(Arrays.asList(fileDetails));
			fileDetailsList.add(fileDetails);
			fileDetails.setElectronics(electronics);
		}

		electronics.setFileDetails(fileDetailsList);
		return new ResponseEntity<ApiResponse<Electronics>>(new ApiResponse(this.electronicsService.saveOrUpdate(electronics),request.getRequestURL().toString()),HttpStatus.OK);
	}

	@GetMapping("/{electronicId}")
	public ResponseEntity<ApiResponse<Electronics>> get(@PathVariable(value = "electronicId") String id, HttpServletRequest request) {
		return ResponseEntity.ok().body(new ApiResponse<Electronics>(this.electronicsService.get(id), request.getRequestURL().toString()));
	}

	@PutMapping()
	public ResponseEntity<ApiResponse<Electronics>> update(@RequestBody(required = true) Electronics electronics, HttpServletRequest request){
		return ResponseEntity.ok().body(new ApiResponse<Electronics>(this.electronicsService.saveOrUpdate(electronics), request.getRequestURL().toString()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("id") String id, HttpServletRequest request){
		return ResponseEntity.ok().body(new ApiResponse(this.electronicsService.delete(id), request.getRequestURL().toString()));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Page<Electronics>>> getAll(@RequestParam(value = "rating", required = false) Short rating,@RequestParam(value = "discountedPrice", required = false) String discountedPrice,Pageable pageable, HttpServletRequest request){
 d		return ResponseEntity.ok().body(new ApiResponse<Page<Electronics>>(this.electronicsService.getAll(pageable, rating, discountedPrice), request.getRequestURL().toString()));
	}

	@Component
	public static class ElectonicsConverter implements Converter<String, Electronics> {
		@Autowired
		private ObjectMapper objectMapper;

		@SneakyThrows
		@Override
		public Electronics convert(String source) {
			return objectMapper.readValue(source, Electronics.class);
		}
	}

	@Component
	public static class FileDetailsConverter implements Converter<String, FileDetails> {
		@Autowired
		private ObjectMapper objectMapper;

		@SneakyThrows
		@Override
		public FileDetails convert(String source) {
			return objectMapper.readValue(source, FileDetails.class);
		}
	}

}
