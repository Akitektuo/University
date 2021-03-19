package ro.ubb.truckers.repository.mapper.file;

import ro.ubb.truckers.domain.entity.Company;
import ro.ubb.truckers.util.Extensions;
import ro.ubb.truckers.util.StringValidator;

public class CompanyFileMapper implements FileMapper<Company> {
    @Override
    public String[] mapEntityToStringArray(Company entity) {
        return Extensions.asStringArray(entity.getId(), entity.getName());
    }

    @Override
    public Company mapStringArrayToEntity(String[] listOfProperties) {
        var id = StringValidator.convertToInt(listOfProperties[0]);
        var name = listOfProperties[1];

        return new Company(id, name);
    }
}
