package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.buyer.dao.BuyerDAO;
import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.common.exception.PKNotFoundException;
import kr.or.ddit.common.paging.PaginationInfo;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.BuyerVO;

public class BuyerServiceImpl implements BuyerService {
	private BuyerDAO dao = new BuyerDAOImpl();
	
	@Override
	public BuyerVO retrieveBuyer(String buyerId) {
		BuyerVO vo =  dao.selectBuyer(buyerId);
		if(vo == null) {
			throw new PKNotFoundException(buyerId);
		}
		return vo;
	}

	@Override
	public List<BuyerVO> retrieveBuyerList(PaginationInfo paging) {
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		return dao.selectBuyerList(paging);
	}
	
	@Override
	public ServiceResult createBuyer(BuyerVO buyer) {
		ServiceResult result = null;
		if(dao.insertBuyer(buyer) > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}
	
	@Override
	public ServiceResult modifyBuyer(BuyerVO buyer) {
		ServiceResult result = null;
		if(dao.updateBuyer(buyer) > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}
}
