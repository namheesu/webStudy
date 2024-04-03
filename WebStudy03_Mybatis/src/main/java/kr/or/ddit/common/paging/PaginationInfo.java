package kr.or.ddit.common.paging;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 페이징 처리와 관련된 속성을 가진 객체
 *
 */
@Getter 
@NoArgsConstructor
@ToString
public class PaginationInfo {
	
	public PaginationInfo(int screenSize, int blockSize) {
		super();
		this.screenSize = screenSize;
		this.blockSize = blockSize;
	}

	private int totalRecord;	// 데이터베이스 조회시 전체 갯수
	private int screenSize = 10; 	// 개발자가 임의 결정
	private int blockSize = 5;		// 개발자가 임의 결정
	
	
	private int currentPage; 	// 클라이언트의 결정(요청 파라미터로 받음)

	// totalRecord가 결정되는 순간 결정됨 
	private int totalPage;		// 계산식으로 만들어짐 
	
	// currentPage가 결정되는 순간 결정됨
	private int startRow;		// 계산식으로 만들어짐
	private int endRow;			// 계산식으로 만들어짐
	private int startPage;		// 계산식으로 만들어짐
	private int endPage;		// 계산식으로 만들어짐
	
	private SearchCondition simpleCondition;	// 단순 키워드 검색용
	private Map<String, Object> detailCondition = new LinkedHashMap<>();
	
	public void setSimpleCondition(SearchCondition simpleCondition) {
		this.simpleCondition = simpleCondition;
	}
	
	public void addDetailCondition(String key, Object value) {
		detailCondition.put(key, value);
	}
	
	public void setDetailCondition(Map<String, Object> detailCondition) {
		this.detailCondition = detailCondition;
	}
	
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		totalPage = (totalRecord + (screenSize -1)) / screenSize;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		endRow = currentPage * screenSize;
		startRow = endRow - (screenSize - 1);
		endPage = ((currentPage + (blockSize - 1)) / blockSize) * blockSize;
		startPage = endPage - (blockSize - 1);
	}
	
	public int getEndPage() {
		return endPage > totalPage ? totalPage : endPage;
	}
}
