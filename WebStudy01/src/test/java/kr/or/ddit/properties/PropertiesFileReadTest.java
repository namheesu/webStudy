package kr.or.ddit.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import kr.or.ddit.servlet08.ServerTimeServlet;

class PropertiesFileReadTest {

	@Test
	void test() throws IOException, URISyntaxException {
		Properties properties = new Properties();
		String logicalPath = "/kr/or/ddit/message/message-common_en.properties";	// 슬래쉬를 빼면 클래스로더를 직접 잦아가야함
		URL realPath = ServerTimeServlet.class.getResource(logicalPath);
		File file = new File(realPath.toURI());
		try(
			FileInputStream fis = new FileInputStream(file);
		){
			properties.load(fis);
			System.out.println(properties.size());
			properties.forEach((n,v)->{
				System.out.printf("%s : %s\n", n, v);
			});
			properties.setProperty("newProp", "newValue");// set(메모리상에서 데이터 변경가능), get있음(데이터 꺼내기 가능) 
		}
	}
	// readonly(set없음,get은있음->데이터 꺼낼수만 있음)
	@Test
	void testResourceBundle() {
		String baseName = "kr/or/ddit/message/message-common";	// 확장자가 포함되지 않는다, 클래스패스 이후의 경로 사용 ,슬래쉬 사용안함, 로케일 포함하지 않음
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, Locale.ENGLISH); // 어떤 언어를 사용할 것인지 getBundle메소드를 이용
//		bundle.getKeys(); 	// stream API 사용 불가 : forEach사용 못함
		bundle.keySet().forEach(mc->{
			System.out.printf("%s : %s\n", mc, bundle.getString(mc));
		});
	}

}
