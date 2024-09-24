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
import com.google.gson.GsonBuilder;
import com.universalmediaserver.coverartarchive.api.endpoint.release.ReleaseEndpoint;
import com.universalmediaserver.coverartarchive.api.endpoint.release_group.ReleaseGroupEndpoint;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cover Art Archive API client.
 *
 * @author SurfaceS
 */
public class CoverArtArchiveClient {

	private static final String DEFAULT_BASE_URL = "https://coverartarchive.org/";
	private static final Gson GSON = new GsonBuilder().create();
	private static final String ERROR = ": CoverArtArchive error";
	private static final String ERROR_REQUEST = "Error while sending the request";

	private String userAgent;
	private String baseUrl;
	/**
	 * used for testing json members
	 */
	private String lastBody;
	private boolean testing;

	/**
	 * Create a new MusicBrainz API client.
	 */
	public CoverArtArchiveClient() {
		this(DEFAULT_BASE_URL, null);
	}

	/**
	 * Create a new Cover Art Archive API client.
	 *
	 * @param userAgent User-Agent header
	 */
	public CoverArtArchiveClient(String userAgent) {
		this(DEFAULT_BASE_URL, userAgent);
	}

	/**
	 * Create a new Cover Art Archive API client.
	 *
	 * @param baseUrl the base api url
	 * @param userAgent User-Agent header
	 */
	public CoverArtArchiveClient(String baseUrl, String userAgent) {
		this.baseUrl = baseUrl;
		this.userAgent = userAgent;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getLastBody() {
		return lastBody;
	}

	public void setUserAgent(String value) {
		this.userAgent = value;
	}

	public void setBaseUrl(String value) {
		this.baseUrl = value;
	}

	public void setTesting(boolean value) {
		this.testing = value;
	}

	/**
	 * Get release endpoint.
	 *
	 * @return Release endpoint.
	 */
	public ReleaseEndpoint release(String musicBrainzId) {
		return new ReleaseEndpoint(this, musicBrainzId);
	}

	/**
	 * Get release group endpoint.
	 *
	 * @return Release Group endpoint.
	 */
	public ReleaseGroupEndpoint releaseGroup(String musicBrainzId) {
		return new ReleaseGroupEndpoint(this, musicBrainzId);
	}

	public <T> T get(String endpoint, Class<T> resultClass, Map<String, String> query) {
		try {
			HttpRequest request = getBuilder(endpoint, query).GET().build();
			return getResult(request, resultClass);
		} catch (CoverArtArchiveException ex) {
			Logger.getLogger(CoverArtArchiveClient.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
		return null;
	}

	public <T> T post(String endpoint, Class<T> resultClass, Map<String, String> query, String postData) {
		try {
			HttpRequest request = getBuilder(endpoint, query).POST(BodyPublishers.ofString(postData)).build();
			return getResult(request, resultClass);
		} catch (CoverArtArchiveException ex) {
			Logger.getLogger(CoverArtArchiveClient.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public <T> T put(String endpoint, Class<T> resultClass, Map<String, String> query, String postData) {
		try {
			HttpRequest request = getBuilder(endpoint, query).PUT(BodyPublishers.ofString(postData)).build();
			return getResult(request, resultClass);
		} catch (CoverArtArchiveException ex) {
			Logger.getLogger(CoverArtArchiveClient.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public <T> T delete(String endpoint, Class<T> resultClass, Map<String, String> query) {
		try {
			HttpRequest request = getBuilder(endpoint, query).DELETE().build();
			return getResult(request, resultClass);
		} catch (CoverArtArchiveException ex) {
			Logger.getLogger(CoverArtArchiveClient.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	private <T> T getResult(HttpRequest request, Class<T> resultClass) throws CoverArtArchiveException {
		try {
			//wait until rate limiter allow it.
			int requestId = CoverArtArchiveRateLimiter.getRequestId();
			return getResult(requestId, request, resultClass);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

	private <T> T getResult(int requestId, HttpRequest request, Class<T> resultClass) throws InterruptedException, CoverArtArchiveException {
		try {
			HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String body = response.body();
			int statusCode = response.statusCode();
			if (testing) {
				lastBody = body;
			}
			if (statusCode >= 200 && statusCode < 300) {
				return GSON.fromJson(body, resultClass);
			} else {
				throw new CoverArtArchiveException(statusCode + ERROR);
			}
		} catch (IOException ex) {
			throw new CoverArtArchiveException(ERROR_REQUEST, ex);
		} finally {
			CoverArtArchiveRateLimiter.setRequestEnd(requestId);
		}
	}

	private String getRedirectLocation(int requestId, HttpRequest request) throws InterruptedException, CoverArtArchiveException {
		try {
			HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NEVER).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			int statusCode = response.statusCode();

			if (statusCode == 307 && response.headers().firstValue("location").isPresent()) {
				return response.headers().firstValue("location").orElse(null);
			} else if (statusCode == 404) {
				return null;
			} else {
				throw new CoverArtArchiveException(statusCode + ERROR);
			}
		} catch (IOException ex) {
			throw new CoverArtArchiveException(ERROR_REQUEST, ex);
		} finally {
			CoverArtArchiveRateLimiter.setRequestEnd(requestId);
		}
	}

	public String getRedirectLocation(String endpoint) {
		try {
			HttpRequest request = getBuilder(endpoint, null).GET().build();
			//wait until rate limiter allow it.
			int requestId = CoverArtArchiveRateLimiter.getRequestId();
			return getRedirectLocation(requestId, request);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			return null;
		} catch (CoverArtArchiveException ex) {
			Logger.getLogger(CoverArtArchiveClient.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
		return null;
	}

	private byte[] getJpegBytes(int requestId, HttpRequest request) throws InterruptedException, CoverArtArchiveException {
		try {
			HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
			HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
			int statusCode = response.statusCode();
			switch (statusCode) {
				case 200 -> {
					return response.body();
				}
				case 404 -> {
					return new byte[0];
				}
				default ->
					throw new CoverArtArchiveException(statusCode + ERROR);
			}
		} catch (IOException ex) {
			throw new CoverArtArchiveException(ERROR_REQUEST, ex);
		} finally {
			CoverArtArchiveRateLimiter.setRequestEnd(requestId);
		}
	}

	public byte[] getJpegBytes(String endpoint) {
		try {
			HttpRequest request = getBuilder(endpoint, null).GET()
					.setHeader("accept", "image/jpeg")
					.build();
			//wait until rate limiter allow it.
			int requestId = CoverArtArchiveRateLimiter.getRequestId();
			return getJpegBytes(requestId, request);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		} catch (CoverArtArchiveException ex) {
			Logger.getLogger(CoverArtArchiveClient.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
		return new byte[0];
	}

	private HttpRequest.Builder getBuilder(String endpoint, Map<String, String> query) throws CoverArtArchiveException {
		StringBuilder urlBuilder = new StringBuilder();
		if (query == null) {
			query = new HashMap<>();
		}
		for (Entry<String, String> param : query.entrySet()) {
			String value = param.getValue();
			if (value != null && value.trim().length() > 0) {
				urlBuilder.append(urlBuilder.isEmpty() ? "?" : "&");
				String key = param.getKey();
				value = URLEncoder.encode(value, StandardCharsets.UTF_8);
				urlBuilder.append(key).append("=").append(value);
			}
		}
		urlBuilder.insert(0, endpoint);
		URI contextURI;
		try {
			contextURI = new URI(baseUrl);
		} catch (URISyntaxException ex) {
			throw new CoverArtArchiveException("Base url '" + baseUrl + "' malformed", ex);
		}
		URI requestUri;
		try {
			requestUri = contextURI.resolve(urlBuilder.toString());
		} catch (IllegalArgumentException ex) {
			throw new CoverArtArchiveException("Request url malformed", ex);
		}
		HttpRequest.Builder builder = HttpRequest.newBuilder(requestUri)
				.setHeader("Content-Type", "application/json;charset=utf-8")
				.setHeader("accept", "application/json");

		if (userAgent != null) {
			builder.setHeader("userAgent", userAgent);
		}
		return builder;
	}

}
