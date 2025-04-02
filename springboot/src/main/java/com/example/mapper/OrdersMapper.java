package com.example.mapper;

import com.example.entity.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface OrdersMapper {
    @Insert("insert into orders (order_no, supplier_id, name, count, total, time) values (#{orderNo}, #{supplierId}, #{name}, #{count}, #{total}, #{time})")
    void insert(Orders orders);

    List<Orders> selectAll(Orders orders);


    void updateById(Orders orders);

    @Delete("delete from orders where id = #{id}")
    void deleteById(Integer id);
}
