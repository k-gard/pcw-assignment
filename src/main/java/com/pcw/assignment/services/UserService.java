package com.pcw.assignment.services;

import com.pcw.assignment.models.User;
import com.pcw.assignment.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UsersRepository repository;

    public UserService(UsersRepository repository) {
        this.repository = repository;
    }

    public List<User> findAllusers(){

        return repository.findAll();
    }



    public User addUser(User user) {
        return repository.save(user);
    }

    public User getById(int id) {
        return repository.getById(id);
    }
}
