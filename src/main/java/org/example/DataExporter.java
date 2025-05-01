package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class DataExporter {
    public ObjectMapper objectMapper;


    public DataExporter() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void exportRooms(List<Room> rooms, File file, Comparator<Room> comparator) throws IOException {
        rooms.sort(comparator);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, rooms);
    }

    public List<Room> importRooms(File file) throws IOException {
        return List.of(objectMapper.readValue(file, Room[].class));
    }

    public void exportVisitors(List<Visitor> visitors, File file, Comparator<Visitor> comparator) throws IOException {
        visitors.sort(comparator);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, visitors);
    }

    public List<Visitor> importVisitors(File file) throws IOException {
        return List.of(objectMapper.readValue(file, Visitor[].class));
    }
}
