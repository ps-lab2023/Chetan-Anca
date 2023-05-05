package com.softwareProject.PetClinicProject.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.softwareProject.PetClinicProject.exception.AppointmentNotFoundException;
import com.softwareProject.PetClinicProject.model.Appointment;
import com.softwareProject.PetClinicProject.model.MedicalFacility;
import com.softwareProject.PetClinicProject.service.AppointmentService;
import com.softwareProject.PetClinicProject.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static com.itextpdf.text.FontFactory.getFont;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private AppointmentService appointmentService;

    private static final String[] HEADERS = new String[]{"No", "Doctor Name", "Animal Name", "Date", "Medical Facilities", "Prices", "Total"};

    public void generateBill(long id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentService.getAppointmentById(id);

        String nr = Long.toString(appointment.getAppointmentId());
        String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
        String animalName = appointment.getAnimal().getName();
        String date = appointment.getDate().toString();
        String medicalFacilities = "";
        String prices = "";
        String total = Integer.toString(appointment.computePrice());

        for (MedicalFacility facility : appointment.getMedicalFacilities()) {
            medicalFacilities += facility.getName() + "\n";
            prices += facility.getPrice() + "\n";
        }

        Document document = new Document(PageSize.LETTER.rotate());

        try {
            PdfWriter.getInstance(document, new FileOutputStream("Appointment" + id + "_bill" + ".pdf"));
            document.open();

            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font fontRow = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

            PdfPTable table = new PdfPTable(HEADERS.length);
            for (String header : HEADERS) {
                PdfPCell cell = new PdfPCell();
                cell.setGrayFill(0.9f);
                cell.setPhrase(new Phrase(header.toUpperCase(), fontHeader));
                table.addCell(cell);
            }
            table.completeRow();

            Phrase phrase = new Phrase(nr, fontRow);
            table.addCell(new PdfPCell(phrase));
            Phrase phrase1 = new Phrase(doctorName, fontRow);
            table.addCell(new PdfPCell(phrase1));
            Phrase phrase2 = new Phrase(animalName, fontRow);
            table.addCell(new PdfPCell(phrase2));
            Phrase phrase3 = new Phrase(date, fontRow);
            table.addCell(new PdfPCell(phrase3));
            Phrase phrase4 = new Phrase(medicalFacilities, fontRow);
            table.addCell(new PdfPCell(phrase4));
            Phrase phrase5 = new Phrase(prices, fontRow);
            table.addCell(new PdfPCell(phrase5));
            Phrase phrase6 = new Phrase(total, fontRow);
            table.addCell(new PdfPCell(phrase6));
            table.completeRow();

            Paragraph chunk = new Paragraph("Appointment bill", getFont("Header"));
            chunk.setAlignment(Element.ALIGN_CENTER);
            document.add(chunk);
            Paragraph paragraph = new Paragraph("\n \n", getFont("Data"));
            document.add(paragraph);
            document.add(table);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
