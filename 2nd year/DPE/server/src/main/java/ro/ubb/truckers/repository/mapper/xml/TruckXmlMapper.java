package ro.ubb.truckers.repository.mapper.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.truckers.domain.entity.Truck;
import ro.ubb.truckers.util.StringValidator;
import ro.ubb.truckers.util.XmlUtil;

public class TruckXmlMapper implements XmlMapper<Truck> {
    public Node mapEntityToNode(Truck truck, Document document) {
        var truckElement = document.createElement(getElementRootTage());

        XmlUtil.addChildWithTextContent(document, truckElement, "id", truck.getId());
        XmlUtil.addChildWithTextContent(document, truckElement, "model", truck.getModel());
        XmlUtil.addChildWithTextContent(document, truckElement, "licensePlate", truck.getLicensePlate());
        XmlUtil.addChildWithTextContent(document, truckElement, "mileage", truck.getMileage());
        XmlUtil.addChildWithTextContent(document, truckElement, "driverId", truck.getDriverId());
        XmlUtil.addChildWithTextContent(document, truckElement, "garageId", truck.getGarageId());

        return truckElement;
    }

    public Truck mapNodeToEntity(Element element) {
        var id = StringValidator.convertToInt(XmlUtil.getTagContent(element, "id"));
        var model = XmlUtil.getTagContent(element, "model");
        var licensePlate = XmlUtil.getTagContent(element, "licensePlate");
        var mileage = StringValidator.convertToInt(XmlUtil.getTagContent(element, "mileage"));
        var driverId = StringValidator.convertToInt(XmlUtil.getTagContent(element, "driverId"));
        var garageId = StringValidator.convertToInt(XmlUtil.getTagContent(element, "garageId"));

        return new Truck(id, model, licensePlate, mileage, driverId, garageId);
    }

    public String getDocumentRootTag() {
        return "trucks";
    }

    public String getElementRootTage() {
        return "truck";
    }
}