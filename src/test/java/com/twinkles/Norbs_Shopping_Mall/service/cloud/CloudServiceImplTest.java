package com.twinkles.Norbs_Shopping_Mall.service.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CloudServiceImplTest {

    @Autowired
    @Qualifier("cloudinary-service")
    private CloudService cloudService;

    @Autowired
    Cloudinary cloudinary;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("cloudinary object not null test")
    void cloudinaryInstanceTest(){
        assertNotNull(cloudinary);
    }

    @Test
    @DisplayName("Upload to cloudinary")
    void uploadToCloudinary() throws IOException {
        Path file = Paths.get("src/test/resources/fiokee.png");
        assertThat(file.toFile().exists()).isTrue();
        Map<?,?> uploadResult = cloudService.upload(Files.readAllBytes(file), ObjectUtils.emptyMap());
        log.info("upload result to cloud -> {}",uploadResult);
        assertThat(uploadResult.get("url")).isNotNull();

    }

    @Test
    @DisplayName("Upload Multipart to cloudinary")
    void multipartUploadToCloudinary() throws IOException {
        Path path = Paths.get("src/test/resources/fiokee.png");
        assertThat(path.toFile()).exists();
        assertThat(path.getFileName().toString()).isEqualTo("fiokee.png");

        MultipartFile multipartFile = new MockMultipartFile(path.getFileName().toString(), path.getFileName().toString(),
                "png/img", Files.readAllBytes(path));
        assertThat(multipartFile).isNotNull();

        Map<?,?> uploadResult = cloudService.upload(multipartFile.getBytes(), ObjectUtils.asMap("overwrite",true));
        assertThat(uploadResult.get("url")).isNotNull();


    }
}