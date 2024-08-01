package com.example.project_app_book.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_app_book.R;
import com.example.project_app_book.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentDanhSachYeuThich extends Fragment {

    private ListView lstYeuThich;
    private ArrayAdapter<String> adapter;
    private List<String> bookTitles;
    private DatabaseReference userFavoritesRef;
    private DatabaseReference booksRef;

    public FragmentDanhSachYeuThich() {
        // Required empty public constructor
    }

    public static FragmentDanhSachYeuThich newInstance(String param1, String param2) {
        FragmentDanhSachYeuThich fragment = new FragmentDanhSachYeuThich();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_yeu_thich, container, false);

        lstYeuThich = view.findViewById(R.id.lstYeuThich);
        bookTitles = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookTitles);
        lstYeuThich.setAdapter(adapter);

       String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userFavoritesRef = FirebaseDatabase.getInstance().getReference("user").child(userId).child("favourite");
        booksRef = FirebaseDatabase.getInstance().getReference("books");

        loadFavoriteBooks();

        return view;
    }

    private void loadFavoriteBooks() {
        userFavoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookTitles.clear();
                for (DataSnapshot favSnapshot : snapshot.getChildren()) {
                    String bookId = favSnapshot.getKey();
                    loadBookDetails(bookId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void loadBookDetails(String bookId) {
        booksRef.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Book book = snapshot.getValue(Book.class);
                if (book != null) {
                    bookTitles.add(book.getTitle());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}
