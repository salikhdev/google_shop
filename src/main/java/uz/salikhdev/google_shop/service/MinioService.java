package uz.salikhdev.google_shop.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient client;

    @Value("${minio.bucket-name}")
    private String bucket;

    @Value("${minio.url}")
    private String minioUrl;


    public Map<String, String> upload(MultipartFile file, String folderName) {

        try {
            String fileName = folderName + "/" + UUID.randomUUID() + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            boolean exists = client.bucketExists(
                    BucketExistsArgs.builder().bucket(bucket).build()
            );

            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }


            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            }


            File compressedFile = null;

            try {
                if (isImageFile(extension)) {
                    compressedFile = compressImage(file);
                }

                InputStream inputStream;
                long size;

                if (compressedFile != null) {
                    inputStream = new FileInputStream(compressedFile);
                    size = compressedFile.length();
                } else {
                    inputStream = file.getInputStream();
                    size = file.getSize();
                }

                PutObjectArgs build = PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(inputStream, size, -1)
                        .contentType(file.getContentType())
                        .build();
                client.putObject(
                        build
                );

                inputStream.close();
                if (compressedFile != null) compressedFile.delete();

                Map<String, String> map = new HashMap<>();
                map.put("fileName", bucket + "/" + fileName);
                map.put("size", String.valueOf(build.objectSize()));
                return map;
            } catch (Exception e) {
                log.error("Save error: {}", e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String fileName) {
        try {
            String replace = fileName.replace(bucket + "/", "");
            client.removeObject(
                    RemoveObjectArgs.builder().bucket(bucket).object(replace).build());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private File compressImage(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("compressed_", ".jpg");

        double quality = 1; // Boshlang‘ich sifat
        Thumbnails.of(file.getInputStream())
                .size(1000, 1000)
                .outputQuality(quality)
                .outputFormat("jpg")
                .toFile(tempFile);

        // Agar hajm katta chiqsa, sifatni pasaytirib qayta yozamiz
        while (tempFile.length() > 1024 * 1024 && quality > 0.9) {
            quality -= 0.3;
            Thumbnails.of(file.getInputStream())
                    .size(1000, 1000)
                    .outputQuality(quality)
                    .outputFormat("jpg")
                    .toFile(tempFile);
        }

        log.info("Compressed image size: {} KB", tempFile.length() / 1024);
        return tempFile;
    }

    private boolean isImageFile(String ext) {
        String e = ext.toLowerCase();
        return e.equals("jpg") || e.equals("jpeg") || e.equals("png") || e.equals("webp");
    }

    public String getPublicUrl(String key) {
        try {
            String replace = key.replace(bucket + "/", "");
            return String.format("%s/%s/%s", minioUrl, bucket, replace);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}