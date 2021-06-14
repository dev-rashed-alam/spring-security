package com.example.demo.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.security.AppUserRole.*;

@Repository("fake")
public class FakeAppUserDaoService implements AppUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeAppUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AppUser> selectApplicationUserByUserName(String username) {
        return getApplicationUsers().stream().filter(appUser -> username.equals(appUser.getUsername())).findFirst();
    }

    private List<AppUser> getApplicationUsers() {
        return Lists.newArrayList(
                new AppUser("Student", passwordEncoder.encode("password"), STUDENT.getGrantedAuthorities(), true, true, true, true),
                new AppUser("Admin", passwordEncoder.encode("password"), ADMIN.getGrantedAuthorities(), true, true, true, true),
                new AppUser("Trainee", passwordEncoder.encode("password"), ADMINTRAINEE.getGrantedAuthorities(), true, true, true, true)
        );
    }
}
