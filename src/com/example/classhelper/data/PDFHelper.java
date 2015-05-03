package com.example.classhelper.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;

import com.example.classhelper.model.Grade;
import com.example.classhelper.model.Model;
import com.example.classhelper.model.Student;
import com.example.classhelper.model.Test;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
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
   {
	   mModel = student;
	   mAppContext = context;
	   mFileName = student.toString() + " - Grades.pdf";
	   try
	   {
		   mDocument = new Document();
		   File file = new File(Environment.getExternalStoragePublicDirectory(
		            Environment.DIRECTORY_DOWNLOADS), 
		            mFileName);
		   FileOutputStream outputStream = new FileOutputStream(file);
		   PdfWriter.getInstance(mDocument, outputStream);
		   addMetaData();
		   //addStudentContent(mDocument);
		   mDocument.close();
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
   }
   
   public PDFHelper(Test test, Context context)
   {
	   mModel = test;
	   mAppContext = context;
	   mFileName = test.getCourse().toString() +
			       " - " + 
			       test.toString() + 
			       "- Grades.pdf";
   }
   
   public void createTestReport() throws IOException
   {
	   try
	   {
		   mFont = BaseFont.createFont("/assets/fonts/arial.ttf", 
				   	  "Cp1253", 
					  BaseFont.EMBEDDED);
		   mDocument = new Document();
		   File file = new File(Environment.getExternalStoragePublicDirectory(
		            Environment.DIRECTORY_DOWNLOADS), 
		            mFileName);
		   FileOutputStream outputStream = new FileOutputStream(file);
		   PdfWriter.getInstance(mDocument, outputStream);
		   mDocument.open();
		   addMetaData();
		   addTestContent(mDocument);
		   mDocument.close();
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
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
	   Anchor anchor = new Anchor("First Chapter");
	   anchor.setName("First Chapter");
		
	   // Second parameter is the number of the chapter
	   Chapter catPart = new Chapter(new Paragraph(anchor), 1);
		
	   Paragraph subPara = new Paragraph("Subcategory 1", new Font(mFont, 16));
	   Section subCatPart = catPart.addSection(subPara);
	   subCatPart.add(new Paragraph("\u039d\u03cd\u03c6\u03b5\u03c2", new Font(mFont, 16)));
		
	   subPara = new Paragraph("Subcategory 2", new Font(mFont, 16));
	   subCatPart = catPart.addSection(subPara);
	   subCatPart.add(new Paragraph("Paragraph 1"));
	   subCatPart.add(new Paragraph("Paragraph 2"));
	   subCatPart.add(new Paragraph("Paragraph 3"));
		
	   // add a table
	   ArrayList<Grade> grades = GradeDAO.get(mAppContext)
			   .getGradesByTest((Test) mModel);
	   PdfPTable table = new PdfPTable(2);
	   
	   PdfPCell c1 = new PdfPCell(new Phrase("Student Name"));
	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	   table.addCell(c1);
		
	   c1 = new PdfPCell(new Phrase("Grade"));
	   c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	   table.addCell(c1);
	   table.setHeaderRows(1);
	   
	   for (Grade g : grades)
	   {
		   table.addCell(new PdfPCell(new Phrase(g.getStudent().toString(),
				   								 new Font(mFont, 12))));
		   table.addCell(String.valueOf(g.getGradeValue()));
	   }
	   subCatPart.add(table);
		
	   // now add all this to the document
	   document.add(catPart);
		
	   // Next section
	   anchor = new Anchor("Second Chapter");
	   anchor.setName("Second Chapter");
		
	   // Second parameter is the number of the chapter
	   catPart = new Chapter(new Paragraph(anchor), 1);
		
	   subPara = new Paragraph("Subcategory", new Font(mFont, 16));
	   subCatPart = catPart.addSection(subPara);
	   subCatPart.add(new Paragraph("This is a very important message"));
		
	   // now add all this to the document
	   document.add(catPart);
   }
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() 
	{
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) 
	        return true;
	    return false;
	}
}
