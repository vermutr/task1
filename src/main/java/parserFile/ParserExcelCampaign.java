package parserFile;

import model.Campaign;
import org.apache.poi.ss.usermodel.*;
import service.Actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ParserExcelCampaign {

    Actions actions=new Actions();

    public void convertFileToObject(String file) {
        List<Campaign> myList = new ArrayList<>();
        try {
            Workbook wb = WorkbookFactory.create(new File(file));
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                Campaign campaign = new Campaign();
                myList.add(Actions.assignCampaign(row, campaign));
                if(myList.size()==10){
                    List<Campaign> campaignList=actions.statusDisabled(myList);
                    actions.writeToXSLX(campaignList);
                    actions.threadHandling(myList);
                    myList.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


