package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class DummyMusicFinder implements MusicFinder {
	public List<String> getMusic() {
		return Collections.singletonList("From DummyMusicFinder...");
	}
}
