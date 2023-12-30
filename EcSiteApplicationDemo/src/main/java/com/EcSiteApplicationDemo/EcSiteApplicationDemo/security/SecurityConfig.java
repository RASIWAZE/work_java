package com.EcSiteApplicationDemo.EcSiteApplicationDemo.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration  // このクラスがSpringの設定クラスであることを示す
public class SecurityConfig {
	
	// ユーザー登録時のパスワードハッシュ化時に必要なBCryptPasswordEncoderクラスを定義
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	/* UserDetailsManagerを実装したJdbcUserDetailsManagerを使って、DBからログインフォームで入力されたユーザー名に対応するユーザー情報を取得し、
	 * 取得した値（ユーザー名・パスワード・有効アカウントかどうか）をプロパティにセットしたUserDetailsManagerクラスのインスタンスを返す */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // ログインフォームで入力されたユーザー名に対応するユーザー情報をDBから検索するためのSQLクエリを設定
        //　該当するユーザーのid(ユーザー名)、パスワード、有効なアカウントかどうかを返す
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select id, password, active from users where id=?");

        // ログインフォームで入力されたユーザー名に対応するユーザーのロールをDBから検索するためのSQLクエリを設定する
        // 該当するユーザーのid・ロールを返す
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?");

        // 上記のコードでプロパティがセットされたオブジェクトjdbcUserDetailsManagerを返す
        return jdbcUserDetailsManager;
    }

    @Bean
    //  特定のエンドポイントへのアクセスをロールベースで制限、ログイン・ログアウトの設定、例外処理を設定
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                        		.requestMatchers("/").hasRole("USER")  // "/owner/**"のエンドポイントは"OWNER"ロールを持つユーザーのみアクセス可能
                                .requestMatchers("/owner/**").hasRole("OWNER")  // "/owner/**"のエンドポイントは"OWNER"ロールを持つユーザーのみアクセス可能
                                .requestMatchers("/sign-up","/registerTheUser","/css/**").permitAll()  // ユーザー登録ページとその処理メソッド、静的リソースへのアクセスを全てのユーザーに許可
                                .anyRequest().authenticated()  // 上記以外のエンドポイントに対してはすべてのログインしたユーザーがアクセス可能
                )
        		// ログインフォーム設定
                .formLogin(form ->
                        form
                                .loginPage("/my-login")  // ログインページのエンドポイント
                                .loginProcessingUrl("/authenticateTheUser")  // ログインフォーム情報の送信先エンドポイント
                                .permitAll()  // すべてのユーザーがログインページとログイン処理URLにアクセス可能
                )
                .logout(logout -> logout.permitAll()  // すべてのユーザーがログアウト可能、デフォルトのエンドポイント"/logout"にアクセス
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")  // アクセス権がないページにアクセスしようとした場合のリダイレクト先
                );

        return http.build();  // HttpSecurityオブジェクトをビルドして返す
    }
}
