package kr.or.ddit.member.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.or.ddit.vo.MemberVO;

class MemberDAOTest {

	static MemberDAO dao;
	MemberVO inputData;
	
	// 전체 케이스들보다 한번 먼저 실행된다 
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dao = new InMemoryMemberDAOImpl();
	}
	
	@BeforeEach
	void setUp() {
		inputData = new MemberVO();
		inputData.setMemId("a001");
	}

	@Test
	void testSelectMemberForAuth() {
		MemberVO saved = dao.selectMemberForAuth(inputData);
		assertNotNull(saved);
	}

}
