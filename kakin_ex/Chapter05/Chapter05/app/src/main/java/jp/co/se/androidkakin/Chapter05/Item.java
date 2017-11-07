package jp.co.se.androidkakin.Chapter05;

/*
 * 電子書籍アプリ内で使用するアイテム情報を表現するクラス
 *
 */
public class Item {
	public String itemId;
	public int nameId;

	public Item(String itemId, int nameId) {
		this.itemId = itemId;
		this.nameId = nameId;
	}

	public static final Item[] ITEMLIST = new Item[] {
			new Item("content1_weekly", R.string.content1_weekly),
			new Item("content1_monthly", R.string.content1_monthly),
			new Item("content2_weekly", R.string.content2_weekly),
			new Item("content3_weekly", R.string.content3_weekly),
	};
}
