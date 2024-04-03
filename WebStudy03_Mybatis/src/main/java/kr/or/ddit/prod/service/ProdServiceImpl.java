 package kr.or.ddit.prod.service;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;

import kr.or.ddit.common.exception.PKNotFoundException;
import kr.or.ddit.common.paging.PaginationInfo;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements ProdService {
	private ProdDAO dao = new ProdDAOImpl();
	
	@Override
	public ServiceResult createProd(ProdVO prod) {
		ServiceResult result = null;
		if(dao.insertProd(prod) > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public List<ProdVO> retrieveProdList(PaginationInfo  paging) {
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		return dao.selectProdList(paging);
	}

	@Override
	public ProdVO retrieveProd(String prodId) {
		ProdVO prod = dao.selectProd(prodId);
		if(prod == null) {
			throw new PKNotFoundException(String.format("%s에 해당하는 상품 없음", prodId));
		}
		return dao.selectProd(prodId);
	}

	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		ServiceResult result = null;
		if(dao.updateProd(prod) > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

}
