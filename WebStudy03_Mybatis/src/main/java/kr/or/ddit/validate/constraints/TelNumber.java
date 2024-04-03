package kr.or.ddit.validate.constraints;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.groups.Default;

// 어노테이션이라는 특수한 형태의 객체
// @Target : 메타 어노테이션
// static import 구문 : FIELD앞 생략 가능 
// @Retention : 만들어낸 어노테이션을 언제까지 가지고 있을지 정하는 어노테이션 -> (class : 소스단계, 컴파일 단계 포함(런타임때는 포함 안함))
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = TelNumberValidator.class)
public @interface TelNumber {
	String regex() default "\\d{3}-\\d{3,4}-\\d{4}";
	String message() default "{kr.or.ddit.validate.constraints.TelNumber.message}";
	Class<?>[] groups() default {};
	Class<?>[] payload() default {};
}
