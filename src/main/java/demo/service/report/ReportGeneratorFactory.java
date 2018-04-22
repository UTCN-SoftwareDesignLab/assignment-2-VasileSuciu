package demo.service.report;

import demo.database.Constants;
import org.springframework.stereotype.Service;

@Service
public class ReportGeneratorFactory {

    public ReportGenerator getReportGenerator(String type){
        if (type.equals(Constants.Reports.CSV)){
            return new ReportGeneratorCSV();
        }
        if (type.equals(Constants.Reports.PDF)){
            return new ReportGeneratorPDF();
        }
        return null;
    }
}
