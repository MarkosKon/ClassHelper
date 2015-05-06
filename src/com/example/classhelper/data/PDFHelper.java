package com.example.classhelper.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;

import com.example.classhelper.R;
import com.example.classhelper.model.Grade;
import com.example.classhelper.model.Model;
import com.example.classhelper.model.Student;
import com.example.classhelper.model.Test;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFHelper 
{
   public static final String TAG = "PDFHelper";
   private static BaseFont mFont ;
   
   private Context mAppContext;
   private String mFileName;
   private Model mModel;
   private Document mDocument;
   
   public PDFHelper(Student student, Context context)
   		throws IOException, DocumentException
   {
	   try
	   {
		   // Use custom unicode font for Greek character support.
		   mFont = BaseFont.createFont("/assets/fonts/arial.ttf", 
				   	  "Cp1253", 
					  BaseFont.EMBEDDED);
	   }
	   catch (IOException e)
	   {
		   e.printStackTrace();
	   }
	   mModel = student;
	   mAppContext = context;
	   mFileName = student.toString() + " - Grades.pdf";
   }
   
   public PDFHelper(Test test, Context context) 
		   throws IOException, DocumentException
   {
	   try
	   {
		   // Use custom unicode font for Greek character support.
		   mFont = BaseFont.createFont("/assets/fonts/arial.ttf", 
				   	  "Cp1253", 
					  BaseFont.EMBEDDED);
	   }
	   catch (IOException e)
	   {
		   e.printStackTrace();
	   }
	   mModel = test;
	   mAppContext = context;
	   mFileName = test.getCourse().toString() +
			       " - " + 
			       test.getName() + 
			       "- Grades.pdf";
   }
   
   public boolean createReport() throws IOException
   {
	   try
	   {
		   mDocument = new Document();
		   
		   // If the SD card in mounted on the device.
		   if (isExternalStorageWritable())
		   {
			   File file = new File(Environment.getExternalStoragePublicDirectory(
			            Environment.DIRECTORY_DOWNLOADS), 
			            mFileName);
			   FileOutputStream outputStream = new FileOutputStream(file);
			   PdfWriter.getInstance(mDocument, outputStream);
		   }
		   else
			   return false;
		   
		   mDocument.open();
		   addMetaData();
		   if (mModel instanceof Test)
			   addTestContent(mDocument);
		   else if (mModel instanceof Student)
		   		addStudentContent(mDocument);
		   mDocument.close();
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   return true;
   }

   /**
    * This method adds meta-data to the PDF document which can be
    * viewed under File -> Properties. 
    */
   private void addMetaData() 
   {
	   mDocument.addTitle(mFileName);
	   mDocument.addSubject("Report");
	   mDocument.addKeywords("Android, Java, PDF, iText");
	   mDocument.addAuthor("ClassHelper");
	   mDocument.addCreator("ClassHelper");
   }
   
   private void addTestContent(Document document) 
		   throws DocumentException, BadElementException 
   {
	   String string = ((Test) mModel).getCourse().getModule().getName();
	   Paragraph paragraph = new Paragraph(mAppContext.getResources()
			   								.getString(R.string.student_module_id_label) + 
			   								": " + string, 
   											new Font(mFont, 16)); 
	   										// Oups :/
	   document.add(paragraph);
	   
	   string = ((Test) mModel).getCourse().getName();
	   paragraph = new Paragraph(mAppContext.getResources().getString(R.string.test_course_id_label) + 
			   						": " + string, 
			   						new Font(mFont, 16));
	   document.add(paragraph);
	   
	   string = ((Test) mModel).getName();
	   paragraph = new Paragraph(mAppContext.getResources().getString(R.string.grade_test_id_label) + 
			   					 ": " + string,
			   					 new Font(mFont, 16));
	   document.add(paragraph);
	   
	   document.add(Chunk.NEWLINE);
	   
	   // Add a table
	   ArrayList<Grade> grades = GradeDAO.get(mAppContext)
			   .getGradesByTest((Test) mModel);
	   PdfPTable table = new PdfPTable(3); // Number of table columns.
	   
	   PdfPCell c1 = new PdfPCell(new Phrase(mAppContext.getResources()
												.getString(R.string.model_id_label),
											 new Font(mFont, 14)));
	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	   table.addCell(c1);
		
	   c1 = new PdfPCell(new Phrase("Student Name"));
	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	   table.addCell(c1);
	   
	   c1 = new PdfPCell(new Phrase(mAppContext.getResources()
										.getString(R.string.grade_value_label),
								    new Font(mFont, 14)));
	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	   table.addCell(c1);
	   
	   table.setHeaderRows(1);
	   
	   for (Grade g : grades)
	   {
		   table.addCell(String.valueOf(g.getStudent().getId()));
		   table.addCell(new PdfPCell(new Phrase(g.getStudent().toString(),
				   								 	new Font(mFont, 12))));
		   table.addCell(String.valueOf(g.getGradeValue()));
	   }
	   document.add(table);
   }
	

   private void addStudentContent(Document document) 
   		throws DocumentException, BadElementException
   {
	   String string = ((Student) mModel).getModule().getName();
	   Paragraph paragraph = new Paragraph(mAppContext.getResources()
			   								.getString(R.string.student_module_id_label) +
			   							   ": " + string,
			   							   new Font(mFont, 16));
	   document.add(paragraph);
	   
	   string = String.valueOf(((Student) mModel).getId());
	   paragraph = new Paragraph(mAppContext.getResources()
			   								.getString(R.string.model_id_label) + string,
			   					 new Font(mFont, 16));
	   document.add(paragraph);
	   
	   string = ((Student) mModel).toString();
	   paragraph = new Paragraph(mAppContext.getResources()
					.getString(R.string.grade_student_id_label) +
				   ": " + string,
				   new Font(mFont, 16));
	   document.add(paragraph);
	   
	   document.add(Chunk.NEWLINE);
	   
	   // Add a table.
	   ArrayList<Grade> grades = GradeDAO.get(mAppContext)
			   .getGradesByStudent((Student) mModel);
	   PdfPTable table = new PdfPTable(3); // Number of table columns.
	   
	   PdfPCell c1 = new PdfPCell(new Phrase(mAppContext.getResources()
			   									.getString(R.string.course_name_label),
			   								 new Font(mFont, 14)));
	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	   table.addCell(c1);
		
	   c1 = new PdfPCell(new Phrase(mAppContext.getResources()
										.getString(R.string.test_name_label),
										new Font(mFont, 14)));
	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	   table.addCell(c1);
	   
	   c1 = new PdfPCell(new Phrase(mAppContext.getResources()
										.getString(R.string.grade_value_label),
										new Font(mFont, 14)));
	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	   table.addCell(c1);
	   
	   table.setHeaderRows(1);
	   
	   for (Grade g : grades)
	   {
		   table.addCell(new PdfPCell(new Phrase(g.getTest().getCourse().getName(),
				   								 new Font(mFont, 12))));
		   table.addCell(new PdfPCell(new Phrase(g.getTest().getName(),
				   								 	new Font(mFont, 12))));
		   table.addCell(String.valueOf(g.getGradeValue()));
	   }
	   document.add(table);
   }
   
	/* Checks if external storage is available for read and write */
   private boolean isExternalStorageWritable() 
   {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) 
	        return true;
	    return false;
    }
}
