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
import java.util.List;

/**
 * Image Schema.
 *
 * @author SurfaceS
 */
public class ImageSchema {

	@SerializedName("id")
	private Long id;
	@SerializedName("image")
	private String image;
	@SerializedName("types")
	private List<String> types;
	@SerializedName("front")
	private Boolean front;
	@SerializedName("back")
	private Boolean back;
	@SerializedName("approved")
	private Boolean approved;
	@SerializedName("comment")
	private String comment;
	@SerializedName("edit")
	private Long edit;
	@SerializedName("thumbnails")
	private ThumbnailSchema thumbnails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public Boolean getFront() {
		return front;
	}

	public void setFront(Boolean front) {
		this.front = front;
	}

	public Boolean getBack() {
		return back;
	}

	public void setBack(Boolean back) {
		this.back = back;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getEdit() {
		return edit;
	}

	public void setEdit(Long edit) {
		this.edit = edit;
	}

	public ThumbnailSchema getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(ThumbnailSchema thumbnails) {
		this.thumbnails = thumbnails;
	}

}
