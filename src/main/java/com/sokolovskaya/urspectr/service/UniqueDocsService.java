package com.sokolovskaya.urspectr.service;

import com.opencsv.CSVWriter;
import com.sokolovskaya.urspectr.model.BackspinCollection;
import com.sokolovskaya.urspectr.model.UniDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UniqueDocsService {

    public File getUniqueDocuments(BackspinCollection collection) throws IOException {

        BackspinCollection collectionNotDeleted = BackspinCollection.builder()
                .backspins(collection.getBackspins()
                        .stream()
                        .filter(backspin -> backspin.getDeleted().equals(false))
                        .collect(Collectors.toList()))
                .build();

        Set<UniDoc> uniDocSet = new HashSet<>();
        collectionNotDeleted.getBackspins()
                .forEach(backspin -> backspin.getDocuments()
                        .forEach(document -> uniDocSet
                                .add(UniDoc.builder()
                                        .infoBank(document.getInfoBank())
                                        .numberInInfoBank(document.getNumberInInfoBank())
                                        .backspinId(backspin.getId())
                                        .build())));

        Map<String, Integer> uniqueDocsNumber = new HashMap<>();
        uniDocSet
                .forEach(uniDoc -> uniqueDocsNumber.compute(uniDoc.getInfoBank() + " " + uniDoc.getNumberInInfoBank(),
                                (k, v) -> uniqueDocsNumber.containsKey(k) ? v + 1 : uniqueDocsNumber.put(k, 1)));

        File file = new File("tmp/unidoc.csv");
        FileWriter fileWriter = new FileWriter(file);
        CSVWriter writer = new CSVWriter(fileWriter);
        String[] header = "InfoBank,Number".split(",");
        writer.writeNext(header);
        uniqueDocsNumber.forEach((k, v) -> writer.writeNext(new String[]{k, v.toString()}));
        writer.close();

        return file;
    }
}
