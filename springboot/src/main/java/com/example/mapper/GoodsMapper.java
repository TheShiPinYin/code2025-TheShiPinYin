package com.example.mapper;

import com.example.entity.Category;
import com.example.entity.Goods;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface GoodsMapper {
    @Insert("insert into goods (name, price, img, supplier_id, category_id, count) values (#{name}, #{price}, #{img}, #{supplierId}, #{categoryId}, #{count})")
    void insert(Goods goods);

    List<Goods> selectAll(Category category);

    void updateById(Goods goods);

    @Delete("delete from goods where id = #{id}")
    void deleteById(Integer id);
}