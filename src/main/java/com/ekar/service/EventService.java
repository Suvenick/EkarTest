package com.ekar.service;

import com.ekar.model.EventAudit;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class EventService {

    public EventAudit endProcessing(){
        return EventAudit
                .builder()
                .eventTime(LocalDateTime.now())
                .document("{}")
                .eventType(EventAudit.EventType.END_PROCESSING.name())
                .build();
    }

    @SneakyThrows
    public EventAudit newSettingsProcessing(int addConsumers, int addProducers){
        Map<String,Integer> changeSettings = new LinkedHashMap<>();
        changeSettings.put("addConsumers", addConsumers);
        changeSettings.put("addProducers", addProducers);
        return EventAudit
                .builder()
                .eventTime(LocalDateTime.now())
                .document(new ObjectMapper().writeValueAsString(changeSettings))
                .eventType(EventAudit.EventType.NEW_SETTINGS.name())
                .build();
    }

    @SneakyThrows
    public EventAudit newCounter(int newValue){
        Map<String,Integer> changeSettings = new LinkedHashMap<>();
        changeSettings.put("newValue", newValue);
        return EventAudit
                .builder()
                .eventTime(LocalDateTime.now())
                .document(new ObjectMapper().writeValueAsString(changeSettings))
                .eventType(EventAudit.EventType.NEW_COUNTER_VALUE.name())
                .build();
    }

}
