package com.cloud.service.feign.file;

import com.cloud.service.fallback.FilesFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

@FeignClient(name = "file-center", fallbackFactory = FilesFallBackFactory.class)
public interface FilesClient {
	@PostMapping("/files-anon/pdftojpg")
	File pdfTojpg(@RequestParam("pdfFileOfBank") String pdfFileOfBank, @RequestParam("fileSource") String fileSource);
}
 