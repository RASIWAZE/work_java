package DemoJson;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DemoJson {

    public static void main(String[] args) throws IOException {
    	
    	// ObjectMapperでJSONデータとJavaオブジェクトを変換
        ObjectMapper objectMapper = new ObjectMapper();
        
        // readTreeメソッドを使って、指定されたパスのJSONファイルを読み込み、その内容をJsonNodeオブジェクトとして取得
        JsonNode json = objectMapper.readTree(Paths.get("src/main/resources/sample.json").toFile());
        
		// ObjectMapperのconvertValueメソッドを使って、JsonNodeオブジェクトをLinkedHashMapオブジェクトとして取得、jsonListに代入
        LinkedHashMap<String, LinkedHashMap<String, Object>> jsonList = objectMapper.convertValue(json, LinkedHashMap.class);
		
        
        ////////////////////
        // 課題1
        ////////////////////
        for(int i=1; i <= json.size(); i++) {
        
            String id;
            String name;
            
            // 10未満の数値に対しては、頭に0を付けて2桁にする
            if(i < 10) {                

                id = json.get("0" + i).get("id").textValue();
                name = json.get("0" + i).get("short").textValue();
                
            } else {
                // 10以上の数値に対しては、そのまま文字列に変換
                id = json.get("" + i).get("id").textValue();
                name = json.get("" + i).get("short").textValue();
            }
              
            // idとnameを出力
            System.out.println("id:" + id + "は" + name);
        }
		
        
		System.out.println("\n--------------------------\n");
		
		
        ////////////////////
        // 課題2
        ////////////////////		
		
		// printItemsメソッドを実行
        printItems(jsonList);
    }
    

    public static void printItems(LinkedHashMap<String, LinkedHashMap<String, Object>> jsonList) {
    	
    	// ループカウンタを初期化
        int loop = 0;
        
        // jsonListのキーのセットを取得し、それぞれのキーについて処理
        for (String key : jsonList.keySet()) {
        	
        	// jsonListからキーに対応する値を取得し、それをLinkedHashMapとしてitemに代入
            LinkedHashMap<String, Object> item = jsonList.get(key);
            
            // itemから"name"というキーに対応する値を取得し、それをString型にキャストしてnameに代入
            String name = (String) item.get("name");
            
            // itemから"city"というキーに対応する値を取得し、それをList<LinkedHashMap<String, String>>型にキャストしてcitiesに代入
            List<LinkedHashMap<String, String>> cities = (List<LinkedHashMap<String, String>>) item.get("city");
            
            // citiesの各要素を取得して、cityに代入
            for (LinkedHashMap<String, String> city : cities) {
            	
            	// cityから"city"というキーに対応する値を取得し、cityNameに代入
                String cityName = city.get("city");
                
                /* 
                 *  */
                System.out.println("[" + loop + "]=> "
                		+ "\n array(1) {"
                		+ "\n [\"" + name + "\"]=> "
                		+ "\n string(" + cityName.getBytes().length + ") \"" + cityName + "\""
                		+ "\n  }");
                
                // ループカウンタをインクリメント
                loop++;
            }
        }
    }
}


