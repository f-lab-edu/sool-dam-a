package com.flab.sooldama.domain.user.dao;

import com.flab.sooldama.domain.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertUser(User user);


}
