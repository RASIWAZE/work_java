package com.EcSiteApplicationDemo.EcSiteApplicationDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	/* '/my-login'エンドポイントに対するGETリクエストを処理
     * my-login.htmlファイルを表示する */
	@GetMapping("/my-login")
	public String myGetLogin() {
		
		return "my-login";
	}
	
	/* "/my-login"エンドポイントから"/authenticateTheUser"エンドポイントに送信されてきたPostリクエストを
	 * SpringSecurityが処理して、 ログインできたらトップページを表示させる*/
 	@PostMapping("/authenticateTheUser")
 	public String authenticateTheUser() {
 		
 		return "top";
 	}
}
