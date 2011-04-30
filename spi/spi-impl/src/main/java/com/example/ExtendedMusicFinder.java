package com.example;

import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ExtendedMusicFinder implements MusicFinder {
	public List<String> getMusic() {
		return Collections.singletonList("From ExtendedMusicFinder...");
	}
}
