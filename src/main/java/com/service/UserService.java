package com.service;

import com.entity.Account;
import com.entity.Role;
import com.entity.User;
import com.repository.AccountRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    public boolean topUpAccount(String userName, Integer amount) {
        User userFromDB = userRepository.findByUsername(userName);
        if (userFromDB == null) return false;
        Account account = userFromDB.getAccount();
        if (account.getBlocked()) return false;
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        return true;
    }

    public boolean withdrawFromAccount(String userName, Integer amount) {
        User userFromDB = userRepository.findByUsername(userName);
        if (userFromDB == null) return false;
        Account account = userFromDB.getAccount();
        if (account.getBlocked() || account.getBalance() < amount) return false;
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        return true;
    }

    public Account getAccountByUserName(String userName) {
        User userFromDB = userRepository.findByUsername(userName);
        if (userFromDB == null) return null;
        return userFromDB.getAccount();
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) return false;
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAccount(accountRepository.save(new Account(0, false)));
        userRepository.save(user);
        return true;
    }

    public void blockUser(String userName) {
        User userFromDB = userRepository.findByUsername(userName);
        if (userFromDB == null) return;
        Account account = userFromDB.getAccount();
        account.setBlocked(true);
        accountRepository.save(account);
    }

    public void unblockUser(String userName) {
        User userFromDB = userRepository.findByUsername(userName);
        if (userFromDB == null) return;
        Account account = userFromDB.getAccount();
        account.setBlocked(false);
        accountRepository.save(account);
    }
}
