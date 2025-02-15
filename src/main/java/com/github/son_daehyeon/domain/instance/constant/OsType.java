package com.github.son_daehyeon.domain.instance.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OsType {
	UBUNTU_24_04("ubuntu-24.04.tar.zst"),
	;

	private final String template;
}
