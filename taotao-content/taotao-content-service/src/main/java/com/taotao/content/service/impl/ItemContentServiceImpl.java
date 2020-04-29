package com.taotao.content.service.impl;

import com.taotao.content.service.ItemContentService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.LayuiResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.ZtreeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemContentServiceImpl implements ItemContentService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    private TbContentMapper tbContentMapper;


    @Override
    public List<ZtreeResult> getZtreeResult(Long id) {
        List<ZtreeResult> results = new ArrayList<ZtreeResult>();
        List<TbContentCategory> tbContentCategories= tbContentCategoryMapper.findTbContentByParentId(id);
        for(TbContentCategory tbContentCategory : tbContentCategories){
            ZtreeResult ztreeResult = new ZtreeResult();
            ztreeResult.setId(tbContentCategory.getId());
            ztreeResult.setName(tbContentCategory.getName());
            ztreeResult.setIsParent(tbContentCategory.getIsParent());
            results.add(ztreeResult);
        }
        return results;
    }

    @Override
    public LayuiResult findContentByCategoryId(Long categoryId, Integer page, Integer limit) {
        LayuiResult result = new LayuiResult();
        result.setCount(0);
        result.setMsg("");
        int count = tbContentMapper.findTbContentByCount(categoryId);
        result.setCount(count);
        List<TbContent> data =tbContentMapper.findTbContentByPage(categoryId,(page-1)*limit,limit);
        result.setData(data);
        return result;
    }

    @Override
    public LayuiResult deleteContentByCategoryId(List<TbContent> tbContents, Integer page, Integer limit) {
        LayuiResult result = new LayuiResult();
        result.setCount(0);
        result.setMsg("");
        if(tbContents.size()<=0){
            return result;
        }
        int i = tbContentMapper.deleteContentByCategoryId(tbContents);
        if(i<=0){
            return result;
        }
        //2.查询之后返回展示
        Long categoryId = tbContents.get(0).getCategoryId(); //内容类型
        int count = tbContentMapper.findTbContentByCount(categoryId);
        if(count<=0){
            return result;
        }
        result.setCount(count);
        List<TbContent> data =tbContentMapper.findTbContentByPage(categoryId,(page-1)*limit,limit);
        result.setData(data);
        return result;
    }

    @Override
    public LayuiResult addContent(TbContent tbContent, Integer page, Integer limit) {
        //生成一个当前时间作为创建时间
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        //内容的基本信息准备完毕
        LayuiResult result = new LayuiResult();
        result.setCount(0);
        result.setMsg("");
        tbContentMapper.addContent(tbContent);
        Long categoryId = tbContent.getCategoryId();
        int count = tbContentMapper.findTbContentByCount(categoryId);
        if(count<=0){
            return result;
        }
        result.setCount(count);
        List<TbContent> data =tbContentMapper.findTbContentByPage(categoryId,(page-1)*limit,limit);
        result.setData(data);
        return result;
    }
}
