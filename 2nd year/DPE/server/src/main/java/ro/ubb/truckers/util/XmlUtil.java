package ro.ubb.truckers.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.ubb.truckers.domain.TruckersException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

public class XmlUtil {
    private XmlUtil() {
    }

    /**
     * Adds an element with a user-supplied tag and text content inside the tag to an {@code Element} from a given {@code document}.
     *
     * @param document    which contains the parent {@code Element}
     * @param parent      which will contain the newly-created {@code Element}
     * @param tagName     tag to be added
     * @param textContent text to be contained inside tag
     */
    public static void addChildWithTextContent(Document document, Element parent, String tagName, Object textContent) {
        Element childElement = document.createElement(tagName);
        childElement.setTextContent(textContent.toString());
        parent.appendChild(childElement);
    }

    /**
     * Creates an XML {@code Transformer} which does not omit XML declaration and indents XML file it transforms.
     *
     * @return {@code Transformer} created
     */
    public static Transformer createXmlTransformer() {
        try {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();

            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            return transformer;
        } catch (TransformerConfigurationException transformerConfigurationException) {
            throw new TruckersException("Error in creating transformer for writing XML file. Data not written to XML file.");
        }
    }

    /**
     * Returns the content of the specified tag that is part of the given element.
     *
     * @param element that contains the content.
     * @param tagName by which the content is extracted.
     * @return the content as {@code String}.
     */
    public static String getTagContent(Element element, String tagName) {
        return element.getElementsByTagName(tagName)
                .item(0)
                .getTextContent();
    }
}
