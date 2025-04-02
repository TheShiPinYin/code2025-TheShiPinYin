package com.example.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Orders;
import com.example.service.OrdersService;
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
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private OrdersService ordersService;

    @PostMapping("/add")
    public Result add(@RequestBody Orders orders) {
        ordersService.add(orders);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Orders orders) {
        ordersService.update(orders);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable Integer id) {
        ordersService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Orders> list) {
        ordersService.deleteBatch(list);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(Orders orders) {
        List<Orders> list = ordersService.selectAll(orders);
        return Result.success(list);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Orders orders) {
        PageInfo<Orders> info = ordersService.selectPage(pageNum, pageSize, orders);
        return Result.success(info);
    }

    @GetMapping("/export")
    public void exportData(Orders orders, HttpServletResponse response) throws Exception {
        String ids = orders.getIds();
        if (StrUtil.isNotBlank(ids)) {
            String[] idArr = ids.split(",");
            orders.setIdsarr(idArr);
        }
        List<Orders> list = ordersService.selectAll(orders);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("orderNo", "订单编号");
        writer.addHeaderAlias("name", "商品名称");
        writer.addHeaderAlias("count", "商品数量");
        writer.addHeaderAlias("total", "订单总价");
        writer.addHeaderAlias("supplierName", "供货商");
        writer.addHeaderAlias("time", "订单时间");
        writer.setOnlyAlias(true);
        writer.write(list);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("订单信息", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream os = response.getOutputStream();
        writer.flush(os);
        writer.close();
        os.close();
    }

    @PostMapping("/import")
    public Result importData(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("订单编号", "orderNo");
        reader.addHeaderAlias("商品名称", "name");
        reader.addHeaderAlias("商品数量", "count");
        reader.addHeaderAlias("订单总价", "total");
        reader.addHeaderAlias("供货商", "supplierName");
        reader.addHeaderAlias("订单时间", "time");
        List<Orders> list = reader.readAll(Orders.class);
        for (Orders orders : list) {
            ordersService.add(orders);
        }
        return Result.success();
    }
}