package work_java;

import java.util.Arrays;

public class Lesson08 {
	public static void main(String[] args) {
		
		/*//////////////////////////////////////
		 * 1
		 * *////////////////////////////////////		
		//mean()メソッドをstaticで定義していないのでLesson08クラスの暗黙定義のコンストラクタを呼び出してインスタンス化
		Lesson08 method = new Lesson08();
		
		//mean()メソッドを呼び出して、正常に入力された場合の1, 2, 3の引数を渡して戻り値をString型の変数resultに代入
		String result = method.orStr("1", "2", "3");
		
		//引数が数値でない場合の1, a, 3の引数を渡して戻り値をString型の変数resultStrに代入
		String resultStr = method.orStr("1", "a", "3");
		
		//引数が３つでない場合の1, 2, 空白の引数を渡して戻り値をString型の変数resultEmptyに代入
		String resultEmpty = method.orStr("1", "2", " ");
		
		//resultの戻り値を出力
		System.out.println(result);
		System.out.println(resultStr);
		System.out.println(resultEmpty);
		
//		出力結果
//		2.0
//		2つ目に入力されたものが数値ではありません
//		最後に入力されたものが数値ではありません
		
		
		System.out.println("----------");
		/*//////////////////////////////////////
		 * 2
		 * *////////////////////////////////////
		String[] colors1 = {"BLUE", "RED", "GREEN"};
		String[] colors2 = {"yellow", "magenta", "cyan"};
		
		//2つの配列 colors1, colors2 を合体して１つの配列 colors3 にする
		arrays(colors1, colors2);		
		
//		出力結果
//		[BLUE, RED, GREEN, yellow, magenta, cyan]
//		[blue, red, green, yellow, magenta, cyan]
//		[blue, red, green, yellow, magenta, cyan, black]
//		greenあり
	}

	
	
	/*//////////////////////////////////////
	 * 1
	 * *////////////////////////////////////
	
	//受け取ったString型の数値を引数が３つか、引数が数値でないかを確認し、3つの数値であった場合に平均値を返すメソッド
	public String orStr(String x, String y, String z) {
		
		//////////引数が空欄だった場合に返す結果//////////
		
		//String形配列numsの要素として引数x, y, zを格納
		String[] nums = {x, y, z};
		
		//引数が空欄出ないかチェックし、空欄だった場合は「入力された数値が３つに足りません」と出力
		for(int i = 0; i < 3; i++) {
			//半角・全角の空白が入力された、もしくは何も入力されなかった場合
			if(nums[i].equals(" ") || nums[i].equals("　") ||  nums[i].equals("")) {
				return "入力された数値が３つに足りません";
			} else {
				continue;
			}
		}
		
		//////////引数が数値でなかった場合に返す結果//////////
		//平均値の計算で使うint型配列intNumsを初期化
		int[] intNums = {0, 0, 0};
		
		//intNumsに引数をint型に変換して代入、正しく数値に変換できなかった場合は「数値でない値が入力されています」と出力
		for(int i = 0; i < 3; i++) {
			try {
				intNums[i] = Integer.parseInt(nums[i]);
			} catch (NumberFormatException e){
				return "数値でない値が入力されています";
			}
		}
		
		//intNumsに代入された引数x,y,zの平均値をdouble型変数Calculateに代入
		double Calculate = Math.round((((intNums[0] + intNums[1] + intNums[2]) / 3.0) * 10.0) / 10.0);
		
		//平均値の結果Calculateを文字列にして返す
		return "平均値: " + Calculate;
	}

	
	
	
	/*//////////////////////////////////////
	 * 2
	 * *////////////////////////////////////
	
	/* （「Javaの組み込みメソッドのみを利用」の組み込みメソッドというのがどういうものか調べてもよくわからなかったので
	 * 「クラスをimportせずにデフォルトの状態のまま」という解釈で課題の解決を試みました）*/
	
	
	public static void arrays (String[] a, String[] b) {
		
		//////////2つの配列 colors1, colors2 を合体して１つの配列 colors3 にする//////////
		//引数として渡されて配列aと配列bの合計要素数で呼び出した配列をcolors3に代入
		String[] colors3 = new String[a.length + b.length];
		
		//配列aに格納されているi番目の要素をcolors3のi番目に格納
		for(int i = 0; i < a.length; i++) {
			colors3[i] = a[i];
		}
		
		//配列bに格納されているj番目の要素をcolors3のi番目に格納
		//iは上記のループで配列aの要素が格納された要素の続きから
		int j = 0;
		
		for(int i = a.length; i < a.length + b.length; i++) {			
			colors3[i] = b[j];
			j++;		
		}
		
		System.out.println(Arrays.toString(colors3));
		
		
		//////////colors3 のすべての文字を小文字に揃える//////////
		//ループでcolors3に格納されている各要素をtoLowerCase()で小文字に変換して再格納
		for(int i = 0; i < colors3.length - 1; i++) {
			colors3[i] = colors3[i].toLowerCase();
		}
		
		System.out.println(Arrays.toString(colors3));
		
		
		
		//////////colors3 配列の最後に要素 'black'を追加//////////
		//colors3のサイズ+1の要素数の配列copied3を作成
		String[] copied3 = new String[colors3.length + 1];
		
		//copied3にcolors3の要素をコピー、末尾の格納スペースは空のまま
		System.arraycopy(colors3, 0, copied3, 0, colors3.length);
		
		//copied3の末尾に"black"を格納
		copied3[colors3.length] = "black";
		
		//colors3の参照先をcopied3に変更
		colors3 = copied3;
		
		System.out.println(Arrays.toString(colors3));
		
		
		//////////colors3 配列に要素 'green' が存在するかを判定し、存在したら「あり」存在しなければ「なし」というメッセージを表示//////////
		//for-each文でcolors3の各要素をtrueOrFalseへ代入
		for(String trueOrFalse : colors3) {
			
			//trueOrFalseへ代入された要素が"green"か確認
			//"green"であれば"あり"と出力、なければスキップ
			if(trueOrFalse.equals("green")) {
				System.out.println("greenあり");
			} else {
				continue;
			}
		}
	}
}