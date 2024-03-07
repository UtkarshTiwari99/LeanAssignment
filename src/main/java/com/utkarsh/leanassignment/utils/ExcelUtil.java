package com.utkarsh.leanassignment.utils;

import com.utkarsh.leanassignment.model.Option;
import com.utkarsh.leanassignment.model.Question;
import com.utkarsh.leanassignment.model.TestData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelUtil {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

//    static List<String> HEADERS = new ArrayList<>();
    static String SHEET = "TestData";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static TestData excelToTestData(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            TestData testData = new TestData();
            List<Question> questions= new ArrayList<Question>();

            int rowNumber = 0;
            int numberOfOptions=0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    Iterator<Cell> cellsInRow = currentRow.iterator();
                    var column=0;
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();
                        if(currentCell.getStringCellValue().isEmpty())
                            break;
                        if(currentCell.getStringCellValue().contains("op_"))
                            numberOfOptions++;
                    }
                    rowNumber++;
                    System.out.println(numberOfOptions);
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                int cellIdx = 1;

                var question= new Question();
                while (cellsInRow.hasNext()) {

                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 1:
                            System.out.println(currentCell.getNumericCellValue());
                            question.setQuestionNumber((int) currentCell.getNumericCellValue());
                            break;

                        case 2:
                            System.out.println(currentCell.getStringCellValue());
                            question.setStatement(currentCell.getStringCellValue());
                            break;

                        case 3:
                            System.out.println(currentCell.getNumericCellValue());
                            question.setMarks((int)currentCell.getNumericCellValue());
                            break;

                        default:
                            if(cellIdx<=4+numberOfOptions*2&&cellIdx>=4){
                                var op=new Option();
                                op.setOp_number((cellIdx+2)/2);
                                op.setContent(currentCell.getStringCellValue());
                                currentCell=cellsInRow.next();
                                op.setNextQuestion((int)currentCell.getNumericCellValue());
                                question.getOptions().add(op);
                            }
                            break;
                    }

                    cellIdx++;
                }
                questions.add(question);
            }

            workbook.close();
            System.out.println(questions);
            return new TestData("1",questions);
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}