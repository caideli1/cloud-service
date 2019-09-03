package com.cloud.service.fallback;

import com.cloud.service.feign.file.FilesClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * files服务降级类
 *
 * @author danquan.miao
 * @date 2019/7/23 0023
 * @since 1.0.0
 */
@Component
public class FilesFallBackFactory implements FallbackFactory<FilesClient> {
    @Override
    public FilesClient create(Throwable throwable) {
        return (String pdfFileOfBank, String fileSource) -> null;
    }
}
