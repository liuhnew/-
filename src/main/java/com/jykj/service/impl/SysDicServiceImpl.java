package com.jykj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jykj.dao.SysDicMapper;
import com.jykj.entity.SysDic;
import com.jykj.service.SysDicService;
import com.jykj.util.StringUtil;
import com.jykj.util.TreeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sysDicService")
public class SysDicServiceImpl implements SysDicService {

    @Autowired
    private SysDicMapper sysDicMapper;

    @Override
    public void save(SysDic sysDic) {
        sysDicMapper.insertSelective(sysDic);
    }

    @Override
    public void delete(Integer id) {
        sysDicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(SysDic sysDic) {
        sysDicMapper.updateByPrimaryKeySelective(sysDic);
    }

    @Override
    public PageInfo<SysDic> list(String keyWord,
                                 Integer pageIndex,
                                 Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<SysDic> list = sysDicMapper.list(keyWord);
        PageInfo<SysDic> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public SysDic selectById(Integer id) {
        return sysDicMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TreeBean<SysDic>> tree() {
        List<SysDic> list = sysDicMapper.list(null);
        List<TreeBean<SysDic>> resultList = new ArrayList<TreeBean<SysDic>>();
        for (SysDic sysDic : list) {
            TreeBean<SysDic> treeBean = pass(sysDic);
            resultList.add(treeBean);
        }
        List<TreeBean<SysDic>> resultList1 = new ArrayList<TreeBean<SysDic>>();
        List<TreeBean<SysDic>> temp;
        for (TreeBean treeBean : resultList) {
            temp = new ArrayList<TreeBean<SysDic>>();
            for (TreeBean tb : resultList) {
                if (StringUtil.isNotEmpty(tb.getPid())&&tb.getPid().equals(treeBean.getId())){
                    temp.add(tb);
                    treeBean.setChildren(temp);
                }
            }
            if ("0".equals(treeBean.getPid())){
                resultList1.add(treeBean);
            }
        }
        return resultList1;
    }

    public TreeBean<SysDic> pass(SysDic sysDic) {
        TreeBean treeBean = new TreeBean();
        treeBean.setId("" + sysDic.getId());
        treeBean.setPid("" + sysDic.getParentId());
        treeBean.setData(sysDic);
        treeBean.setChildren(null);
        treeBean.setText(sysDic.getDicName());
        return treeBean;
    }
}
