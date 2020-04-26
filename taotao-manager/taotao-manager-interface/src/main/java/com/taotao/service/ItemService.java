package com.taotao.service;

import com.taotao.pojo.*;
import java.util.Date;
import java.util.List;

//商品服务
public interface ItemService {
    TbItem findTbItemById(long itemId);

    LayuiResult findTbItemByPage(int page,int limit);

    TaotaoResult updateItem(List<TbItem> tbItem, int type, Date date);

    LayuiResult getLikeItem(Integer page, Integer limit, String title, Integer priceMin, Integer priceMax, Long cid);

    PictureResult addPicture(String fileName, byte[] bytes);

    /**
     * 添加商品信息
     * @param tbItem
     * @param itemDesc
     * @return
     */
    TaotaoResult addItem(TbItem tbItem, String itemDesc);
}