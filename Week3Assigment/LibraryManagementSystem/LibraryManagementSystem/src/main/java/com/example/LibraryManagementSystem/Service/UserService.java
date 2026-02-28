package com.example.LibraryManagementSystem.Service;

import com.example.LibraryManagementSystem.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    List<User> users = new ArrayList<>();
    public void registerUser(User user) {
        users.add(user);
    }
    public boolean validateLogin(String email, String password){
        return  users.stream().anyMatch(u->u.getEmail().equals(email) && u.getPassword().equals(password));
    }
}
