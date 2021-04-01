package com.sokolovskaya.urspectr.service;

import com.sokolovskaya.urspectr.model.BackspinCollection;
import com.sokolovskaya.urspectr.model.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AverageService {

    public Float getAverageNumberOfDocumentsInBackspin(BackspinCollection collection) {

        BackspinCollection collectionNotDeleted = BackspinCollection.builder()
                .backspins(collection.getBackspins()
                        .stream()
                        .filter(backspin -> backspin.getDeleted().equals(false))
                        .collect(Collectors.toList()))
                .build();

        Integer numberOfBackspins = collectionNotDeleted.getBackspins().size();

        List<Document> documents = new ArrayList<>();
        collectionNotDeleted.getBackspins().forEach(backspin -> documents.addAll(backspin.getDocuments()));

        return Float.valueOf(documents.size()) / Float.valueOf(numberOfBackspins);
    }
}
