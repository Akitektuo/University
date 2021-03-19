package ro.ubb.truckers.repository.mapper.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.truckers.domain.entity.Garage;
import ro.ubb.truckers.util.StringValidator;
import ro.ubb.truckers.util.XmlUtil;

public class GarageXmlMapper implements XmlMapper<Garage> {
    public Node mapEntityToNode(Garage garage, Document document) {
        var garageElement = document.createElement(getElementRootTage());

        XmlUtil.addChildWithTextContent(document, garageElement, "id", garage.getId());
        XmlUtil.addChildWithTextContent(document, garageElement, "model", garage.getLocation());
        XmlUtil.addChildWithTextContent(document, garageElement, "capacity", garage.getCapacity());
        XmlUtil.addChildWithTextContent(document, garageElement, "allocatedTrucks", garage.getAllocatedTrucks());
        XmlUtil.addChildWithTextContent(document, garageElement, "companyId", garage.getCompanyId());

        return garageElement;
    }

    public Garage mapNodeToEntity(Element element) {
        var id = StringValidator.convertToInt(XmlUtil.getTagContent(element, "id"));
        var location = XmlUtil.getTagContent(element, "location");
        var capacity = StringValidator.convertToInt(XmlUtil.getTagContent(element, "capacity"));
        var allocatedTrucks = StringValidator.convertToInt(XmlUtil.getTagContent(element, "allocatedTrucks"));
        var companyId = StringValidator.convertToInt(XmlUtil.getTagContent(element, "companyId"));

        return new Garage(id, location, capacity, allocatedTrucks, companyId);
    }

    public String getDocumentRootTag() {
        return "garages";
    }

    public String getElementRootTage() {
        return "garage";
    }
}
