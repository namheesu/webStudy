package kr.or.ddit.basetech.generic.material;

import lombok.Data;

/**
 * 타입변수 T와 C의 구체적 타입은 런타임에 SampleHouse 
 *
 * @param <T>
 * @param <C>
 */
@Data
public class SampleHouse<T,C> {	// 한개의 클래스 안에 두개의 제너릭 사용
	private T person;
	
	public C casting1(){
		return (C) person;
	}
}
