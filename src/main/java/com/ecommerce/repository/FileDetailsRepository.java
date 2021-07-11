package com.ecommerce.repository;

import com.ecommerce.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailsRepository extends JpaRepository<FileDetails,Long> {
}
