package com.example.classhelper.fragment;

import com.example.classhelper.R;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EmailFragment extends Fragment
{
	public static final String TAG = "EmailFragment";
	public static final String EXTRA_RECIPIENT_LIST = "java.lang.String";
	
	private String[] mRecipients;
	
	private EditText mSubjectEditText;
	private EditText mBodyEditText;
	private TextView mRecipientsTextView;
	private Button mSendButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mRecipients =  (String[]) getActivity().getIntent()
				.getSerializableExtra(EXTRA_RECIPIENT_LIST);
		
		getActivity().setTitle("Email");
		
		setRetainInstance(true);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_email, parent, false);
		
		mSubjectEditText = (EditText) v.findViewById(R.id.email_subject);
		
		mBodyEditText = (EditText) v.findViewById(R.id.email_body);
		
		mRecipientsTextView = (TextView) v.findViewById(R.id.email_recipient_list);
		String recipients = new String();
		for(String r : mRecipients)
		{
			recipients += r + "\n";
		}
		mRecipientsTextView.setText(recipients);
		
		mSendButton = (Button) v.findViewById(R.id.email_send);
		mSendButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, mRecipients);
				i.putExtra(Intent.EXTRA_SUBJECT, mSubjectEditText.getText().toString());
				i.putExtra(Intent.EXTRA_TEXT, mBodyEditText.getText());
				try
				{
					startActivity(Intent.createChooser(i, "Send email..."));
				}
				catch (android.content.ActivityNotFoundException ex)
				{
					Toast.makeText(getActivity(), "There are no email client installed", 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		return v;
	}
}
