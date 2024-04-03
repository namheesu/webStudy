package kr.or.ddit.buyer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.common.paging.PaginationInfo;
import kr.or.ddit.vo.BuyerVO;

public interface BuyerDAO {
	/**
	 * 제조사 조회
	 * @param buyerId
	 * @return 없으면, null 반환
	 */
	public BuyerVO selectBuyer(@Param("buyerId") String buyerId);
	
	/**
	 * totalRecord/totalPage를 결정하기 위한 쿼리
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo paging);
	
	/**
	 * 페이징 처리 결과 데이터 목록 조회
	 * @param paging
	 * @return
	 */
	public List<BuyerVO> selectBuyerList(PaginationInfo paging);
	
	public int insertBuyer(BuyerVO buyer);
	
	public int updateBuyer(BuyerVO buyer);
}
