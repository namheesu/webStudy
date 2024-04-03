package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.common.exception.CustomPersistenceException;
import kr.or.ddit.common.paging.PaginationInfo;
import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO {
	
	// mybatis란 프레임워크에 의존해야하기 때문에 의존형성하는 코드 새로 생성
	private SqlSessionFactory sqlSessionFactory =
			CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public MemberVO selectMemberForAuth(String memId) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
			return sqlSession.selectOne("kr.or.ddit.member.dao.MemberDAO.selectMemberForAuth",memId);
		}
	}

//	template method pattern 으로 해결 예정
	@Override
	public int insertMember(MemberVO member) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			int rowcnt = sqlSession.insert("kr.or.ddit.member.dao.MemberDAO.insertMember", member);
			sqlSession.commit();
			return rowcnt;
		}
	}

	@Override
	public MemberVO selectMember(String memId) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			return sqlSession.selectOne("kr.or.ddit.member.dao.MemberDAO.selectMember", memId);
		}
	}

	@Override
	public List<MemberVO> selectMemberList(PaginationInfo paging) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				return sqlSession.selectList("kr.or.ddit.member.dao.MemberDAO.selectMemberList", paging);
			}
	}

	@Override
	public int updateMember(MemberVO member) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				int rowcnt = mapperProxy.updateMember(member);
				sqlSession.commit();
				return rowcnt;
			}
	}

	@Override
	public int deleteMember(String memId) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			int rowcnt = sqlSession.insert("kr.or.ddit.member.dao.MemberDAO.deleteMember", memId);
			sqlSession.commit();
			return rowcnt;
		}
	}

	@Override
	public int selectTotalRecord(PaginationInfo paging) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				return mapperProxy.selectTotalRecord(paging);
		}
	}

}
