package kr.or.ddit.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import kr.or.ddit.vo.PropertyVO;

class JavaBeanTest {

	@Test
	void test1() {
		Object obj1 = new Object();
		Object obj2 = new Object();
		
		System.out.printf("obj1 == obj2 : %b\n", obj1==obj2);
		System.out.printf("obj1.equals(obj2) : %b\n", obj1.equals(obj2));
	}
	
	@Test
	void test2() {
		PropertyVO vo1 = new PropertyVO();
		vo1.setPropertyName("newProp");
		PropertyVO vo2 = new PropertyVO();
		vo2.setPropertyName("newProp");
		// equals가 오버라이딩 된 경우에만 상태비교함
		System.out.printf("vo1==vo2 %b\n", vo1==vo2);
		System.out.printf("vo1.equals(vo2) : %b\n", vo1.equals(vo2));
	}
	
	@Test
	void test3() {
		String str1 = "value";	// 기본형이면서 객체잠초형, 상수메모리공간에 참조됨
		String str2 = "value";
		String str3 = new String("value");
		String str4 = new String("value");
		
		System.out.printf("str1==str2 : %b\n", str1==str2);
		System.out.printf("str1==str3 : %b\n", str1==str3);	// 주소비교로 false
		
		System.out.printf("str1.equals(str3) : %b\n", str1.equals(str3));	// String은 오버라이딩 되어있기 때문에 true
		System.out.printf("str3.equals(str4) : %b\n", str3.equals(str4));
		
		System.out.printf("str3==str4 : %b\n", str3==str4);	// 
	}

}
