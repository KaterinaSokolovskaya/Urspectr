package com.sokolovskaya.urspectr.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sokolovskaya.urspectr.model.Backspin;
import com.sokolovskaya.urspectr.model.BackspinCollection;
import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConvertService {

    private File file;

    public BackspinCollection convertToBackspinCollection(MultipartFile multipartFile) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        return BackspinCollection.builder()
                .backspins(Arrays.asList(mapper.readValue(convertMultipartFileToJson(multipartFile), Backspin[].class)))
                .build();
    }

    private File convertMultipartFileToJson(MultipartFile multipartFile) throws IOException {

        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).contains(".json")) {
            file = convertMultipartFileToFile(multipartFile);
        }
        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).contains(".zip")) {
            file = convertMultipartFileToFile(multipartFile);
            file = unzipZipFile(file);
        }

        return file;
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File uploadedFile = new File("tmp/" + multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(uploadedFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return uploadedFile;
    }

    private File unzipZipFile(File file) throws ZipException {
        ZipFile zip = new ZipFile(file);
        zip.extractAll("tmp/");
        file.delete();

        return new File("tmp/" + zip.getFile().getName().replace(".zip", ".json"));
    }

    public static void deleteAllFiles(String path) {
        for (File myFile : Objects.requireNonNull(new File(path).listFiles()))
            if (myFile.isFile()) myFile.delete();
    }
}
