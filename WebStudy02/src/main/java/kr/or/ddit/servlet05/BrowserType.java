package kr.or.ddit.servlet05;

import java.util.stream.Stream;

public enum BrowserType {
	EDG("엣지"), WHALE("웨일"), CHROME("크롬"), SAFARI("사파리"), OTHER("기타등등");
	
	
	private String browserName;
	public String getBrowserName() {
		return browserName;
	}

	private BrowserType(String broswerName) {
		this.browserName = broswerName;
	}
	
	public static BrowserType findBrowserType(String userAgent) {
		final String agent = userAgent.toUpperCase();
		return Stream.of(values())
				.filter(c->agent.contains(c.name()))
				.findFirst()
				.orElse(OTHER);
	}
	
	public static String findBrowserName(String userAgent) {
		return findBrowserType(userAgent).getBrowserName();
	}
	
//	
//	public static final BrowserType EDG = new BrowserType();
//	public static final BrowserType WHALE = new BrowserType();
//	public static final BrowserType CHROME = new BrowserType();
//	
//	private static BrowserType[] instances = {
//		EDG, WHALE, CHROME
//	};
//	
//	public static BrowserType[] values() {
//		return instances;
//	}

}
 