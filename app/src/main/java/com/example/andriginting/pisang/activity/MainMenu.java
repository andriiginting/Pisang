package com.example.andriginting.pisang.activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;

import com.example.andriginting.pisang.Adapter.KontenAdapter;
import com.example.andriginting.pisang.Model.MenuModel;
import com.example.andriginting.pisang.R;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KontenAdapter adapter;
    private List<MenuModel.MenuModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_konten_main);

        modelList = new ArrayList<>();
        adapter = new KontenAdapter(this, modelList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        gambar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_add,menu);
        return true;
    }

    private int dpToPx(int i) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, r.getDisplayMetrics()));
    }

    //untuk itemdecoration
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private void gambar() {
        int[] cover = new int[]{
                R.drawable.coklat,
                R.drawable.cabe,
                R.drawable.kopi,
                R.drawable.banana
        };

        MenuModel.MenuModel a = new MenuModel.MenuModel("pisang", cover[3]);
        modelList.add(a);
        a = new MenuModel.MenuModel("Pisang", cover[3]);
        modelList.add(a);
        a = new MenuModel.MenuModel("coklat", cover[0]);
        modelList.add(a);
        a = new MenuModel.MenuModel("cabe", cover[1]);
        modelList.add(a);
        a = new MenuModel.MenuModel("cabe", cover[1]);
        modelList.add(a);
        a = new MenuModel.MenuModel("Pisang", cover[3]);
        modelList.add(a);
        a = new MenuModel.MenuModel("Pisang", cover[3]);
        modelList.add(a);
        a = new MenuModel.MenuModel("Pisang", cover[3]);
        modelList.add(a);
        a = new MenuModel.MenuModel("Pisang", cover[3]);
        modelList.add(a);
    }

}

