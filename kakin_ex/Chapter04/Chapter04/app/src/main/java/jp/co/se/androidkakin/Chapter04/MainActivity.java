package jp.co.se.androidkakin.Chapter04;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jp.co.se.androidkakin.Chapter04.util.IabBroadcastReceiver;
import jp.co.se.androidkakin.Chapter04.util.IabBroadcastReceiver.IabBroadcastListener;
import jp.co.se.androidkakin.Chapter04.util.IabHelper;
import jp.co.se.androidkakin.Chapter04.util.IabHelper.IabAsyncInProgressException;
import jp.co.se.androidkakin.Chapter04.util.IabResult;
import jp.co.se.androidkakin.Chapter04.util.Inventory;
import jp.co.se.androidkakin.Chapter04.util.Purchase;

/*
 * ステージセレクト画面を表現するクラス
 * アイテムの購入もこの画面で行う。
 *
 */
public class MainActivity extends AppCompatActivity implements IabBroadcastListener {
    // デバッグログ用のタグ
    static final String TAG = "breakout";

    // データベースの初期化済みフラグ
    private static final String DB_INITIALIZED = "db_initialized";

    // アイテム購入時に送信するリクエストコード
    static final int RC_REQUEST = 10001;

    // IabHelperのインスタンス
    IabHelper mHelper;

    // IabBroadcastReceiverのインスタンス
    IabBroadcastReceiver mBroadcastReceiver;

    // 各種ボタンのインスタンス
    private Button btn_stage1;
    private Button btn_stage2;
    private Button btn_stage3;
    private Button btn_powerup;
    private Button btn_speedup;
    private Button btn_shop;

    private Button btn_consume;
    private Button btn_buy;

    // 各種TextViewのインスタンス
    private TextView tv_players;
    private TextView tv_powerup;
    private TextView tv_speedup;

    // プレイヤーのステータス値
    private int players = 1;
    private int speedUp = 1;
    private int powerUp = 1;

    // ハンドラのインスタンス
    Handler handler = new Handler();

    // データベースのインスタンス
    private Database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TextViewを初期化する
        tv_players = (TextView) findViewById(R.id.tv_players);
        tv_powerup = (TextView) findViewById(R.id.tv_powerup);
        tv_speedup = (TextView) findViewById(R.id.tv_speedup);

        // ステージ1選択ボタン
        btn_stage1 = (Button) findViewById(R.id.btn_stage1);
        btn_stage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(getApplicationContext(), PlayGame.class);

                // ブロックパターン
                boolean pattern[] = {
                        false, true, false, true, false,
                        true, false, true, false, true,
                        false, true, false, true, false,
                };
                intent.putExtra("PATTERN", pattern);
                intent.putExtra("PLAYERS", players);
                intent.putExtra("SPEEDUP", speedUp);
                intent.putExtra("POWERUP", powerUp);

