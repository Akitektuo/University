package ro.ubb.truckers.repository.mapper.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.truckers.domain.entity.User;
import ro.ubb.truckers.util.StringValidator;
import ro.ubb.truckers.util.XmlUtil;

public class UserXmlMapper implements XmlMapper<User> {
    public Node mapEntityToNode(User user, Document document) {
        var userElement = document.createElement(getElementRootTage());

        XmlUtil.addChildWithTextContent(document, userElement, "id", user.getId());
        XmlUtil.addChildWithTextContent(document, userElement, "firstName", user.getFirstName());
        XmlUtil.addChildWithTextContent(document, userElement, "lastName", user.getLastName());
        XmlUtil.addChildWithTextContent(document, userElement, "email", user.getEmail());
        XmlUtil.addChildWithTextContent(document, userElement, "password", user.getPassword());
        XmlUtil.addChildWithTextContent(document, userElement, "dateOfBirth", user.getDateOfBirth());
        XmlUtil.addChildWithTextContent(document, userElement, "truckId", user.getTruckId());

        return userElement;
    }

    public User mapNodeToEntity(Element element) {
        var id = StringValidator.convertToInt(XmlUtil.getTagContent(element, "id"));
        var firstName = XmlUtil.getTagContent(element, "firstName");
        var lastName = XmlUtil.getTagContent(element, "lastName");
        var email = XmlUtil.getTagContent(element, "email");
        var password = XmlUtil.getTagContent(element, "password");
        var dateOfBirth = StringValidator.convertToDate(XmlUtil.getTagContent(element, "dateOfBirth"));
        var truckId = StringValidator.convertToInt(XmlUtil.getTagContent(element, "truckId"));

        return new User(id, firstName, lastName, email, password, dateOfBirth, truckId);
    }

    public String getDocumentRootTag() {
        return "users";
    }

    public String getElementRootTage() {
        return "user";
    }
}