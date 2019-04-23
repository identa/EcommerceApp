package com.example.ecommerceapp.adapters;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.CartItemModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {
    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()){
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
                default:
                    return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout, viewGroup, false);
                return new CartItemViewHolder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout, viewGroup, false);
                return new CartTotalAmountViewHolder(cartTotalView);
                default:
                    return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (cartItemModelList.get(i).getType()){
            case CartItemModel.CART_ITEM:
                int id = cartItemModelList.get(i).getProductID();
                String resource = cartItemModelList.get(i).getProductImage();
                String title = cartItemModelList.get(i).getProductTitle();
                double productPrice = cartItemModelList.get(i).getProductPrice();
                double cuttedPrice = cartItemModelList.get(i).getCuttedPrice();
                int quantity = cartItemModelList.get(i).getProductQuantity();
                ((CartItemViewHolder) viewHolder).setItemDetails(id, resource, title,productPrice, cuttedPrice, quantity);
                break;
                case CartItemModel.TOTAL_AMOUNT:
                    String totalItems = cartItemModelList.get(i).getTotalItems();
                    double totalItemPrice = cartItemModelList.get(i).getTotalItemPrice();
                    double totalAmount = cartItemModelList.get(i).getTotalAmount();

                    ((CartTotalAmountViewHolder) viewHolder).setTotalAmount(totalItems, totalItemPrice,totalAmount);
                    break;
            default:
                        return;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView productQuantity;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
        }

        private void setItemDetails(int id, String resource, String title, double productPriceText, double cuttedPriceText, int quantity){
//            productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(productImage);
            productTitle.setText(title);
            productPrice.setText(String.format("$%s", productPriceText));
            cuttedPrice.setText(String.format("$%s", cuttedPriceText));
            productQuantity.setText(String.format("Qty: %s", quantity));

            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    final EditText quantityNo = quantityDialog.findViewById(R.id.quantity_no);
                    Button cancelBtn = quantityDialog.findViewById(R.id.cancel_btn);
                    Button okBtn = quantityDialog.findViewById(R.id.ok_btn);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });

                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText(String.format("Qty: %s", quantityNo.getText()));
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();

                }
            });
        }
    }

    class CartTotalAmountViewHolder extends RecyclerView.ViewHolder{

        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);

            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
        }

        private void setTotalAmount(String totalItemText, double totalItemPriceText, double totalAmountText){
            totalItems.setText(totalItemText);
            totalItemPrice.setText(String.format("$%s", totalItemPriceText));
            totalAmount.setText(String.format("$%s", totalAmountText));
        }
    }
}
