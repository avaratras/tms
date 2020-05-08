package com.hcm.tms.repository;

import com.hcm.tms.entity.Role;
import com.hcm.tms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User,String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE user u " +
                    "LEFT JOIN user_role ur ON u.user_id = ur.user_id " +
                    "LEFT JOIN role r ON ur.role_id = r.role_id " +
                    "SET u.is_enable = b'0', u.modified_date = ?1 " +
                    "WHERE u.user_id = ?2 AND r.role_name = 'TRAINER'", nativeQuery = true)
    void deleteTrainer(LocalDate date, String user_id);

    @Query(value = "SELECT * FROM user u " +
                    "LEFT JOIN city c ON c.city_id = u.city_id " +
                    "LEFT JOIN user_role ur ON u.user_id = ur.user_id " +
                    "LEFT JOIN role r  ON ur.role_id = r.role_id " +
                    "WHERE r.role_name = 'TRAINEE' AND u.is_enable = b'1' ", nativeQuery = true)
    List<User> getAllFreshers();

    @Query(value = "SELECT * FROM user u " +
            "LEFT JOIN city c ON c.city_id = u.city_id " +
            "LEFT JOIN user_role ur ON u.user_id = ur.user_id " +
            "LEFT JOIN role r  ON ur.role_id = r.role_id " +
            "WHERE r.role_name = 'TRAINEE' AND u.is_enable = b'1' AND u.username LIKE %?1%", nativeQuery = true)
    Page<User> getFreshersAndPaging(String username, Pageable paging);


    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.userId <> ?1 AND u.username=?2")
    Optional<User> findUserByUserIdAndUsername(String id,String username);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional <User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userId <> ?1 AND u.email=?2")
    Optional<User> findUserByUserIdAndEmail(String id,String email);

    @Query("SELECT u FROM User u WHERE u.telephone = ?1")
    Optional <User> findUserByTel(String telephone);

    @Query("SELECT u FROM User u WHERE u.userId <> ?1 AND u.telephone=?2")
    Optional<User> findUserByUserIdAndTelephone(String id,String telephone);

    @Query(value = "SELECT * FROM user u " +
            "LEFT JOIN city c ON c.city_id = u.city_id " +
            "LEFT JOIN user_role ur ON u.user_id = ur.user_id " +
            "LEFT JOIN role r  ON ur.role_id = r.role_id " +
            "WHERE r.role_name = 'TRAINER' AND u.is_enable = b'1' ", nativeQuery = true)
     List<User> findAllTrainer();

    List<User> findAll();


    @Query(value = "select * from tms.user join tms.user_role on user.user_id = user_role.user_id join tms.role on role.role_id = user_role.role_id where role.role_id = '3'",
            nativeQuery = true)
    List<User> findAllFresher();

    List<User> findAllById(Iterable<String> ids);

    @Query(value = "SELECT * FROM tms.user INNER JOIN tms.user_role ON user.user_id = user_role.user_id " +
            "INNER JOIN role ON role.role_id = user_role.role_id WHERE role.role_name = ?1 AND is_enable = b'1'",nativeQuery = true)
    List<User> findAllByRoleName(String roleName);
}