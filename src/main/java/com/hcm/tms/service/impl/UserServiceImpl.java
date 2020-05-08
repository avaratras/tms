package com.hcm.tms.service.impl;

import com.hcm.tms.dto.CustomUsersDetails;
import com.hcm.tms.entity.Role;
import com.hcm.tms.entity.User;
import com.hcm.tms.repository.UserRepository;

import com.hcm.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void deleteTrainer(LocalDate date, String user_id) {
        userRepository.deleteTrainer(date, user_id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);


        optionalUser.orElseThrow(() -> new UsernameNotFoundException("user not found" + username));

        CustomUsersDetails customUsersDetails = optionalUser.map(user -> {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for(Role role : user.getRoleList()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
            }
            return new CustomUsersDetails(user,authorities);
        }).get();
        return customUsersDetails;
    }

    @Override
    public void delete( String id) {
        Optional<User> optionalUser = userRepository.findById(id);


        optionalUser.ifPresent(user -> {
            user.setEnable(false);
            user.setModifiedDate(LocalDateTime.now());
            userRepository.save(user);
        });
    }
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }



    @Override
    public List<User> getAllFreshers() {
        return userRepository.getAllFreshers();
    }


    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional <User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUserByUserIdAndUsername(String id, String username) {
        return userRepository.findUserByUserIdAndUsername(id, username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserByUserIdAndEmail(String id, String email) {
        return userRepository.findUserByUserIdAndEmail(id, email);
    }

    @Override
    public Optional<User> findUserByTel(String telephone) {
        return userRepository.findUserByTel(telephone);
    }

    @Override
    public Optional<User> findUserByUserIdAndTelephone(String id, String telephone) {
        return userRepository.findUserByUserIdAndTelephone(id, telephone);
    }

    @Override
    public Page<User> getFreshersAndPaging(String username, Integer pageNo) {

        int pageSize = 4;

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc("user_id")));

        Page<User> users =userRepository.getFreshersAndPaging(username, paging);
        return users;
    }

    @Override
    public List<Integer> getPages(Page<User> pages) {
        Long totalPages = Math.round((double)pages.getTotalPages()) ;
        List<Integer> listPageNumbers = new ArrayList<>();

        if(totalPages > 0) {
            listPageNumbers = IntStream.rangeClosed(1, Math.toIntExact(totalPages)).boxed().collect(Collectors.toList());
        }
        return listPageNumbers;
    }

    @Override
    public List<User> findAllTrainer() {
        return userRepository.findAllTrainer();
    }

    @Override
    public List<User> findAllByRoleName(String roleName) {
        return userRepository.findAllByRoleName(roleName);
    }


    /*@Override
    public List<User> findAllFresher() {
        return userRepository.findAllFresher();
    }*/

    @Override
    public ResponseEntity<User> saveFresher(User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }



}
