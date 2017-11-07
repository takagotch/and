package jp.co.se.androidkakin.Ap1_2_APKExpansionFiles;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.vending.expansion.zipfile.ZipResourceFile;
import com.android.vending.expansion.zipfile.ZipResourceFile.ZipEntryRO;
import com.google.android.vending.expansion.downloader.DownloadProgressInfo;
import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;
import com.google.android.vending.expansion.downloader.DownloaderServiceMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.IDownloaderClient;
import com.google.android.vending.expansion.downloader.IDownloaderService;
import com.google.android.vending.expansion.downloader.IStub;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

public class MainActivity extends AppCompatActivity  implements IDownloaderClient {

    private TextView mStatusText;
    private ImageView mImageView;

    private int mState;
    private Bitmap img;

    private IDownloaderService mRemoteService;

    private IStub mDownloaderClientStub;

    private void setState(int newState) {
        // newStateが更新されたときにだけメッセージを更新する
        if (mState != newState) {
            // newStateに対応するメッセージを表示する
            mStatusText.setText(Helpers.getDownloaderStringResourceIDFromState(newState));
            mState = newState;
        }
    }

    // 拡張ファイルの定義クラス
    private static class XAPKFile {
        public final boolean mIsMain; // true:mainファイル, false:patchファイル
        public final int mFileVersion; // ファイルバージョン
        public final long mFileSize; // ファイルサイズ

        XAPKFile(boolean isMain, int fileVersion, long fileSize) {
            mIsMain = isMain;
            mFileVersion = fileVersion;
            mFileSize = fileSize;
        }
    }

    // このアプリケーションで使用する拡張ファイル
    private static final XAPKFile[] xAPKS = {
            // mainファイルの定義
            new XAPKFile(
                    true, // mainファイル
                    2, // ファイルバージョン
                    14703L // ファイルサイズ
            )
    };

    // 既に拡張ファイルがダウンロードされているか確認する
    boolean expansionFilesDelivered() {
        for (XAPKFile xf : xAPKS) {
            // ファイル名を取得する
            String fileName = Helpers.getExpansionAPKFileName(this, xf.mIsMain, xf.mFileVersion);
            // 拡張ファイルが存在しなければfalseを返す
            if (!Helpers.doesFileExist(this, fileName, xf.mFileSize, false)) {
                return false;
            }
        }
        // 拡張ファイルが存在すればtrueを返す
        return true;
    }

