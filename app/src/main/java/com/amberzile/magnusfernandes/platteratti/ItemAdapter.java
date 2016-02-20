package com.amberzile.magnusfernandes.platteratti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
public class ItemAdapter extends ArrayAdapter{
    List list = new ArrayList();
    Context ctx;
    public ItemAdapter(Context context, int resource) {
        super(context, resource);
        this.ctx = context;
    }
    public void add(Item object) {
        list.add(object);
        super.add(object);
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder itemHolder;
        if (row==null){
            itemHolder = new ItemHolder();
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.listview_mainmenu, parent, false);
            itemHolder.tx_title = (TextView)row.findViewById(R.id.itemTitle);
            itemHolder.tx_description = (TextView)row.findViewById(R.id.itemDescription);
            itemHolder.tx_price = (TextView)row.findViewById(R.id.itemPrice);
            itemHolder.tx_image = (ImageView)row.findViewById(R.id.itemImage);
            itemHolder.tx_button = (ImageButton)row.findViewById(R.id.cartButton);
            itemHolder.tx_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout rl = (RelativeLayout)v.getParent();
                    TextView tv = (TextView)rl.findViewById(R.id.itemTitle);
                    ImageView iv = (ImageView)rl.findViewById(R.id.itemImage);
                    String text = tv.getText().toString();
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                }
            });
            row.setTag(itemHolder);
        }
        else{
            itemHolder = (ItemHolder)row.getTag();
        }
        Item item = (Item)getItem(position);
        itemHolder.tx_title.setText(item.getTitle());
        itemHolder.tx_description.setText(item.getDescription());
        itemHolder.tx_price.setText(String.valueOf(item.getPrice()));
        byte[] outImage = item.getImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        if (theImage!=null){
            itemHolder.tx_image.setImageBitmap(theImage);
        }
        return row;
    }
    static class ItemHolder{
        TextView tx_title, tx_description, tx_price;
        ImageView tx_image;
        ImageButton tx_button;
    }
}