package com.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Category;
import com.example.entity.Goods;
import com.example.entity.User;
import com.example.service.GoodsService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    GoodsService goodsService;

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

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        goodsService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Goods> List) {
        goodsService.deleteBatch(List);
        return Result.success();
    }

    /*@GetMapping("/selectAll")
    public Result selectAll(Goods goods) {
        List<Goods> goodsList = goodsService.selectAll(goods);
        return Result.success(goodsList);
    }*/

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Category category) {
        PageInfo<Goods> pageInfo = goodsService.selectPage(pageNum, pageSize, category);
        return Result.success(pageInfo);
    }

    /**
     * 数据导出
     * ids: 1,2,3
     */
    @GetMapping("/export")
    public void exportData(Goods goods, HttpServletResponse response) throws Exception {
        String ids = goods.getIds();
        if (StrUtil.isNotBlank(ids)) {
            String[] idArr = ids.split(",");
            goods.setIdsarr(idArr);
        }
        // 1. 拿到所有数据
        List<Goods> list = goodsService.selectAll();
        // 2. 构建Writer对象
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 3. 设置中文表头
        writer.addHeaderAlias("name", "商品名称");
        writer.addHeaderAlias("price", "商品价格");
        writer.addHeaderAlias("categoryName", "商品分类"); // 怎么在写id？搞错了。。。
        writer.addHeaderAlias("supplierName", "供货商");
        writer.addHeaderAlias("count", "商品库存");
        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        writer.setOnlyAlias(true);
        // 4. 写出数据到writer
        writer.write(list);
        // 5. 设置输出的文件的名称以及输出流的头信息
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员信息", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        // 6. 写出到输出流 并关闭 writer
        ServletOutputStream os = response.getOutputStream();
        writer.flush(os);
        writer.close();
        os.close();
    }

    /**
     * 批量导入
     */
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws Exception {
        //  1. 拿到输入流 构建 reader
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //  2. 通过Reader 读取 excel 里面的数据
        reader.addHeaderAlias("name", "商品名称");
        reader.addHeaderAlias("price", "商品价格");
        reader.addHeaderAlias("categoryId", "商品分类");
        reader.addHeaderAlias("supplierId", "供货商");
        reader.addHeaderAlias("count", "商品库存");
        List<Goods> list = reader.readAll(Goods.class);
        // 3. 将数据写到数据库
        for (Goods goods : list) {
            goodsService.add(goods);
        }
        return Result.success();
    }
}