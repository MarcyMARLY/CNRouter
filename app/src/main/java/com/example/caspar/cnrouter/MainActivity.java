package com.example.caspar.cnrouter;

import android.media.Image;
import android.os.Handler;
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
        //Directly connected
        r1.addStaticRow("171.128.0.0/26");
        r1.addStaticRow("171.128.0.63/26");
        r2.addStaticRow("171.128.0.94/28");
        r3.addStaticRow("171.128.0.109/28");


        // remote nnetworks
        r1.addDynRow("171.128.0.94/28", "171.128.0.94/28", 12);
        r1.addDynRow("171.128.0.109/28", "171.128.0.109/28", 22);
        r2.addDynRow("171.128.0.0/26", "171.128.0.0/26", 32);
        r2.addDynRow("171.128.0.63/26", "171.128.0.0/26", 12);
        r2.addDynRow("171.128.0.109/28", "171.128.0.0/26", 42);
        r3.addDynRow("171.128.0.0/26", "171.128.0.0/26", 12);
        r3.addDynRow("171.128.0.63/26", "171.128.0.0/26", 12);
        r3.addDynRow("171.128.0.94/28", "171.128.0.0/26", 12);

        //Initialisation for visualisation
        router1 = (ImageView) findViewById(R.id.prouter1);
        router2 = (ImageView) findViewById(R.id.prouter2);
        router3 = (ImageView) findViewById(R.id.prouter3);
        N1C1 = (ImageView) findViewById(R.id.pN1C1);
        N1C2 = (ImageView) findViewById(R.id.pN1C2);
        N2C1 = (ImageView) findViewById(R.id.pN2C1);
        N3C1 = (ImageView) findViewById(R.id.pN3C1);
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
    /**
     * Method for finding directly connected networks
     */
    public void findDirCon(final int ch1,final int ch2){

        if(ch1==ch2){
            //the same network
            tv1.setText(tv1.getText()+"\n"+"Inside one network the message is successfully delivered." );
            //For animation
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getImageView(convertPosToIP(ch1).getIPaddress()).setVisibility(View.VISIBLE);
                }
            }, 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getImageView(convertPosToIP(ch1).getIPaddress()).setVisibility(View.GONE);
                    getImageView(ch2).setVisibility(View.VISIBLE);
                }
            }, 1600);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getImageView(ch2).setVisibility(View.GONE);
                }
            }, 1900);
        }else {
            //2 network to 1 router
            ArrayList<LocalRow> directlyConTable = convertPosToIP(ch1).getRTable();
            for (int i = 0; i < directlyConTable.size(); i++) {
                if (convertPosToIP(ch2).getIPaddress() == directlyConTable.get(i).getNetworkAddress()) {
                    // t=true;
                    tv1.setText(tv1.getText()+"\n"+"Network " + ch1 + " is directly connected  to Network " + ch2 + ". The message is successfully delivered.");
                    //animation
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImageView(ch1).setVisibility(View.VISIBLE);
                        }
                    }, 400);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImageView(ch1).setVisibility(View.GONE);
                            getImageView(convertPosToIP(ch1).getIPaddress()).setVisibility(View.VISIBLE);
                        }
                    }, 500);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImageView(convertPosToIP(ch1).getIPaddress()).setVisibility(View.GONE);
                            getImageView(ch2).setVisibility(View.VISIBLE);
                        }
                    }, 900);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImageView(ch2).setVisibility(View.GONE);
                        }
                    }, 1200);
                }
            }
        }
    }
    /**
     * method for finding remote networks
     */
    public void findRemCon(final int ch1,final int ch2){

        ArrayList<RemoteRow> remoteConTable = convertPosToIP(ch1).getRRTable();
        for (int i = 0; i < remoteConTable.size(); i++) {
            if (convertPosToIP(ch2).getIPaddress() == remoteConTable.get(i).getNetworkAddress()) {
                tv1.setText(tv1.getText() + "There's a path: Next hop: " + convertIPtoRouter(remoteConTable.get(i).getNextHop()) + " Metrics: " + remoteConTable.get(i).getDistance() + "\n");
                //animation
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getImageView(convertPosToIP(ch1).getIPaddress()).setVisibility(View.VISIBLE);
                    }
                }, 900);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getImageView(convertPosToIP(ch1).getIPaddress()).setVisibility(View.GONE);
                        getImageView(convertPosToIP(ch2).getIPaddress()).setVisibility(View.VISIBLE);
                    }
                }, 1000);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getImageView(convertPosToIP(ch2).getIPaddress()).setVisibility(View.GONE);
                    }
                }, 1300);
                findDirCon(ch2,ch2);
            }
        }
    }
    /**
     * methos to fins the path between to networks
     * */
    public void findThePath(final int ch1,final int ch2)
    {
        //boolean t = false;
        tv1.setText("");
        //checks for direct connection
        findDirCon(ch1,ch2);

            ArrayList<RemoteRow> remoteConTable = convertPosToIP(ch1).getRRTable();
            for (int i = 0; i < remoteConTable.size(); i++) {
                if (convertPosToIP(ch2).getIPaddress() == remoteConTable.get(i).getNetworkAddress()) {
                    tv1.setText(tv1.getText() + "There's a path: Next hop: " + convertIPtoRouter(remoteConTable.get(i).getNextHop()) + " Metrics: " + remoteConTable.get(i).getDistance() + "\n");
                    final String ip = remoteConTable.get(i).getNextHop();
                    getImageView(ch1).setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImageView(ch1).setVisibility(View.GONE);
                            getImageView(convertPosToIP(ch1).getIPaddress()).setVisibility(View.VISIBLE);
                        }
                    }, 100);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImageView(convertPosToIP(ch1).getIPaddress()).setVisibility(View.GONE);
                           // getImageView(ip).setVisibility(View.VISIBLE);
                        }
                    }, 400);
                    /*new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            getImageView(ip).setVisibility(View.GONE);
                        }
                    }, 700);*/
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
// conversation to approprite format
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
    /**
     * methods for animation
     * */
    public ImageView getImageView(int ch){
        if(ch==1){
            return N1C1;
        }
        if(ch==2){
            return N1C2;
        }
        if(ch==3){
            return N2C1;
        }
        else{
            return N3C1;
        }
    }
    public ImageView getImageView(String ip){
        if(ip == "171.128.0.0/26" ||ip == "171.128.0.0/26" ){
            return router1;
        }
        if(ip == "171.128.0.94/28"){
            return router2;
        }
        return router3;

    }

}
