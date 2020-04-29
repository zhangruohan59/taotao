package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.ItemCat;
import com.taotao.pojo.ItemCatResult;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.ZtreeResult;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<ZtreeResult> getZtreeResult(Long id) {
        List<TbItemCat> tbItemCats = tbItemCatMapper.findTbItemCatByParentId(id);
        List<ZtreeResult> results = new ArrayList<ZtreeResult>();
        for (TbItemCat tbItemCat: tbItemCats){
            ZtreeResult result = new ZtreeResult();
            result.setId(tbItemCat.getId());
            result.setName(tbItemCat.getName());
            result.setIsParent(tbItemCat.getIsParent());
            results.add(result);
        }
        return results;
    }

    @Override
    public ItemCatResult getItemCats() {
        ItemCatResult itemCatResult= new ItemCatResult();
        itemCatResult.setData(getItemCatList(0L));
        return itemCatResult;
    }
    private List<?> getItemCatList(Long parentId){
        int count=0;
        List list = new ArrayList();
        List<TbItemCat> tbItemCats =tbItemCatMapper.findTbItemCatByParentId(parentId);
        for(TbItemCat itemCat : tbItemCats){
            ItemCat item = new ItemCat();
            //判断是父级目录还是最底级目录
            if(itemCat.getIsParent()){
                item.setUrl("/products/"+itemCat.getId()+".html");
                if(itemCat.getParentId()==0){
                    //一定是一级目录
                    item.setName("<a href='/products/"+itemCat.getId()+".html' >"+itemCat.getName()+"</a>");
                }else{
                  //一定是二级目录
                    item.setName(itemCat.getName());
                }
                    item.setItem(getItemCatList(itemCat.getId()));
                    list.add(item);
                    count++;
                    if(parentId ==0&& count>=14){
                        break;
                    }
            }else{
                //一定是最底级目录（三级目录
                list.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
            }
        }
        return list;
    }
}
