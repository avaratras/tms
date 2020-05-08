package com.hcm.tms.validation;

import com.hcm.tms.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @Autowired
    private ApplicationContext applicationContext;

    private FieldValueExists service;
    private String fieldName;

    @Override
    public void initialize(Unique constraintAnnotation) {

        Class<? extends FieldValueExists> clazz = constraintAnnotation.service();
        this.fieldName = constraintAnnotation.fieldName();
        String serviceQualifier = constraintAnnotation.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(serviceQualifier, clazz);
        } else {
            try {
                this.service = this.applicationContext.getBean(clazz);
            }catch (Exception e) {
                System.out.println("error at initialize:: " + e.toString());
            }
            //FieldValueExists a =  this.applicationContext.getBean(clazz);
            //this.service = this.applicationContext.getBean(clazz);
        }
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try{
            this.service.fieldValueExists(value, this.fieldName);
        } catch (Exception e) {
            System.out.println("error at isValid: " + e.toString());
        }
        if(this.service.fieldValueExists(value, this.fieldName)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fieldName).addConstraintViolation();
        }
        return !this.service.fieldValueExists(value, this.fieldName);
    }
}