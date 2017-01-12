/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.google.code.shardbatis;

public class ShardException extends Exception {
	private static final long serialVersionUID = 1793760050084714190L;

	public ShardException() {
	}

	public ShardException(String msg) {
		super(msg);
	}

	public ShardException(String msg, Throwable t) {
		super(msg, t);
	}

	public ShardException(Throwable t) {
		super(t);
	}
}