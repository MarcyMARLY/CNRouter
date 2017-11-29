package com.example.caspar.cnrouter;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView router1;
    private ImageView router2;
    private ImageView router3;
    private ImageView N1C1;
    private ImageView N1C2;
    private ImageView N2C1;
    private ImageView N3C1;
    private TextView tv1;
    private Button n11;
    private Button n21;
    private Button n31;
    private Button n41;
    private Button n12;
    private Button n22;
    private Button n32;
    private Button n42;
    private int ch1=0,ch2=0;
    Router r1;
    Router r2;
    Router r3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r1 = new Router("171.128.0.0/26"); //26
        r2 = new Router("171.128.0.94/28"); //28
        r3 = new Router("171.128.0.109/28"); //28
        r1.addStaticRow("171.128.0.0/26");
        r1.addStaticRow("171.128.0.63/26");
        r2.addStaticRow("171.128.0.94/28");
        r3.addStaticRow("171.128.0.109/28");
        //r1.addDynRow("171.128.0.94/28", "171.128.0.94/28", 54);
        r1.addDynRow("171.128.0.94/28", "171.128.0.94/28", 12);
        r1.addDynRow("171.128.0.109/28", "171.128.0.109/28", 22);
        r2.addDynRow("171.128.0.0/26", "171.128.0.0/26", 32);
        r2.addDynRow("171.128.0.63/26", "171.128.0.0/26", 12);
        r2.addDynRow("171.128.0.109/28", "171.128.0.0/26", 42);
        r3.addDynRow("171.128.0.0/26", "171.128.0.0/26", 12);
        r3.addDynRow("171.128.0.63/26", "171.128.0.0/26", 12);
        r3.addDynRow("171.128.0.94/28", "171.128.0.0/26", 12);


        router1 = (ImageView) findViewById(R.id.router1);
        router2 = (ImageView) findViewById(R.id.router2);
        router3 = (ImageView) findViewById(R.id.router3);
        N1C1 = (ImageView) findViewById(R.id.N1C1);
        N1C2 = (ImageView) findViewById(R.id.N1C2);
        N2C1 = (ImageView) findViewById(R.id.N2C1);
        N3C1 = (ImageView) findViewById(R.id.N3C1);
        tv1 = (TextView) findViewById(R.id.tv1);
        n11 = (Button) findViewById(R.id.n11);
        n21 = (Button) findViewById(R.id.n21);
        n31 = (Button) findViewById(R.id.n31);
        n41 = (Button) findViewById(R.id.n41);
        n12 = (Button) findViewById(R.id.n12);
        n22 = (Button) findViewById(R.id.n22);
        n32 = (Button) findViewById(R.id.n32);
        n42 = (Button) findViewById(R.id.n42);

        n11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ch1 = 1;
                tv1.setText("You choice is Network 1");
            }
        });
        n21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ch1 = 2;
                tv1.setText("You choice is Network 2");
            }
        });
        n31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ch1 = 3;
                tv1.setText("You choice is Network 3");
            }
        });
        n41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ch1 = 4;
                tv1.setText("You choice is Network 4");
            }
        });

        n12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ch1!=0){
                    ch2 = 1;
                    findThePath(ch1,ch2);
                }
                ch1=0;
            }
        });
        n22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ch1!=0){
                    ch2 = 2;
                    findThePath(ch1,ch2);
                }
                ch1=0;
            }
        });
        n32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ch1!=0){
                    ch2 = 3;
                    findThePath(ch1,ch2);
                }
                ch1=0;
            }
        });
        n42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ch1!=0){
                    ch2 = 4;
                    findThePath(ch1,ch2);
                }
                ch1=0;
            }
        });

    }
    public void findDirCon(int ch1,int ch2){
        if(ch1==ch2){
            tv1.setText(tv1.getText()+"\n"+"Inside one network the message is successfully delivered." );
        }else {
            ArrayList<LocalRow> directlyConTable = convertPosToIP(ch1).getRTable();
            for (int i = 0; i < directlyConTable.size(); i++) {
                if (convertPosToIP(ch2).getIPaddress() == directlyConTable.get(i).getNetworkAddress()) {
                    // t=true;
                    tv1.setText(tv1.getText()+"\n"+"Network " + ch1 + " is directly connected  to Network " + ch2 + ". The message is successfully delivered.");
                }
            }
        }
    }
    public void findRemCon(int ch1,int ch2){
        ArrayList<RemoteRow> remoteConTable = convertPosToIP(ch1).getRRTable();
        for (int i = 0; i < remoteConTable.size(); i++) {
            if (convertPosToIP(ch2).getIPaddress() == remoteConTable.get(i).getNetworkAddress()) {
                tv1.setText(tv1.getText() + "There's a path: Next hop: " + convertIPtoRouter(remoteConTable.get(i).getNextHop()) + " Metrics: " + remoteConTable.get(i).getDistance() + "\n");
                findDirCon(ch2,ch2);
            }
        }
    }

    public void findThePath(int ch1,int ch2)
    {
        //boolean t = false;
        tv1.setText("");
        findDirCon(ch1,ch2);

            ArrayList<RemoteRow> remoteConTable = convertPosToIP(ch1).getRRTable();
            for (int i = 0; i < remoteConTable.size(); i++) {
                if (convertPosToIP(ch2).getIPaddress() == remoteConTable.get(i).getNetworkAddress()) {
                    tv1.setText(tv1.getText() + "There's a path: Next hop: " + convertIPtoRouter(remoteConTable.get(i).getNextHop()) + " Metrics: " + remoteConTable.get(i).getDistance() + "\n");
                    if(remoteConTable.get(i).getNextHop() == "171.128.0.0/26"){
                        if(remoteConTable.get(i).getNetworkAddress()==remoteConTable.get(i).getNextHop()){
                            findDirCon(convertIPToPos(remoteConTable.get(i).getNextHop()),convertIPToPos(remoteConTable.get(i).getNetworkAddress()));
                        }
                        findRemCon(1,convertIPToPos(convertPosToIP(ch2).getIPaddress()));
                        //findDirCon(1,convertIPToPos(convertPosToIP(ch2).getIPaddress()));
                    }else{
                        findDirCon(convertIPToPos(remoteConTable.get(i).getNextHop()),convertIPToPos(remoteConTable.get(i).getNetworkAddress()));
                    }

                }
            }


    }

    public Router convertPosToIP(int ch){
        if(ch==1||ch==2){
            return r1;
        }
        if(ch==3){
            return r2;
        }
            return r3;

    }
    public int convertIPToPos(String ip){
        if(ip == "171.128.0.0/26"){
            return 1;
        }
        if(ip == "171.128.0.63/26")
        {
            return 2;
        }
        if(ip == "171.128.0.94/28")
        {
            return 3;
        }
        return 4;
    }
    public String convertIPtoRouter(String ip){
        if(ip == "171.128.0.0/26"){
            return "r1";
        }
        if(ip == "171.128.0.63/26")
        {
            return "r1";
        }
        if(ip == "171.128.0.94/28")
        {
            return "r2";
        }
        return "r3";
    }

}
