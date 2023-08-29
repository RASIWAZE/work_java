package work_java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lesson06 {
	
	public static void main(String[] args) {
		//1.リストを任意の値で作成
		List<String> lists = new ArrayList<>(List.of("first", "second", "third"));
		
		//2.作成したリストに要素を追加
		//listsはArrayListで可変長のため、.add()メソッドでリスト内の最後の要素として追加
		lists.add("fourth");
		
		//3.追加した要素にアクセス
		//ArrayListはgetで要素を取得、最後に追加された要素のインデックスは3なのでその数値で取得
		System.out.println(lists.get(3));
		
		//4.Mapを任意のキーと値で作成してください
		Map<String, Integer> person = new HashMap<>();
		
		//5.作成したMapに要素を追加してください
		person.put("house", 1);
		person.put("pets", 3);
		person.put("shoes", 6);
		
		//6.追加した要素にアクセスしてください
		System.out.println(person.get("house"));
		System.out.println(person.get("pets"));
		System.out.println(person.get("shoes"));
		
		//7.リストとMapを使用した多次元配列を作成してください。
		//Mapのデータを持つArrayListを変数studentに格納
		List<Map<String, Object>> student = new ArrayList<>();		
		
		//student内のHachMap要素を作成
		Map<String, Object> property1 = new HashMap<>();
		property1.put("name", "taro");
		property1.put("age", 20);
		property1.put("from", "tokyo");
		//studentに作成したHashMap要素を格納
		student.add(property1);		
		
		//同上
		Map<String, Object> property2 = new HashMap<>();
		property2.put("name", "jiro");
		property2.put("age", 25);
		property2.put("from", "osaka");
		student.add(property2);

		//同上
		Map<String, Object> property3 = new HashMap<>();
		property3.put("name", "saburo");
		property3.put("age", 30);
		property3.put("from", "aichi");
		student.add(property3);
		
		//for-each文で変数studentに格納されていた要素をMapのデータを持つ変数propertyに一つずつ代入しながら上書き
		for(Map<String, Object> property: student) {
			System.out.println(property);
		}
	}
}

















