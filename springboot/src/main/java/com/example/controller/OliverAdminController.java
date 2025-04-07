package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.model.dto.OliverAdminRequestDto;
import com.example.model.entity.OliverAdmin;
import com.example.service.IOliverAdminService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author OliverKim
 * @since 2025-04-07
 */

@RestController
public class OliverAdminController {
    
    @Resource
    private IOliverAdminService oliverAdminService;

    /**
     * 在restful标准中get请求就已经表示获取数据了，put请求表示更新数据了
     * post表示新增，delete表示删除
     * url中的复数就表示批量了
     * ResponseEntity是spring web自带的携带状态码的返回对象
     */
    @GetMapping("admin/{id}")
    public ResponseEntity<OliverAdmin> getOliverAdminById(@PathVariable("id") Integer id) {
        /**
         * 这里的id是restful风格的url参数
         * @PathVariable 标记的是url中的参数
         * mp自带了根据id查询的功能
         */
        OliverAdmin admin = oliverAdminService.getById(id);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/admins/query")
    /**
    * 但是有的时候需要传输数组，get请求不能传递数组所以用post，post请求的query要在url上体现
     */
    public ResponseEntity<List<OliverAdmin>> getAdmins(String username, String name, List<Integer> ids) {
        /**
         * 用了mp后，即可用service + lambda的方式直接生成单表sql语句，需要join的地方才要写xml
         */
        LambdaQueryWrapper<OliverAdmin> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(username)) {
            // type safety
            wrapper.like(OliverAdmin::getUsername, username);
        }
        if (StrUtil.isNotBlank(name)) {
            wrapper.like(OliverAdmin::getName, name);
        }
        if (ids != null && !ids.isEmpty()) {
            wrapper.in(OliverAdmin::getId, ids);
        }
        List<OliverAdmin> admins = oliverAdminService.list(wrapper);
        return ResponseEntity.ok(admins);
    }
    
    @PutMapping("/admin/{id}")
    public ResponseEntity<OliverAdmin> updateOliverAdminById(@PathVariable Integer id, @RequestBody OliverAdminRequestDto dto) {
        /**
         * 这里的ResponseEntity标准的404返回到前端去
         */
        OliverAdmin admin = oliverAdminService.getById(id);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        // 更新admin对象
        admin.setUsername(dto.getUsername());
        admin.setName(dto.getName());
        admin.setPhone(dto.getPhone());
        admin.setEmail(dto.getEmail());
        admin.setRole(dto.getRole());
        admin.setAvatar(dto.getAvatar());
        
        // 更新数据库，依然不写sql
        oliverAdminService.updateById(admin);
        
        return ResponseEntity.ok(admin);
    }

    /**
     * 前面说过了，post一般表示添加，单数表示添加单个
     * 这里是最需要用到dto的，因为dto不包含id，不然如果传过来一个id可能会变成更新，也可能会影响id的自增行为
     * @Validated 表示开启参数校验，具体的校验规则在dto中写，代码里不用写一点校验
     */
    @PostMapping("/admin")
    public ResponseEntity<OliverAdmin> addOliverAdmin(@RequestBody @Validated OliverAdminRequestDto dto) {
        // 用builder创建一个对象
        OliverAdmin admin = OliverAdmin.builder()
                .username(dto.getUsername())
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .role(dto.getRole())
                .avatar(dto.getAvatar())
                .build();
        
        // 这里的id是自增的，mp会自动处理,save是保存单个对象，对于批量的用saveBatch
        // delete也是一样的，deleteById, deleteBatch，用mp根本不用写单表sql
        oliverAdminService.save(admin);
        return ResponseEntity.ok(admin);
    }
    
    
    
}
