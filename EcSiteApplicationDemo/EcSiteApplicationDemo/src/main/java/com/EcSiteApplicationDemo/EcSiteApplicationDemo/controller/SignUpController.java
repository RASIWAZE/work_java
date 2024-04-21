package com.EcSiteApplicationDemo.EcSiteApplicationDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.User;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.service.EcSiteService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class SignUpController {
		
	private EcSiteService ecSiteService;

	//　パスワードをハッシュ化するためにフィールドにBCryptPasswordEncoderクラスを追加
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public SignUpController(EcSiteService ecSiteService, BCryptPasswordEncoder passwordEncoder){
		this.ecSiteService = ecSiteService;
		this.passwordEncoder = passwordEncoder;
	}
	
	// このクラス内のすべてのリクエストハンドラーメソッド実行前に実行されるメソッド
	@InitBinder
	// ビューから渡されたパラメータをjavaオブジェクトにバインドするクラスであるdataBinderに格納
	public void initBinder(DataBinder dataBinder) {

		// 渡された文字列パラメータの前後の空白を除去するためのStringTrimmerEditorクラスをインスタンス化
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		// StringクラスのパラメータにStringTrimmerEditorクラスを実行して空白を除去する
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	// '/sign-up'エンドポイントにGEＴリクエストがあったらユーザー新規登録ページを返す
    @GetMapping("/sign-up")
	public String signUp(Model theModel) {
		
    	//　ユーザークラスのインスタンスを作成
    	User theUser = new User();
    	
    	// ユーザーインスタンスをuserとしてModelインターフェイスに追加
    	theModel.addAttribute("user", theUser);
    	
    	// ユーザー新規登録ページを返す
		return "sign-up";
	}
    
    /* エンドポイント"/sign-up"からエンドポイント""に送信されてきたPostリクエストを処理して 
     * 新規ユーザーを登録してトップページを表示させる*/
    @PostMapping("/registerTheUser")
    public String registerTheUser(@Valid @ModelAttribute("user") User user,
                                    BindingResult theBindingResult,
                                    HttpSession session, Model theModel) {

        System.out.println(user.getPassword()); 
        
        // ユーザー登録画面で入力されたデータにエラーがあった場合、ユーザー登録画面にリダイレクトされる
        if (theBindingResult.hasErrors()){

            System.out.println("登録情報入力にエラーがありました。\n"); 

            return "sign-up";
        }

        // ユーザーが入力したパスワードをbcryptアルゴリズムでハッシュ化
        String theUserPass = passwordEncoder.encode(user.getPassword());

        // ユーザー登録画面から渡されたユーザーデータにactiveデータをUserインスタンスに追加してtempUserに代入
        User tempUser = new User (user.getId() ,user.getEmail(),theUserPass, true);    

        System.out.println(tempUser); 

                

        // tempUserのid(ユーザー名)と同じユーザー名があった場合取得して、Userクラスのexistingに代入
        User existingId = ecSiteService.findUserById(tempUser.getId());
        
        // existingIdに値が代入されていた場合、新規ユーザー登録画面へ戻す
        if (existingId != null){
            theModel.addAttribute("user", new User());
            theModel.addAttribute("idRegistrationError", "・このユーザー名は既に使用されています。");

            System.out.println("このユーザー名は既に使用されています。");

            return "sign-up";
        }
        
        // tempUserのメールアドレスと同じメールアドレスがあった場合取得して、UserクラスのexistingEmailに代入
        User existingEmail = ecSiteService.findByEmail(tempUser.getEmail());
        
        // existingEmailに値が代入されていた場合、新規ユーザー登録画面へ戻す
        if (existingEmail != null){
            theModel.addAttribute("user", new User());
            theModel.addAttribute("emailRegistrationError", "・このメールアドレスは既に使用されています。");

            System.out.println("このメールアドレスは既に使用されています。");

            return "sign-up";
        }

        // データベースへ新規ユーザー情報を登録
        ecSiteService.registerUser(tempUser);

        System.out.println("ユーザーを保存しました。");

        // セッション情報に新規ユーザーを保存
        session.setAttribute("user", tempUser);

        // 重複して同じユーザーを保存できないようにredirectプレフィックスを追加してトップページへリダイレクトする
        return "redirect:/my-login";
    }
}