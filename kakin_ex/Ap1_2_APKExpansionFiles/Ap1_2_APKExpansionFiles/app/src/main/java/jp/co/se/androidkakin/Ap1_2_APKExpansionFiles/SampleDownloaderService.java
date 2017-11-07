
package jp.co.se.androidkakin.Ap1_2_APKExpansionFiles;

import com.google.android.vending.expansion.downloader.impl.DownloaderService;

public class SampleDownloaderService extends DownloaderService {
	// 公開鍵
    private static final String BASE64_PUBLIC_KEY = "MUST_BE_SET_WHEN_REGIST_TO_PLAY_STORE"; // ここに公開鍵を設定する

    // SALTデータ
    private static final byte[] SALT = new byte[] {
            1, 43, -12, -1, 54, 98,
            -100, -12, 43, 2, -8, -4, 9, 5, -106, -108, -33, 45, -1, 84
    };

    @Override
    public String getPublicKey() {
    	// 公開鍵を返す
        return BASE64_PUBLIC_KEY;
    }

    @Override
    public byte[] getSALT() {
    	// SALTデータを返す
        return SALT;
    }

    @Override
    public String getAlarmReceiverClassName() {
    	// ブロードキャストレシーバの名前を返す
        return SampleAlarmReceiver.class.getName();
    }

}
