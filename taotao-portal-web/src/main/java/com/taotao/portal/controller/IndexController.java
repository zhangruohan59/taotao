package com.taotao.portal.controller;

import com.taotao.pojo.ItemCatResult;
import com.taotao.service.ItemCatService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/itemCat/all")
    @ResponseBody
    public String showIndexCat(){
        ItemCatResult result = itemCatService.getItemCats();
        String json = JsonUtils.objectToJson(result);
        return json;
    }
}
