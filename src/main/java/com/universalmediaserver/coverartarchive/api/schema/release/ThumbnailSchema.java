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
package com.universalmediaserver.coverartarchive.api.schema.release;

import com.google.gson.annotations.SerializedName;

/**
 * Thumbnail Schema.
 *
 * @author SurfaceS
 */
public class ThumbnailSchema {

	@SerializedName("250")
	private String size250;
	@SerializedName("500")
	private String size500;
	@SerializedName("1200")
	private String size1200;
	@SerializedName("small")
	private String small;
	@SerializedName("large")
	private String large;

	public String get250() {
		return size250;
	}

	public void set250(String size250) {
		this.size250 = size250;
	}

	public String get500() {
		return size500;
	}

	public void set500(String size500) {
		this.size500 = size500;
	}

	public String get1200() {
		return size1200;
	}

	public void set1200(String size1200) {
		this.size1200 = size1200;
	}

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

}
