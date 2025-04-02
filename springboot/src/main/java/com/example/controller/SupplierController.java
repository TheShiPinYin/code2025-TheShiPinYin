package com.example.controller;

import com.example.common.Result;
import com.example.entity.Supplier;
import com.example.service.SupplierService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Resource
    private SupplierService supplierService;

    @PostMapping("/add")
    public Result add(@RequestBody Supplier supplier) {
        supplierService.add(supplier);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Supplier supplier) {
        supplierService.update(supplier);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result delete(@PathVariable Integer id) {
        supplierService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(Supplier supplier) {
        List<Supplier> list = supplierService.selectAll(supplier);
        return Result.success(list);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Supplier supplier) {
        PageInfo<Supplier> info = supplierService.selectPage(pageNum, pageSize, supplier);
        return Result.success(info);
    }
}
