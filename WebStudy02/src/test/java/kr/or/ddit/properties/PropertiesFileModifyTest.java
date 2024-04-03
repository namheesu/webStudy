package kr.or.ddit.properties;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import kr.or.ddit.servlet08.ServerTimeServlet;


// modify이기 때문에 resourceBundle 사용 못함
class PropertiesFileModifyTest {

	@Test
	void test() throws URISyntaxException, FileNotFoundException, IOException {
		String logicalPath = "/kr/or/ddit/message/message-common_en.properties";	// 슬래쉬를 빼면 클래스로더를 직접 잦아가야함
		URL realPath = ServerTimeServlet.class.getResource(logicalPath);
		File file = new File(realPath.toURI());
		 
		Properties properties = new Properties();
		try(
			FileInputStream fis = new FileInputStream(file);
		){
			properties.load(fis);
		}
		
		properties.setProperty("cop.sms.trnsmitCn", "newValue");
		properties.remove("cop.ntceEndde");
		
		try(
			FileOutputStream fos = new FileOutputStream(file);
		){
			properties.store(fos, "추가한 데이터");
		}
	}
	

}
