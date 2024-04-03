package kr.or.ddit.basetech.generic.material;

/**
 * @author PC-06
 *
 */
public class ExtendedHouse extends SampleHouse<Child, Parent>{

	/**
	 * 타입변수 C의 구제척 타입은 인스턴스 메소드인 casting2가 호출될때 첫번째 인자를 통해 결정됨.
	 * @param <C>
	 * @param castingType
	 * @return
	 */
	public <C> C casting2(Class<C> castingType) {
		return (C) getPerson();
	}
	
	/**
	 * 타입변수 T와 C의 구체적 타입은
	 * @param <T>
	 * @param <C>
	 * @param target
	 * @param castingType
	 * @return
	 */
	public static <T,C> C casting3(T target, Class<C> castingType) {
		return (C)target;
	}
	
}
