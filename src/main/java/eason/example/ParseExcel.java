package eason.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParseExcel {
    public static void parseExcel(String schoolname) {
        System.out.println("-Start parsing " + schoolname + ".xlsx-");
        String mainpath = "src/main/resources/SchoolData/";
        File folder = new File(mainpath);
        File[] listOfSchool = folder.listFiles();
        if (listOfSchool != null) {
            // get school name
            for (File file1 : listOfSchool) {
                if (file1.isDirectory() && file1.getName().equals(schoolname)) {
                    School school = new School();
                    school.setName(schoolname);
                    File[] listOfRestaurant = file1.listFiles();
                    // get restaurant name
                    for (File file2 : listOfRestaurant) {
                        if (file2.isDirectory()) {
                            school.append_restaurant(file2.getName());
                            ArrayList<Restaurant> restaurantlist = school.getRestaurantList();
                            Restaurant restaurant = restaurantlist.get(restaurantlist.size() - 1);
                            File[] listOfVendor = file2.listFiles();
                            // get vendor name
                            for (File file3 : listOfVendor) {
                                if (file3.isFile() && file3.getName().contains(".xlsx")) {
                                    String vendorname = file3.getName().substring(0, file3.getName().lastIndexOf("."));
                                    restaurant.append_vendor(vendorname);
                                    ArrayList<Vendor> vendorlist = restaurant.getVendorList();
                                    Vendor vendor = vendorlist.get(vendorlist.size() - 1);
                                    // start parse excel
                                    String xlsxpath = mainpath + schoolname + "/" + file2.getName() + "/"
                                            + file3.getName();
                                    try (FileInputStream fis = new FileInputStream(xlsxpath);
                                            Workbook workbook = new XSSFWorkbook(fis)) {
                                        Sheet sheet = workbook.getSheetAt(0);
                                        for (Row row : sheet) {
                                            Cell nameCell = row.getCell(0);
                                            Cell priceCell = row.getCell(1);
                                            if (nameCell != null && priceCell != null) {
                                                String name = nameCell.getStringCellValue();
                                                int price;
                                                if (priceCell.getCellType() == CellType.NUMERIC) {
                                                    price = (int) priceCell.getNumericCellValue();
                                                } else if (priceCell.getCellType() == CellType.STRING) {
                                                    price = Integer.parseInt(priceCell.getStringCellValue());
                                                } else {
                                                    price = 0; // 其他情況預設價格為0
                                                }
                                                vendor.append_item(name, price);
                                            }
                                            Cell notCell = row.getCell(2);
                                            if (notCell != null) {
                                                vendor.setNote(notCell.getStringCellValue());
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }
                    }
                    json_operator.writejson(school);
                    System.out.println(schoolname + " parsed success!!");
                } else {
                    System.out.println("NO File Named: " + schoolname);
                }
            }
        }

    }
}