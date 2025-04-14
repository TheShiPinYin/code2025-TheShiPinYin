package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.model.dto.LoginDto;
import com.example.model.dto.UserDto;
import com.example.model.dto.UserRequestDto;
import com.example.model.entity.User;
import com.example.service.impl.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    
    @Resource
    private UserService userService;
    
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        String token = userService.login(dto.getUsername(), dto.getPassword());
        if (token == null) {
            return ResponseEntity.badRequest().body("登录失败");
        }
        return ResponseEntity.ok(token);
    }
    
    @PostMapping("users")
    public ResponseEntity<?> getUsers(@RequestBody UserRequestDto dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (!dto.getIds().isEmpty()) {
            wrapper.in(User::getId, dto.getIds());
        }
        if (dto.getRole() != null) {
            wrapper.eq(User::getRole, dto.getRole());
        }
        List<User> users = userService.list(wrapper);
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("user")
    public ResponseEntity<?> addUser(@RequestBody @Validated UserDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .role(dto.getRole())
                .avatar(dto.getAvatar())
                .build();
        boolean saved = userService.save(user);
        if (!saved) {
            return ResponseEntity.internalServerError().body("");
        }
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("users")
    public ResponseEntity<?> deleteUsers(@RequestBody List<Integer> ids) {
        boolean removed = userService.removeBatchByIds(ids);
        if (!removed) {
            return ResponseEntity.internalServerError().body("");
        }
        return ResponseEntity.ok("");
    }
    
    
}
















//import cn.hutool.core.util.StrUtil;
//import cn.hutool.poi.excel.ExcelReader;
//import cn.hutool.poi.excel.ExcelUtil;
//import cn.hutool.poi.excel.ExcelWriter;
//import com.example.common.Result;
//import com.example.model.entity.User;
//import com.example.service.UserService;
//import com.github.pagehelper.PageInfo;
//import jakarta.annotation.Resource;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.InputStream;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Resource
//    UserService userService;
//
//    @PostMapping("/add")
//    public Result add(@RequestBody User user) {  // @RequestBody 接收前端传来的 json参数
//        userService.add(user);
//        return Result.success();
//    }
//
//    @PutMapping("/update")
//    public Result update(@RequestBody User user) {  // @RequestBody 接收前端传来的 json参数
//        userService.update(user);
//        return Result.success();
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public Result delete(@PathVariable Integer id) {  // @PathVariable 接收前端传来的路径参数
//        userService.deleteById(id);
//        return Result.success();
//    }
//
//    @DeleteMapping("/deleteBatch")
//    public Result deleteBatch(@RequestBody List<User> list) {  //  @RequestBody 接收前端传来的 json数组
//        userService.deleteBatch(list);
//        return Result.success();
//    }
//
//    @GetMapping("/selectAll")  //   完整的请求路径：http://ip:port/user/selectAll
//    public Result selectAll(User user) {
//        List<User> userList = userService.selectAll(user);
//        return Result.success(userList);
//    }
//
//    /**
//     * 分页查询
//     * pageNum: 当前的页码
//     * pageSize：每页的个数
//     */
//    @GetMapping("/selectPage")
//    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
//                             @RequestParam(defaultValue = "10") Integer pageSize,
//                             User user) {
//        PageInfo<User> pageInfo = userService.selectPage(pageNum, pageSize, user);
//        return Result.success(pageInfo);  // 返回的是分页的对象
//    }
//
//    /**
//     * 数据导出
//     * ids: 1,2,3
//     */
//    @GetMapping("/export")
//    public void exportData(User user, HttpServletResponse response) throws Exception {
//        String ids = user.getIds();
//        if (StrUtil.isNotBlank(ids)) {
//            String[] idArr = ids.split(",");
//            user.setIdsarr(idArr);
//        }
//        // 1. 拿到所有数据
//        List<User> list = userService.selectAll(user);
//        // 2. 构建Writer对象
//        ExcelWriter writer = ExcelUtil.getWriter(true);
//        // 3. 设置中文表头
//        writer.addHeaderAlias("username", "账号");
//        writer.addHeaderAlias("name", "名称");
//        writer.addHeaderAlias("phone", "电话");
//        writer.addHeaderAlias("email", "邮箱");
//        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
//        writer.setOnlyAlias(true);
//        // 4. 写出数据到writer
//        writer.write(list);
//        // 5. 设置输出的文件的名称以及输出流的头信息
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        String fileName = URLEncoder.encode("管理员信息", StandardCharsets.UTF_8);
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
//        // 6. 写出到输出流 并关闭 writer
//        ServletOutputStream os = response.getOutputStream();
//        writer.flush(os);
//        writer.close();
//        os.close();
//    }
//
//    /**
//     * 批量导入
//     */
//    @PostMapping("/import")
//    public Result importData(MultipartFile file) throws Exception {
//        //  1. 拿到输入流 构建 reader
//        InputStream inputStream = file.getInputStream();
//        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        //  2. 通过Reader 读取 excel 里面的数据
//        reader.addHeaderAlias("账号", "username");
//        reader.addHeaderAlias("名称", "name");
//        reader.addHeaderAlias("电话", "phone");
//        reader.addHeaderAlias("邮箱", "email");
//        List<User> list = reader.readAll(User.class);
//        // 3. 将数据写到数据库
//        for (User user : list) {
//            userService.add(user);
//        }
//        return Result.success();
//    }
//
//}