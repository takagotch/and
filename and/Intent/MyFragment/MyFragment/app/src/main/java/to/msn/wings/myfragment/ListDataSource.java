package to.msn.wings.myfragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ListDataSource {
    private static final ArrayList<String> name = new ArrayList<String>() {
        {
            add("胡椒");
            add("ターメリック");
            add("コリアンダー");
            add("生姜");
            add("ニンニク");
            add("サフラン");
        }
    };
    private static final ArrayList<String> alias = new ArrayList<String>() {
        {
            add("pepper");
            add("turmeric");
            add("coriander");
            add("ginger");
            add("garlic");
            add("saffron");
        }
    };
    private static final ArrayList<String> info = new ArrayList<String>() {
        {
            add("コショウ科コショウ属でつる性植物です。黒胡椒、白胡椒、青胡椒、赤胡椒などがあります。");
            add("ショウガ科ウコン属で多年草です。「ウコン」とも呼ばれます。");
            add("セリ科の一年草です。「パクチー」「香菜（シャンツァイ）」「中国パセリ」とも呼ばれます。");
            add("ショウガ科の多年草です。野菜として料理に使ったり、薬としても使用されます。");
            add("ヒガンバナ科ネギ属の多年草です。日本では、2月29日がニンニクの日です。");
            add("アヤメ科の多年草です。めしべを乾燥させた香辛料を、パエリアなどの料理で使います。");
        }
    };

    public static List<String> getAllNames() {
        return name;
    }

    public static List<Map<String, String>> getAll() {
        List<Map<String, String>> result = new ArrayList<>();
        for(int i = 0; i < name.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", name.get(i));
            map.put("alias", alias.get(i));
            map.put("info", info.get(i));
            result.add(map);
        }
        return result;
    }

    static Map<String, String> getInfoByName(String key) {
        int index = name.indexOf(key);
        Map<String, String> map = new HashMap<>();
        map.put("name", name.get(index));
        map.put("alias", alias.get(index));
        map.put("info", info.get(index));
        return map;
    }
}
