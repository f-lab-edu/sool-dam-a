package com.flab.sooldama.domain.user.dao;

import com.flab.sooldama.domain.user.domain.User;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper // Mybatis 매핑 xml 에 기재된 sql 을 호출하기 위한 인터페이스임을 의미합니다. 인터페이스를 매퍼로 등록합니다.
public interface UserMapper {

    void insertUser(User user);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String email);

    void deleteAllUsers();
}