                startActivity(intent);
            }
        });

        // ステージ2選択ボタン
        btn_stage2 = (Button) findViewById(R.id.btn_stage2);
        btn_stage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(getApplicationContext(), PlayGame.class);

                // ブロックパターン
                boolean pattern[] = {
                        false, false, false, false, false,
                        true, true, true, true, true,
                        false, false, false, false, false,
                };
                intent.putExtra("PATTERN", pattern);
                intent.putExtra("PLAYERS", players);
                intent.putExtra("SPEEDUP", speedUp);
                intent.putExtra("POWERUP", powerUp);

                startActivity(intent);
            }
        });

        // ステージ3選択ボタン
        btn_stage3 = (Button) findViewById(R.id.btn_stage3);
        btn_stage3.setEnabled(false); // ステージ3選択ボタンはデフォルトで押せないようにしておく
        btn_stage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(getApplicationContext(), PlayGame.class);

                // ブロックパターン
                boolean pattern[] = {
                        false, false, true, false, false,
                        false, true, false, true, false,
                        true, false, false, false, true,
                };
                intent.putExtra("PATTERN", pattern);
                intent.putExtra("PLAYERS", players);
                intent.putExtra("SPEEDUP", speedUp);
                intent.putExtra("POWERUP", powerUp);

                startActivity(intent);
            }
        });

        // ショップボタン
        btn_shop = (Button) findViewById(R.id.btn_shop);
        btn_shop.setEnabled(false); // ショップボタンはデフォルトで押せないようにしておく
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = new CharSequence[Item.ITEMLIST.length];

                // CATALOGクラスからAlertDialogに表示するアイテムリストを作成する
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
                                    String mItemId = Item.ITEMLIST[which].itemId;
                                    mHelper.launchPurchaseFlow(MainActivity.this, mItemId, RC_REQUEST,
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

        // データベースを初期化する
        mDatabase = new Database(this);

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
                btn_shop.setEnabled(true);

                mBroadcastReceiver = new IabBroadcastReceiver(MainActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // データベースをリストアする
                restoreDatabase();
            }
        });

        // 画面を更新する
        checkOwnedItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkOwnedItem();
    }

    @Override
    public void onStop() {
        super.onStop();
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

        // データベースをクローズ
        mDatabase.close();
    }

    @Override
    public void receivedBroadcast() {

    }

    private void restoreDatabase() {
        // データベースが初期化されていない場合は購入情報をリストアする
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        boolean initialized = prefs.getBoolean(DB_INITIALIZED, false);
        if (!initialized) {
            try {
                // Playストアから購入情報を取得する
                mHelper.queryInventoryAsync(mGotInventoryListener);
            } catch (IabAsyncInProgressException e) {
                Log.d(TAG, "Error querying inventory. Another async operation in progress.");
            }
        }
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

            // ステージ3のリカバリ
            Purchase premiumStagePurchase = inventory.getPurchase(Item.ITEMLIST[3].itemId);
            if (premiumStagePurchase != null && verifyDeveloperPayload(premiumStagePurchase)) {
                // データベースに登録する
                mDatabase.addItem("item_004");
                Log.d(TAG, "We have Stage 3.");
                return;
            }

            // 購入情報のリストアが完了したことを示すフラグを有効にする。
            SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(DB_INITIALIZED, true);
            edit.commit();

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
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

            // 「Speed Up」購入完了
            if (purchase.getSku().equals(Item.ITEMLIST[0].itemId)) {
                Log.d(TAG, "Purchase is Speed Up. Starting Speed Up consumption.");
                try {
                    // 消費開始
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabAsyncInProgressException e) {
                    Log.d(TAG, "Error consuming Speed Up. Another async operation in progress.");
                    return;
                }
            }

            // 「Power Up」購入完了
            else if (purchase.getSku().equals(Item.ITEMLIST[1].itemId)) {
                Log.d(TAG, "Purchase is Power Up. Starting Power Up consumption.");
                try {
                    // 消費開始
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabAsyncInProgressException e) {
                    Log.d(TAG, "Error consuming Power Up. Another async operation in progress.");
                    return;
                }
            }

            // 「Players Up」購入完了
            else if (purchase.getSku().equals(Item.ITEMLIST[2].itemId)) {
                Log.d(TAG, "Purchase is Players Up. Starting Players Up consumption.");
                try {
                    // 消費開始
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabAsyncInProgressException e) {
                    Log.d(TAG, "Error consuming Players Up. Another async operation in progress.");
                    return;
                }
            }

            // 「ステージ3」購入完了
            else if (purchase.getSku().equals(Item.ITEMLIST[3].itemId)) {
                Log.d(TAG, "Purchase is Stage 3.");

                // データベースに登録する
                mDatabase.addItem(purchase.getSku());

                // データベースの内容を画面に反映する
                checkOwnedItem();
            }
        }
    };

    // 消費処理が完了したら呼ばれるイベントリスナー
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            if (mHelper == null) return;

            if (result.isSuccess()) {
                Log.d(TAG, "Consumption successful. Provisioning.");

                // データベースに登録する
                mDatabase.addItem(purchase.getSku());
            }
            else {
                Log.d(TAG, "Error while consuming: " + result);
            }

            // データベースの内容を画面に反映する
            checkOwnedItem();

            Log.d(TAG, "End consumption flow.");
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

    // データベースの内容をゲームに反映する
    public void checkOwnedItem() {
        Cursor ownedItemsCursor;

        // データベースを参照してゲーム内のアイテム数を更新する
        ownedItemsCursor = mDatabase.queryAllPurchasedItems();
        ownedItemsCursor.moveToFirst();

        for (int i=0; i<ownedItemsCursor.getCount(); i++) {
            // スピードアップ数を更新する
            if (ownedItemsCursor.getString(0).equals("item_001")) {
                speedUp = ownedItemsCursor.getInt(1);

            // パワーアップ数を更新する
            } else if (ownedItemsCursor.getString(0).equals("item_002")) {
                powerUp = ownedItemsCursor.getInt(1);

            // プレイヤー数を更新する
            } else if (ownedItemsCursor.getString(0).equals("item_003")) {
                players = ownedItemsCursor.getInt(1);

            // ステージ3を有効にする
            } else if (ownedItemsCursor.getString(0).equals("item_004")) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        btn_stage3.setEnabled(true);
                    }
                });
            }
            ownedItemsCursor.moveToNext();
        }

        // 画面を更新する
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv_players.setText("PLAYERS:"+players);
                tv_speedup.setText("SPEED UP:"+speedUp);
                tv_powerup.setText("POWER UP:"+powerUp);
            }
        });
    }
}
