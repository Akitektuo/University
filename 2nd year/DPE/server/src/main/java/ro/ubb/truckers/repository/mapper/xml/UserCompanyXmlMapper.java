package ro.ubb.truckers.repository.mapper.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.truckers.domain.entity.UserCompany;
import ro.ubb.truckers.util.StringValidator;
import ro.ubb.truckers.util.XmlUtil;

public class UserCompanyXmlMapper implements XmlMapper<UserCompany> {
    public Node mapEntityToNode(UserCompany userCompany, Document document) {
        var companyElement = document.createElement(getElementRootTage());

        XmlUtil.addChildWithTextContent(document, companyElement, "userId", userCompany.getUserId());
        XmlUtil.addChildWithTextContent(document, companyElement, "companyId", userCompany.getCompanyId());
        XmlUtil.addChildWithTextContent(document, companyElement, "manager", userCompany.isManager());

        return companyElement;
    }

    public UserCompany mapNodeToEntity(Element element) {
        var userId = StringValidator.convertToInt(XmlUtil.getTagContent(element, "userId"));
        var companyId = StringValidator.convertToInt(XmlUtil.getTagContent(element, "companyId"));
        var manager = StringValidator.convertToBoolean(XmlUtil.getTagContent(element, "manager"));

        return new UserCompany(userId, companyId, manager);
    }

    public String getDocumentRootTag() {
        return "userCompanies";
    }

    public String getElementRootTage() {
        return "userCompany";
    }
}