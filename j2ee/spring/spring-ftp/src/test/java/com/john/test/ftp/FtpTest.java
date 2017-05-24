package com.john.test.ftp;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;

import com.john.test.BaseTest;
import com.john.util.FTPUtil;

public class FtpTest extends BaseTest {
	
	@Autowired
	private MessageChannel ftpUploadChannel;
	
	@Test
	public void uploadTest() {
		try {
			InputStream in = new FileInputStream("D:\\222.jpg");
			boolean uploadOriginal = FTPUtil.ftpUpload(ftpUploadChannel, in, "222.jpg", "test");
			log.info("upload result:" + uploadOriginal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
