package org.zerock.mreview.common.file;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mreview.common.file.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${orz.zerock.upload.path}")
    private String uploadPath;


    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) {

        List<UploadResultDTO> uploadResultDTOList = new ArrayList<>();

        /******파일 저장하는 단계 고려사항*******
         * 업로드된 확장자가 이미지만 가능하도록 검사(첨부파일을 이용한 원격 쉘)
         * 동일한 이름의 파일이 업로드 된다면 기존 파일을 덮어쓰는 문제
         * 업로드된 파일을 저장하는 폴더의 용량
         **********************************/
        for (MultipartFile uploadFile : uploadFiles) {

            // 이미지 파일만 업로드 가능
            if (uploadFile.getContentType().startsWith("image") == false) {
                log.warn("this file is not image tpye");
                return new ResponseEntity<>((HttpStatus.FORBIDDEN));
            }

            // 실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("fileName: " + fileName);

            // 날짜 폴더 생성
            String folderPath = makeFolder();

            // UUID
            String uuid = UUID.randomUUID().toString();

            // 저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try {
                // 원본 파일 저장
                uploadFile.transferTo(savePath); // 실제 이미지 저장

                // 섬네일 생성
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                // 섬네일 파일 이름은 중간에 s_로 시작하도록
                File thumbnailFile = new File(thumbnailSaveName);
                // 섬네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);

                uploadResultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } // end for
        return new ResponseEntity<>(uploadResultDTOList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        // make folder --------------------
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size) {

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

            log.info("srcFileName: " + srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);

            if (size != null && size.equals("1")) {
                file = new File(file.getParent(), file.getName().substring(2));
            }

            log.info("file: " + file);

            HttpHeaders header = new HttpHeaders();

            // MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            // 파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(@RequestBody String fileName) {
        String srcFileName = null;
        System.out.println("fileName :::::::::::::::::: " + fileName);
        try {
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
