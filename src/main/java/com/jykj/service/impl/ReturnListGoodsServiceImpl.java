package com.jykj.service.impl;

import com.jykj.dao.ReturnListGoodsMapper;
import com.jykj.entity.ReturnListGoods;
import com.jykj.service.ReturnListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("returnListGoodsService")
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService {

    @Resource
    private ReturnListGoodsMapper returnListGoodsMapper;

    @Override
    public List<ReturnListGoods> listByReturnListId(Integer returnListId) {
        return returnListGoodsMapper.listByReturnListId(returnListId);
    }

    @Override
    public List<ReturnListGoods> list(ReturnListGoods returnListGoods) {
        return returnListGoodsMapper.list(returnListGoods);
    }
}
