package com.example.classhelper.fragment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.classhelper.R;
//import com.example.classhelper.adapter.MyPrintDocumentAdapter;
import com.example.classhelper.data.StudentDAO;
import com.example.classhelper.data.TestDAO;
import com.example.classhelper.model.Grade;
import com.example.classhelper.model.Student;
import com.example.classhelper.model.Test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.PrintAttributes.MediaSize;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class GradePagerFragment extends Fragment 
	implements OnItemSelectedListener
{
	public static final String TAG = "GradePagerFragment";
	public static final String EXTRA_GRADE = "com.example.criminalintent.grade";
	private Grade mGrade;
	
	private TextView mIdTextView;
	private EditText mValueEditText;
	private Spinner mStudentSpinner;
	private Spinner mTestSpinner;
	private Button mSaveButton;
	
	private Callbacks mCallbacks;
	
	public interface Callbacks
	{
		void onListItemUpdate(Grade grade);
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mCallbacks = (Callbacks) activity;
	}
	
	@Override
	public void onDetach()
	{
		super.onDetach();
		mCallbacks = null;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mGrade = (Grade) getArguments().getSerializable(EXTRA_GRADE);
		setHasOptionsMenu(true);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_grade, parent, false);
		
		// p.325.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			// p.327. the if on the statement inside the if.
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);	
			}
		}
		
		mIdTextView = (TextView) v.findViewById(R.id.grade_id);
		mIdTextView.setText(mIdTextView.getText() + String.valueOf(mGrade.getId()));
		
		mValueEditText = (EditText) v.findViewById(R.id.grade_grade_value);
		mValueEditText.setText(String.valueOf(mGrade.getGradeValue()));
		mValueEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				if (s.length() > 0)
					mGrade.setGradeValue(Integer.parseInt(s.toString())); // oh boy.
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// Setup a spinner with the available last names of the students.
		mStudentSpinner = (Spinner) v.findViewById(R.id.grade_student_id);
		ArrayList<Student> students = StudentDAO.get(getActivity()).getAllStudents();
		ArrayList<String> studentNames = new ArrayList<String>();
		for (Student s : students)
		{
			studentNames.add(s.getLastName());
		}
		if (mGrade.getStudent().getLastName() != null)
		{
			studentNames.remove(mGrade.getStudent().getLastName());
			studentNames.add(0, mGrade.getStudent().getLastName());
		}
		
		ArrayAdapter<String> spinnerAdapter	= new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, studentNames);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mStudentSpinner.setAdapter(spinnerAdapter);
		mStudentSpinner.setOnItemSelectedListener(this);
		
		// Setup a spinner with the available names of the tests.
		mTestSpinner = (Spinner) v.findViewById(R.id.grade_test_id);
		ArrayList<Test> tests = TestDAO.get(getActivity()).getAllTests();
		ArrayList<String> testNames = new ArrayList<String>();
		for (Test t : tests)
		{
			testNames.add(t.getName());
		}
		if (mGrade.getTest().getName() != null)
		{
			testNames.remove(mGrade.getTest().getName());
			testNames.add(0, mGrade.getTest().getName());
		}
		
		ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, testNames);
		spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mTestSpinner.setAdapter(spinnerAdapter1);
		mTestSpinner.setOnItemSelectedListener(this);
		
		mSaveButton = (Button) v.findViewById(R.id.grade_save);
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				mCallbacks.onListItemUpdate(mGrade);
			}
		});
		
		return v;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				if (NavUtils.getParentActivityName(getActivity()) != null)
					NavUtils.navigateUpFromSameTask(getActivity());
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public static GradePagerFragment newInstance(Grade grade)
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_GRADE, grade);
		
		GradePagerFragment fragment = new GradePagerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) 
	{
		Spinner spinner = (Spinner) parent;
		if (spinner.getId() == R.id.grade_student_id)
		{
			Student student = StudentDAO.get(getActivity())
					.getStudentByLastName(parent.getItemAtPosition(position).toString());
			mGrade.setStudent(student);
		}
		else if (spinner.getId() == R.id.grade_test_id)
		{
			Test test = TestDAO.get(getActivity())
					.getTestByName(parent.getItemAtPosition(position).toString());
			mGrade.setTest(test);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	@TargetApi(19)
	private void doPrint() 
	{
	    // Get a PrintManager instance
	    PrintManager printManager = (PrintManager) getActivity()
	            .getSystemService(Context.PRINT_SERVICE);

	    // Set job name, which will be displayed in the print queue
	    String jobName = getActivity().getString(R.string.app_name) + " Document";

	    // Start a print job, passing in a PrintDocumentAdapter implementation
	    // to handle the generation of a print document
	    printManager.print(jobName, new MyPrintDocumentAdapter(getActivity()), null); //
	}
	
	@TargetApi(19)
	public class MyPrintDocumentAdapter extends PrintDocumentAdapter 
	{
		private PrintedPdfDocument mPdfDocument;
		private Context mAppContext;
		
		public MyPrintDocumentAdapter(Context context) 
		{
			super();
			mAppContext = context;
		}

		@Override
		public void onLayout(PrintAttributes oldAttributes,
							 PrintAttributes newAttributes,
						 	CancellationSignal cancellationSignal,
						 	LayoutResultCallback callback, 
						 	Bundle metadata) 
		{
			// Create a new PdfDocument with the requested page attributes
		    mPdfDocument = new PrintedPdfDocument(mAppContext, newAttributes);

		    // Respond to cancellation request
		    if (cancellationSignal.isCanceled() ) 
		    {
		        callback.onLayoutCancelled();
		        return;
		    }

		    // Compute the expected number of printed pages
		    int pages = computePageCount(newAttributes);

		    if (pages > 0) 
		    {
		        // Return print information to print framework
		        PrintDocumentInfo info = new PrintDocumentInfo
		        		.Builder("print_output.pdf")
		        		.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
		        		.setPageCount(pages)
		        		.build();
		        // Content layout reflow is complete
		        callback.onLayoutFinished(info, true);
		    } 
		    else 
		    {
		        // Otherwise report an error to the print framework
		        callback.onLayoutFailed("Page count calculation failed.");
		    }
		}

		@Override
		public void onWrite(PageRange[] pageRanges, 
							ParcelFileDescriptor destination,
							CancellationSignal cancellationSignal, 
							WriteResultCallback callback) 
		{
			// Iterate over each page of the document,
		    // check if it's in the output range.
		    for (int i = 0; i < 1/*totalPages*/; i++) 
		    {
		        // Check to see if this page is in the output range.
		        if (containsPage(pageRanges, i)) 
		        {
		            // If so, add it to writtenPagesArray. writtenPagesArray.size()
		            // is used to compute the next output page index.
		            //writtenPagesArray.append(writtenPagesArray.size(), i);
		            PdfDocument.Page page = mPdfDocument.startPage(i);

		            // check for cancellation
		            if (cancellationSignal.isCanceled()) {
		                callback.onWriteCancelled();
		                mPdfDocument.close();
		                mPdfDocument = null;
		                return;
		            }

		            // Draw page content for printing
		            drawPage(page);

		            // Rendering is complete, so page can be finalized.
		            mPdfDocument.finishPage(page);
		        }
		    }

		    // Write PDF document to file
		    try 
		    {
		        mPdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));
		    } 
		    catch (IOException e) 
		    {
		        callback.onWriteFailed(e.toString());
		        return;
		    } 
		    finally 
		    {
		        mPdfDocument.close();
		        mPdfDocument = null;
		    }
		    PageRange[] writtenPages = computeWrittenPages();
		    // Signal the print framework the document is complete
		    callback.onWriteFinished(writtenPages);
		}
		
		private boolean containsPage(PageRange[] pageRanges, int i) 
		{
			// TODO Auto-generated method stub
			return false;
		}

		// TODO
		private PageRange[] computeWrittenPages() 
		{
			
			return null;
		}

		private void drawPage(PdfDocument.Page page) 
		{
			Canvas canvas = page.getCanvas();

		    // units are in points (1/72 of an inch)
		    int titleBaseLine = 72;
		    int leftMargin = 54;

		    Paint paint = new Paint();
		    paint.setColor(Color.BLACK);
		    paint.setTextSize(36);
		    canvas.drawText("Test Title", leftMargin, titleBaseLine, paint);

		    paint.setTextSize(11);
		    canvas.drawText("Test paragraph", leftMargin, titleBaseLine + 25, paint);

		    paint.setColor(Color.BLUE);
		    canvas.drawRect(100, 100, 172, 172, paint);
		}

		private int computePageCount(PrintAttributes printAttributes) 
		{
			int itemsPerPage = 4; // default item count for portrait mode

		    MediaSize pageSize = printAttributes.getMediaSize();
		    if (!pageSize.isPortrait()) 
		    {
		        // Six items per page in landscape orientation
		        itemsPerPage = 6;
		    }
		
		    // Determine number of print items
		    int printItemCount = getPrintItemCount();
		
		    return (int) Math.ceil(printItemCount / itemsPerPage);
		}
		
		// TODO
		private int getPrintItemCount() 
		{
			return 1;
		}
	}
}
