package kr.or.ddit.property.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.PropertyVO;


//sql로 DB에 명령 보내기
//Oracle DB타입으로 받아온 데이터를 JAVA타입으로 매핑하는 역할까지 함. => SQL Mapper = Data Mapper = My batis 
public class PropertyDAOImpl implements PropertyDAO {

   private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
   
   @Override
   public int insertProperty(PropertyVO newProp) {
      try(
            SqlSession sqlSession = sqlSessionFactory.openSession();
         ){
//            int rowcnt = sqlSession.insert("kr.or.ddit.property.dao.PropertyDAO.insertProperty", newProp);
//            sqlSession.commit();
            PropertyDAO mapperProxy = sqlSession.getMapper(PropertyDAO.class);
            int rowcnt = mapperProxy.insertProperty(newProp);
            sqlSession.commit();
            return rowcnt;
         }
   }

   @Override
   public List<PropertyVO> selectProperties() {
      try(
         SqlSession sqlSession = sqlSessionFactory.openSession();
      ){
         return sqlSession.selectList("kr.or.ddit.property.dao.PropertyDAO.selectProperties");
      }
   }

   @Override
   public PropertyVO selectProperty(String propertyName) {
      try(
         SqlSession sqlSession = sqlSessionFactory.openSession();
      ){
            
         //타입안정성(qualified name부분이 하드코딩 돼있음), 파라미터를 안넘겨도 컴파일 오류가 안남 => 프록시매퍼로 해결
//            return sqlSession.selectOne("kr.or.ddit.property.dao.PropertyDAO.selectProperty", propertyName);
         PropertyDAO mapperProxy = sqlSession.getMapper(PropertyDAO.class); //가짜(대체) 매퍼
         return mapperProxy.selectProperty(propertyName);
         }
   }

   @Override
   public int updateProperty(PropertyVO modifyProp) {
      try(
            SqlSession sqlSession = sqlSessionFactory.openSession();
         ){
            int rowcnt = sqlSession.update("kr.or.ddit.property.dao.PropertyDAO.updateProperty", modifyProp);
            sqlSession.commit();
            return rowcnt;
         }
   }

   @Override
   public int deleteProperty(String propertyName) {
      try(
            SqlSession sqlSession = sqlSessionFactory.openSession();
         ){
//            int rowcnt = sqlSession.delete("kr.or.ddit.property.dao.PropertyDAO.deleteProperty", propertyName);
            PropertyDAO mapperProxy = sqlSession.getMapper(PropertyDAO.class);
            int rowcnt = mapperProxy.deleteProperty(propertyName);
            sqlSession.commit();
            return rowcnt;
         }
   }

}