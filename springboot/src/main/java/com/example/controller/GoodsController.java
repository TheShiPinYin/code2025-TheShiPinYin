package com.example.controller;

import com.example.common.Result;
import com.example.entity.Category;
import com.example.entity.Goods;
import com.example.service.GoodsService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @PostMapping("/add")
    public Result add(@RequestBody Goods goods) {
        goodsService.add(goods);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Goods goods) {
        goodsService.update(goods);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable Integer id) {
        goodsService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Goods> goodsList) {
        goodsService.deleteBatch(goodsList);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Category category) {
        PageInfo<Goods> info = goodsService.selectPage(pageNum, pageSize, category);
        return Result.success(info);
    }

    // 导出接口，需要根据实际情况实现
    @GetMapping("/export")
    public void export(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String ids,
                       @RequestParam(required = false) String token) {
        // 实现导出逻辑
    }

    // 导入接口，需要根据实际情况实现
    @PostMapping("/import")
    public Result importData() {
        // 实现导入逻辑
        return Result.success();
    }
}