package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entity.Electronics;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ElectronicsService {

	@CachePut(value = "electronics", key = "#result.id")
	Electronics saveOrUpdate(Electronics electronics);

	Page<Electronics> getAll(Pageable pageable, Short rating, String discountedPrice);

	@Cacheable(value = "electronics", key = "#id")
	Electronics get(String id);

	@CacheEvict(value = "electronics",key = "#id", allEntries = true)
	Electronics delete(String id);
}
