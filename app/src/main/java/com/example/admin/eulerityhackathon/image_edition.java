package com.example.admin.eulerityhackathon;


import android.content.Intent;

import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageView;


import com.squareup.picasso.Picasso;


import jp.wasabeef.picasso.transformations.ColorFilterTransformation;

import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;

public class image_edition extends AppCompatActivity {
    ImageView imageViewGrayscaleTransformation;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edition);


        Intent intent = getIntent();

        url = intent.getStringExtra("img");

        imageViewGrayscaleTransformation = (ImageView) findViewById(R.id.grayscaleTransformation);
        imageView2 = (ImageView) findViewById(R.id.effect_invert);
        imageView3 = (ImageView) findViewById(R.id.effect_mean_remove);
        imageView4 = (ImageView) findViewById(R.id.imageView2);
        imageView5 = (ImageView) findViewById(R.id.imageView3);
        imageView6 = (ImageView) findViewById(R.id.imageView4);


        Picasso.get().load(url).transform(new GrayscaleTransformation()).into(imageViewGrayscaleTransformation);
        Picasso.get().load(url).transform(new ColorFilterTransformation(Color.parseColor("#539b59b6"))).into(imageView2);
        Picasso.get().load(url).transform(new BrightnessFilterTransformation(imageView3.getContext(), 0.5f)).into(imageView3);
        Picasso.get().load(url).transform(new BrightnessFilterTransformation(imageView4.getContext(), -0.5f)).into(imageView4);
        Picasso.get().load(url).transform(new ContrastFilterTransformation(imageView5.getContext(), 34)).into(imageView5);
        Picasso.get().load(url).transform(new ContrastFilterTransformation(imageView6.getContext(), -10)).into(imageView6);


    }


    public void imageClicked1(View view) {

        Intent intent = new Intent(image_edition.this, image_text_edition.class);
        intent.putExtra("imageClicked1", url);
        startActivity(intent);

    }


    public void imageClicked2(View view) {

        Intent intent = new Intent(image_edition.this, image_text_edition.class);
        intent.putExtra("imageClicked2", url);
        startActivity(intent);

    }

    public void imageClicked3(View view) {

        Intent intent = new Intent(image_edition.this, image_text_edition.class);
        intent.putExtra("imageClicked3", url);
        startActivity(intent);

    }

    public void imageClicked4(View view) {

        Intent intent = new Intent(image_edition.this, image_text_edition.class);
        intent.putExtra("imageClicked4", url);
        startActivity(intent);

    }

    public void imageClicked5(View view) {

        Intent intent = new Intent(image_edition.this, image_text_edition.class);
        intent.putExtra("imageClicked5", url);
        startActivity(intent);

    }

    public void imageClicked6(View view) {

        Intent intent = new Intent(image_edition.this, image_text_edition.class);
        intent.putExtra("imageClicked6", url);
        startActivity(intent);

    }

}
