/*
 * Copyright (C) 2024 Universal Media Server
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.universalmediaserver.coverartarchive.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base setup and compare parsed object to json string.
 *
 * @author SurfaceS
 */
public class BaseTestClass {

	private static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();
	private static final String MUSICBRAINZ_AGENT = "MUSICBRAINZ_AGENT";

	protected static String userAgent;
	protected static CoverArtArchiveClient client;

	protected static boolean haveMusicBrainzUserAgent() {
		return System.getenv(MUSICBRAINZ_AGENT) != null;
	}

	@BeforeAll
	public static void setUpClass() {
		setMusicBrainzAPIClient();
	}

	private static void setMusicBrainzAPIClient() {
		userAgent = System.getenv(MUSICBRAINZ_AGENT);
		client = new CoverArtArchiveClient(userAgent);
		client.setTesting(true);
	}

	protected static void assertParsedObject(Object parsed) {
		assertTrue(compare(client.getLastBody(), parsed), "Parsed data missing something");
	}

	protected static boolean compare(String source, Object parsed) {
		JsonElement j1 = GSON.fromJson(source, JsonElement.class);
		//remove nulls
		j1 = GSON.toJsonTree(j1);
		JsonElement j2 = GSON.toJsonTree(parsed);
		if (!j1.equals(j2)) {
			compare(j1, j2, "$");
			System.out.println("received :");
			System.out.println(j1.toString());
			System.out.println("parsed :");
			System.out.println(j2.toString());
			return false;
		}
		return true;
	}

	private static void compare(JsonElement j1, JsonElement j2, String identifier) {
		if (j1.isJsonObject()) {
			if (!j2.isJsonObject()) {
				System.out.println(identifier + " is an object but parsed value is not.");
			} else if (!j1.equals(j2)) {
				compare(j1.getAsJsonObject(), j2.getAsJsonObject(), identifier);
			}
		} else if (j1.isJsonArray()) {
			if (!j2.isJsonArray()) {
				System.out.println(identifier + " is an array but parsed value is not.");
			} else if (!j1.equals(j2)) {
				compare(j1.getAsJsonArray(), j2.getAsJsonArray(), identifier);
			}
		} else if (j1.isJsonPrimitive()) {
			if (!j2.isJsonPrimitive()) {
				System.out.println(identifier + " is a primitive but parsed value is not.");
			} else if (!j1.equals(j2)) {
				System.out.println(identifier + " is a primitive not equals in parsed value.");
			}
		}
	}

	private static void compare(JsonObject j1, JsonObject j2, String identifier) {
		for (String key : j1.keySet()) {
			if (!j2.has(key)) {
				System.out.println(identifier + "." + key + " was not found in parsed value.");
			} else if (!j1.get(key).equals(j2.get(key))) {
				compare(j1.get(key), j2.get(key), identifier + "." + key);
			}
		}
	}

	private static void compare(JsonArray j1, JsonArray j2, String identifier) {
		if (j1.size() != j2.size()) {
			System.out.println(identifier + " array size not equals in parsed value.");
			return;
		}
		for (int i = 0; i < j1.size(); i++) {
			if (!j1.get(i).equals(j2.get(i))) {
				compare(j1.get(i), j2.get(i), identifier + "[" + i + "]");
			}
		}
	}

}
