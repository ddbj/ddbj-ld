package com.ddbj.ld.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
	// 認証処理を差し込むインターセプタのためのマーカアノテーション。
	// 特に実装はない。
}
