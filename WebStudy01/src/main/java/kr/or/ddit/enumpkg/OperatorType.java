package kr.or.ddit.enumpkg;

public enum OperatorType {
	PLUS('+',(l,r)->l+r), 
	MINUS('-',(l,r)->l-r), 
	MULTIPLY('*',(l,r)->l*r), 
	DIVIDE('/', new BiOperandOperator() {
		public double operate(double leftOp, double rightOp) {
			return leftOp / rightOp;
		}
	});
	
	private OperatorType(char sign, BiOperandOperator realOperator) {
		this.sign = sign;
		this.realOperator = realOperator;
	}
	
	private BiOperandOperator realOperator;		// 실제 연산 수행함 
	
	private char sign;
	
	public char getSign() {
		return sign;
	}
	
	public double operate(double leftOp, double rightOp) {
		return realOperator.operate(leftOp, rightOp);
	}
	
	public String getExpression(double leftOp, double rightOp) {
		return String.format("%f %c %f = %f", leftOp, sign, rightOp, operate(leftOp, rightOp));
	}
	
	@FunctionalInterface 		// 람다식으로 바꿀수 있는 특징이 있음
	public static interface BiOperandOperator {	// 이항연산자의 행위정보를 넣을수있는 인터페이스 생성
		public double operate(double leftOp, double rightOp);
	}
}
