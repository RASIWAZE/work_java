package work_java;
import java.util.Random;

public class Lesson05 {

	public static void main(String[] args) {
		//1-10までの数値を生成(知らなかったのでネットで調べました)し、num5に格納
	    Random rand1 = new Random();
	    int num5 = rand1.nextInt(10) + 1;
	    
	    //num5を2で割った余りが0か1かで生成されたのが奇数が偶数か判定し、結果をString型変数evenOrOddに格納
	    String evenOrOdd = num5 % 2 == 0 ? "この数字は" + num5 + "なので偶数です。" : "この数字は" + num5 + "なので奇数です。";
	   
	    //evenOrOddを出力
	    System.out.println(evenOrOdd);

	    //1-100までの数値を生成し、scoreに格納
	    Random rand2= new Random();
	    int score = rand2.nextInt(101);
	    //点数が100点のとき
	    if (score == 100) {
	        System.out.println("得点は満点");
	        //点数が80点 ~ 99点のとき
	    } else if (score >= 80) {
	        System.out.println("得点は" + score + "点なので優です。");
	        //点数が70点 ~ 79点のとき
	    } else if (score >= 70) {
	        System.out.println("得点は" + score + "点なので良です。");
	        //点数が50点 ~ 69点のとき
	    } else if (score >= 50) {
	        System.out.println("得点は" + score + "点なので可です。");
	        //点数が0点 ~ 49点のとき
	    } else {
	        System.out.println("得点は" + score + "点なので不可です。");
	    }


	    //1-100までの数値を生成し、score2とscore3に格納
	    Random rand3= new Random();
	    int score2 = rand3.nextInt(101);
	    int score3 = rand3.nextInt(101);

	    //両方とも 60 点以上の場合
	    if (score2 >= 60 && score3 >= 60) {
	        System.out.println("合格");
	        //合計が 130 点以上の場合
	    } else if (score2 + score3 >= 130) {
	        System.out.println("合格");
	        //合計が 100 点以上かつ　どちらかのテストが90点以上
	    } else if (score2 + score3 >= 100 && (score2 >= 90 || score3 >= 90)) {
	        System.out.println("合格");
	        //上記以外
	    } else {
	        System.out.println("不合格");
	    }
	}
}
