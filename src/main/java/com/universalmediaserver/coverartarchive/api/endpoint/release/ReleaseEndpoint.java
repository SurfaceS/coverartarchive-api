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
package com.universalmediaserver.coverartarchive.api.endpoint.release;

import com.universalmediaserver.coverartarchive.api.CoverArtArchiveClient;
import com.universalmediaserver.coverartarchive.api.endpoint.Endpoint;
import com.universalmediaserver.coverartarchive.api.endpoint.ThumbnailSize;
import com.universalmediaserver.coverartarchive.api.schema.ResultSchema;

/**
 * Release endpoint.
 *
 * @author SurfaceS
 */
public class ReleaseEndpoint extends Endpoint {

	protected static final String RELEASE_ENDPOINT = "release/";

	private final String musicBrainzId;

	public ReleaseEndpoint(CoverArtArchiveClient musicBrainzAPIClient, String musicBrainzId) {
		super(musicBrainzAPIClient);
		this.musicBrainzId = musicBrainzId;
	}

	/**
	 * Get release details.
	 *
	 * @return Release details.
	 */
	public ResultSchema getDetails() {
		return client.get(RELEASE_ENDPOINT + musicBrainzId, ResultSchema.class, null);
	}

	/**
	 * Get the image location that is most suitable to be called the "front" of the release.
	 *
	 * @return the image location.
	 */
	public String getFrontImageLocation() {
		return client.getRedirectLocation(RELEASE_ENDPOINT + musicBrainzId + "/front");
	}

	/**
	 * Get the image bytes that is most suitable to be called the "front" of the release.
	 *
	 * @return the image bytes.
	 */
	public byte[] getFrontImageBytes() {
		return client.getJpegBytes(RELEASE_ENDPOINT + musicBrainzId + "/front");
	}

	/**
	 * Get the image location that is most suitable to be called the "front" of the release.
	 *
	 * @return the image location.
	 */
	public String getFrontImageLocation(ThumbnailSize size) {
		return client.getRedirectLocation(RELEASE_ENDPOINT + musicBrainzId + "/front-" + size);
	}

	/**
	 * Get the image bytes that is most suitable to be called the "front" of the release.
	 *
	 * @return the image bytes.
	 */
	public byte[] getFrontImageBytes(ThumbnailSize size) {
		return client.getJpegBytes(RELEASE_ENDPOINT + musicBrainzId + "/front-" + size);
	}

	/**
	 * Get the image location that is most suitable to be called the "back" of the release.
	 *
	 * @return the image location.
	 */
	public String getBackImageLocation() {
		return client.getRedirectLocation(RELEASE_ENDPOINT + musicBrainzId + "/back");
	}

	/**
	 * Get the image bytes that is most suitable to be called the "back" of the release.
	 *
	 * @return the image bytes.
	 */
	public byte[] getBackImageBytes() {
		return client.getJpegBytes(RELEASE_ENDPOINT + musicBrainzId + "/back");
	}

	/**
	 * Get the image location that is most suitable to be called the "back" of the release.
	 *
	 * @return the image location.
	 */
	public String getBackImageLocation(ThumbnailSize size) {
		return client.getRedirectLocation(RELEASE_ENDPOINT + musicBrainzId + "/back-" + size);
	}

	/**
	 * Get the image bytes that is most suitable to be called the "back" of the release.
	 *
	 * @return the image bytes.
	 */
	public byte[] getBackImageBytes(ThumbnailSize size) {
		return client.getJpegBytes(RELEASE_ENDPOINT + musicBrainzId + "/back-" + size);
	}

	/**
	 * Get the image location for a specific piece of artwork of the release.
	 *
	 * @return the image location.
	 */
	public String getImageLocation(Long id) {
		return client.getRedirectLocation(RELEASE_ENDPOINT + musicBrainzId + "/" + id);
	}

	/**
	 * Get the image bytes for a specific piece of artwork of the release.
	 *
	 * @return the image bytes.
	 */
	public byte[] getImageBytes(Long id) {
		return client.getJpegBytes(RELEASE_ENDPOINT + musicBrainzId + "/" + id);
	}

	/**
	 * Get the image location for a specific piece of artwork of the release.
	 *
	 * @return the image location.
	 */
	public String getImageLocation(Long id, ThumbnailSize size) {
		return client.getRedirectLocation(RELEASE_ENDPOINT + musicBrainzId + "/" + id + "-" + size);
	}

	/**
	 * Get the image bytes for a specific piece of artwork of the release.
	 *
	 * @return the image bytes.
	 */
	public byte[] getImageBytes(Long id, ThumbnailSize size) {
		return client.getJpegBytes(RELEASE_ENDPOINT + musicBrainzId + "/" + id + "-" + size);
	}

}
