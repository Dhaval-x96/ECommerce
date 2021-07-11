package com.ecommerce.services.impl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ecommerce.exceptions.DataNotFoundException;
import com.ecommerce.exceptions.ObjectIsNullException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.entity.Electronics;
import com.ecommerce.repository.ElectronicsRepository;
import com.ecommerce.services.ElectronicsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ElectronicsServiceImpl implements ElectronicsService {

	@Autowired
	private ElectronicsRepository electronicsRepository;

	@Override
	public Page<Electronics> getAll(Pageable pageable, Short rating, String discountedPrice){
		if(rating !=null) {
			return this.electronicsRepository.findAllByRatingIsGreaterThanEqual(pageable, rating);
		} else if(discountedPrice !=null){
			return this.electronicsRepository.getDiscountedGreaterThan(pageable, discountedPrice);
		} else {
			return this.electronicsRepository.findAll(pageable);
		}

	}

	@Override
	public Electronics get(String id){

		if (id != null) {
			Optional<Electronics> electronics = this.electronicsRepository.findById(id);
			if(electronics.isPresent()){
				log.info("{} is the data for id {}",electronics, id );
				return electronics.get();
			}else {
				log.info("{} id data is not available.", id);
				throw new DataNotFoundException("400",id +" data is not available in the table.");
			}
		}

		log.info("please provide valid id or non-empty id.");
		return null;
	}

	@Override
	public Electronics delete(String id) {
		if(id!=null){
			Optional<Electronics> optionalElectronics=  this.electronicsRepository.findById(id);

			if(!optionalElectronics.isPresent()){
				throw new DataNotFoundException("400", "Please provide proper valid unique identifier.");
			}

			Electronics electronics = optionalElectronics.get();
			optionalElectronics.ifPresent(e-> this.electronicsRepository.delete(e));
			return electronics != null ? electronics : null;
		}else {
			throw new DataNotFoundException("400", "Please provide unique identifier.");
		}

	}

	@Override
	public Electronics saveOrUpdate(Electronics electronics) {
		if (electronics != null) {
			if(electronics.getId() ==null){
				/*SAVE*/
				Electronics response= this.electronicsRepository.save(electronics);
				log.info("{} is saved", electronics);
				return response;
			}else {
				/*UPDATE*/
				Optional<Electronics> tmpElectronics = this.electronicsRepository.findById(electronics.getId());
//				LocalDateTime createdDate = //this.electronicsRepository.findCreatedDate(electronics.getId());
				if(!tmpElectronics.isPresent()){
					/* Id is not valid*/
					throw new DataNotFoundException("400", "Please enter valid id");
				}

				electronics.createdDateAndUpdatedDate(tmpElectronics.get().getCreatedDate(),LocalDateTime.now(Clock.systemUTC()));

				electronics.setFileDetails(tmpElectronics.get().getFileDetails());
				Electronics response= this.electronicsRepository.save(electronics);
				log.info("{} is saved", electronics);
				return response;
			}
		} else {
			log.warn("the electronics object is null or id is not provided");
			if(electronics==null){
				throw new ObjectIsNullException();
			} else {
				throw new DataNotFoundException("400", "Please provide proper id.");
			}
		}
	}
}
