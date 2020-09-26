package com.example.shudugame;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog {
    private final View[] keys = new View[9];
    private final int[] used;
    private ShuDuView shuDuView;

    public CustomDialog(@NonNull Context context, int[] used, ShuDuView shuDuView) {
        super(context);
        this.used = used;
        this.shuDuView = shuDuView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("KeyDialog");
        setContentView(R.layout.layout);
        keys[0] = findViewById(R.id.key1);
        keys[1] = findViewById(R.id.key2);
        keys[2] = findViewById(R.id.key3);
        keys[3] = findViewById(R.id.key4);
        keys[4] = findViewById(R.id.key5);
        keys[5] = findViewById(R.id.key6);
        keys[6] = findViewById(R.id.key7);
        keys[7] = findViewById(R.id.key8);
        keys[8] = findViewById(R.id.key9);
        for (int i = 0; i < used.length; i++) {
            if (used[i] != 0) {
                keys[used[i] - 1].setVisibility(View.INVISIBLE);
            }

        }
        setListener();
    }

    private void setListener() {
        for (int i = 0; i < keys.length; i++) {
            //实际就是点击的数字
            final int t = i + 1;
            keys[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnResult(t);
                }
            });

        }
    }

    private void returnResult(int t) {
        shuDuView.setSelectedTile(t);
        dismiss();
    }

}
