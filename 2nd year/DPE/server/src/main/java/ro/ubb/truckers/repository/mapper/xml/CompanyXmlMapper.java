package ro.ubb.truckers.repository.mapper.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.util.StringValidator;
import ro.ubb.truckers.util.XmlUtil;

public class CompanyXmlMapper implements XmlMapper<Company> {
    public Node mapEntityToNode(Company company, Document document) {
        var companyElement = document.createElement(getElementRootTage());

        XmlUtil.addChildWithTextContent(document, companyElement, "id", company.getId());
        XmlUtil.addChildWithTextContent(document, companyElement, "name", company.getName());

        return companyElement;
    }

    public Company mapNodeToEntity(Element element) {
        var id = StringValidator.convertToInt(XmlUtil.getTagContent(element, "id"));
        var name = XmlUtil.getTagContent(element, "name");

        return new Company(id, name);
    }

    public String getDocumentRootTag() {
        return "companies";
    }

    public String getElementRootTage() {
        return "company";
    }
}