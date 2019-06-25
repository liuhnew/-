package com.jykj.service;

import java.util.List;

import com.jykj.entity.CustomerReturn;
import com.jykj.entity.CustomerReturnGoods;

/**
 * 客户退货单Service接口
 * @author Administrator
 *
 */
public interface CustomerReturnService {

	CustomerReturn findById(Integer id);

	String getTodayMaxCustomerReturnNumber();

	void save(CustomerReturn customerReturnList,List<CustomerReturnGoods> customerReturnListGoodsList);

	List<CustomerReturn> list(String customerReturnNumber,
								  Integer customerId,
								  Integer state,
								  String customerReturnDate,
								  Integer pageIndex,
								  Integer pageSize);

	 void delete(Integer id);

	 void update(CustomerReturn customerReturnList);


}
