package com.liferay.mobile.formsscreenletdemo.view;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.formsscreenletdemo.R;

/**
 * @author Victor Oliveira
 */
public class ContentViewHolder extends ViewHolder {

	TextView dateTextView;
	TextView descriptionTextView;
	TextView valueTextView;

	public ContentViewHolder(View itemView) {
		super(itemView);

		dateTextView = itemView.findViewById(R.id.date_row);
		descriptionTextView = itemView.findViewById(R.id.description_row);
		valueTextView = itemView.findViewById(R.id.value_row);
	}
}
