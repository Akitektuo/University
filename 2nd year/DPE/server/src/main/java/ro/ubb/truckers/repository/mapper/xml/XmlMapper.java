package ro.ubb.truckers.repository.mapper.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * An XML mapper helps with interpreting the read data from the XML files and with formatting the entity for writing data
 * into XML files.
 *
 * @param <T> representing the type of entity that is mapped.
 */
public interface XmlMapper<T> {

    /**
     * Maps the given entity to a {@code Node} and appends it to a given {@code Document}.
     *
     * @param entity to map to a {@code Node}.
     * @return the entity as a node appended to given {@code Document}.
     */
    Node mapEntityToNode(T entity, Document document);

    /**
     * Maps the given {@code Element} representing a node to the entity {@code T} of the mapper.
     *
     * @param element representing the element to map
     * @return a mapped entity of type {@code T}.
     */
    T mapNodeToEntity(Element element);

    /**
     * Returns the root tag of the entire {@code Document} which stores entities of type {@code T}. This is subject to
     * change, but the returned value will probably be something similar to "users" if, for example, the XML file stores
     * entities of type {@code User}.
     *
     * @return root tag of the entire {@code Document}
     */
    String getDocumentRootTag();

    /**
     * Returns the root tag of an element representing one instance of an entity of type {@code T}. This is subject to
     * change, but the returned value will probably be something similar to "user" if, for example, the XML file stores
     * entities of type {@code User}.
     *
     * @return root tag of an element representing an entity of type {@code T}
     */
    String getElementRootTage();
}
