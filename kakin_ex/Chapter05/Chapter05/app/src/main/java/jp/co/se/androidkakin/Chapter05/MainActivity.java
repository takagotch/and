package jp.co.se.androidkakin.Chapter05;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.co.se.androidkakin.Chapter05.util.IabBroadcastReceiver;
import jp.co.se.androidkakin.Chapter05.util.IabBroadcastReceiver.IabBroadcastListener;
import jp.co.se.androidkakin.Chapter05.util.IabHelper;
import jp.co.se.androidkakin.Chapter05.util.IabHelper.IabAsyncInProgressException;
import jp.co.se.androidkakin.Chapter05.util.IabResult;
import jp.co.se.androidkakin.Chapter05.util.Inventory;
import jp.co.se.androidkakin.Chapter05.util.Purchase;

public class MainActivity extends AppCompatActivity implements IabBroadcastListener {
    // デバッグログ用のタグ
    static final String TAG = "subscription";

    // アイテム購入時に送信するリクエストコード
    static final int RC_REQUEST = 10001;

    // IabHelperのインスタンス
    IabHelper mHelper;

    // IabBroadcastReceiverのインスタンス
    IabBroadcastReceiver mBroadcastReceiver;

    // 各種ボタンのインスタンス
    Button mShopButton;
    Button mCont1;
    Button mCont2;
    Button mCont3;

    // 各種TextViewのインスタンス
    TextView mBody;

    // ハンドラのインスタンス
    Handler handler;

    // コンテンツごとのアイテムIDを保持する(保持されていれば購入済みとみなす)
    String mHasContent1 = ""; // コンテンツ1は週額と月額のいずれかのアイテムIDが保持される
    String mHasContent2 = "";
    String mHasContent3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        mBody = (TextView) findViewById(R.id.textView1);

