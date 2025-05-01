package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DataExporterTest {
    private DataExporter dataExporter;
    private ObjectMapper mockMapper;

    @BeforeEach
    public void setup() {
        dataExporter = new DataExporter();
        mockMapper = mock(ObjectMapper.class);
        dataExporter.objectMapper = mockMapper;
    }

    @Test
    public void testExportRooms() throws IOException {
        Room room1 = new Room(1, "Люкс", 500.0);
        Room room2 = new Room(2, "Одномісний номер", 90.0);
        List<Room> rooms = new ArrayList<>(List.of(room2, room1));
        File file = mock(File.class);

        ObjectWriter mockWriter = mock(ObjectWriter.class);
        when(mockMapper.writerWithDefaultPrettyPrinter()).thenReturn(mockWriter);
        doNothing().when(mockWriter).writeValue(file, rooms);

        dataExporter.exportRooms(rooms, file, Comparator.comparing(Room::getIdRoom));

        assertEquals(1, rooms.getFirst().getIdRoom());
        verify(mockMapper, times(1)).writerWithDefaultPrettyPrinter();
        verify(mockWriter, times(1)).writeValue(file, rooms);
    }

    @Test
    public void testImportRooms() throws IOException {
        File file = mock(File.class);
        Room[] roomsArray = {
                new Room(101, "Двомісний номер", 100.0),
                new Room(102, "Одномісний номер", 80.0)
        };

        when(mockMapper.readValue(file, Room[].class)).thenReturn(roomsArray);

        List<Room> importedRooms = dataExporter.importRooms(file);

        assertEquals(2, importedRooms.size());
        assertEquals(101, importedRooms.getFirst().getIdRoom());
        verify(mockMapper, times(1)).readValue(file, Room[].class);
    }

    @Test
    public void testExportVisitors() throws IOException {
        Visitor visitor1 = new Visitor("Олена", "Шевченко", 28, "+380671112233");
        Visitor visitor2 = new Visitor("Віктор", "Нечепоренко", 19, "+380698267233");
        List<Visitor> visitors = new ArrayList<>(List.of(visitor1, visitor2));
        File file = mock(File.class);

        ObjectWriter mockWriter = mock(ObjectWriter.class);
        when(mockMapper.writerWithDefaultPrettyPrinter()).thenReturn(mockWriter);
        doNothing().when(mockWriter).writeValue(file, visitors);

        dataExporter.exportVisitors(visitors, file, Comparator.comparing(Visitor::getNameVisitor));

        assertEquals("Віктор", visitors.getFirst().getNameVisitor()); // перевірка сортування
        verify(mockMapper, times(1)).writerWithDefaultPrettyPrinter();
        verify(mockWriter, times(1)).writeValue(file, visitors);
    }

    @Test
    public void testImportVisitors() throws IOException {
        File file = mock(File.class);
        Visitor[] visitorsArray = {
                new Visitor("Андрій", "Ляшенко", 60, "+380681567948"),
                new Visitor("Катерина", "Пучко", 35, "+380637854327")
        };

        when(mockMapper.readValue(file, Visitor[].class)).thenReturn(visitorsArray);

        List<Visitor> importedVisitors = dataExporter.importVisitors(file);

        assertEquals(2, importedVisitors.size());
        assertEquals("Андрій", importedVisitors.getFirst().getNameVisitor());
        verify(mockMapper, times(1)).readValue(file, Visitor[].class);
    }
}

