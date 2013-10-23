package com.ikimuhendis.android.sample.fragments;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;

public abstract class BaseFragment extends SherlockFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setTitle();
	}

	protected void setTitle() {
		getActivity().setTitle(getTitleResourceId());
	}

	public abstract int getTitleResourceId();

}
