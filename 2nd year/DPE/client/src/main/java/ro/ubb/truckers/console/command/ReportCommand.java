package ro.ubb.truckers.console.command;

import ro.ubb.truckers.console.util.table.TableBuilder;
import ro.ubb.truckers.controller.Controller;
import ro.ubb.truckers.domain.TruckersException;
import ro.ubb.truckers.domain.report.CompanyReport;
import ro.ubb.truckers.domain.report.GarageReport;
import ro.ubb.truckers.domain.report.UserReport;
import ro.ubb.truckers.domain.report.sortable.CompanyReportField;
import ro.ubb.truckers.domain.report.sortable.GarageReportField;
import ro.ubb.truckers.domain.report.sortable.UserReportField;
import ro.ubb.truckers.util.ServiceProvider;
import ro.ubb.truckers.util.StringValidator;
import ro.ubb.truckers.util.constant.SortType;

import java.util.List;

public class ReportCommand implements BaseCommand {
    private final Controller controller = ServiceProvider.inject(Controller.class);

    /**
     * Returns the keyword of the command.
     *
     * @return a {@code String} - the keyword that identifies the command
     */
    @Override
    public String getKeyword() {
        return "report";
    }

    /**
     * @return a {@code List} - consists of all the patterns that the command has
     */
    @Override
    public List<String> getPatterns() {
        return List.of("report companies { count | average } { asc | desc }",
                "report garages { min | average | max } { asc | desc }",
                "report users { total | count | average } { asc | desc }");
    }

    /**
     * Returns a short description of the command.
     *
     * @return a {@code String} - a short description of the command
     */
    @Override
    public String getDescription() {
        return "Prints a report for the specified entity as a formatted table.";
    }

    /**
     * Executes the command.
     *
     * @param arguments must not be null.
     * @return a {@code boolean} - represents if the program should continue or not
     */
    @Override
    public boolean run(List<String> arguments) {
        StringValidator.requireArguments(arguments);

        switch (arguments.get(0)) {
            case "companies" -> handleCompaniesReport(arguments);
            case "garages" -> handleGaragesReport(arguments);
            case "users" -> handleUsersReport(arguments);
            default -> throw new TruckersException("There is no report for '%s'", arguments.get(0));
        }

        return true;
    }

    private void handleCompaniesReport(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 3);

        var field = CompanyReportField.enumOf(arguments.get(1));
        var sort = SortType.enumOf(arguments.get(2));

        var report = controller.getCompaniesReportSortedBy(field, sort);

        new TableBuilder<>(report, sort)
                .addColumn("Company ID", CompanyReport::getCompanyId)
                .addColumn("Company Name", CompanyReport::getCompanyName)
                .addColumn("Deliveries No",
                        field == CompanyReportField.COUNT,
                        CompanyReport::getNumberOfDeliveries)
                .addColumn("Distance Average",
                        field == CompanyReportField.AVERAGE,
                        CompanyReport::getAverageOfDistance)
                .print();
    }

    private void handleGaragesReport(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 3);

        var field = GarageReportField.enumOf(arguments.get(1));
        var sort = SortType.enumOf(arguments.get(2));

        var report = controller.getGaragesReportSortedBy(field, sort);

        new TableBuilder<>(report, sort)
                .addColumn("Garage ID", GarageReport::getGarageId)
                .addColumn("Garage Location", GarageReport::getGarageLocation)
                .addColumn("Truck Minimum Mileage",
                        field == GarageReportField.MIN,
                        GarageReport::getTruckMinimumMileage)
                .addColumn("Truck Average Mileage",
                        field == GarageReportField.AVERAGE,
                        GarageReport::getTruckAverageMileage)
                .addColumn("Truck Maximum Mileage",
                        field == GarageReportField.MAX,
                        GarageReport::getTruckMaximumMileage)
                .print();
    }

    private void handleUsersReport(List<String> arguments) {
        StringValidator.requireArgumentsSize(arguments, 3);

        var field = UserReportField.enumOf(arguments.get(1));
        var sort = SortType.enumOf(arguments.get(2));

        var report = controller.getUsersReportSortedBy(field, sort);

        new TableBuilder<>(report, sort)
                .addColumn("User ID", UserReport::getUserId)
                .addColumn("Name", UserReport::getUserFullName)
                .addColumn("Company", UserReport::getCompanyName)
                .addColumn("Total Distance",
                        field == UserReportField.TOTAL,
                        UserReport::getTotalDistance)
                .addColumn("Deliveries No",
                        field == UserReportField.COUNT,
                        UserReport::getNumberOfDeliveries)
                .addColumn("Distance Average",
                        field == UserReportField.AVERAGE,
                        UserReport::getAverageOfDistance)
                .print();
    }
}
