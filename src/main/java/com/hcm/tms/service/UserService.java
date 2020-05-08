package com.hcm.tms.service;

import com.hcm.tms.entity.Role;
import com.hcm.tms.entity.User;

import com.hcm.tms.entity.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.*;

import com.hcm.tms.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserService {

    void delete(String id);

    void addUser(User user);



    Optional <User> getUserById(String id);

    Optional <User> findUserByUsername(String username);

    Optional<User> findUserByUserIdAndUsername(String id,String username);

    Optional <User> findUserByEmail(String email);

    Optional<User> findUserByUserIdAndEmail(String id,String email);

    Optional <User> findUserByTel(String telephone);

    Optional<User> findUserByUserIdAndTelephone(String id,String telephone);

    void deleteTrainer(LocalDate date, String user_id);

    List<User> getAllFreshers();

   public ResponseEntity<User> saveFresher(User user);

   public List<User> findAll();


    Page<User> getFreshersAndPaging(String username, Integer pageNo);

    List<Integer> getPages(Page<User> pages);


    List<User> findAllTrainer();

//    ResponseEntity<User> insertFresher(User user);

    List<User> findAllByRoleName(String roleName);


}