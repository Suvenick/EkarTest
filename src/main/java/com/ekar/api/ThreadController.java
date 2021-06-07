package com.ekar.api;

import com.ekar.service.ThreadHolderService;
import com.ekar.shared.SharedCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ThreadController {

    private final SharedCounter counter;
    private final ThreadHolderService threadHolderService;

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestParam("value") int value){
        counter.set(value);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("get")
    public ResponseEntity<SharedCounter.OperationResult> get(){
        return ResponseEntity.ok(counter.get());
    }

    @PostMapping("increase")
    public ResponseEntity<String> increase(@RequestParam("consumer") int addConsumers,
                                           @RequestParam("producer") int addProducers){
        threadHolderService.change(addConsumers, addProducers);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
