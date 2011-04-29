package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class MusicFinder {
	public static List<String> getMusic() {
		final List<String> music = new ArrayList<String>();
		music.add("Vasya Pupkin - Empty track.mp3");
		music.add("John Smith - How are you.mp3");
		music.add("Qwerty - asdfghj.mp3");
		music.add("<Unnamed> - <Unnamed>.wav");
		music.add("Bender - Kill all humans.mp3");
		return music;
	}
}
