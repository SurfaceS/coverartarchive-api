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

import com.universalmediaserver.coverartarchive.api.BaseTestClass;
import com.universalmediaserver.coverartarchive.api.endpoint.ThumbnailSize;
import com.universalmediaserver.coverartarchive.api.schema.ResultSchema;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author SurfaceS
 */
public class ReleaseGroupEndpointTest extends BaseTestClass {

	static String RELEASE_GROUP_ID = "c31a5e2b-0bf8-32e0-8aeb-ef4ba9973932";

	public ReleaseGroupEndpointTest() {
	}

	/**
	 * Test of getDetails method, of class ReleaseGroupEndpoint.
	 */
	@Test
	public void testGetDetails() {
		System.out.println("ReleaseGroupEndpoint getDetails");
		ResultSchema result = client
				.releaseGroup(RELEASE_GROUP_ID)
				.getDetails();
		assertParsedObject(result);
	}

	/**
	 * Test of getFrontImageLocation method, of class ReleaseGroupEndpoint.
	 */
	@Test
	public void testGetFrontImageLocation() {
		System.out.println("ReleaseGroupEndpoint getFrontImageLocation");
		String result = client
				.releaseGroup(RELEASE_GROUP_ID)
				.getFrontImageLocation();
		assertNotNull(result);
	}

	/**
	 * Test of getFrontImageLocation method, of class ReleaseGroupEndpoint.
	 */
	@Test
	public void testGetFrontImageLocationWithSize() {
		System.out.println("ReleaseGroupEndpoint getFrontImageLocation with size");
		String result = client
				.releaseGroup(RELEASE_GROUP_ID)
				.getFrontImageLocation(ThumbnailSize.ORIGINAL);
		assertNotNull(result);
	}

	/**
	 * Test of getFrontImageBytes method, of class ReleaseGroupEndpoint.
	 */
	@Test
	public void testGetFrontImageDataWithSize() {
		System.out.println("ReleaseGroupEndpoint getFrontImageBytes with size");
		byte[] result = client
				.releaseGroup(RELEASE_GROUP_ID)
				.getFrontImageBytes(ThumbnailSize.LARGE);
		assertNotNull(result);
		assertTrue(result.length > 0);
	}

}
