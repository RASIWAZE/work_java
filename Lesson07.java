package work_java;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Lesson07 {

	public static void main(String[] args) {
		

		/*/////////////////////////////////////
		 * 1
		 *///////////////////////////////////*/
		for(int i = 1; i < 10; i++) {  //掛けられる数を、掛ける数が1-9までループする間固定。一回ループが完了したら数を繰り上げて再度掛ける数をループ
			
			System.out.println("-------------------");
			
			for(int j = 1; j < 10; j++) {  //掛ける数値のループ
				System.out.println(i + " * " + j + " = " + i*j);  //[1 * 1 = 1]の形式でループ中の数を出力
			}
		}
		
		System.out.println("-------------------");
		
		/*/////////////////////////////////////
		 * 2
		 *///////////////////////////////////*/
		HashMap<String, HashMap<String, String>> employees = new HashMap<String, HashMap<String, String>>() {
		    {
		        put("中田", new HashMap<String, String>() {
		            {
		                put("age", "23");
		                put("pref", "東京");
		            }
		        });
		        put("山本", new HashMap<String, String>() {
		            {
		                put("age", "19");
		                put("pref", "京都");
		            }
		        });
		        put("佐藤", new HashMap<String, String>() {
		            {
		                put("age", "30");
		                put("pref", "大阪");
		            }
		        });
		        put("小林", new HashMap<String, String>() {
		            {
		                put("age", "22");
		                put("pref", "福岡");
		            }
		        });
		    }
		};
		
		//HashMap<String, HashMap<String, String>>のキーと値を取得
		//for-each文でemployees.entrySet()で取得された値をHashMap.Entry<String, HashMap<String, String>>クラスのouterInfoに代入
		//.entrySet()はSet(Map.Entry<K, V>)クラスを返すのでouterInfoはHashMap.Entry<String, HashMap<String, String>>クラスの変数
		for(HashMap.Entry<String, HashMap<String, String>> outerInfo : employees.entrySet()) {
			
			//employees.entrySet()から取得されてouterInfoに代入された<String, HashMap<String, String>>のセットのうちString型のキーを取得
			String name = outerInfo.getKey();
			
			//employees.entrySet()から取得されてouterInfoに代入された<String, HashMap<String, String>>のセットのうちHashMap<String, String>型の値を取得
			HashMap<String, String> value = outerInfo.getValue();
			
			//名前であるキー要素をすべて出力
			System.out.println("名前: " + name);
			
			
			//HashMap<String, HashMap<String, String>>の値であるHashMap<String, String>のキーと値を取得
			//valueに代入されているouterInfo.getValue()、つまりHashMap<String, String>のセットをvalue.entrySet()で取得し、innerInfoへ代入
			for(HashMap.Entry<String, String> innerInfo : value.entrySet()) {
				
				//innerInfoに代入されたキーをStringクラス型の変数ageOrPrefに代入
				String ageOrPref = innerInfo.getKey();
				
				//ageOrPrefが"age"か"pref"かによって表示内容を変更
				if(ageOrPref == "age") {
					System.out.println("年齢: " + innerInfo.getValue());
				} else if (ageOrPref == "pref") {
					System.out.println("出身地: " + innerInfo.getValue());
				}				
			}
			System.out.println("-------------------");
			//HashMapのため出力順は挿入順によらない
		}
		
		
		
		/*/////////////////////////////////////
		 * 応用1
		 *///////////////////////////////////*/
		
		//3の倍数をthreeNums、3の倍数出ない数字をnotThreeNumsをに格納
		List<Integer> threeMulti = new ArrayList<>();
		List<Integer> notthreeMulti = new ArrayList<>();
		
		for(int i = 1; i <= 40; i++) {
			
			if((i % 3) == 0){
				threeMulti.add(i);
			} else {
				notthreeMulti.add(i);
			}
		}		
		
		
		//3の倍数でない2桁の数値でループ
		for(int j = 6; j < notthreeMulti.size(); j++) {
			
			//notThreeNumsのj番目の要素を文字列に変換
			String StrNum = String.valueOf(notthreeMulti.get(j));
			
			//containsメソッドで3が含まれていたらthreeMultiに追加
			if(StrNum.contains("3")) {
				threeMulti.add(notthreeMulti.get(j));
			}			
		}
		
		//3が付く数字と3の倍数だけのリストthreeMultiを数の小さい順に並べ替えて出力		
		Collections.sort(threeMulti);
		System.out.println(threeMulti);
		
		
		
		System.out.println("-------------------");
		
		

		/*/////////////////////////////////////
		 * 応用2
		 *///////////////////////////////////*/
		//HashMap<String, HashMap<String, String>>のキーから名前を抽出
		for(HashMap.Entry<String, HashMap<String, String>> outerInfo : employees.entrySet()) {
			
			String name = outerInfo.getKey();
			
			HashMap<String, String> value = outerInfo.getValue();
			
			//記述の前半を出力
			System.out.print("[name => '" + name + "', ");
			
			//HashMap<String, HashMap<String, String>>の値のHashMap<String, String>のうちのキーに当たる年齢を抽出、値である出身地はスキップ
			for(HashMap.Entry<String, String> innerInfo : value.entrySet()) {
				
				String age = innerInfo.getKey();
				
				
				if(age == "age") {
					//記述の後半を出力
					System.out.println("'age' => '" + innerInfo.getValue() + "']");
				} else if (age == "pref") {
					continue;
				}				
			}			
		}
	}
}