package ro.ubb.truckers.repository.mapper.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.truckers.domain.entity.Delivery;
import ro.ubb.truckers.util.StringValidator;
import ro.ubb.truckers.util.XmlUtil;

public class DeliveryXmlMapper implements XmlMapper<Delivery> {
    public Node mapEntityToNode(Delivery delivery, Document document) {
        var deliveryElement = document.createElement(getElementRootTage());

        XmlUtil.addChildWithTextContent(document, deliveryElement, "id", delivery.getId());
        XmlUtil.addChildWithTextContent(document, deliveryElement, "origin", delivery.getOrigin());
        XmlUtil.addChildWithTextContent(document, deliveryElement, "destination", delivery.getDestination());
        XmlUtil.addChildWithTextContent(document, deliveryElement, "distance", delivery.getDistance());
        XmlUtil.addChildWithTextContent(document, deliveryElement, "delivered", delivery.isDelivered());
        XmlUtil.addChildWithTextContent(document, deliveryElement, "load", delivery.getLoad());
        XmlUtil.addChildWithTextContent(document, deliveryElement, "truckId", delivery.getTruckId());
        XmlUtil.addChildWithTextContent(document, deliveryElement, "companyId", delivery.getCompanyId());

        return deliveryElement;
    }

    public Delivery mapNodeToEntity(Element element) {
        var id = StringValidator.convertToInt(XmlUtil.getTagContent(element, "id"));
        var origin = XmlUtil.getTagContent(element, "origin");
        var destination = XmlUtil.getTagContent(element, "destination");
        var distance = StringValidator.convertToInt(XmlUtil.getTagContent(element, "distance"));
        var delivered = StringValidator.convertToBoolean(XmlUtil.getTagContent(element, "delivered"));
        var load = XmlUtil.getTagContent(element, "load");
        var truckId = StringValidator.convertToInt(XmlUtil.getTagContent(element, "truckId"));
        var companyId = StringValidator.convertToInt(XmlUtil.getTagContent(element, "companyId"));

        return new Delivery(id, origin, destination, distance, delivered, load, truckId, companyId);
    }

    public String getDocumentRootTag() {
        return "deliveries";
    }

    public String getElementRootTage() {
        return "delivery";
    }
}