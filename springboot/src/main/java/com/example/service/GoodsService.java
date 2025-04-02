package com.example.service;

import com.example.entity.Category;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    public void add(Goods goods) {
        goodsMapper.insert(goods);
    }

    public PageInfo<Goods> selectPage(Integer pageNum, Integer pageSize, Category category) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.selectAll(category);
        return PageInfo.of(list);
    }

    public void update(Goods goods) {
        goodsMapper.updateById(goods);
    }

    public void deleteById(Integer id) {
        goodsMapper.deleteById(id);
    }

    public void deleteBatch(List<Goods> goodsList) {
        for (Goods goods : goodsList) {
            goodsMapper.deleteById(goods.getId());
        }
    }
}