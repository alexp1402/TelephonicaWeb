package org.callservice.service;

import org.callservice.entity.User;
import org.callservice.repository.RoleRepository;
import org.callservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//implements UserDetailsService
public class UserService  {
//    //        @PersistenceContext
////        private EntityManager em;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    RoleRepository roleRepository;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
////        User user = userRepository.findByUsername(username);
////
////        if (user == null) {
////            throw new UsernameNotFoundException("User not found");
////        }
////
////        return user;
//        return null;
//    }
//
//    public User findUserById(Long userId) {
////        Optional<User> userFromDb = userRepository.findById(userId);
////        return userFromDb.orElse(new User());
//        return null;
//    }
//
//    public List<User> allUsers() {
////        return userRepository.findAll();
//        return null;
//    }
//
//    public boolean saveUser(User user) {
////        User userFromDB = userRepository.findByUsername(user.getUsername());
//
////        if (userFromDB != null) {
////            return false;
////        }
//
////        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
////        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
////        userRepository.save(user);
//        return true;
//    }
//
//    public boolean deleteUser(Long userId) {
////        if (userRepository.findById(userId).isPresent()) {
////            userRepository.deleteById(userId);
////            return true;
////        }
//        return false;
//    }
//
//    public List<User> usergtList(Long idMin) {
////        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
////                .setParameter("paramId", idMin).getResultList();
//        return null;
//    }
}
