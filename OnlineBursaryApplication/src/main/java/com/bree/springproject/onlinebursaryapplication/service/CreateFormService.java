package com.bree.springproject.onlinebursaryapplication.service;

import com.bree.springproject.onlinebursaryapplication.Entity.ApplicationFormCreateTable;
import com.bree.springproject.onlinebursaryapplication.models.Months;
import com.bree.springproject.onlinebursaryapplication.repository.FormCreateRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Data
public class CreateFormService {

    @Autowired
    FormCreateRepository formCreateRepository;

    public ResponseEntity<String> createSectionA(Map<String,
            String> sectionA, String month, Long userId, String section) {

        log.info("Forwarded the request to create the form");


        //validate the month format.
        log.info("Validating the month format.");

        month = month.toLowerCase();

        //get the numerical value of the month provided.
        int monthValue = Months.valueOf(month).ordinal();
       //getting the year.
        int currentYear = Year.now().getValue();

        //getting the total value of the month-field for the form.
        String monthFieldValue = String.valueOf((monthValue + currentYear));

        log.info("Proceeding with insertion.");
        List<String> fields = new ArrayList<>(sectionA.keySet());
        List<ApplicationFormCreateTable> sectionAColumns = new ArrayList<>();

        for(String field : fields)
        {
            ApplicationFormCreateTable sectionAColumn = new ApplicationFormCreateTable();

            sectionAColumn.setUserId(userId);
            sectionAColumn.setBursaryMonth(monthFieldValue);
            sectionAColumn.setField(field);
            sectionAColumn.setType(sectionA.get(field));
            sectionAColumn.setSection(section);
            sectionAColumns.add(sectionAColumn);
        }

        //batch update.
        formCreateRepository.saveAll(sectionAColumns);

        return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
    }




    /*This method is responsible for updating the form */
    public ResponseEntity<String> updateForm(List<ApplicationFormCreateTable> updatedSection) {

        log.info("Forwarded the request to update the form");

        //here we batch update the form.
        formCreateRepository.saveAll(updatedSection);

        log.info("Updated successfully");
        return new ResponseEntity<>("Update successful", HttpStatus.OK);
    }


    public ResponseEntity<List<List<ApplicationFormCreateTable>>> getForm() {

        log.info("Forwarded request to get the form");

        int year = Year.now().getValue();

        int month = LocalDate.now().getMonthValue();

        int searchValue = month + year;

        List<ApplicationFormCreateTable> applicationForm;


        //getting the complete form.
        do{
            applicationForm = formCreateRepository.findAllByBursaryMonth(String.valueOf(searchValue));
            searchValue = searchValue - 1;

        }while(applicationForm.isEmpty());


        //where we need to break down the form into sections.

        return new ResponseEntity<>( sortingForm(applicationForm, 0), HttpStatus.OK);
    }

    private List<List<ApplicationFormCreateTable>> sortingForm(
            List<ApplicationFormCreateTable> applicationForm, int year) {
        //list to hold the sorted form.
        List<List<ApplicationFormCreateTable>> sortedForm = new ArrayList<>();
        List<ApplicationFormCreateTable> section = new ArrayList<>();
        log.info("Breaking down the form into sections");

        /*In the for loop below, I am grouping form in sections as provided from the database.
        * We will also decode the month from the given integer*/

        String previousSection = null;

        for(ApplicationFormCreateTable row : applicationForm) {


            /*Here we need to decode the month name from the given number
            This method may be reused when coding the functionality where the
            user enters a specific month when they want to get the form for, so will check if the
            year provided is 0*/
            log.info("decoding the months");
            int currentYear = year;

            if(year == 0)
            {
                currentYear = Year.now().getValue();
            }

            int month = Integer.parseInt(row.getBursaryMonth()) - currentYear;

            String formMonth = String.valueOf(Months.values()[month]);

            row.setBursaryMonth(formMonth);


            log.info("Moving to grouping of the form");

            String currentSection = row.getSection();

            if (currentSection.equals(previousSection)) {
                section.add(row);
            } else {
                sortedForm.add(section);
                section = new ArrayList<>();
                section.add(row);
                previousSection = currentSection;
            }

        }

        log.info("Grouping of the form done.");
        return sortedForm;
    }
}
