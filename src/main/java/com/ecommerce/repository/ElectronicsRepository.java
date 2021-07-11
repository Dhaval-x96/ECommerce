package com.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Electronics;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ElectronicsRepository extends PagingAndSortingRepository<Electronics, String>{
    Electronics findByTitle(String title);

    @Query("select createdDate from Electronics where id=:id")
    LocalDateTime findCreatedDate(String id);

    @Query("select e from Electronics e where concat(e.title,e.description,e.discountedPrice,e.finalPrice,e.noOfPeopleRates,e.originalPrice,e.rating) like %?1%")
    List<Electronics> search(String value);

    Page<Electronics> findAllByRatingIsGreaterThanEqual(Pageable pageable, Short rating);

    @Query(value = "select * from electronics e where replace(e.discounted_price, '% off','') > ?1", nativeQuery = true)
    Page<Electronics> getDiscountedGreaterThan(Pageable pageable,String discount);

}
