package ro.ubb.truckers.repository;

import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.BaseEntity;
import ro.ubb.truckers.domain.validator.Validator;
import ro.ubb.truckers.repository.mapper.file.FileMapper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.ubb.truckers.util.Constants.FILE_PROPERTY_DELIMITER;

/**
 * The file repository keeps the data stored in txt files.
 *
 * @param <ID> represents the type of id of the entity.
 * @param <T>  represents the type of the entity itself.
 */
public class FileRepository<ID, T extends BaseEntity<ID>> extends BaseRepository<ID, T> {
    private final String entityName;
    private final FileMapper<T> fileMapper;
    private String fileName;

    protected FileRepository(String entityName, Validator<T> validator, FileMapper<T> fileMapper) {
        super(validator);
        this.entityName = entityName;
        this.fileMapper = fileMapper;
        fileName = "";
    }

    protected FileRepository(String entityName, Validator<T> validator, FileMapper<T> fileMapper, String fileName) {
        super(validator);
        this.entityName = entityName;
        this.fileMapper = fileMapper;
        this.fileName = fileName;
    }

    /**
     * Returns all of the data from the repository.
     *
     * @return a {@code Map} consisting of all the data from the repository.
     */
    @Override
    public Map<ID, T> getData() {
        try (var lines = Files.lines(getPath())) {
            return readData(lines);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    /**
     * Sets the data in the repository to the given state.
     *
     * @param data represents the new state of data.
     */
    @Override
    public void setData(Map<ID, T> data) {
        var path = getPath();

        if (Files.notExists(path)) {
            System.out.printf("No file found with the name '%s', created a new one with the provided name%n", path.getFileName());
        }

        try (var writer = Files.newBufferedWriter(path)) {
            writeData(writer, data);
        } catch (IOException e) {
            throw new TruckersException("An IO exception occurred with message: %s", e.getMessage());
        }
    }

    /**
     * Returns a specific path for the given file name and entity name.
     *
     * @return the {@code Path} of the file.
     */
    private Path getPath() {
        if (fileName.isBlank()) {
            throw new TruckersException("No file name was provided for file repository");
        }

        return Paths.get(String.format("src/main/assets/file/%s_%s.txt", fileName, entityName));
    }

    /**
     * Sets the repository's file name for reading and writing data.
     *
     * @param fileName represents the new file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads the given {@code Stream<String>} of lines and returns a {@code HashMap} consisting of the read data.
     *
     * @param lines represents an initialized and checked stream of file lines.
     * @return a {@code HashMap} with the read data.
     */
    private HashMap<ID, T> readData(Stream<String> lines) {
        var map = lines.map(line -> line.split(FILE_PROPERTY_DELIMITER))
                .map(fileMapper::mapStringArrayToEntity)
                .collect(Collectors.toMap(BaseEntity::getId, entity -> entity));

        return new HashMap<>(map);
    }

    /**
     * Writes an entire file based on the given {@code BufferedWriter} and data as a {@code Map}.
     *
     * @param writer represents an initialized {@code BufferedWriter} for writing the file.
     * @param data   represents the data to write into the file.
     */
    private void writeData(BufferedWriter writer, Map<ID, T> data) {
        data.values().forEach(entity -> {
            var properties = String.join(FILE_PROPERTY_DELIMITER, fileMapper.mapEntityToStringArray(entity));
            try {
                writer.append(properties)
                        .append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
