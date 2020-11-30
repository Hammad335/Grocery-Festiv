package com.codewithhamad.onlinestore;

import android.content.Intent;
import android.os.Bundle;
        import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class MainFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView newItemsRecView, popularItemsRecView, suggestedItemsRecView;

    private GroceryItemAdapter newItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);
        initBottomNavView();

//        Utils.clearSharedPreferences(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecViews();
    }

    private void initRecViews() {
        newItemsAdapter= new GroceryItemAdapter(getActivity());
        newItemsRecView.setAdapter(newItemsAdapter);
        newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemsAdapter= new GroceryItemAdapter(getActivity());
        popularItemsRecView.setAdapter(popularItemsAdapter);
        popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemsAdapter= new GroceryItemAdapter(getActivity());
        suggestedItemsRecView.setAdapter(suggestedItemsAdapter);
        suggestedItemsRecView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // sending data of newItems to newItemsAdapter
        ArrayList<GroceryItem> allItems= Utils.getAllItems(getActivity());
        if (allItems != null) {
            newItemsAdapter.setItems(allItems);


            Comparator<GroceryItem> newItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {

                    // if o1 id is greater than o2 id, then we are returning positive number,
                    // else if the case is reverse, we are returning negative number,
                    // else if both object's ids are equal, we are returning zero.
                    return o1.getId() - o2.getId();
                }
            };

            // reversing the order, so that the object having greater id can show up first
            // by default, it shows items in ascending order according to their ids.
            Comparator<GroceryItem> reverseComparator = Collections.reverseOrder(newItemsComparator);
            Collections.sort(allItems, reverseComparator);
            newItemsAdapter.setItems(allItems);
        }

        // sending data of popularItems to popularItemsAdapter
        ArrayList<GroceryItem> popularItems = Utils.getAllItems(getActivity());
        if (popularItems != null) {
            Comparator<GroceryItem> popularItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getPopularityPoint() - o2.getPopularityPoint();
                }
            };
            Collections.sort(popularItems, Collections.reverseOrder(popularItemsComparator));
            popularItemsAdapter.setItems(popularItems);
        }

        // sending data of suggestedItems to suggestedItemsAdapter
        ArrayList<GroceryItem> suggestedItems = Utils.getAllItems(getActivity());
        if (suggestedItems != null) {
            Comparator<GroceryItem> suggestedItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getUserPoint() - o2.getUserPoint();
                }
            };
            Collections.sort(suggestedItems, Collections.reverseOrder(suggestedItemsComparator));
            suggestedItemsAdapter.setItems(suggestedItems);
        }


    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        // TODO: 9/6/2020 Finish this

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        break;
                    case R.id.search:
                        Intent intent= new Intent(getActivity(), SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
//                        getActivity().overridePendingTransition(R.anim.lefttoright, 0);
                        break;
                    case R.id.cart:
                        Intent cartIntent= new Intent(getActivity(), CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
//                        getActivity().overridePendingTransition(R.anim.righttoleft, R.anim.blink_anim);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initViews(View view) {
        bottomNavigationView= view.findViewById(R.id.bottomNavView);
        newItemsRecView= view.findViewById(R.id.newItemsRecView);
        popularItemsRecView= view.findViewById(R.id.popularItemRecView);
        suggestedItemsRecView= view.findViewById(R.id.suggestedItemsRecView);
    }
}
