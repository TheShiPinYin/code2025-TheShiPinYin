package com.example.mapper;

import com.example.entity.Category;
import com.example.entity.Goods;

import java.util.List;

public interface GoodsMapper {
    int insert(Goods goods);
// 你混着用的是吧
    // 你的取名也有大问题，回混淆我也被混淆了
    List<Goods> selectAll();

    List<Goods> selectPage();

    void updateById(Goods goods);

    int deleteById(Integer id);
}