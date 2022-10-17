package com.flab.sooldama.domain.product.dao;

import com.flab.sooldama.domain.product.domain.Product;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {

    List<Product> selectProducts(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Product> selectProductsByCategoryId(
            @Param("offset") Integer offset,
            @Param("limit") Integer limit,
            @Param("categoryId") Integer categoryId);
}
