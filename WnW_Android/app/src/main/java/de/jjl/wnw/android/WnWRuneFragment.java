package de.jjl.wnw.android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WnWRuneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WnWRuneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WnWRuneFragment extends Fragment
{
	public WnWRuneFragment()
	{
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//if (getArguments() != null)
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_rune, container, false);
	}
}
