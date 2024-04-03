package kr.or.ddit.basetech.designpattern.adapter.homework;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcentKST {
	private PluggableKST pluggable = new SamsungProduct();
	
	public void plugIn(PluggableKST plug) {
		log.info("{} 에서 실행한 메소드", plug);
	}
}
