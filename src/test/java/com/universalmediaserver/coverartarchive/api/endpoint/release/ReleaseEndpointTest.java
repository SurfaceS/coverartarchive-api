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
public class ReleaseEndpointTest extends BaseTestClass {

	static String RELEASE_ID = "76df3287-6cda-33eb-8e9a-044b5e15ffdd";
	static Long IMAGE_ID = 829521842L;

	public ReleaseEndpointTest() {
	}

	/**
	 * Test of getDetails method, of class ReleaseEndpoint.
	 */
	@Test
	public void testGetDetails() {
		System.out.println("ReleaseEndpoint getDetails");
		ResultSchema result = client
				.release(RELEASE_ID)
				.getDetails();
		assertParsedObject(result);
	}

	/**
	 * Test of getFrontImageLocation method, of class ReleaseEndpoint.
	 */
	@Test
	public void testGetFrontImageLocation() {
		System.out.println("ReleaseEndpoint getFrontImageLocation");
		String result = client
				.release(RELEASE_ID)
				.getFrontImageLocation();
		assertNotNull(result);
	}

	/**
	 * Test of getFrontImageLocation method, of class ReleaseEndpoint.
	 */
	@Test
	public void testGetFrontImageLocationWithSize() {
		System.out.println("ReleaseEndpoint getFrontImageLocation with size");
		String result = client
				.release(RELEASE_ID)
				.getFrontImageLocation(ThumbnailSize.ORIGINAL);
		assertNotNull(result);
	}

	/**
	 * Test of getFrontImageBytes method, of class ReleaseEndpoint.
	 */
	@Test
	public void testGetFrontImageDataWithSize() {
		System.out.println("ReleaseEndpoint getFrontImageBytes with size");
		byte[] result = client
				.release(RELEASE_ID)
				.getFrontImageBytes(ThumbnailSize.ORIGINAL);
		assertNotNull(result);
		assertTrue(result.length > 0);
	}

	/**
	 * Test of getBackImageLocation method, of class ReleaseEndpoint.
	 */
	@Test
	public void testGetBackImageLocation() {
		System.out.println("ReleaseEndpoint getBackImageLocation");
		String result = client
				.release(RELEASE_ID)
				.getBackImageLocation();
		assertNotNull(result);
	}

	/**
	 * Test of getBackImageLocation method, of class ReleaseEndpoint.
	 */
	@Test
	public void testGetBackImageLocationWithSize() {
		System.out.println("ReleaseEndpoint getBackImageLocation with size");
		String result = client
				.release(RELEASE_ID)
				.getBackImageLocation(ThumbnailSize.ORIGINAL);
		assertNotNull(result);
	}

	/**
	 * Test of getImageLocation method, of class ReleaseEndpoint.
	 */
	@Test
	public void testGetImageLocation() {
		System.out.println("ReleaseEndpoint getImageLocation");
		String result = client
				.release(RELEASE_ID)
				.getImageLocation(IMAGE_ID);
		assertNotNull(result);
	}

	/**
	 * Test of getImageLocation method, of class ReleaseEndpoint.
	 */
	@Test
	public void testGetImageLocationWithSize() {
		System.out.println("ReleaseEndpoint getImageLocation with size");
		String result = client
				.release(RELEASE_ID)
				.getImageLocation(IMAGE_ID, ThumbnailSize.SMALL);
		assertNotNull(result);
	}

	/**
	 * Test of getImageBytes method, of class ReleaseEndpoint.
	 */
	@Test
	public void testGetImageDataWithSize() {
		System.out.println("ReleaseEndpoint getImageBytes with size");
		byte[] result = client
				.release(RELEASE_ID)
				.getImageBytes(IMAGE_ID, ThumbnailSize.SMALL);
		assertNotNull(result);
		assertTrue(result.length > 0);
	}

}
