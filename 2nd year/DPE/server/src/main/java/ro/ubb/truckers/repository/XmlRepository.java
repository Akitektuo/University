package ro.ubb.truckers.repository;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.entity.BaseEntity;
import ro.ubb.truckers.domain.validator.Validator;
import ro.ubb.truckers.repository.mapper.xml.XmlMapper;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.XmlUtil;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The XML repository keeps the data stored in txt XMLs.
 *
 * @param <ID> represents the type of id of the entity.
 * @param <T>  represents the type of the entity itself.
 */
public class XmlRepository<ID, T extends BaseEntity<ID>> extends BaseRepository<ID, T> {
    private final String entityName;
    private final XmlMapper<T> xmlMapper;
    private String fileName;

    protected XmlRepository(String entityName, Validator<T> validator, XmlMapper<T> xmlMapper) {
        super(validator);
        this.entityName = entityName;
        this.xmlMapper = xmlMapper;
        fileName = "";
    }

    protected XmlRepository(String entityName, Validator<T> validator, XmlMapper<T> xmlMapper, String fileName) {
        super(validator);
        this.entityName = entityName;
        this.xmlMapper = xmlMapper;
        this.fileName = fileName;
    }

    /**
     * Returns all of the data from the repository.
     *
     * @return a {@code Map} consisting of all the data from the repository.
     */
    @Override
    public Map<ID, T> getData() {
        var path = getPath();
        if (Files.notExists(path)) {
            return new HashMap<>();
        }

        try {
            var entities = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(Files.newInputStream(path))
                    .getDocumentElement()
                    .getChildNodes();

            return parseData(entities);
        } catch (ParserConfigurationException parserConfigurationException) {
            throw new TruckersException("There was an error in configuring the XML parser. Data has not been read from the XML file.");
        } catch (IOException ioException) {
            throw new TruckersException("An IO exception has occurred. Data has not been read from the XML file.");
        } catch (SAXException saxException) {
            throw new TruckersException("An SAX exception has occurred. Data has not been read from the XML file.");
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
     * Parses the entities as {@code NodeList} and maps them to a {@code HashMap}
     *
     * @param entities to parse.
     * @return a {@code HashMap} consisting of parsed entities.
     */
    private HashMap<ID, T> parseData(NodeList entities) {
        var map = Extensions.toStream(entities)
                .filter(entity -> entity instanceof Element)
                .map(entity -> xmlMapper.mapNodeToEntity((Element) entity))
                .collect(Collectors.toMap(BaseEntity::getId, element -> element));

        return new HashMap<>(map);
    }

    /**
     * Sets the repository's XML name for reading and writing data.
     *
     * @param fileName represents the new XML name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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

        return Paths.get(String.format("src/main/assets/xml/%s_%s.xml", fileName, entityName));
    }

    /**
     * Overwrites an entire XML file based on the given {@code BufferedWriter}, data as a {@code Map}.
     *
     * @param writer for writing data into the XML.
     * @param data   represents the data to write into the XML.
     */
    private void writeData(BufferedWriter writer, Map<ID, T> data) {
        try {
            var transformer = XmlUtil.createXmlTransformer();
            var documentToWrite = convertDataToDocument(data);
            var source = new DOMSource(documentToWrite);
            var streamResult = new StreamResult(writer);

            transformer.transform(source, streamResult);
        } catch (ParserConfigurationException parserConfigurationException) {
            throw new TruckersException("There was an error in configuring the XML parser. Data has not been written to the XML file.");
        } catch (TransformerException transformerException) {
            throw new TruckersException("An unrecoverable error has occurred during the writing of data to the XML file. Data may me corrupted. Proceed with caution.");
        }
    }

    /**
     * Converts the data given as {@code Map} to an XML Document
     *
     * @param data to be converted
     * @return a {@code Document} representing the data
     */
    private Document convertDataToDocument(Map<ID, T> data) throws ParserConfigurationException {
        var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        var root = document.createElement(xmlMapper.getDocumentRootTag());
        document.appendChild(root);

        data.forEach((key, value) -> root.appendChild(xmlMapper.mapEntityToNode(value, document)));

        return document;
    }
}