    // ダウンロードした拡張ファイルの整合性を検証する
    void validateXAPKZipFiles() {
        AsyncTask<Object, DownloadProgressInfo, Boolean> validationTask = new AsyncTask<Object, DownloadProgressInfo, Boolean>() {

            @Override
            protected void onPreExecute() {
                // 検証開始のメッセージを設定する
                mStatusText.setText(R.string.text_verifying_download);
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Object... params) {
                // 登録してある拡張ファイルを1つずつ処理する
                for (XAPKFile xf : xAPKS) {
                    // ファイル名を取得する(ファイル名のみ)
                    String fileName = Helpers.getExpansionAPKFileName(MainActivity.this, xf.mIsMain, xf.mFileVersion);

                    // もし、ダウンロードした拡張ファイルが存在しない場合はエラーを返す
                    if (!Helpers.doesFileExist(MainActivity.this, fileName, xf.mFileSize, false)) {
                        return false;
                    }

                    // ダウンロードした拡張ファイルのファイル名を取得する(フルパス)
                    fileName = Helpers.generateSaveFileName(MainActivity.this, fileName);

                    ZipResourceFile zrf;
                    byte[] buf = new byte[1024*256];
                    try {
                        // ダウンロードした拡張ファイルを開く
                        zrf = new ZipResourceFile(fileName);

                        // 拡張ファイルに含まれる全てのエントリ情報を取得する
                        ZipEntryRO[] entries = zrf.getAllEntries();

                        // 全てのエントリ情報を1つずつ処理する
                        for (ZipEntryRO entry : entries) {
                            if (-1 != entry.mCRC32) {
                                // エントリのデータからCRC情報を計算する
                                long length = entry.mUncompressedLength;
                                CRC32 crc = new CRC32();
                                DataInputStream dis = null;

                                // エントリから入力ストリームを作成する
                                dis = new DataInputStream(zrf.getInputStream(entry.mFileName));

                                while (length > 0) {
                                    int seek = (int) (length > buf.length ? buf.length : length);
                                    dis.readFully(buf, 0, seek);
                                    crc.update(buf, 0, seek);
                                    length -= seek;
                                }

                                // 計算したCRC情報が、エントリに含まれるCRC情報と一致しなければエラーを返す
                                if (crc.getValue() != entry.mCRC32) {
                                    return false;
                                }

                                if (null != dis) {
                                    dis.close();
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            protected void onProgressUpdate(DownloadProgressInfo... values) {
                onDownloadProgress(values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    mStatusText.setText(R.string.text_validation_complete);
                } else {
                    mStatusText.setText(R.string.text_validation_failed);
                }
                super.onPostExecute(result);
            }

        };
        validationTask.execute(new Object());
    }

    // ダウンロードした拡張ファイルの整合性を検証する
    void showPicturesFiles() {
        AsyncTask<Object, DownloadProgressInfo, Boolean> showPicturesTask = new AsyncTask<Object, DownloadProgressInfo, Boolean>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Object... params) {
                // 登録してある拡張ファイルを1つずつ処理する
                for (XAPKFile xf : xAPKS) {
                    // ファイル名を取得する(ファイル名のみ)
                    String fileName = Helpers.getExpansionAPKFileName(MainActivity.this, xf.mIsMain, xf.mFileVersion);

                    // もし、ダウンロードした拡張ファイルが存在しない場合はエラーを返す
                    if (!Helpers.doesFileExist(MainActivity.this, fileName, xf.mFileSize, false)) {
                        return false;
                    }

                    // ダウンロードした拡張ファイルのファイル名を取得する(フルパス)
                    fileName = Helpers.generateSaveFileName(MainActivity.this, fileName);

                    ZipResourceFile zrf;
                    try {
                        // ダウンロードした拡張ファイルを開く
                        zrf = new ZipResourceFile(fileName);

                        // 拡張ファイルに含まれる全てのエントリ情報を取得する
                        ZipEntryRO[] entries = zrf.getAllEntries();

                        // 全てのエントリ情報を1つずつ処理する
                        for (ZipEntryRO entry : entries) {
                            DataInputStream dis = null;

                            // エントリから入力ストリームを作成する
                            dis = new DataInputStream(zrf.getInputStream(entry.mFileName));

                            // 画像データをバッファに読み込む
                            img = BitmapFactory.decodeStream(dis);

                            if (null != dis) {
                                dis.close();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            protected void onProgressUpdate(DownloadProgressInfo... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    mImageView.setImageBitmap(img);
                }
                super.onPostExecute(result);
            }

        };
        showPicturesTask.execute(new Object());
    }

    private void initializeDownloadUI() {
        setContentView(R.layout.activity_main);

        mStatusText = (TextView) findViewById(R.id.statusText);
        mImageView = (ImageView) findViewById(R.id.imageView1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeDownloadUI();
        // スタブを作成する
        mDownloaderClientStub =
                DownloaderClientMarshaller.CreateStub(this, SampleDownloaderService.class);

        // 既に拡張ファイルがダウンロードされていないか確認し、ダウンロードされていない場合はダウンロードを開始する
        if (!expansionFilesDelivered()) {
            try {
                Intent launchIntent = MainActivity.this.getIntent();

                // ノーティフィケーションを選択したときに発行するインテントを作成する
                Intent intentToLaunchThisActivityFromNotification
                        = new Intent(MainActivity.this, MainActivity.this.getClass());
                intentToLaunchThisActivityFromNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentToLaunchThisActivityFromNotification.setAction(launchIntent.getAction());

                if (launchIntent.getCategories() != null) {
                    for (String category : launchIntent.getCategories()) {
                        intentToLaunchThisActivityFromNotification.addCategory(category);
                    }
                }

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        MainActivity.this,
                        0, intentToLaunchThisActivityFromNotification,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                int startResult = DownloaderClientMarshaller.startDownloadServiceIfRequired(this,
                        pendingIntent, SampleDownloaderService.class);

                if (startResult != DownloaderClientMarshaller.NO_DOWNLOAD_REQUIRED) {
                    initializeDownloadUI();
                    return;
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            // 既に拡張ファイルがダウンロードされている場合は、ダウンロードされているファイルの整合性を検証する
            validateXAPKZipFiles();
            showPicturesFiles();
        }
    }

    @Override
    protected void onStart() {
        if (null != mDownloaderClientStub) {
            mDownloaderClientStub.connect(this);
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (null != mDownloaderClientStub) {
            mDownloaderClientStub.disconnect(this);
        }
        super.onStop();
    }

    @Override
    public void onServiceConnected(Messenger m) {
        mRemoteService = DownloaderServiceMarshaller.CreateProxy(m);
        mRemoteService.onClientUpdated(mDownloaderClientStub.getMessenger());
    }

    @Override
    public void onDownloadStateChanged(int newState) {
        setState(newState);
        switch (newState) {
            case IDownloaderClient.STATE_IDLE:
                break;
            case IDownloaderClient.STATE_CONNECTING:
                break;
            case IDownloaderClient.STATE_FETCHING_URL:
                break;
            case IDownloaderClient.STATE_DOWNLOADING:
                break;
            case IDownloaderClient.STATE_FAILED_CANCELED:
                break;
            case IDownloaderClient.STATE_FAILED:
                break;
            case IDownloaderClient.STATE_FAILED_FETCHING_URL:
                break;
            case IDownloaderClient.STATE_FAILED_UNLICENSED:
                break;
            case IDownloaderClient.STATE_PAUSED_NEED_CELLULAR_PERMISSION:
                break;
            case IDownloaderClient.STATE_PAUSED_WIFI_DISABLED_NEED_CELLULAR_PERMISSION:
                break;
            case IDownloaderClient.STATE_PAUSED_BY_REQUEST:
                break;
            case IDownloaderClient.STATE_PAUSED_ROAMING:
                break;
            case IDownloaderClient.STATE_PAUSED_SDCARD_UNAVAILABLE:
                break;
            case IDownloaderClient.STATE_COMPLETED:
                validateXAPKZipFiles();
                showPicturesFiles();
                return;
            default:
        }
    }

    @Override
    public void onDownloadProgress(DownloadProgressInfo progress) {
        //
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
