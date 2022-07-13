package com.infosys.timd.bioskopapi.Repository;

import com.infosys.timd.bioskopapi.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
//    @Query("Select u.username from User u where u.username like %:username%")
//    public List<User> getUserNameLike (@Param("username")String name);

    //Custom query
    @Query(value = "select * from users u where u.username like %:keyword%" , nativeQuery = true)
    List<User> findByKeyword(@Param("keyword") String keyword);
}

