package com.sokolovskaya.urspectr.controller;

import com.sokolovskaya.urspectr.service.AverageService;
import com.sokolovskaya.urspectr.service.ConvertService;
import com.sokolovskaya.urspectr.service.UniqueDocsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AverageController {

    private final ConvertService convertService;
    private final AverageService averageService;
    private final UniqueDocsService uniqueDocsService;

    @GetMapping("/average")
    public String getPage(Model model) {
        model.addAttribute("average");
        model.addAttribute("unique");
        return "average";
    }

    @PostMapping("/average")
    public
    String fileUpload(Model model, @RequestParam("file") MultipartFile file) throws IOException {

        model.addAttribute("average",
                averageService.getAverageNumberOfDocumentsInBackspin(convertService.convertToBackspinCollection(file)));

        ConvertService.deleteAllFiles("tmp/");

        return "average";
    }

    @PostMapping("/unique")
    void fileDownload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {

        Path downloadFile = Paths.get(uniqueDocsService.getUniqueDocuments(convertService.convertToBackspinCollection(file)).getAbsolutePath());
        response.setHeader("Content-disposition", "attachment;filename=" + "tmp/unidoc.csv");
        response.setContentType("application/csv");

        Files.copy(downloadFile, response.getOutputStream());
        response.getOutputStream().flush();

        ConvertService.deleteAllFiles("tmp/");
    }
}
