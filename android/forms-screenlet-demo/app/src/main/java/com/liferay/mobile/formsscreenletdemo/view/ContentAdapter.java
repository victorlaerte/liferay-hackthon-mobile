package com.liferay.mobile.formsscreenletdemo.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.liferay.mobile.formsscreenletdemo.R;

/**
 * @author Victor Oliveira
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentViewHolder> {

	Context context;
	JSONArray jsonArray;

	public ContentAdapter(Context context, JSONArray jsonArray) {
		this.context = context;
		this.jsonArray = jsonArray;
	}

	@Override
	public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.content_row, parent, false);
		return new ContentViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ContentViewHolder holder, int position) {

		try {
			JSONObject jsonRow = jsonArray.getJSONObject(position);
			holder.dateTextView.setText(jsonRow.getString("date"));
			holder.descriptionTextView.setText(jsonRow.getString("description"));
			holder.valueTextView.setText(String.valueOf(jsonRow.getDouble("value")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		return jsonArray.length();
	}
}