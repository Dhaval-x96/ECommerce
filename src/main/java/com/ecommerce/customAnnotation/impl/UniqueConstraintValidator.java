package com.ecommerce.customAnnotation.impl;

import com.ecommerce.customAnnotation.UniqueField;
import com.ecommerce.repository.ElectronicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueConstraintValidator implements ConstraintValidator<UniqueField,String> {

    @Autowired
    private ElectronicsRepository electronicsRepository;

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
       return this.electronicsRepository.findByTitle(title) == null;
    }
}