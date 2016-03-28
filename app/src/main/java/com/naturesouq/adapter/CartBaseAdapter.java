package com.naturesouq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naturesouq.R;
import com.naturesouq.common.Cart;
import com.naturesouq.model.CartRowItems;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shahid on 10/21/2015.
 */
public class CartBaseAdapter extends RecyclerView.Adapter<CartBaseAdapter.CustomViewHolder> {

    private List<CartRowItems> cartRowItems;
    private Context mContext;
    int counter = 1;
    private ViewClickListener mViewClickListener;
    float sum;
    TextView tv;

    public CartBaseAdapter(List<CartRowItems> cartRowItems, Context mContext ,TextView tv) {
        this.cartRowItems = cartRowItems;
        this.mContext = mContext;
        this.tv=tv;

        String price[]=new String[cartRowItems.size()];
        for(int i=0;i<cartRowItems.size();i++){
            price[i]=cartRowItems.get(i).getProduct_price();
            sum=sum+Float.parseFloat(price[i]);

        }
       // new Cart().action(""+sum,mContext);
        if(String.valueOf(sum).contains(".")){
            int index =  String.valueOf(sum).indexOf(".");

            if(String.valueOf(sum).substring(index).length() > 3){
                sum=Float.parseFloat(String.valueOf(sum).substring(0, index)+""+String.valueOf(sum).substring(index, (index + 3)));

            }
        }
        tv.setText("AED "+sum);
        sum=0;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,final int position) {

        final CartRowItems feedItem = cartRowItems.get(position);
        if(!(feedItem.getProduct_image_url().equals(""))){
            Picasso.with(mContext).load(feedItem.getProduct_image_url()).error(R.drawable.placeholder_item)
                    .placeholder(R.drawable.placeholder_cart).into(holder.productImage);
        }

        holder.productName.setText(feedItem.getProduct_name());
        holder.productDesc.setText(feedItem.getProduct_shortDesc());
        holder.productPrice.setText("AED " +feedItem.getProduct_price());
       // holder.productImage.setTag(holder);
        holder.addItem.setTag(position);
        holder.removeItem.setTag(position);

        //holder.productImage.setOnClickListener(clickListener);
        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onImageClicked(position);

            }
        });
        holder.counterValue.setText(feedItem.getQty());

        holder.removeProduct.setTag(holder);
        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewClickListener.onRemoveClicked(position);
                //cartRowItems.remove(position);
                //notifyDataSetChanged();
            }
        });


        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(String.valueOf(holder.counterValue.getText())))
                    counter = Integer.parseInt(String.valueOf(holder.counterValue.getText()));
                float price = (Float.parseFloat(feedItem.getProduct_price()) / counter);
                if(counter<10){

                    counter++;
                    feedItem.setQty("" + counter);
                    float reflectedPrice = price * counter;
                    feedItem.setProduct_price("" + reflectedPrice);
                    holder.productPrice.setText("AED " + reflectedPrice);
                    holder.counterValue.setText(Integer.toString(counter));
                    //   new Cart().action(cartRowItems);
                    new CartBaseAdapter(cartRowItems,mContext,tv);

                }else{
                    Toast.makeText(mContext,"You can't able to add more than 10 quantity of a single product.",Toast.LENGTH_LONG).show();
                }



            }
        });


        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 1) {
                    if(!TextUtils.isEmpty(String.valueOf(holder.counterValue.getText())))
                        counter=Integer.parseInt(String.valueOf(holder.counterValue.getText()));

                    float price=0;

                    if(!TextUtils.isEmpty(""+counter)) {
                        price=(Float.parseFloat(feedItem.getProduct_price())/counter);
                        counter--;
                    }

                    feedItem.setQty("" + counter);
                    float reflectedPrice=price*counter;
                    feedItem.setProduct_price("" +reflectedPrice);
                    holder.productPrice.setText("AED " + reflectedPrice);
                   // feedItem.setProduct_price(""+price);
                   // reflectedPrice=0;
                    holder.counterValue.setText(Integer.toString(counter));
                   // new Cart().action(cartRowItems);
                    new CartBaseAdapter(cartRowItems,mContext,tv);

                }

            }

            });


            // holder.counterValue.setText("1");

    }


    @Override
    public int getItemCount() {
        return cartRowItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return cartRowItems.indexOf(getItemId(position));
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView productImage;
        protected TextView productName;
        protected TextView productDesc;
        protected TextView productPrice,removeProduct;
        LinearLayout addItem ,removeItem,main;
        protected EditText counterValue ;



        public CustomViewHolder(View itemView) {
            super(itemView);
            this.productImage = (ImageView)itemView.findViewById(R.id.productImage);
            this.productName = (TextView)itemView.findViewById(R.id.name);
            this.productDesc = (TextView)itemView.findViewById(R.id.discription);
            this.productPrice = (TextView)itemView.findViewById(R.id.price);

            this.addItem = (LinearLayout) itemView.findViewById(R.id.plus);
            this.removeItem = (LinearLayout) itemView.findViewById(R.id.minus);
            this.counterValue = (EditText) itemView.findViewById(R.id.itemCount);
            this.removeProduct = (TextView)itemView.findViewById(R.id.remove);
            this.main = (LinearLayout) itemView.findViewById(R.id.main);


            counterValue.setFocusable(false);

           //  counterValue.setText(""+counter);

//
//            addItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position=(int)v.getTag();
//                    CartRowItems feedItem = cartRowItems.get(position);
//
//
//                   // Toast.makeText(mContext,""+ feedItem.getProduct_name(),Toast.LENGTH_LONG).show();
//                    if (v == addItem) {
//                        if (!TextUtils.isEmpty(String.valueOf(counterValue.getText())))
//                            counter = Integer.parseInt(String.valueOf(counterValue.getText()));
//                        counter++;
//                        feedItem.setQty(""+counter);
//                        counterValue.setText(Integer.toString(counter));
//
//
//
//                    }
//                }
//            });
//            removeItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (v == removeItem) {
//                        if (counter > 1) {
//                            int position=(int)v.getTag();
//                            CartRowItems feedItem = cartRowItems.get(position);
//                            //Toast.makeText(mContext,""+ feedItem.getProduct_name(),Toast.LENGTH_LONG).show();
//
//                            if(!TextUtils.isEmpty(String.valueOf(counterValue.getText())))
//                            counter=Integer.parseInt(String.valueOf(counterValue.getText()));
//                            if(!TextUtils.isEmpty(""+counter))
//                            counter--;
//                            feedItem.setQty(""+counter);
//                            counterValue.setText(Integer.toString(counter));
//                        } else {
//
//                        }
//
//                    }
//                }
//            });


        }
    }

    public List<CartRowItems> getCartRowItems() {
        return cartRowItems;
    }

    public interface ViewClickListener {
        void onImageClicked(int position);
        void onRemoveClicked(int position);
    }

    public void setViewClickListener (ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }


}
