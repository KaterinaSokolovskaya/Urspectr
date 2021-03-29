package com.sokolovskaya.urspectr.controller;

import com.sokolovskaya.urspectr.service.AverageService;
import com.sokolovskaya.urspectr.service.ConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AverageController {

    private final ConvertService convertService;
    private final AverageService averageService;

    @GetMapping("/index")
    public String getPage(Model model) {
        model.addAttribute("average");
        return "average";
    }

    @PostMapping("/average")
    public
    String fileUpload(Model model, @RequestParam("file") MultipartFile file) throws IOException {

        model.addAttribute("average",
                averageService.getAverageNumberOfDocumentsInBackspin(convertService.convertToBackspinCollection(file)));

        ConvertService.deleteAllFiles("tmp/");

        return "index";
    }
}
