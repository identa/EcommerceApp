package com.example.ecommerceapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.MyCartFragment;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.CartItemModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;
import com.example.ecommerceapp.models.entities.responses.EditCartResponse;
import com.example.ecommerceapp.models.interfaces.DeleteCartAPI;
import com.example.ecommerceapp.models.services.DeleteCartService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter implements DeleteCartService {
    private List<CartItemModel> cartItemModelList;
    private int lastPosition = -1;
    private boolean inDelivery;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    public CartAdapter(List<CartItemModel> cartItemModelList, boolean inDelivery) {
        this.cartItemModelList = cartItemModelList;
        this.inDelivery = inDelivery;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
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
        switch (viewType) {
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
        switch (cartItemModelList.get(i).getType()) {
            case CartItemModel.CART_ITEM:
                int id = cartItemModelList.get(i).getProductID();
                String resource = cartItemModelList.get(i).getProductImage();
                String title = cartItemModelList.get(i).getProductTitle();
                double productPrice = cartItemModelList.get(i).getProductPrice();
                double cuttedPrice = cartItemModelList.get(i).getCuttedPrice();
                int quantity = cartItemModelList.get(i).getProductQuantity();
                int limit = cartItemModelList.get(i).getLimit();
                ((CartItemViewHolder) viewHolder).setItemDetails(id, resource, title, productPrice, cuttedPrice, quantity, limit, i);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems = cartItemModelList.get(i).getTotalItems();
                double totalItemPrice = cartItemModelList.get(i).getTotalItemPrice();
                int totalAmount = cartItemModelList.get(i).getTotalAmount();

                ((CartTotalAmountViewHolder) viewHolder).setTotalAmount(totalItems, totalItemPrice, totalAmount);
                break;
            default:
                return;
        }

        if (lastPosition < i) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = i;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    @Override
    public void doDeleteCart(int id, final int position, final Context context) {
        DeleteCartAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(DeleteCartAPI.class);
        Call<DeleteCartResponse> call = api.deleteCart(id);
        call.enqueue(new Callback<DeleteCartResponse>() {
            @Override
            public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        CartItemModel totalAmountModel = cartItemModelList.get(cartItemModelList.size() - 1);
                        totalAmountModel.setTotalItems(totalAmountModel.getTotalItems() - cartItemModelList.get(position).getProductQuantity());
                        totalAmountModel.setTotalItemPrice(totalAmountModel.getTotalItemPrice() - cartItemModelList.get(position).getProductQuantity() * cartItemModelList.get(position).getProductPrice());
                        totalAmountModel.setTotalAmount(totalAmountModel.getTotalAmount() - 1);
                        cartItemModelList.remove(position);

                        if (cartItemModelList.size() == 1) {
                            cartItemModelList.remove(cartItemModelList.get(cartItemModelList.size() - 1));
                            notifyDataSetChanged();
                        } else {
                            notifyDataSetChanged();
                        }

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteCartResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void doEditCart(int id, final int quantity, final int position, final Context context) {
        DeleteCartAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(DeleteCartAPI.class);
        Call<EditCartResponse> call = api.editCart(id, quantity);
        call.enqueue(new Callback<EditCartResponse>() {
            @Override
            public void onResponse(Call<EditCartResponse> call, Response<EditCartResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        CartItemModel totalAmountModel = cartItemModelList.get(cartItemModelList.size() - 1);
                        totalAmountModel.setTotalItems(totalAmountModel.getTotalItems() - cartItemModelList.get(position).getProductQuantity() + response.body().getData().getQuantity());
                        totalAmountModel.setTotalItemPrice(totalAmountModel.getTotalItemPrice() - cartItemModelList.get(position).getProductQuantity() * cartItemModelList.get(position).getProductPrice() + response.body().getData().getQuantity() * response.body().getData().getPrice());
                        cartItemModelList.get(position).setProductQuantity(response.body().getData().getQuantity());

                        notifyDataSetChanged();
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EditCartResponse> call, Throwable t) {

            }
        });
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView productQuantity;
        private LinearLayout deleteBtn;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);

            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
        }

        private void setItemDetails(final int id, String resource, String title, double productPriceText, double cuttedPriceText, final int quantity, final int limit, final int position) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(productImage);
            productTitle.setText(title);
            productPrice.setText(String.format("$%s", productPriceText));
            cuttedPrice.setText(String.format("$%s", cuttedPriceText));
            productQuantity.setText(String.format("Qty: %s", quantity));

            if (inDelivery){
                productQuantity.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                productQuantity.setOnClickListener(null);
                deleteBtn.setVisibility(View.GONE);
                deleteBtn.setOnClickListener(null);
            }else {
                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog quantityDialog = new Dialog(itemView.getContext());
                        quantityDialog.setContentView(R.layout.quantity_dialog);
                        quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        quantityDialog.setCancelable(false);
                        final NumberPicker numberPicker = quantityDialog.findViewById(R.id.num_picker);
                        numberPicker.setMaxValue(limit);
                        numberPicker.setMinValue(1);
                        numberPicker.setWrapSelectorWheel(true);
                        numberPicker.setValue(quantity);
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
                                doEditCart(id, numberPicker.getValue(), position, itemView.getContext());
                                quantityDialog.dismiss();
                            }
                        });
                        quantityDialog.show();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.AlertDialogTheme);
                        builder.setCancelable(false);
                        builder.setTitle("Are you sure?");
                        builder.setMessage("Do you want to delete this product from your cart?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDeleteCart(id, position, itemView.getContext());
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            }
        }
    }

    class CartTotalAmountViewHolder extends RecyclerView.ViewHolder {

        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView totalAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);

            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            totalAmount = itemView.findViewById(R.id.total_price);
        }

        private void setTotalAmount(int totalItemText, double totalItemPriceText, int totalAmountText) {
            totalItems.setText(String.format("Price (%d items)", totalItemText));
            totalItemPrice.setText(String.format("$%s", totalItemPriceText));
            totalAmount.setText(String.format("%d", totalAmountText));
        }
    }
}
