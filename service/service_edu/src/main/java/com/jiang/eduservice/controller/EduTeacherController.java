package com.jiang.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiang.commonutils.R;
import com.jiang.eduservice.entity.EduTeacher;
import com.jiang.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-04
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService service;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = service.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean flag = service.removeById(id);
        if(flag){
           return R.ok();
        }else{
            return R.error();
        }
    }
    @ApiOperation(value = "分页查询讲师信息")
    @GetMapping("queryTeacherForPage/{current}/{limit}")
    public R queryTeacherForPage(@PathVariable Long current,@PathVariable Long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        service.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

        return R.ok().data("total",total).data("rows",records);
    }

    //添加讲师接口的方法
    @ApiOperation(value = "添加讲师接口的方法")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = service.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

