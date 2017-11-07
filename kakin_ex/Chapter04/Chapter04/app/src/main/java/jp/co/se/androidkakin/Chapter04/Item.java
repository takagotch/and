package jp.co.se.androidkakin.Chapter04;

/*
 * ゲーム内で使用するアイテム情報を表現するクラス
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
		new Item("item_001", R.string.memu_item_speed_up),
		new Item("item_002", R.string.memu_item_power_up),
		new Item("item_003", R.string.memu_item_players_up),
		new Item("item_004", R.string.content_stage3),
    };
}
