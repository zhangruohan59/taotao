package com.taotao.content.service;

import com.taotao.pojo.LayuiResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.ZtreeResult;

import java.util.List;

public interface ItemContentService {

    List<ZtreeResult> getZtreeResult(Long id);

    /**
     * 根据内容分类ID查询内容信息
     * @param categoryId  内容分类ID
     * @param page  当前页
     * @param limit  每一页显示的条数
     * @return  layui需要的格式
     */
    LayuiResult findContentByCategoryId(Long categoryId, Integer page, Integer limit);

    /**
     * 根据内容ID删除指定内容信息
     * @param tbContents  需要删除的集合对象
     * @param page  当前页
     * @param limit  每一页显示的条数
     * @return
     */
    LayuiResult deleteContentByCategoryId(List<TbContent> tbContents, Integer page, Integer limit);

    /**
     * 添加内容
     * @param tbContent 内容详情
     * @param page  当前页
     * @param limit  每一页显示的条数
     * @return
     */
    LayuiResult addContent(TbContent tbContent, Integer page, Integer limit);
}
