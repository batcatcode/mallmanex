package com.example.mallman1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mallman1.R;
import com.example.mallman1.models.Product;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProducts(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView priceTextView;
        private final TextView stockTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_product_name);
            priceTextView = itemView.findViewById(R.id.tv_product_price);
            stockTextView = itemView.findViewById(R.id.tv_product_stock);
        }

        public void bind(final Product product, final OnProductClickListener listener) {
            nameTextView.setText(product.getName());
            priceTextView.setText(String.format(Locale.getDefault(), "₹%.2f", product.getPrice()));
            stockTextView.setText(String.format(Locale.getDefault(), "Stock: %d", product.getStock()));

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProductClick(product);
                }
            });
        }
    }
}