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
package com.universalmediaserver.coverartarchive.api.endpoint.release_group;

import com.universalmediaserver.coverartarchive.api.CoverArtArchiveClient;
import com.universalmediaserver.coverartarchive.api.endpoint.Endpoint;
import com.universalmediaserver.coverartarchive.api.endpoint.ThumbnailSize;
import com.universalmediaserver.coverartarchive.api.schema.release.ReleaseSchema;
import java.io.InputStream;

/**
 * Release Group endpoint.
 *
 * @author SurfaceS
 */
public class ReleaseGroupEndpoint extends Endpoint {

	protected static final String RELEASE_GROUP_ENDPOINT = "release-group/";

	private final String musicBrainzId;

	public ReleaseGroupEndpoint(CoverArtArchiveClient musicBrainzAPIClient, String musicBrainzId) {
		super(musicBrainzAPIClient);
		this.musicBrainzId = musicBrainzId;
	}

	/**
	 * Get release group details.
	 *
	 * @return Release group details.
	 */
	public ReleaseSchema getDetails() {
		return client.get(RELEASE_GROUP_ENDPOINT + musicBrainzId, ReleaseSchema.class, null);
	}

	/**
	 * Get the image location that is most suitable to be called the "front" of a release group (or one of its thumbnails).
	 *
	 * @return the image location.
	 */
	public String getFrontImageLocation() {
		return client.getRedirectLocation(RELEASE_GROUP_ENDPOINT + musicBrainzId + "/front");
	}

	/**
	 * Get the image location that is most suitable to be called the "front" of a release group (or one of its thumbnails).
	 *
	 * @return the image location.
	 */
	public String getFrontImageLocation(ThumbnailSize size) {
		return client.getRedirectLocation(RELEASE_GROUP_ENDPOINT + musicBrainzId + "/front-" + size);
	}

	/**
	 * Get the image data that is most suitable to be called the "front" of a release group (or one of its thumbnails).
	 *
	 * @return the image data.
	 */
	public InputStream getFrontImageData(ThumbnailSize size) {
		return client.getJpegStream(RELEASE_GROUP_ENDPOINT + musicBrainzId + "/front-" + size);
	}

}
