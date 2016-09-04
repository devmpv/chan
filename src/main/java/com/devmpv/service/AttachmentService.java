package com.devmpv.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.devmpv.model.Attachment;
import com.devmpv.model.AttachmentRepository;

@Service
public class AttachmentService {
	private static final Logger LOG = LoggerFactory.getLogger(AttachmentService.class);

	private Path storagePath;
	private AttachmentRepository repo;

	@Autowired
	public AttachmentService(@Value("${chan.filestorage}") String filestorage, AttachmentRepository repo)
			throws Exception {
		this.repo = repo;
		this.storagePath = Paths.get(filestorage.replaceFirst("^~", System.getProperty("user.home")));
		if (!Files.exists(storagePath)) {
			try {
				Files.createDirectories(storagePath);
			} catch (IOException e) {
				LOG.error("Unable to create attachment directory.", e);
				throw e;
			}
		}
	}

	public Attachment add(File value) {
		Attachment attach = new Attachment();
		try {
			String md5 = String.valueOf(DigestUtils.md5DigestAsHex(new FileInputStream(value)));
			Files.copy(value.toPath(), storagePath.resolve(md5));
			attach.setMd5(md5);
			repo.save(attach);
		} catch (IOException e) {
			LOG.error("Error saving attachment", e);
		}
		return attach;
	}

	public Set<File> getFileSet(Set<Attachment> attach) {
		Set<File> result = new HashSet<>();
		attach.stream().filter(a -> Files.exists(storagePath.resolve(a.getMd5()))).forEach(a -> {
			result.add(storagePath.resolve(a.getMd5()).toFile());
		});
		return result;
	}
}
