package com.quiz.springboot.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CommonsMultipartFileWrapper implements MultipartFile {

	private final byte[] content;
	private final String originalFilename;
	private final String name;

	public CommonsMultipartFileWrapper(String originalFilename, byte[] content) {
		this.name = "file";
		this.originalFilename = originalFilename;
		this.content = content;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOriginalFilename() {
		return originalFilename;
	}

	@Override
	public String getContentType() {
		return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	}

	@Override
	public boolean isEmpty() {
		return content == null || content.length == 0;
	}

	@Override
	public long getSize() {
		return content.length;
	}

	@Override
	public byte[] getBytes() {
		return content;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(content);
	}

	@Override
	public void transferTo(File dest) throws IOException {
		try (OutputStream out = new FileOutputStream(dest)) {
			out.write(content);
		}
	}
}