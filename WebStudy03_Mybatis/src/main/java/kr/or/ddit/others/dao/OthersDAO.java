package kr.or.ddit.others.dao;

import java.util.List;
import java.util.Map;

/**
 * 동적 UI 생성에 필요한 데이터 조회 Persistance Layer
 *
 */
public interface OthersDAO {
	public List<Map<String, Object>> selectLprodList();	// vo가 없기 때문에 map사용(칼럼명이 키) 두개씩 엔트리
	public List<Map<String, Object>> selectBuyerList();	// BuyerVO대신 map 사용
}