        mCont1 = (Button) findViewById(R.id.btn_content1);
        mCont1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download("http://www.YOURSERVER.com/content1.txt");
            }
        });

        mCont2 = (Button) findViewById(R.id.btn_content2);
        mCont2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download("http://www.YOURSERVER.com/content2.txt");
            }
        });

        mCont3 = (Button) findViewById(R.id.btn_content3);
        mCont3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download("http://www.YOURSERVER.com/content3.txt");
            }
        });

        mShopButton = (Button) findViewById(R.id.btn_shop);
        mShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = new CharSequence[Item.ITEMLIST.length];

                for (int i=0; i<Item.ITEMLIST.length; i++) {
                    items[i] = getString(Item.ITEMLIST[i].nameId);
                }

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.item_shop)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 選択したアイテムの購入処理を開始する

                                // 必要に応じてデベロッパーペイロードを設定する。今回は未設定。
                                String payload = "";

                                try {
                                    // 選択したアイテムのアイテムIDを取得する
                                    String mItemId = Item.ITEMLIST[which].itemId;

                                    // アップグレード/ダウングレードする場合のアイテムID
                                    List<String> oldSkus = null;

                                    // コンテンツ1が購入済みの場合
                                    if (!TextUtils.isEmpty(mHasContent1)) {
                                        oldSkus = new ArrayList<String>();

                                        // 週額から月額へのアップグレードの場合
                                        if (mItemId.equals("content1_monthly")) {
                                            Log.d(TAG, "oldSkus add subscription_001.");
                                            oldSkus.add("content1_weekly");
                                        }
                                        // 月額から週額へのダウングレードの場合
                                        else if (mItemId.equals("content1_weekly")) {
                                            Log.d(TAG, "oldSkus add subscription_002.");
                                            oldSkus.add("content1_monthly");
                                        }
                                    } else {
                                        // コンテンツ1が未購入ならoldSkusはnullなので、アップグレード/ダウングレードなし。
                                        // つまり、通常の購入となる。
                                    }

                                    mHelper.launchPurchaseFlow(MainActivity.this, mItemId, IabHelper.ITEM_TYPE_SUBS, oldSkus, RC_REQUEST,
                                            mPurchaseFinishedListener, payload);
                                } catch (IabAsyncInProgressException e) {
                                    Log.d(TAG, "Error launching purchase flow. Another async operation in progress.");
                                    Toast.makeText(MainActivity.this, "アイテムを購入できませんでした。", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).show();
            }
        });

        // パブリックキー
        String base64EncodedPublicKey = "MUST_BE_SET_WHEN_REGIST_TO_PLAY_STORE";

        // IabHelperを初期化する
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // IabHelperのデバッグログを有効にする
        mHelper.enableDebugLogging(true);

        // IabHelper初期化開始
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // アプリ内課金をサポートしているので、ショップボタンを有効にする
                mShopButton.setEnabled(true);

                mBroadcastReceiver = new IabBroadcastReceiver(MainActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                try {
                    // 購入情報を取得する
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabAsyncInProgressException e) {
                    Log.d(TAG, "Error querying inventory. Another async operation in progress.");
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (mHelper == null) return;

        try {
            // 購入情報を取得する
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabAsyncInProgressException e) {
            Log.d(TAG, "Error querying inventory. Another async operation in progress.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // ダウンロード用メソッド
    public boolean download(String targetUrl) {
        HttpURLConnection http = null;
        InputStream in = null;

        try {
            URL url = new URL(targetUrl);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            in = http.getInputStream();

            String src = new String();
            byte[] line = new byte[1024];
            int size;
            while(true) {
                size = in.read(line);
                if (size <= 0) {
                    break;
                }
                src += new String(line);
                mBody.setText(src);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (http != null) {
                http.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }

        return false;
    }


    @Override
    public void receivedBroadcast() {

    }

    // 購入情報の取得が完了したら呼ばれるイベントリスナー
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            if (mHelper == null) return;

            if (result.isFailure()) {
                Log.d(TAG, "Failed to query inventory: " + result);
                return;
            }
            Log.d(TAG, "Query inventory was successful.");

            // 全コンテンツの購入情報をクリア
            mHasContent1 = "";
            mHasContent2 = "";
            mHasContent3 = "";

            // コンテンツ1(週額)の購入情報を取得
            Purchase cont1_weekly = inventory.getPurchase(Item.ITEMLIST[0].itemId);

            // コンテンツ1(月額)の購入情報を取得
            Purchase cont1_monthly = inventory.getPurchase(Item.ITEMLIST[1].itemId);

            // コンテンツ2(週額)の購入情報を取得
            Purchase cont2_weekly = inventory.getPurchase(Item.ITEMLIST[2].itemId);

            // コンテンツ3(週額)の購入情報を取得
            Purchase cont3_weekly = inventory.getPurchase(Item.ITEMLIST[3].itemId);

            // コンテンツ1(週額)が購入済みの場合
            if (cont1_weekly != null && cont1_weekly.isAutoRenewing()) {
                // コンテンツ1(週額)のアイテムIDを保存する
                mHasContent1 = Item.ITEMLIST[0].itemId;

            // コンテンツ1(月額)が購入済みの場合
            } else if (cont1_monthly != null && cont1_monthly.isAutoRenewing()) {
                // コンテンツ1(月額)のアイテムIDを保存する
                mHasContent1 = Item.ITEMLIST[1].itemId;

            // コンテンツ2(週額)が購入済みの場合
            } else if (cont2_weekly != null && cont2_weekly.isAutoRenewing()) {

                // コンテンツ2(週額)のアイテムIDを保存する
                mHasContent2 = Item.ITEMLIST[2].itemId;

            // コンテンツ3(週額)が購入済みの場合
            } else if (cont3_weekly != null && cont3_weekly.isAutoRenewing()) {

                // コンテンツ3(週額)のアイテムIDを保存する
                mHasContent3 = Item.ITEMLIST[3].itemId;
            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");

            // 画面を更新する
            checkOwnedItem();
        }
    };

    // 購入処理が完了したら呼ばれるイベントリスナー
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (mHelper == null) return;

            if (result.isFailure()) {
                Log.d(TAG, "Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                Log.d(TAG, "Error purchasing. Authenticity verification failed.");
                return;
            }
            Log.d(TAG, "Purchase successful.");

            // コンテンツ1(週額)の購入完了
            if (purchase.getSku().equals(Item.ITEMLIST[0].itemId)) {
                // コンテンツ1(週額)のアイテムIDを保存する
                mHasContent1 = Item.ITEMLIST[0].itemId;
            }

            // コンテンツ1(月額)の購入完了
            else if (purchase.getSku().equals(Item.ITEMLIST[1].itemId)) {
                // コンテンツ1(月額)のアイテムIDを保存する
                mHasContent1 = Item.ITEMLIST[1].itemId;
            }

            // コンテンツ2(週額)の購入完了
            else if (purchase.getSku().equals(Item.ITEMLIST[2].itemId)) {
                // コンテンツ2(週額)のアイテムIDを保存する
                mHasContent2 = Item.ITEMLIST[2].itemId;
            }

            // コンテンツ3(週額)の購入完了
            else if (purchase.getSku().equals(Item.ITEMLIST[3].itemId)) {
                // コンテンツ3(週額)のアイテムIDを保存する
                mHasContent3 = Item.ITEMLIST[3].itemId;
            }

            // データベースの内容を画面に反映する
            checkOwnedItem();
        }
    };

    // アイテムの購入結果を受け取る
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        if (mHelper == null) return;

        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    // アイテムの購入状態をアプリケーションに反映する
    public void checkOwnedItem() {
        Log.d(TAG, "called checkOwnedItem.");

        // コンテンツ1の購入状態を反映
        if (!TextUtils.isEmpty(mHasContent1)) {
            enableButton(mCont1, true);
        } else {
            enableButton(mCont1, false);
        }

        // コンテンツ2の購入状態を反映
        if (!TextUtils.isEmpty(mHasContent2)) {
            enableButton(mCont2, true);
        } else {
            enableButton(mCont2, false);
        }

        // コンテンツ3の購入状態を反映
        if (!TextUtils.isEmpty(mHasContent3)) {
            enableButton(mCont3, true);
        } else {
            enableButton(mCont3, false);
        }
    }

    // 指定されたボタンを有効/無効にする
    public void enableButton(final Button button, final boolean enabled) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(enabled);
            }
        });
    }
}
