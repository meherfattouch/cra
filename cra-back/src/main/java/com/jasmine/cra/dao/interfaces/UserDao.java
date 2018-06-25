package com.jasmine.cra.dao.interfaces;

import com.jasmine.cra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {


    public User findById(int id);


}
