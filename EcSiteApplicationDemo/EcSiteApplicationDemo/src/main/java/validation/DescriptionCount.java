package validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


/*@Constraintでバリデーション内容を記述したクラスを指定
 *@Targetでどの要素に適用でk理宇かを指定
 *@Retentionで子のバリデーションが保持される期間を指定  */
@Constraint(validatedBy = DescriptionCountConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DescriptionCount {

	//　アノテーションに渡すパラメータを設定、エラーがあった時のエラーメッセージを設定
		public String message() default "・200文字以下で入力してください";
		
		public Class<?>[] groups() default {};
		
		public Class<? extends Payload>[] payload() default {};
}
