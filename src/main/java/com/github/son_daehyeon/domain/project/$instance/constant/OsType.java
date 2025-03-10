package com.github.son_daehyeon.domain.project.$instance.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OsType {
	UBUNTU_24_10("ubuntu-24.10.tar.zst"),
	UBUNTU_24_04("ubuntu-24.04.tar.zst"),
	UBUNTU_22_04("ubuntu-22.04.tar.zst"),
	DEBIAN_12("debian-12.tar.zst"),
	DEBIAN_11("debian-11.tar.zst"),
	;

	private final String template;
}
