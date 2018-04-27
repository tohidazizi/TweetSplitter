package mj.ghadir.tweetsplitter;

import android.content.Context;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class ItemAdapter extends BaseAdapter {

	private ArrayList<String> arrayText = new ArrayList<String>();
	private Context context;

	public ItemAdapter(Context context, ArrayList<String> arrayText) {
		this.context = context;
		this.arrayText = arrayText;
	}

	@Override
	public int getCount() {
		return arrayText.size();
	}

	@Override
	public String getItem(int position) {
		return arrayText.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = mInflater.inflate(R.layout.item, null);
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

		holder.imgCopy = (ImageView) v.findViewById(R.id.imgCopy);
        holder.edtText = (EditText) v.findViewById(R.id.edtText);
		holder.edtText.setText(arrayText.get(position));
		//strikeThroughText(holder.edtText);

		holder.imgCopy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                String textToCopy = arrayText.get(position);
                int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(textToCopy);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("tweet", textToCopy);
                    clipboard.setPrimaryClip(clip);
                }
                Snackbar.make(v, " Copied ", Snackbar.LENGTH_LONG)
                        .setAction("", null).show();
			}
		});

		
		return v;
	}

	public static class ViewHolder {
		public ImageView imgCopy;
		public EditText edtText;
	}

    private void strikeThroughText(TextView price){
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static String ConvertNumeric(String text) {
        text = text.replace("0", "۰");
        text = text.replace("1", "۱");
        text = text.replace("2", "۲");
        text = text.replace("3", "۳");
        text = text.replace("4", "۴");
        text = text.replace("5", "۵");
        text = text.replace("6", "۶");
        text = text.replace("7", "۷");
        text = text.replace("8", "۸");
        text = text.replace("9", "۹");
        return text;
    }

}