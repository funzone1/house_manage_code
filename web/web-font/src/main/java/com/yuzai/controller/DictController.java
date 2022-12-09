package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuzai.entity.Dict;
import com.yuzai.result.Result;
import com.yuzai.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable String dictCode){
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }

    @RequestMapping("/findListByParentId/{id}")
    public Result findListByParentId(@PathVariable Long id){
        List<Dict> listByParentId = dictService.findListByParentId(id);
        return Result.ok(listByParentId);
    }
}
